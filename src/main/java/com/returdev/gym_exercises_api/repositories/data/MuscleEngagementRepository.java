package com.returdev.gym_exercises_api.repositories.data;

import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.model.enums.Muscle;
import com.returdev.gym_exercises_api.model.enums.MuscleActivationLevel;
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

    @Query("SELECT me FROM MuscleEngagementEntity me WHERE me.muscle IN :muscles AND me.muscleActivationLevel IN :activationLevels")
    List<MuscleEngagementEntity> findByMusclesAndActivationLevels(@Param("muscles") List<Muscle> muscles, @Param("activationLevels") List<MuscleActivationLevel> activationLevels);



}
