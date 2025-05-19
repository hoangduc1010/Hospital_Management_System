package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.DoctorEntity;
import hospital.management.hospital_management.domain.NurseEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.NurseRepository;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NurseService {
    private final NurseRepository nurseRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    public void createNurse(UserRequest userInfo, UserEntity userEntity) throws CustomException {
        if(userInfo.getDepartmentId()==null){
            throw new CustomException("Khoa trực không được để trống");
        }

        if(userInfo.getNurseDiploma()==null){
            throw new CustomException("Bằng cấp không để trống");
        }
        Set<DepartmentEntity> departments=new HashSet<>();
        NurseEntity nurse=new NurseEntity();
        nurse.setDepartments(departments);
        nurse.setUser(userEntity);
        nurse.setNurseDiploma(userInfo.getNurseDiploma());
        nurse.setDepartments(departments);
        nurse.setYearOfExperience(userInfo.getYearOfExperience());
        NurseEntity savedNurse=this.nurseRepository.save(nurse);
        for(Long departmentId:userInfo.getDepartmentId()){
            DepartmentEntity departmentEntity=this.departmentRepository.findById(departmentId).get();
            departmentEntity.getNurses().add(savedNurse);
            savedNurse.getDepartments().add(departmentEntity);
            this.nurseRepository.save(savedNurse);
        }
        userEntity.setNurse(nurse);
        this.userRepository.save(userEntity);

    }
}
