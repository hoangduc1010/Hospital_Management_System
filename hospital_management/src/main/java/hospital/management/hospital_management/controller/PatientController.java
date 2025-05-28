package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.dto.request.PatientRequest;
import hospital.management.hospital_management.dto.response.PatienResponse;
import hospital.management.hospital_management.service.PatientService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/appointments")
    @ApiMessage("Đặt lịch khám")
    @RoleAccess(allowedRoles = {RoleEnum.USER})
    public ResponseEntity<PatienResponse> appointments(@RequestBody PatientRequest patientRequest) throws CustomException {
        return ResponseEntity.ok().body(this.patientService.takeAppointments(patientRequest));
    }

    @DeleteMapping("/cancel_appointments/{userId}")
    @ApiMessage("Huỷ lịch khám")
    @RoleAccess(allowedRoles = {RoleEnum.PATIENT})
    public ResponseEntity<Void> cancelAppointments(@PathVariable Long userId) throws CustomException{
        this.patientService.cancelAppointments(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/appointments/status")
    @ApiMessage("Thay đổi trạng thái bệnh nhân")
    @RoleAccess(allowedRoles = {RoleEnum.RECEPTIONIST,RoleEnum.DOCTOR,RoleEnum.NURSE})
    public ResponseEntity<Void> changeStatusOfPatient(@RequestBody PatientRequest patientRequest) throws CustomException {
        this.patientService.changeStatusOfPatient(patientRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @ApiMessage("Cập nhật thông tin bệnh nhân")
    @RoleAccess(allowedRoles = {RoleEnum.DOCTOR,RoleEnum.NURSE})
    public ResponseEntity<PatienResponse> updatePatient(@RequestBody PatientRequest patientRequest) throws CustomException {
        return ResponseEntity.ok().body(this.patientService.updatePatient(patientRequest));
    }



}
