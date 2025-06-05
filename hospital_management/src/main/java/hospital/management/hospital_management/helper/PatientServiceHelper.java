package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.*;
import hospital.management.hospital_management.dto.request.MedicineRequest;
import hospital.management.hospital_management.dto.request.PatientRequest;
import hospital.management.hospital_management.dto.request.PrescribeMedicationRequest;
import hospital.management.hospital_management.dto.response.MedicalRecordResponse;
import hospital.management.hospital_management.dto.response.MedicineResponse;
import hospital.management.hospital_management.dto.response.PatienResponse;
import hospital.management.hospital_management.dto.response.PrescribeMedicationResponse;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.MedicineRepository;
import hospital.management.hospital_management.repository.PatientRepository;
import hospital.management.hospital_management.repository.RoomRepository;
import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class PatientServiceHelper {
    private final RoomRepository roomRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final MedicineRepository medicineRepository;
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
        if(patient.getCurrentDepartment()!=null){
            patienResponse.setDepartmentName(patient.getCurrentDepartment().getDepartmentName());
        }
        if(patient.getRoom()!=null){
            patienResponse.setRoomNumber(patient.getRoom().getRoomNumber());
        }
        if(patient.getMedicalRecord()!=null){
            MedicalRecordEntity currentMedicalRecord=patient.getMedicalRecord();
            MedicalRecordResponse medicalRecordResponse=new MedicalRecordResponse();
            medicalRecordResponse.setId(currentMedicalRecord.getId());
            medicalRecordResponse.setAllergies(currentMedicalRecord.getAllergies());
            medicalRecordResponse.setMedicalHistory(currentMedicalRecord.getMedicalHistory());
            medicalRecordResponse.setDiagnosis(currentMedicalRecord.getDiagnosis());
            medicalRecordResponse.setSymptoms(currentMedicalRecord.getSymptoms());
            medicalRecordResponse.setDoctorNote(currentMedicalRecord.getDoctorNote());
            medicalRecordResponse.setTreatmentPlan(currentMedicalRecord.getTreatmentPlan());
            patienResponse.setMedicalRecord(medicalRecordResponse);
        }

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

    public void checkValidInforUpdate(PatientRequest patientRequest) throws CustomException{
        if(patientRequest.getPatientId()==null){
            if(patientRequest.getPatientStatus()!=PatientStatusEnum.UNDER_TREATMENT){
                throw new CustomException("Bệnh nhân chưa có hồ sơ cần có trạng thái đang điều trị");
            }
        }else{
            return;
        }
        PatientEntity currentPatient=new PatientEntity();
        if(patientRequest.getPatientId()!=null){
            currentPatient=this.patientRepository.findById(patientRequest.getPatientId()).get();
            if(patientRequest.getPatientId()==null || currentPatient==null){
                throw new CustomException("Người dùng không tồn tại");
            }
        }
        if(patientRequest.getRoomId()!=null && currentPatient.getCurrentDepartment()!=null){
            RoomEntity currentRoom=this.roomRepository.findById(patientRequest.getRoomId()).get();
            if(currentRoom==null){
                throw new CustomException("Số phòng không tồn tại ");
            }
            if(currentRoom.getIsActive()==false){
                throw new CustomException("Phòng khám "+currentRoom.getRoomNumber()+" đang dừng hoạt động");
            }
            if(currentRoom.getNumberOfBeds() <=0){
                throw new CustomException("Số lượng giường của phòng "+currentRoom.getRoomNumber()+" đã hết");
            }
            DepartmentEntity currentDepartment=this.departmentRepository.findById(patientRequest.getDepartmentId()).get();
            if(currentDepartment==null || currentDepartment.getIsActive()==false){
                throw new CustomException("Khoa không tồn tại");
            }

            if(!currentDepartment.getRooms().contains(currentRoom)){
                throw new CustomException("Khoa "+currentDepartment.getDepartmentName() + " không tồn tại phòng "+currentRoom.getRoomNumber());
            }
        }



    }

    public void checkValidInfoPrescribeMedication(PrescribeMedicationRequest prescribeMedicationRequest) throws CustomException{
        if(prescribeMedicationRequest.getPatientId()==null){
            throw new CustomException("Id bệnh nhân không để trống");
        }
        if(prescribeMedicationRequest.getMedicines()==null){
            throw new CustomException("Danh sách thuốc không để trống");
        }
        for(MedicineRequest medicine:prescribeMedicationRequest.getMedicines()){
            MedicineEntity currentMedicine=this.medicineRepository.findByMedicineName(medicine.getMedicineName());
            if(currentMedicine==null || currentMedicine.getIsActive() ==false){
                throw new CustomException("Thuốc "+medicine.getMedicineName()+ " không tồn tại");
            }
            if(currentMedicine.getQuantityInStock()<=0){
                throw new CustomException("Thuốc "+currentMedicine.getMedicineName()+" đã hết");
            }
        }
    }
    public PrescribeMedicationResponse convertToPrescribeMedicationResponse(PrescribeMedicationRequest prescribeMedicationRequest){
       PrescribeMedicationResponse prescribeMedicationResponse=new PrescribeMedicationResponse();
       PatientEntity currentPatient=this.patientRepository.findById(prescribeMedicationRequest.getPatientId()).get();
       prescribeMedicationResponse.setPatientId(currentPatient.getId());
       prescribeMedicationResponse.setPatientName(currentPatient.getUser().getFullname());
        List<Map<String, Object>> medicines = new ArrayList<>();
        for (MedicineRequest req : prescribeMedicationRequest.getMedicines()) {
            Map<String, Object> medicineData = new HashMap<>();
            medicineData.put("medicineName", req.getMedicineName());
            medicineData.put("quantity", req.getQuantity());
            medicines.add(medicineData);
        }
        prescribeMedicationResponse.setMedicines(medicines);
        return prescribeMedicationResponse;
    }
}
