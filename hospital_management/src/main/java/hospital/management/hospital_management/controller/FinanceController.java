package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.service.FinanceService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/finances")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;

//    @PostMapping
//    @ApiMessage("Thanh toán viện phí")
//    @RoleAccess(allowedRoles = {RoleEnum.ACCOUNTANT,RoleEnum.RECEPTIONIST})
//    public ResponseEntity<>
}
