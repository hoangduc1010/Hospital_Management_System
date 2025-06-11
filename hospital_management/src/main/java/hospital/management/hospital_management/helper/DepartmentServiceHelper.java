package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.dto.response.DepartmentResponse;
import hospital.management.hospital_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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
        if(departmentEntity.getDoctors()!=null){
            departmentResponse.setNumberOfDoctors(departmentEntity.getDoctors().size());
        }
        if(departmentEntity.getNurses()!=null){
            departmentResponse.setNumberOfNurse(departmentEntity.getNurses().size());
        }
        if(departmentEntity.getPatients()!=null){
            departmentResponse.setNumberOfPatient(departmentEntity.getPatients().size());
        }
        if(departmentEntity.getDepartmentHead()!=null){
            departmentResponse.setDepartmentHeadName(departmentEntity.getDepartmentHead().getUser().getFullname());
        }
        return departmentResponse;
    }
}
