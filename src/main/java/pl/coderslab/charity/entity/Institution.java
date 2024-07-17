package pl.coderslab.charity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "institutions")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{institution.name.notBlank}")
    @Size(max = 255, message = "{institution.name.size}")
    private String name;

    @NotBlank(message = "{institution.description.notBlank}")
    @Size(max = 500, message = "{institution.description.size}")
    private String description;
}
