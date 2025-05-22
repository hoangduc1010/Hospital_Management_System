package hospital.management.hospital_management.service;

import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.DoctorEntity;
import hospital.management.hospital_management.domain.NurseEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    public void createDoctor(UserRequest userInfo, UserEntity userEntity) throws CustomException{
        if(userInfo.getDepartmentId()==null){
            throw new CustomException("Khoa trực không được để trống");
        }
        if(userInfo.getDoctorDiploma()==null){
            throw new CustomException("Bằng cấp không để trống");
        }
        Set<DepartmentEntity> departments=new HashSet<>();
        DoctorEntity doctor=new DoctorEntity();
        if(userInfo.getUserId()!=null && userEntity.getDoctor()!=null){
            doctor=this.doctorRepository.findById(userEntity.getDoctor().getId()).get();
        }
        doctor.setDepartments(departments);
        doctor.setUser(userEntity);
        doctor.setDoctorDiploma(userInfo.getDoctorDiploma());
        doctor.setDepartments(departments);
        doctor.setYearOfExperience(userInfo.getYearOfExperience());
        DoctorEntity savedDoctor=this.doctorRepository.save(doctor);
        for(Long departmentId:userInfo.getDepartmentId()){
            DepartmentEntity departmentEntity=this.departmentRepository.findById(departmentId).get();
            departmentEntity.getDoctors().add(savedDoctor);
            savedDoctor.getDepartments().add(departmentEntity);
            this.doctorRepository.save(savedDoctor);
        }
        userEntity.setDoctor(doctor);
        this.userRepository.save(userEntity);

    }


}
