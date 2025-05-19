package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.DoctorDiplomaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    UserEntity user;

    Integer yearOfExperience;

    @NotNull(message = "Bằng cấp không được để trống")
    @Enumerated(EnumType.STRING)
    DoctorDiplomaEnum doctorDiploma;


//    @NotNull(message = "Khoa trực không được để trống")
//    @ManyToOne
//    @JoinColumn(name = "department_id")
//    DepartmentEntity department;


}
