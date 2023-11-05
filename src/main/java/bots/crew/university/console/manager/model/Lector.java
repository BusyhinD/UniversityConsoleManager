package bots.crew.university.console.manager.model;

import bots.crew.university.console.manager.model.enumiration.Degree;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "lectors")
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @Positive
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Degree degree;
}
