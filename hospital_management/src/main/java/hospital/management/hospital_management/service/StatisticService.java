package hospital.management.hospital_management.service;

import hospital.management.hospital_management.domain.FinanceEntity;
import hospital.management.hospital_management.dto.response.DoctorNurseActiveResponse;
import hospital.management.hospital_management.dto.response.TotalCostIndayResponse;
import hospital.management.hospital_management.helper.StatisticServiceHelper;
import hospital.management.hospital_management.repository.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticServiceHelper statisticServiceHelper;
    private final FinanceRepository financeRepository;
    public List<DoctorNurseActiveResponse> getAllDoctorAndNurseAreActive(){
        return this.statisticServiceHelper.convertToDoctorNurseResponse();
    }
    public TotalCostIndayResponse getTotalCostInDay(){
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate today = LocalDate.now(zone);
        Instant startOfDay = today.atStartOfDay(zone).toInstant();
        Instant endOfDay = today.plusDays(1).atStartOfDay(zone).toInstant();
        List<FinanceEntity> financeInDay=this.financeRepository.findByBillingDateBetween(startOfDay,endOfDay);
        return this.statisticServiceHelper.convertToTotalCostInDayResponse(financeInDay);
    }
}
