package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.MedicineEntity;
import hospital.management.hospital_management.dto.request.MedicineRequest;
import hospital.management.hospital_management.dto.response.MedicineResponse;
import hospital.management.hospital_management.dto.response.ResponsePaginationDTO;
import hospital.management.hospital_management.helper.MedicineServiceHelper;
import hospital.management.hospital_management.helper.PaginationHelper;
import hospital.management.hospital_management.repository.MedicineRepository;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;
    private final MedicineServiceHelper medicineServiceHelper;
    private final PaginationHelper paginationHelper;
    public MedicineResponse saveMedicine(MedicineRequest medicineRequest) throws CustomException {
        this.medicineServiceHelper.checkValidInfoSaveMedicine(medicineRequest);
        MedicineEntity currentMedicine=new MedicineEntity();
        if(medicineRequest.getId()!=null){
            currentMedicine=this.medicineRepository.findById(medicineRequest.getId()).get();
        }
        MedicineResponse medicineResponse=new MedicineResponse();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(medicineRequest,currentMedicine);
        this.medicineRepository.save(currentMedicine);
        modelMapper.map(currentMedicine,medicineResponse);
        return medicineResponse;
    }
    public MedicineResponse deleteMedicine(Long medicineId) throws CustomException{
        this.medicineServiceHelper.checkValidInfoDeleteMedicine(medicineId);
        MedicineEntity currentMedicine=this.medicineRepository.findById(medicineId).get();
        if(currentMedicine.getIsActive()==null){
            currentMedicine.setIsActive(true);
        }else if(currentMedicine.getIsActive()==true){
            currentMedicine.setIsActive(false);
        }else{
            currentMedicine.setIsActive(true);
        }

        this.medicineRepository.save(currentMedicine);
        MedicineResponse medicineResponse=new MedicineResponse();
        modelMapper.map(currentMedicine,medicineResponse);
        return medicineResponse;
    }

    public <T> ResponsePaginationDTO<T> getAllMedicines(Specification specification, Pageable pageable) {
        Page<T> medicinePage = this.medicineRepository.findAll(specification, pageable);
        List<MedicineEntity> medicines= (List<MedicineEntity>) medicinePage.getContent();
        List<MedicineResponse> medicineResponses=new ArrayList<>();
        for(MedicineEntity medicine:medicines){
            MedicineResponse medicineResponse=new MedicineResponse();
            modelMapper.map(medicine,medicineResponse);
            medicineResponses.add(medicineResponse);
        }
        return this.paginationHelper.getAllPagination(medicinePage, (List<T>) medicineResponses, pageable);
    }
    public MedicineResponse findMedicineByMedicineName(String medicineName) throws CustomException{
        MedicineEntity currentMedicine=this.medicineRepository.findByMedicineName(medicineName);
        if(currentMedicine.getIsActive()==false || currentMedicine==null || currentMedicine.getIsActive()==null){
            throw new CustomException("Thuốc không tồn tại");
        }
        MedicineResponse medicineResponse=new MedicineResponse();
        modelMapper.map(currentMedicine,medicineResponse);
        return medicineResponse;
    }


}
