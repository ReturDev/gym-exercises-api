package com.returdev.gym_exercises_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercises_sequence")
    @SequenceGenerator(name = "exercises_sequence", allocationSize = 1)
    @Column(name = "exercise_id")
    @NotNull(message = "{validation.not_null.message}")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "{validation.not_blank.message}")
    @Size(
            min = 10,
            max = 50,
            message = "{validation.size.message}"
    )
    private String name;

    @Column(name = "description", nullable = false)
    @NotNull(message = "{validation.not_null.message}")
    private String description;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    @NotNull(message = "{validation.not_null.message}")
    private EquipmentEntity equipment;

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
