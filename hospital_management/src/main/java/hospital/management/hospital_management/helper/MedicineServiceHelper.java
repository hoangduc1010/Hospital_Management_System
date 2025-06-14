package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.MedicineEntity;
import hospital.management.hospital_management.dto.request.MedicineRequest;
import hospital.management.hospital_management.dto.response.MedicineResponse;
import hospital.management.hospital_management.repository.MedicineRepository;
import hospital.management.hospital_management.util.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicineServiceHelper {
    private final MedicineRepository medicineRepository;
    public void checkValidInfoSaveMedicine(MedicineRequest medicineRequest) throws CustomException {
        if(medicineRequest.getId()!=null){
            MedicineEntity oldMedicine=this.medicineRepository.findById(medicineRequest.getId()).get();
            if(oldMedicine==null){
                throw new CustomException("Id không tồn tại");
            }
            return;
        }
        if(medicineRequest.getMedicineName()==null){
            throw new CustomException("Tên của thuốc không được để trống");
        }
        MedicineEntity currentMedicine=this.medicineRepository.findByMedicineNameAndActiveIngredientAndDosageFormAndStrengthAndUnitAndManufacturerAndCountryOfOrigin(
                medicineRequest.getMedicineName(),
                medicineRequest.getActiveIngredient(),
                medicineRequest.getDosageForm(),
                medicineRequest.getStrength(),
                medicineRequest.getUnit(),
                medicineRequest.getManufacturer(),
                medicineRequest.getCountryOfOrigin());
        if(currentMedicine!=null && medicineRequest.getId()==null){
            throw new CustomException(
                    "Thuốc có thông tin:\n" +
                            "- Tên: " + medicineRequest.getMedicineName() + "\n" +
                            "- Hoạt chất: " + medicineRequest.getActiveIngredient() + "\n" +
                            "- Dạng bào chế: " + medicineRequest.getDosageForm() + "\n" +
                            "- Hàm lượng hoạt chất: " + medicineRequest.getStrength() + "\n" +
                            "- Đơn vị định lượng: " + medicineRequest.getUnit() + "\n" +
                            "- Nhà sản xuất: " + medicineRequest.getManufacturer() + "\n" +
                            "- Xuất xứ: " + medicineRequest.getCountryOfOrigin() + "\n" +
                            "đã tồn tại."
            );
        }

        if(medicineRequest.getQuantityInStock()!=null && medicineRequest.getQuantityInStock()<=0){
            throw new CustomException("Số lượng cần phải lớn hơn 0");
        }
        if(medicineRequest.getPurchasePrice() <=0 && medicineRequest.getPurchasePrice()!=null){
            throw new CustomException("Giá mua cần lớn hơn 0");
        }
        if(medicineRequest.getSellPrice()<=0 && medicineRequest.getSellPrice()!=null){
            throw new CustomException("Giá bán cần lớn hơn 0");
        }
        if(medicineRequest.getCountryOfOrigin() == null || medicineRequest.getManufacturer() == null){
            throw new CustomException("Nhà sản xuất hoặc nguồn gốc của thuốc không được để trống");
        }
        if(medicineRequest.getMedicineCategory()==null){
            throw new CustomException("Danh mục thuốc không được để trống");
        }


    }
    public void checkValidInfoDeleteMedicine(Long medicineId) throws CustomException{
        MedicineEntity currentMedicine=this.medicineRepository.findById(medicineId).get();
        if(currentMedicine==null){
            throw new CustomException("Thuốc không tồn tại");
        }
    }
}
