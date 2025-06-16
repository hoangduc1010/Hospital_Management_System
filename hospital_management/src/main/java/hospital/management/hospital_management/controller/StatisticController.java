package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.dto.request.TotalCostFromDateToRequest;
import hospital.management.hospital_management.dto.response.DoctorNurseActiveResponse;
import hospital.management.hospital_management.dto.response.TotalCostFromDateToResponse;
import hospital.management.hospital_management.dto.response.TotalCostIndayResponse;
import hospital.management.hospital_management.dto.response.TotalPatientInDayResponse;
import hospital.management.hospital_management.service.StatisticService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.annotation.RoleAccess;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/total_cost_from_to")
    @ApiMessage("Thống kê tổng chi phí trong khoảng ngày")
    public ResponseEntity<TotalCostFromDateToResponse> statisticTotalCostFromDateTo(@RequestBody TotalCostFromDateToRequest totalCostFromDateToRequest) throws CustomException {
        return ResponseEntity.ok().body(this.statisticService.getTotalCostFromDateTo(totalCostFromDateToRequest));
    }

    @GetMapping("/total_patients_today")
    @ApiMessage("Thống kê số bệnh nhân điều trị hôm nay")
    public ResponseEntity<TotalPatientInDayResponse> getAllPatientInDate(){
        return ResponseEntity.ok().body(this.statisticService.getAllPatientsInDate());
    }

}
