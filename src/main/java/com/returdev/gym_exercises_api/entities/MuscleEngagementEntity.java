package com.returdev.gym_exercises_api.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.returdev.gym_exercises_api.enums.Muscle;
import com.returdev.gym_exercises_api.enums.MuscleActivationLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(
        name = "muscle_engagements",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"muscle", "activation_level"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MuscleEngagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "muscle_engagement_sequence", allocationSize = 1)
    @Column(name = "muscle_engagement_id")
    private Long id;

    @Column(name = "muscle", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Muscle muscle;

    @Column(name = "activation_level", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private MuscleActivationLevel muscleActivationLevel;

    @ManyToMany(mappedBy = "musclesEngagement")
    @JsonIgnore
    private List<ExerciseEntity> exercises;

}
