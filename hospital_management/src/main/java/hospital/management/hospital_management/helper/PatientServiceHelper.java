package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.PatientRequest;
import hospital.management.hospital_management.dto.response.PatienResponse;
import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import hospital.management.hospital_management.util.error.CustomException;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PatientServiceHelper {

    public void checkValidAppointments(PatientRequest patientRequest) throws CustomException {
        if(patientRequest.getDateOfAppointment()==null){
            throw new CustomException("Ngày khám không được để trống");
        }
        if(patientRequest.getAppointmentsType()==null){
            throw new CustomException("Loại khám không được để trống");
        }
    }
    public PatienResponse convertToPatientResponse(PatientEntity patient){
        PatienResponse patienResponse=new PatienResponse();
        patienResponse.setPatientId(patient.getId());
        patienResponse.setFullname(patient.getUser().getFullname());
        patienResponse.setDob(patient.getUser().getDob());
        patienResponse.setPhoneNumber(patient.getUser().getPhoneNumber());
        patienResponse.setDateOfAppointment(patient.getDateOfAppointment());
        patienResponse.setAppointmentsType(patient.getAppointmentsType());
        return patienResponse;
    }
    public Instant convertAppointmentDateToInstant(String dateOfAppointment){
        String dateString = dateOfAppointment;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        return instant;
    }
    public void checkValidCancelAppointments(UserEntity currentUser) throws CustomException{
        if(currentUser==null){
            throw new CustomException("Tài khoản không tồn tại");
        }
        if(currentUser.getPatient()!=null && !currentUser.getPatient().getPatientStatus().equals(PatientStatusEnum.WAITING)){
            throw new CustomException("Trạng thái hiện tại không thể huỷ lịch khám");
        }
        if(currentUser.getPatient()==null){
            throw new CustomException("Bạn chưa đặt lịch khám");
        }
    }
}
