package hospital.management.hospital_management.config;


import hospital.management.hospital_management.repository.*;
import hospital.management.hospital_management.util.constant.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Override
    public void run(String... args) throws Exception {
        insertRoles();
        insertDepartments();
        insertAdmin();
        insertPatient();

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
                            "(1, 5), " +
                            "(2, 6), " +
                            "(3, 7), " +
                            "(4, 8), " +
                            "(5, 9), " +
                            "(6, 10), " +
                            "(7, 11)"
            );

        }
    }

    public void insertAdmin(){
        if (userRepository.count() == 0) {
            String hashPassword = passwordEncoder.encode("123456");
            jdbcTemplate.update(
                    "INSERT INTO users (id, fullname, phone_number, password, username, role_id, is_active) VALUES " +
                            "(1, 'Hoang Duc Admin', '012349998', ?, 'hoangducadmin', 7, true), " +
                            "(2, 'Bác sĩ 1', '0900000001', ?, 'doctor_1', 3, true), " +
                            "(3, 'Bác sĩ 2', '0900000002', ?, 'doctor_2', 3, true), " +
                            "(4, 'Bác sĩ 3', '0900000003', ?, 'doctor_3', 3, true), " +
                            "(5, 'Bệnh nhân 1', '0900000004', ?, 'patient_1', 2, true), " +
                            "(6, 'Bệnh nhân 2', '0900000005', ?, 'patient_2', 2, true), " +
                            "(7, 'Bệnh nhân 3', '0900000006', ?, 'patient_3', 2, true), " +
                            "(8, 'Bệnh nhân 4', '0900000007', ?, 'patient_4', 2, true), " +
                            "(9, 'Bệnh nhân 5', '0900000008', ?, 'patient_5', 2, true), " +
                            "(10, 'Bệnh nhân 6', '0900000009', ?, 'patient_6', 2, true), " +
                            "(11, 'Bệnh nhân 7', '0900000010', ?, 'patient_7', 2, true)",
                    hashPassword, hashPassword, hashPassword, hashPassword,
                    hashPassword, hashPassword, hashPassword, hashPassword,
                    hashPassword, hashPassword, hashPassword
            );

        }
    }

    public void insertDepartments() {
        if (departmentRepository.count() == 0) {
            jdbcTemplate.update(
                    "INSERT INTO Departments (id, department_name, description)\n" +
                            "VALUES\n" +
                            "  (1, 'Emergency', 'Provides immediate treatment for acute illnesses and injuries.'),\n" +
                            "  (2, 'Cardiology', 'Specializes in heart-related diseases and treatments.'),\n" +
                            "  (3, 'Neurology', 'Deals with disorders of the nervous system.'),\n" +
                            "  (4, 'Oncology', 'Treats cancer patients through chemotherapy, radiation, and more.'),\n" +
                            "  (5, 'Pediatrics', 'Provides medical care for infants, children, and adolescents.'),\n" +
                            "  (6, 'Orthopedics', 'Focuses on the musculoskeletal system – bones, joints, and muscles.'),\n" +
                            "  (7, 'Radiology', 'Conducts medical imaging like X-rays, MRIs, and CT scans.'),\n" +
                            "  (8, 'Maternity', 'Handles childbirth and care for mothers and newborns.'),\n" +
                            "  (9, 'Pharmacy', 'Manages medication dispensing and pharmaceutical care.'),\n" +
                            "  (10, 'Pathology', 'Analyzes lab samples to diagnose diseases.');\n"

                    );
        }
    }

}
