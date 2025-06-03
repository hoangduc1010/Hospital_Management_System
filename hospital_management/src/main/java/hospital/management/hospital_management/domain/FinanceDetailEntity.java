package hospital.management.hospital_management.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "finance_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long financeDetailId;

    @ManyToOne
    @JoinColumn(name = "finance_id", nullable = false)
    FinanceEntity finance;

    private Long unitPrice;

    private Integer quantity;

    private Long subtotal;


}
