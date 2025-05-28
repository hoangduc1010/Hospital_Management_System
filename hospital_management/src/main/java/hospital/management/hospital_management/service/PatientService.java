package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.*;
import hospital.management.hospital_management.dto.request.PatientRequest;
import hospital.management.hospital_management.dto.response.PatienResponse;
import hospital.management.hospital_management.helper.PatientServiceHelper;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.PatientRepository;
import hospital.management.hospital_management.repository.RoomRepository;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientServiceHelper patientServiceHelper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final DepartmentRepository departmentRepository;
    private final RoomRepository roomRepository;
    private final MedicalRecordService medicalRecordService;
    public PatienResponse takeAppointments(PatientRequest patientRequest) throws CustomException {
        String username=SecurityUtil.getEmailOfCurrentUser();
        UserEntity currentUser=this.userRepository.findByUsername(username);
        if(currentUser==null){
            throw new CustomException("Tài khoản không tồn tại");
        }
        if(currentUser.getPatient()!=null && currentUser.getPatient().getPatientStatus().equals(PatientStatusEnum.WAITING)){
            throw new CustomException("Bạn đã tồn tại 1 lịch khám chưa được thực hiện");
        }
        RoleEnum roleEnum = RoleEnum.valueOf("PATIENT");
        currentUser.setRole(this.roleService.findByRoleName(roleEnum));
        this.patientServiceHelper.checkValidAppointments(patientRequest);
        PatientEntity patient=new PatientEntity();
        patient.setUser(currentUser);
        patient.setPatientStatus(PatientStatusEnum.WAITING);
        Instant dateOfAppointments=this.patientServiceHelper.convertAppointmentDateToInstant(patientRequest.getDateOfAppointment());
        patient.setDateOfAppointment(dateOfAppointments);
        patient.setAppointmentsType(patientRequest.getAppointmentsType());
        this.patientRepository.save(patient);
        this.userRepository.save(currentUser);
        PatienResponse patienResponse=this.patientServiceHelper.convertToPatientResponse(patient);
        return patienResponse;

    }
    public void cancelAppointments(Long userId) throws CustomException{
        UserEntity currentUser = this.userRepository.findById(userId).get();
        this.patientServiceHelper.checkValidCancelAppointments(currentUser);

        PatientEntity oldPatient = currentUser.getPatient();

        if (oldPatient != null && oldPatient.getPatientStatus().equals(PatientStatusEnum.WAITING)) {
            currentUser.setPatient(null);
            oldPatient.setUser(null);
            this.userRepository.save(currentUser);
            this.patientRepository.delete(oldPatient);
        }



    }

    public void changeStatusOfPatient(PatientRequest patientRequest) throws CustomException{
        PatientEntity currentPatient=this.patientRepository.findById(patientRequest.getPatientId()).get();
        if(currentPatient==null){
            throw new CustomException("Không tìm thấy bệnh nhân");
        }
        currentPatient.setPatientStatus(patientRequest.getPatientStatus());
        this.patientRepository.save(currentPatient);
    }

    public PatienResponse updatePatient(PatientRequest patientRequest) throws CustomException {
        this.patientServiceHelper.checkValidInforUpdate(patientRequest);
        PatientEntity currentPatient=this.patientRepository.findById(patientRequest.getPatientId()).get();
        DepartmentEntity currentDepartment=this.departmentRepository.findById(patientRequest.getDepartmentId()).get();
        RoomEntity currentRoom=this.roomRepository.findById(patientRequest.getRoomId()).get();
        currentPatient.setPatientStatus(patientRequest.getPatientStatus());
        currentPatient.setRoom(currentRoom);
        currentPatient.setCurrentDepartment(currentDepartment);
        MedicalRecordEntity currentMedical=this.medicalRecordService.saveMedicalRecordWithPatient(patientRequest);
        currentPatient.setMedicalRecord(currentMedical);
        this.patientRepository.save(currentPatient);
        PatienResponse patienResponse=this.patientServiceHelper.convertToPatientResponse(currentPatient);
        return patienResponse;
    }
}
