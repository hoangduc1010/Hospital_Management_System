package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.dto.request.FinanceRequest;
import hospital.management.hospital_management.dto.response.FinanceResponse;
import hospital.management.hospital_management.service.FinanceService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/finances")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;

    @PostMapping("/payment")
    @ApiMessage("Thanh toán viện phí")
    @RoleAccess(allowedRoles = {RoleEnum.ACCOUNTANT,RoleEnum.RECEPTIONIST})
    public ResponseEntity<FinanceResponse> payment(@RequestBody FinanceRequest financeRequest) throws CustomException {
        return ResponseEntity.ok().body(this.financeService.payment(financeRequest));
    }
}
