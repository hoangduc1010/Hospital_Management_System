package hospital.management.hospital_management.service;

import hospital.management.hospital_management.domain.FinanceMedicineEntity;
import hospital.management.hospital_management.domain.FinancePatientEntity;
import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.dto.request.TotalCostFromDateToRequest;
import hospital.management.hospital_management.dto.response.*;
import hospital.management.hospital_management.helper.StatisticServiceHelper;
import hospital.management.hospital_management.repository.FinanceMedicineRepository;
import hospital.management.hospital_management.repository.FinanceRepository;
import hospital.management.hospital_management.repository.PatientRepository;
import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import hospital.management.hospital_management.util.error.CustomException;
import hospital.management.hospital_management.util.format.FormatStringDateToInstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticServiceHelper statisticServiceHelper;
    private final FinanceRepository financeRepository;
    private final FormatStringDateToInstant formatStringDateToInstant;
    private final PatientRepository patientRepository;
    private final FinanceMedicineRepository financeMedicineRepository;
    public List<DoctorNurseActiveResponse> getAllDoctorAndNurseAreActive(){
        return this.statisticServiceHelper.convertToDoctorNurseResponse();
    }
    public TotalCostIndayResponse getTotalCostInDay(){
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate today = LocalDate.now(zone);

        Instant startOfDay = today.atStartOfDay(zone).toInstant();
        Instant endOfDay = today.plusDays(1).atStartOfDay(zone).toInstant();

        List<FinancePatientEntity> financePatientInDay=this.financeRepository.findByBillingDateBetween(startOfDay,endOfDay);
        List<FinanceMedicineEntity> financeMedicineInDay=this.financeMedicineRepository.findByBuyInDateBetween(startOfDay,endOfDay);
        return this.statisticServiceHelper.convertToTotalCostInDayResponse(financePatientInDay,financeMedicineInDay);
    }

    public TotalCostFromDateToResponse getTotalCostFromDateTo(TotalCostFromDateToRequest totalCostFromDateToRequest) throws CustomException {
        if (totalCostFromDateToRequest.getStartDate() == null && totalCostFromDateToRequest.getEndDate() == null) {
            throw new CustomException("Ngày bắt đầu hoặc ngày kết thúc không được bỏ trống");
        }

        // start date not null, end date null
        if (totalCostFromDateToRequest.getStartDate() != null && totalCostFromDateToRequest.getEndDate() == null) {


            //start end end are get the same from start;
            Instant start = this.formatStringDateToInstant.getStartOfDay(totalCostFromDateToRequest.getStartDate());
            Instant end = this.formatStringDateToInstant.getEndOfDay(totalCostFromDateToRequest.getStartDate());

            List<FinancePatientEntity> financePatientInDay = this.financeRepository.findByBillingDateBetween(start, end);
            List<FinanceMedicineEntity> financeMedicineInDay= this.financeMedicineRepository.findByBuyInDateBetween(start,end);
            return this.statisticServiceHelper.convertToTotalCostFromDateToResponse(financePatientInDay, financeMedicineInDay,totalCostFromDateToRequest);
        }


        //start date null , end date not null
        if (totalCostFromDateToRequest.getEndDate() != null && totalCostFromDateToRequest.getStartDate() == null) {

            // start and end are get the same from end date;
            Instant start = this.formatStringDateToInstant.getStartOfDay(totalCostFromDateToRequest.getEndDate());
            Instant end = this.formatStringDateToInstant.getEndOfDay(totalCostFromDateToRequest.getEndDate());
            List<FinancePatientEntity> financeInDay = this.financeRepository.findByBillingDateBetween(start, end);
            List<FinanceMedicineEntity> financeMedicineInDay= this.financeMedicineRepository.findByBuyInDateBetween(start,end);

            return this.statisticServiceHelper.convertToTotalCostFromDateToResponse(financeInDay, financeMedicineInDay,totalCostFromDateToRequest);
        }


        // start date and end date not null
        Instant startDate = this.formatStringDateToInstant.getStartOfDay(totalCostFromDateToRequest.getStartDate());
        Instant endDate = this.formatStringDateToInstant.getEndOfDay(totalCostFromDateToRequest.getEndDate());
        List<FinancePatientEntity> financePatientInDay = this.financeRepository.findByBillingDateBetween(startDate, endDate);
        List<FinanceMedicineEntity> financeMedicineInDay= this.financeMedicineRepository.findByBuyInDateBetween(startDate,endDate);

        return this.statisticServiceHelper.convertToTotalCostFromDateToResponse(financePatientInDay,financeMedicineInDay, totalCostFromDateToRequest);


    }

    public TotalPatientInDayResponse getAllPatientsInDate() {
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate today = LocalDate.now(zone);
        Instant startOfDay = today.atStartOfDay(zone).toInstant();
        Instant endOfDay = today.plusDays(1).atStartOfDay(zone).toInstant();
        List<PatientStatusEnum> statuses = Arrays.asList(
                PatientStatusEnum.ADMITTED,
                PatientStatusEnum.UNDER_TREATMENT,
                PatientStatusEnum.OUTPATIENT
        );

        List<PatientEntity> patients = patientRepository
                .findByDateOfAppointmentBetweenAndPatientStatusIn(startOfDay, endOfDay, statuses);
        int totalPatients = patients.size();
        Map<String, Long> patientByDepartment = patients.stream()
                .filter(p -> p.getCurrentDepartment() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getCurrentDepartment().getDepartmentName(),
                        Collectors.counting()
                ));

        List<PatientDepartmentInDayResponse> departmentResponses = patientByDepartment.entrySet().stream()
                .map(entry -> {
                    PatientDepartmentInDayResponse patientDepartmentInDayResponse = new PatientDepartmentInDayResponse();
                    patientDepartmentInDayResponse.setDepartmentName(entry.getKey());
                    patientDepartmentInDayResponse.setNumberOfPatient(entry.getValue().intValue());
                    return patientDepartmentInDayResponse;
                })
                .collect(Collectors.toList());
        TotalPatientInDayResponse response = new TotalPatientInDayResponse();
        TotalPatientInDayResponse.TotalPatientInDate total = new TotalPatientInDayResponse.TotalPatientInDate();
        total.setDate(today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        total.setTotalPatients(totalPatients);
        response.setTotalPatientInDate(total);
        response.setDepartmentOfPatient(departmentResponses);

        return response;
    }

}
