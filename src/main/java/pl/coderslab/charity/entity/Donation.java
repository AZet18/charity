package pl.coderslab.charity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "{donation.quantity.notNull}")
    @Min(value = 1, message = "{donation.quantity.min}")
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "donation_category",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @NotEmpty(message = "{donation.categories.notEmpty}")
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull(message = "{donation.institution.notNull}")
    private Institution institution;

    @NotBlank(message = "{donation.street.notBlank}")
    private String street;
    @NotBlank(message = "{donation.city.notBlank}")
    private String city;

    @Column(name = "zip_code")
    @NotBlank(message = "{donation.zipCode.notBlank}")
    private String zipCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "pick_up_date")
    @NotNull(message = "{donation.pickUpDate.notNull}")
    @FutureOrPresent(message = "{donation.pickUpDate.futureOrPresent}")
    private LocalDate pickUpDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "pick_up_time")
    @NotNull(message = "{donation.pickUpTime.notNull}")
    private LocalTime pickUpTime;

    @Column(name = "pick_up_comment")
    private String pickUpComment;
}
