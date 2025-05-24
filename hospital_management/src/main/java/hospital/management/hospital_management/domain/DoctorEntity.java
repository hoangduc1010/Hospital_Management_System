package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.DoctorDiplomaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    Integer yearOfExperience;

    @NotNull(message = "Bằng cấp không được để trống")
    @Enumerated(EnumType.STRING)
    DoctorDiplomaEnum doctorDiploma;


    @ManyToMany
    @JoinTable(
            name="doctor_department",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name="department_id")
    )
    Set<DepartmentEntity> departments;



}
