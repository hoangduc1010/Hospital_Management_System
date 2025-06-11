package hospital.management.hospital_management.config;


import hospital.management.hospital_management.domain.MedicineEntity;
import hospital.management.hospital_management.repository.*;
import hospital.management.hospital_management.util.constant.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DataLoaderConfig implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private NurseRepository nurseRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private RoomRepository roomRepository;


    @Override
    public void run(String... args) throws Exception {
        insertRoles();
        insertAdmin();
        insertDoctor();
        insertNurses();
//        insertPatient();
        insertDepartments();
        insertDoctor_Department();
        insertNurse_Department();
        insertRooms();
        insertMedicine();


    }
    public void insertRooms(){
        if(roomRepository.count()==0){
            jdbcTemplate.update(
                    "INSERT INTO rooms (is_active, number_of_beds, room_number, room_type, department_id) VALUES " +
                            "(1, 12, 'R101', 'GENERAL_WARD', 1)," +
                            "(1, 14, 'R102', 'SEMI_PRIVATE', 2)," +
                            "(1, 16, 'R103', 'PRIVATE', 3)," +
                            "(1, 18, 'R104', 'ICU', 4)," +
                            "(1, 11, 'R105', 'NICU', 5)," +
                            "(1, 13, 'R106', 'ISOLATION', 6)," +
                            "(1, 15, 'R107', 'MATERNITY', 7)," +
                            "(1, 20, 'R108', 'GENERAL_WARD', 8)," +
                            "(1, 22, 'R109', 'SEMI_PRIVATE', 9)," +
                            "(1, 25, 'R110', 'PRIVATE', 10)," +
                            "(1, 17, 'R111', 'ICU', 11)," +
                            "(1, 19, 'R112', 'NICU', 12)," +
                            "(1, 23, 'R113', 'ISOLATION', 13)," +
                            "(1, 26, 'R114', 'MATERNITY', 14)," +
                            "(1, 28, 'R115', 'GENERAL_WARD', 15)"
            );


        }
    }
    public void insertNurse_Department(){
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM nurse_department", Integer.class);
        if(count!=null && count==0){
            jdbcTemplate.update(
                    "INSERT INTO nurse_department (nurse_id, department_id) VALUES " +
                            "(1, 1), (1, 2), (1, 3), (1, 4), (1, 5)," +
                            "(2, 6), (2, 7), (2, 8), (2, 9), (2, 10)," +
                            "(3, 11), (3, 12), (3, 13), (3, 14), (3, 15)"
            );

        }
    }
    public void insertDoctor_Department(){
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM doctor_department", Integer.class);
        if (count != null && count == 0) {
            jdbcTemplate.update(
                    "INSERT INTO doctor_department (doctor_id, department_id) VALUES " +
                            "(1, 1), (1, 5), (1, 9), (1, 13)," +
                            "(2, 2), (2, 6), (2, 10)," +
                            "(3, 3), (3, 7), (3, 11), (3, 14)," +
                            "(4, 4), (4, 8), (4, 12), (4, 15)"
            );
        }

    }
    public void insertNurses(){
        if(nurseRepository.count()==0){
            jdbcTemplate.update(
                    "INSERT INTO nurses (nurse_diploma, year_of_experience, user_id) VALUES " +
                            "('INTERMEDIATE_NURSE_IN_MEDICINE', 2, 9), " +
                            "('COLLEGE_DEGREE_IN_NURSING', 4, 10), " +
                            "('BACHELOR_OF_NURSING', 6, 19)"
            );

        }
    }
    public void insertRoles() {
        if(roleRepository.count()==0){
            int id = 1;
            for (RoleEnum role : RoleEnum.values()) {
                jdbcTemplate.update("INSERT INTO roles (id, role_name) VALUES (?, ?)", id++, role.name());
            }
        }

    }
    public void insertPatient(){
        if(patientRepository.count()==0){
            jdbcTemplate.update(
                    "INSERT INTO patients (id, user_id) VALUES " +
                            "(1, 4), " +
                            "(2, 5), " +
                            "(3, 6), " +
                            "(4, 7), " +
                            "(5, 20)"
            );


        }
    }
    public void insertAdmin(){
        if (userRepository.count() == 0) {
            String hashPassword = passwordEncoder.encode("123456");

            jdbcTemplate.update(
                    "INSERT INTO users (id, fullname, phone_number, password, username, role_id, is_active) VALUES " +
                            "(1, 'Hoàng Đức', '0900000001', ?, 'hoangduc', 7, true)," +           // ADMIN
                            "(2, 'Nguyễn Văn A', '0900000002', ?, 'nguyenvana', 3, true)," +     // DOCTOR
                            "(3, 'Trần Thị B', '0900000003', ?, 'tranthib', 3, true)," +         // DOCTOR
                            "(4, 'Lê Văn C', '0900000004', ?, 'levanc', 2, true)," +             // PATIENT
                            "(5, 'Phạm Thị D', '0900000005', ?, 'phamthid', 2, true)," +         // PATIENT
                            "(6, 'Đỗ Văn E', '0900000006', ?, 'dovane', 2, true)," +             // PATIENT
                            "(7, 'Ngô Thị F', '0900000007', ?, 'ngothif', 2, true)," +           // PATIENT
                            "(8, 'Vũ Văn G', '0900000008', ?, 'vuvang', 3, true)," +             // DOCTOR
                            "(9, 'Lý Thị H', '0900000009', ?, 'lythih', 4, true)," +             // NURSE
                            "(10, 'Trịnh Văn I', '0900000010', ?, 'trinhvani', 4, true)," +      // NURSE
                            "(11, 'Bùi Thị K', '0900000011', ?, 'buithik', 5, true)," +          // RECEPTIONIST
                            "(12, 'Đặng Văn L', '0900000012', ?, 'dangvanl', 5, true)," +        // RECEPTIONIST
                            "(13, 'Mai Thị M', '0900000013', ?, 'maithim', 6, true)," +          // ACCOUNTANT
                            "(14, 'Hồ Văn N', '0900000014', ?, 'hovann', 6, true)," +            // ACCOUNTANT
                            "(15, 'Tạ Thị O', '0900000015', ?, 'tatthio', 1, true)," +           // USER
                            "(16, 'Trương Văn P', '0900000016', ?, 'truongvanp', 1, true)," +    // USER
                            "(17, 'Cao Thị Q', '0900000017', ?, 'caothiq', 1, true)," +          // USER
                            "(18, 'Đinh Văn R', '0900000018', ?, 'dinhvanr', 3, true)," +        // DOCTOR
                            "(19, 'Nguyễn Thị S', '0900000019', ?, 'nguyenthis', 4, true)," +    // NURSE
                            "(20, 'Lương Văn T', '0900000020', ?, 'luongvant', 2, true)",        // PATIENT
                    // cung cấp đúng số lượng hashPassword
                    hashPassword, hashPassword, hashPassword, hashPassword, hashPassword,
                    hashPassword, hashPassword, hashPassword, hashPassword, hashPassword,
                    hashPassword, hashPassword, hashPassword, hashPassword, hashPassword,
                    hashPassword, hashPassword, hashPassword, hashPassword, hashPassword
            );


        }
    }
    public void insertDepartments() {
        if (departmentRepository.count() == 0) {

            jdbcTemplate.update(
                    "INSERT INTO Departments (id, department_name, description, department_head_id, is_active) VALUES " +
                            "(1, 'Emergency', 'Provides immediate treatment for acute illnesses and injuries.', 1, true)," +
                            "(2, 'Cardiology', 'Specializes in heart-related diseases and treatments.', 2, true)," +
                            "(3, 'Neurology', 'Deals with disorders of the nervous system.', 3, true)," +
                            "(4, 'Oncology', 'Treats cancer patients through chemotherapy, radiation, and more.', 4, true)," +
                            "(5, 'Pediatrics', 'Provides medical care for infants, children, and adolescents.', NULL, true)," +
                            "(6, 'Orthopedics', 'Focuses on the musculoskeletal system – bones, joints, and muscles.', NULL, true)," +
                            "(7, 'Radiology', 'Conducts medical imaging like X-rays, MRIs, and CT scans.', NULL, true)," +
                            "(8, 'Maternity', 'Handles childbirth and care for mothers and newborns.', NULL, true)," +
                            "(9, 'Pharmacy', 'Manages medication dispensing and pharmaceutical care.', NULL, true)," +
                            "(10, 'Pathology', 'Analyzes lab samples to diagnose diseases.', NULL, true)," +
                            "(11, 'Urology', 'Treats urinary tract system and male reproductive organs.', NULL, true)," +
                            "(12, 'Gastroenterology', 'Focuses on the digestive system and its disorders.', NULL, true)," +
                            "(13, 'Dermatology', 'Deals with skin, hair, and nail health.', NULL, true)," +
                            "(14, 'Ophthalmology', 'Provides care related to eyes and vision.', NULL, true)," +
                            "(15, 'ENT', 'Treats conditions of the ear, nose, and throat.', NULL, true)"
            );



        }
    }
    public void insertMedicine(){
        if(medicineRepository.count()==0){
            jdbcTemplate.update(
                    "INSERT INTO medicines (" +
                            "medicine_name, active_ingredient, dosage_form, strength, unit, " +
                            "quantity_in_stock, sell_price, purchase_price, manufacturer, " +
                            "country_of_origin, notes, medicine_category, is_active) " +
                            "VALUES " +
                            "('Amoxicillin', 'Amoxicillin', 'Tablet', '500mg', 'Tablet', 100, 1.5, 1.0, 'ABC Pharma', 'USA', 'Common antibiotic', 'ANTIBIOTIC', true)," +
                            "('Paracetamol', 'Paracetamol', 'Tablet', '500mg', 'Tablet', 200, 0.8, 0.5, 'DEF Pharma', 'Germany', 'Pain relief', 'ANALGESIC', true)," +
                            "('Ibuprofen', 'Ibuprofen', 'Capsule', '200mg', 'Capsule', 150, 1.2, 0.9, 'GHI Pharma', 'India', 'Anti-inflammatory', 'ANALGESIC', true)," +
                            "('Oseltamivir', 'Oseltamivir', 'Capsule', '75mg', 'Capsule', 80, 2.5, 2.0, 'JKL Pharma', 'Switzerland', 'Antiviral treatment', 'ANTIVIRAL', true)," +
                            "('Fluconazole', 'Fluconazole', 'Tablet', '150mg', 'Tablet', 120, 1.8, 1.3, 'MNO Pharma', 'UK', 'Anti-fungal', 'ANTIFUNGAL', true)," +
                            "('Lisinopril', 'Lisinopril', 'Tablet', '10mg', 'Tablet', 90, 1.6, 1.1, 'PQR Pharma', 'France', 'Blood pressure control', 'ANTIHYPERTENSIVE', true)," +
                            "('Metformin', 'Metformin', 'Tablet', '500mg', 'Tablet', 250, 0.9, 0.6, 'STU Pharma', 'India', 'Diabetes treatment', 'ANTIDIABETIC', true)," +
                            "('Atorvastatin', 'Atorvastatin', 'Tablet', '20mg', 'Tablet', 130, 1.7, 1.2, 'VWX Pharma', 'USA', 'Cholesterol control', 'CARDIOVASCULAR', true)," +
                            "('Omeprazole', 'Omeprazole', 'Capsule', '20mg', 'Capsule', 180, 1.1, 0.7, 'YZA Pharma', 'Vietnam', 'Stomach acid treatment', 'ANTACID', true)," +
                            "('Hepatitis B Vaccine', 'HBsAg', 'Injection', '10mcg/ml', 'Vial', 70, 15.0, 10.0, 'BIO Pharma', 'Japan', 'Hepatitis B prevention', 'VACCINE', true)," +
                            "('Azithromycin', 'Azithromycin', 'Tablet', '250mg', 'Tablet', 110, 2.0, 1.4, 'MedTech', 'Italy', 'Broad-spectrum antibiotic', 'ANTIBIOTIC', true)," +
                            "('Ciprofloxacin', 'Ciprofloxacin', 'Tablet', '500mg', 'Tablet', 95, 1.9, 1.3, 'PharmaWell', 'Canada', 'Bacterial infections', 'ANTIBIOTIC', true)," +
                            "('Doxycycline', 'Doxycycline', 'Capsule', '100mg', 'Capsule', 150, 2.2, 1.7, 'BioHealth', 'USA', 'Infections & acne', 'ANTIBIOTIC', true)," +
                            "('Cetirizine', 'Cetirizine', 'Tablet', '10mg', 'Tablet', 200, 0.6, 0.4, 'ZenPharma', 'India', 'Allergy relief', 'ANTIHISTAMINE', true)," +
                            "('Chlorpheniramine', 'Chlorpheniramine', 'Tablet', '4mg', 'Tablet', 300, 0.5, 0.3, 'GenPharm', 'Vietnam', 'Allergy symptoms', 'ANTIHISTAMINE', true)," +
                            "('Hydrocortisone', 'Hydrocortisone', 'Cream', '1%', 'Tube', 70, 3.0, 2.5, 'SkinCare', 'UK', 'Skin inflammation', 'CORTICOSTEROID', true)," +
                            "('Salbutamol', 'Salbutamol', 'Inhaler', '100mcg/dose', 'Inhaler', 60, 5.0, 4.0, 'Respira', 'Germany', 'Asthma relief', 'BRONCHODILATOR', true)," +
                            "('Insulin', 'Insulin', 'Injection', '100IU/ml', 'Vial', 90, 20.0, 15.0, 'NovoMed', 'Denmark', 'Diabetes control', 'ANTIDIABETIC', true)," +
                            "('Warfarin', 'Warfarin', 'Tablet', '5mg', 'Tablet', 85, 1.5, 1.1, 'CoaguloPharma', 'USA', 'Blood thinner', 'CARDIOVASCULAR', true)," +
                            "('Diazepam', 'Diazepam', 'Tablet', '5mg', 'Tablet', 75, 1.8, 1.3, 'NeuroPharm', 'India', 'Anxiety and seizures', 'SEDATIVE', true)," +
                            "('Ranitidine', 'Ranitidine', 'Tablet', '150mg', 'Tablet', 100, 1.0, 0.7, 'DigestiveCare', 'UK', 'Acid reflux', 'ANTACID', true)," +
                            "('Levothyroxine', 'Levothyroxine', 'Tablet', '100mcg', 'Tablet', 160, 1.4, 1.0, 'ThyroMed', 'France', 'Thyroid hormone', 'CARDIOVASCULAR', true)," +
                            "('Amlodipine', 'Amlodipine', 'Tablet', '5mg', 'Tablet', 140, 1.3, 1.0, 'HeartCare', 'Japan', 'Hypertension', 'ANTIHYPERTENSIVE', true)," +
                            "('Clopidogrel', 'Clopidogrel', 'Tablet', '75mg', 'Tablet', 110, 2.1, 1.5, 'CardioPlus', 'Germany', 'Prevent stroke', 'CARDIOVASCULAR', true)," +
                            "('Furosemide', 'Furosemide', 'Tablet', '40mg', 'Tablet', 120, 1.2, 0.9, 'KidneyCare', 'India', 'Diuretic', 'DIURETIC', true)," +
                            "('Methyldopa', 'Methyldopa', 'Tablet', '250mg', 'Tablet', 130, 1.6, 1.2, 'AlphaPharma', 'Brazil', 'Blood pressure', 'ANTIHYPERTENSIVE', true)," +
                            "('Tetanus Toxoid', 'TT', 'Injection', '0.5ml', 'Ampoule', 85, 12.0, 9.0, 'ImmunoTech', 'Belgium', 'Tetanus prevention', 'VACCINE', true)," +
                            "('Zinc Sulfate', 'Zinc', 'Tablet', '20mg', 'Tablet', 170, 0.7, 0.4, 'NutriHealth', 'Vietnam', 'Supplement', 'DIURETIC', true)," +
                            "('Vitamin C', 'Ascorbic Acid', 'Tablet', '500mg', 'Tablet', 210, 0.6, 0.3, 'HealthPlus', 'India', 'Immunity support', 'DIURETIC', true)," +
                            "('Magnesium B6', 'Magnesium + B6', 'Tablet', '470mg', 'Tablet', 150, 0.9, 0.5, 'VitaPharm', 'Vietnam', 'Muscle and nerve function', 'DIURETIC', true)"
            );


        }
    }
    public void insertDoctor(){
        if(doctorRepository.count()==0){
            jdbcTemplate.update(
                    "INSERT INTO doctors (doctor_diploma, year_of_experience, user_id) VALUES " +
                            "('GENERAL_PRACTITIONER', 3, 2), " +
                            "('ORIENTED_SPECIALIST_DOCTOR', 5, 3), " +
                            "('SPECIALIST_LEVEL_I', 7, 8), " +
                            "('MASTER_OF_MEDICINE', 10, 18)"
            );


        }
    }

}
