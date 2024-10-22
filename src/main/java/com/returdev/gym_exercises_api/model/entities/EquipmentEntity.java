package com.returdev.gym_exercises_api.model.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

/**
 * Represents an equipment entity in the database.
 *
 * <p>
 * The {@link EquipmentEntity} class maps to the "equipments" table and contains the details
 * of various fitness equipment. It includes fields for the unique identifier and name
 * of the equipment, along with validation constraints.
 * </p>
 */
@Entity
@Table(name = "equipments")
@Relation(collectionRelation = "equipments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentEntity {

    /**
     * The unique identifier for the equipment.
     * <p>
     * This field is generated automatically using a sequence generator. It is the primary key
     * for the equipment entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_sequence")
    @SequenceGenerator(name = "equipment_sequence", allocationSize = 1)
    @Column(name = "equipment_id")
    private Long id;

    /**
     * The name of the equipment.
     * <p>
     * This field is required (not blank), must be unique, and has a length constraint of
     * 3 to 25 characters. The value will be validated using the provided annotations.
     * </p>
     */
    @Column(name = "name", nullable = false, unique = true, length = 25)
    @NotBlank(message = "{validation.not_blank.message}")
    @Size(min = 3, max = 25, message = "{validation.size.message}")
    private String name;

}

