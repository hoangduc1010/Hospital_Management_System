package hospital.management.hospital_management.controller;


import com.turkraft.springfilter.boot.Filter;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.MedicineRequest;
import hospital.management.hospital_management.dto.response.MedicineResponse;
import hospital.management.hospital_management.dto.response.ResponsePaginationDTO;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.service.MedicineService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medicines")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;

    @PostMapping
    @ApiMessage("Thêm mới thuốc")
    @RoleAccess(allowedRoles = {RoleEnum.ADMIN})
    public ResponseEntity<MedicineResponse> createMedicine(@RequestBody MedicineRequest medicineRequest) throws CustomException {
        return ResponseEntity.ok().body(this.medicineService.saveMedicine(medicineRequest));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Xoá thuốc")
    @RoleAccess(allowedRoles = {RoleEnum.ADMIN})
    public ResponseEntity<MedicineResponse> deleteMedicine(@PathVariable Long id) throws CustomException{
        return ResponseEntity.ok().body(this.medicineService.deleteMedicine(id));
    }

    @GetMapping("/find/all")
    @ApiMessage("Lấy tất cả thuốc")
    @RoleAccess(allowedRoles = {RoleEnum.DOCTOR,RoleEnum.ADMIN,RoleEnum.NURSE,RoleEnum.RECEPTIONIST})
    public ResponseEntity<ResponsePaginationDTO> getAllMedicines(@Filter Specification<UserEntity> specification,
                                                                 Pageable pageable){
        return ResponseEntity.ok().body(this.medicineService.getAllMedicines(specification,pageable));
    }
    @GetMapping("/find/{medicineName}")
    @ApiMessage("Lấy người dùng theo username")
    @RoleAccess(allowedRoles = {RoleEnum.DOCTOR,RoleEnum.ADMIN,RoleEnum.NURSE,RoleEnum.RECEPTIONIST})
    public ResponseEntity<MedicineResponse> getMedicineByMedicineNAme(@PathVariable String medicineName) throws CustomException {
        return ResponseEntity.ok().body(this.medicineService.findMedicineByMedicineName(medicineName));
    }


}
