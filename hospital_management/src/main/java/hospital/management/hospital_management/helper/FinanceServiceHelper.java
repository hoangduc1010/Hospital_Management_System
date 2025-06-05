package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.*;
import hospital.management.hospital_management.dto.request.FinanceRequest;
import hospital.management.hospital_management.dto.response.FinanceDetailResponse;
import hospital.management.hospital_management.dto.response.FinanceResponse;
import hospital.management.hospital_management.repository.MedicineRepository;
import hospital.management.hospital_management.repository.PatientMedicineRepository;
import hospital.management.hospital_management.repository.PatientRepository;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FinanceServiceHelper {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final MedicineRepository medicineRepository;
    private final PatientMedicineRepository patientMedicineRepository;
    public void checkValidInfoCreateFinance(FinanceRequest financeRequest) throws CustomException {
        if(financeRequest.getPatientId()==null){
            throw new CustomException("Id bệnh nhân không để trống");
        }
        PatientEntity currentPatient=this.patientRepository.findById(financeRequest.getPatientId()).get();
        if(currentPatient==null){
            throw new CustomException("Không tìm thấy bệnh nhân");
        }
        Boolean checkIsAllPayment=true;
        for(PatientMedicineEntity patientMedicine:currentPatient.getPrescriptions()){
            if(patientMedicine.getIsPayment()==false){
                checkIsAllPayment=false;
            }
        }
        if(checkIsAllPayment==true){
            throw new CustomException("Bệnh nhân "+currentPatient.getUser().getFullname() +" đã thanh toán tất cả");
        }
    }

    public List<FinanceDetailResponse> convertToListFinanceDetailResponse(PatientEntity patient){
        List<FinanceDetailResponse> financeDetailResponseList=new ArrayList<>();
        for(PatientMedicineEntity patientMedicine:patient.getPrescriptions()){
            if(patientMedicine.getIsPayment()==false){
                FinanceDetailResponse financeDetailResponse=new FinanceDetailResponse();
                financeDetailResponse.setMedicineName(patientMedicine.getMedicine().getMedicineName());
                financeDetailResponse.setMedicineQuantity(patientMedicine.getQuantity());
                financeDetailResponse.setSubtotal(patientMedicine.getQuantity() * patientMedicine.getMedicine().getSellPrice());
                financeDetailResponseList.add(financeDetailResponse);
                patientMedicine.setIsPayment(true);
                this.patientMedicineRepository.save(patientMedicine);
            }
        }
            return financeDetailResponseList;
    }
    public FinanceResponse convertToFinanceResponse(PatientEntity patient){
        FinanceResponse financeResponse=new FinanceResponse();
        Set<FinanceEntity> financeEntities=patient.getFinanceSet();
        for(FinanceEntity finance:financeEntities){
                financeResponse.setFinanceId(finance.getFinanceId());
                financeResponse.setTotalPrice(finance.getTotalCost());
                financeResponse.setPaymentType(finance.getPaymentType());
                financeResponse.setBillingDate(finance.getBillingDate());
                financeResponse.setPatientName(patient.getUser().getFullname());
        }
        List<FinanceDetailResponse> detailResponseList=this.convertToListFinanceDetailResponse(patient);
        financeResponse.setFinanceDetailResponse(detailResponseList);
        return financeResponse;
    }
}
