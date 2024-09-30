package com.returdev.gym_exercises_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "exercises_sequence", allocationSize = 1)
    @Column(name = "exercise_id")
    @NotNull
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Length(
        min = 4
    )
    private String name;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    @NotNull
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
    @NotEmpty
    private List<MuscleEngagementEntity> musclesEngagement;

}
