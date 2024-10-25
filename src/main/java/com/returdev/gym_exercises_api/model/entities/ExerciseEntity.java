package com.returdev.gym_exercises_api.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

/**
 * Represents an exercise entity in the database.
 *
 * <p>
 * The {@link ExerciseEntity} class maps to the "exercises" table and contains the details
 * of various exercises. Each exercise is associated with a specific equipment and can engage
 * multiple muscles. This class includes fields for the unique identifier, name, description,
 * associated equipment, and muscle engagements, along with validation constraints.
 * </p>
 */
@Entity
@Table(
        name = "exercises",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"name", "equipment_id"}
                )
        }
)
@Relation(collectionRelation = "exercises")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseEntity {

    /**
     * The unique identifier for the exercise.
     * <p>
     * This field is generated automatically using a sequence generator. It is the primary key
     * for the exercise entity and must not be null.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercises_sequence")
    @SequenceGenerator(name = "exercises_sequence", allocationSize = 1)
    @Column(name = "exercise_id")
    @NotNull(message = "{validation.not_null.message}")
    private Long id;

    /**
     * The name of the exercise.
     * <p>
     * This field is required (not blank) and has a length constraint of 10 to 50 characters.
     * The value will be validated using the provided annotations.
     * </p>
     */
    @Column(name = "name", nullable = false)
    @NotBlank(message = "{validation.not_blank.message}")
    @Size(
            min = 10,
            max = 50,
            message = "{validation.size.message}"
    )
    private String name;

    /**
     * The description of the exercise.
     * <p>
     * This field is required (not null) and provides additional details about the exercise.
     * </p>
     */
    @Column(name = "description", nullable = false)
    @NotNull(message = "{validation.not_null.message}")
    @Lob
    private String description;

    /**
     * The equipment associated with the exercise.
     * <p>
     * This field is a many-to-one relationship with the {@link EquipmentEntity} class.
     * It cannot be null, meaning every exercise must be associated with a piece of equipment.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    @NotNull(message = "{validation.not_null.message}")
    private EquipmentEntity equipment;

    /**
     * The muscle engagements involved in the exercise.
     * <p>
     * This field represents a many-to-many relationship with the {@link MuscleEngagementEntity}
     * class. The list must not be empty, meaning every exercise must engage at least one muscle.
     * The fetch type is eager, and cascading is set to merge.
     * </p>
     */
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "exercises_muscles_engagement",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_engagement_id")
    )
    @NotEmpty(message = "{validation.not_empty.message}")
    private List<MuscleEngagementEntity> musclesEngagement;

}

