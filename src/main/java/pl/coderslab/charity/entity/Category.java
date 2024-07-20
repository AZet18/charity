package pl.coderslab.charity.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
}
