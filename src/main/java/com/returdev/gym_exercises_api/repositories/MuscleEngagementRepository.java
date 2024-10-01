package com.returdev.gym_exercises_api.repositories;

import com.returdev.gym_exercises_api.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.enums.Muscle;
import com.returdev.gym_exercises_api.enums.MuscleActivationLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MuscleEngagementRepository extends JpaRepository<MuscleEngagementEntity, Long> {

    List<MuscleEngagementEntity> findByMuscle(Muscle muscle);

    List<MuscleEngagementEntity> findByMuscleActivationLevel(MuscleActivationLevel activationLevel);

    @Query("SELECT me.id FROM MuscleEngagementEntity me WHERE me.muscle = :muscle AND me.muscleActivationLevel = :activationLevel")
    Optional<Long> findIdByMuscleAndActivationLevel(@Param("muscle") Muscle muscle, @Param("activationLevel") MuscleActivationLevel activationLevel);

}
