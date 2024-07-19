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
    @NotNull
    @Min(value = 1)
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "donation_category",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @NotEmpty
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull
    private Institution institution;

    @NotBlank
    private String street;
    @NotBlank
    private String city;

    @Column(name = "zip_code")
    @NotBlank
    private String zipCode;

    @Column(name = "phone_number")
    @NotBlank
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "pick_up_date")
    @NotNull
    @FutureOrPresent
    private LocalDate pickUpDate;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "pick_up_time")
    @NotNull
    private LocalTime pickUpTime;

    @Column(name = "pick_up_comment")
    private String pickUpComment;
}
