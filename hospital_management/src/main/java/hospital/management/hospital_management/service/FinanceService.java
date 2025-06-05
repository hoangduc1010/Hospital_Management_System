package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.*;
import hospital.management.hospital_management.dto.request.FinanceRequest;
import hospital.management.hospital_management.dto.response.FinanceResponse;
import hospital.management.hospital_management.helper.FinanceServiceHelper;
import hospital.management.hospital_management.repository.*;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final MedicineRepository medicineRepository;
    private final FinanceRepository financeRepository;
    private final FinanceDetailRepository financeDetailRepository;
    private final FinanceServiceHelper financeServiceHelper;
    private final PatientRepository patientRepository;
    private final PatientMedicineRepository patientMedicineRepository;

    @Transactional
    public FinanceResponse payment(FinanceRequest financeRequest) throws CustomException {


        this.financeServiceHelper.checkValidInfoCreateFinance(financeRequest);
        FinanceEntity currentFinanceEntity=new FinanceEntity();
        PatientEntity currentPatient=this.patientRepository.findById(financeRequest.getPatientId()).get();
        currentFinanceEntity.setPatient(currentPatient);
        FinanceDetailEntity currentFinanceDetailEntity=new FinanceDetailEntity();
        Set<MedicineEntity> medicineEntitySet=new HashSet<>();



        currentFinanceEntity.setPaymentType(financeRequest.getPaymentType());
        currentFinanceEntity.setInsuranceCoverage(financeRequest.getInsuranceCoverage());
        currentFinanceDetailEntity.setAppointmentsType(currentPatient.getAppointmentsType());

        Double totalCost=0.0;
        totalCost=totalCost+currentPatient.getAppointmentsType().getPrice()+financeRequest.getOtherCost();
        Set<PatientMedicineEntity> currentPatientMedicineEntity=this.patientMedicineRepository.findByPatient_IdAndIsPayment(currentPatient.getId(),false);

        for(PatientMedicineEntity patientMedicineEntity:currentPatientMedicineEntity){
            if(patientMedicineEntity.getIsPayment()==false){
                MedicineEntity currentMedicine=patientMedicineEntity.getMedicine();
                totalCost=totalCost+(currentMedicine.getSellPrice()*patientMedicineEntity.getQuantity());
                medicineEntitySet.add(patientMedicineEntity.getMedicine());
                patientMedicineEntity.setIsPayment(false);
                this.patientMedicineRepository.save(patientMedicineEntity);
            }
        }
        totalCost=totalCost-(totalCost * financeRequest.getInsuranceCoverage() / 100);
        currentFinanceEntity.setTotalCost(totalCost);
        currentFinanceEntity.setIsPayment(true);
        this.financeRepository.save(currentFinanceEntity);
        this.patientRepository.save(currentPatient);
        currentFinanceDetailEntity.setMedicineSet(medicineEntitySet);
        currentFinanceDetailEntity.setFinance(currentFinanceEntity);
        this.financeDetailRepository.save(currentFinanceDetailEntity);
        FinanceResponse financeResponse=this.financeServiceHelper.convertToFinanceResponse(currentPatient);
        return financeResponse;
    }
}
