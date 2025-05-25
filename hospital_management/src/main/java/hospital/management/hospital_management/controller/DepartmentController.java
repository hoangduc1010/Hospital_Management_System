package hospital.management.hospital_management.controller;


import com.turkraft.springfilter.boot.Filter;
import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.dto.response.ResponsePaginationDTO;
import hospital.management.hospital_management.service.DepartmentService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    @GetMapping("/find/all")
    @ApiMessage("Lấy tất cả các khoa")
    @RoleAccess(allowedRoles = {RoleEnum.ADMIN,RoleEnum.DOCTOR,RoleEnum.NURSE,RoleEnum.RECEPTIONIST})
    public ResponseEntity<ResponsePaginationDTO> getAllDepartment(@Filter Specification<DepartmentEntity> specification,
                                                                  Pageable pageable){
        return ResponseEntity.ok().body(this.departmentService.getAllDepartment(specification,pageable));
    }
}
