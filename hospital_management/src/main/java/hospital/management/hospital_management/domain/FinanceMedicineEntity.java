package hospital.management.hospital_management.domain;

import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "finance_medicines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceMedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double totalCost;

    @ManyToMany
    @JoinTable(name = "medicine_finances_list",
            joinColumns = @JoinColumn(name = "finance_medicine_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    Set<MedicineEntity> medicines;

    Instant buyInDate;

    Instant createdAt;

    Instant updatedAt;

    String createdBy;

    String updatedBy;

    @PrePersist
    public void userBeforeCreated() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void userBeforeUpdated() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";

    }
}
