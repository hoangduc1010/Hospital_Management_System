package hospital.management.hospital_management.service;

import hospital.management.hospital_management.domain.FinanceMedicineEntity;
import hospital.management.hospital_management.domain.MedicineEntity;
import hospital.management.hospital_management.dto.request.MedicineRequest;
import hospital.management.hospital_management.repository.FinanceMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FinanceMedicineService {
    private final FinanceMedicineRepository financeMedicineRepository;
    public void saveFinanceMedicineWithMedicineBuyIn(Set<MedicineEntity> medicineBuyIn, MedicineRequest medicineRequest){
        FinanceMedicineEntity financeMedicineEntity=new FinanceMedicineEntity();
        financeMedicineEntity.setTotalCost(medicineRequest.getPurchasePrice() * medicineRequest.getQuantityInStock());
        financeMedicineEntity.setMedicines(medicineBuyIn);
        financeMedicineEntity.setBuyInDate(Instant.now());
        this.financeMedicineRepository.save(financeMedicineEntity);
    }
}
