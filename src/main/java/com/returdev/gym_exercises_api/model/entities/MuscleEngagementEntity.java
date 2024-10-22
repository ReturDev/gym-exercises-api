package com.returdev.gym_exercises_api.model.entities;


import com.returdev.gym_exercises_api.model.enums.Muscle;
import com.returdev.gym_exercises_api.model.enums.MuscleActivationLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a muscle engagement entity in the database.
 *
 * <p>
 * The {@link MuscleEngagementEntity} class maps to the "muscle_engagements" table and contains
 * information about how different muscles are engaged at various activation levels. Each muscle
 * engagement has a unique combination of muscle and activation level.
 * </p>
 */
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

    /**
     * The unique identifier for the muscle engagement.
     * <p>
     * This field is generated automatically and serves as the primary key for the
     * muscle engagement entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muscle_engagement_id")
    private Long id;

    /**
     * The muscle involved in the engagement.
     * <p>
     * This field is an enumerated type that specifies which muscle is being engaged.
     * It cannot be null, indicating that every muscle engagement must specify a muscle.
     * </p>
     */
    @Column(name = "muscle", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.not_null.message}")
    private Muscle muscle;

    /**
     * The activation level of the muscle engagement.
     * <p>
     * This field is an enumerated type that indicates the level of activation
     * (HIGH, MEDIUM, LOW) for the muscle. It cannot be null, meaning every muscle
     * engagement must specify an activation level.
     * </p>
     */
    @Column(name = "activation_level", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.not_null.message}")
    private MuscleActivationLevel muscleActivationLevel;

}

