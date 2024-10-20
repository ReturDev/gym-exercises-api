package com.returdev.gym_exercises_api.repositories.data;


import com.returdev.gym_exercises_api.model.entities.ExerciseEntity;
import com.returdev.gym_exercises_api.model.enums.Muscle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.muscle = :muscle")
    Page<ExerciseEntity> findByMuscle(@Param("muscle") Muscle muscle, Pageable pageable);

    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.muscle IN :muscles")
    Page<ExerciseEntity> findByMuscles(@Param("muscles") List<Muscle> muscles, Pageable pageable);

    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.id = :id")
    Page<ExerciseEntity> findByMuscleEngagementId(@Param("id") Long id, Pageable pageable);

    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.id IN :ids")
    Page<ExerciseEntity> findByMuscleEngagementIds(@Param("ids") List<Long> ids, Pageable pageable);

    Page<ExerciseEntity> findByEquipmentId(Long id, Pageable pageable);

    boolean existsByNameAndEquipmentId(String name, Long equipmentId);


}
