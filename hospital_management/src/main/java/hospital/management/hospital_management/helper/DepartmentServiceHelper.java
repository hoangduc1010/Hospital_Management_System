package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.dto.response.DepartmentResponse;
import hospital.management.hospital_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentServiceHelper {
    private final DepartmentRepository departmentRepository;

    public DepartmentResponse convertToDepartmentResponse(DepartmentEntity departmentEntity){
        DepartmentResponse departmentResponse=new DepartmentResponse();
        departmentResponse.setId(departmentEntity.getId());
        departmentResponse.setDepartmentName(departmentEntity.getDepartmentName());
        departmentResponse.setDescription(departmentEntity.getDescription());
        departmentResponse.setNumberOfDoctors(departmentEntity.getDoctors().size());
        departmentResponse.setNumberOfNurse(departmentEntity.getNurses().size());
        departmentResponse.setNumberOfPatient(departmentEntity.getPatients().size());
        return departmentResponse;
    }
}
