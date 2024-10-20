package com.returdev.gym_exercises_api.model.entities;


import com.returdev.gym_exercises_api.model.enums.Muscle;
import com.returdev.gym_exercises_api.model.enums.MuscleActivationLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muscle_engagement_id")
    private Long id;

    @Column(name = "muscle", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.not_null.message}")

    private Muscle muscle;

    @Column(name = "activation_level", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.not_null.message}")
    private MuscleActivationLevel muscleActivationLevel;


}
