package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Tên không được để trống'")
    String fullname;


    @NotBlank(message="Tên tài khoản không được để trống")
    String username;

    @NotBlank(message="Mật khẩu không được để trống")
    String password;

    @NotBlank(message="Số điện thoại không được để ")
    String phoneNumber;

    String dob;

    @Enumerated(EnumType.STRING)
    GenderEnum gender;

    @ManyToOne
    @JoinColumn(name="role_id")
    RoleEntity role;


    @Column(columnDefinition = "MEDIUMTEXT")
    String refreshToken;



}
