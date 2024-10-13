package com.returdev.gym_exercises_api.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Table(
        name = "equipments"
)
@Relation(collectionRelation = "equipments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_sequence")
    @SequenceGenerator(name = "equipment_sequence", allocationSize = 1)
    @Column(name = "equipment_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 25)
    @NotBlank(message = "{validation.not_blank.message}")
    @Size(min = 3, max = 25, message = "{validation.size.message}")
    private String name;

}
