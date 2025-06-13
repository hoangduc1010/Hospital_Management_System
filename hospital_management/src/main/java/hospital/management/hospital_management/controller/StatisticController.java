package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.dto.response.DoctorNurseActiveResponse;
import hospital.management.hospital_management.dto.response.TotalCostIndayResponse;
import hospital.management.hospital_management.service.StatisticService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
@RoleAccess(allowedRoles = {RoleEnum.ADMIN})
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/nurse_doctor_active")
    @ApiMessage("Thống kê các bác sĩ và y tá đang làm việc")
    public ResponseEntity<List<DoctorNurseActiveResponse>> statisticDoctorNurseActive(){
        return ResponseEntity.ok().body(this.statisticService.getAllDoctorAndNurseAreActive());
    }

    @GetMapping("/total_cost_today")
    @ApiMessage("Thống kế tổng chi phí hôm nay")
    public ResponseEntity<TotalCostIndayResponse> statisticTotalCostInDay(){
        return ResponseEntity.ok().body(this.statisticService.getTotalCostInDay());
    }
}
