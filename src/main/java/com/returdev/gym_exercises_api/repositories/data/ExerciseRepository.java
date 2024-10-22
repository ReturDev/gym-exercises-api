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

/**
 * Repository interface for {@link ExerciseEntity} that provides database access operations.
 *
 * <p>
 * This repository extends {@link JpaRepository}, inheriting several CRUD methods, and
 * defines custom queries to retrieve exercises based on muscle engagements, equipment, and IDs.
 * </p>
 */
@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    /**
     * Finds a page of exercises that engage a specific muscle.
     *
     * <p>
     * This query joins the {@link ExerciseEntity} with its associated muscle engagements
     * to find exercises involving a given {@link Muscle}.
     * </p>
     *
     * @param muscle the muscle that the exercises engage
     * @param pageable the pagination information
     * @return a {@link Page} of exercises that involve the specified muscle
     */
    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.muscle = :muscle")
    Page<ExerciseEntity> findByMuscle(@Param("muscle") Muscle muscle, Pageable pageable);

    /**
     * Finds a page of exercises that engage any of the specified muscles.
     *
     * <p>
     * This query joins the {@link ExerciseEntity} with its associated muscle engagements
     * to find exercises that involve any muscle in the provided list.
     * </p>
     *
     * @param muscles the list of muscles that the exercises engage
     * @param pageable the pagination information
     * @return a {@link Page} of exercises that involve the specified muscles
     */
    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.muscle IN :muscles")
    Page<ExerciseEntity> findByMuscles(@Param("muscles") List<Muscle> muscles, Pageable pageable);

    /**
     * Finds a page of exercises by a specific muscle engagement ID.
     *
     * <p>
     * This query retrieves exercises based on the muscle engagement ID by joining
     * the exercise entity with its muscle engagements.
     * </p>
     *
     * @param id the muscle engagement ID
     * @param pageable the pagination information
     * @return a {@link Page} of exercises associated with the given muscle engagement ID
     */
    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.id = :id")
    Page<ExerciseEntity> findByMuscleEngagementId(@Param("id") Long id, Pageable pageable);

    /**
     * Finds a page of exercises by multiple muscle engagement IDs.
     *
     * <p>
     * This query retrieves exercises based on a list of muscle engagement IDs by joining
     * the exercise entity with its muscle engagements.
     * </p>
     *
     * @param ids the list of muscle engagement IDs
     * @param pageable the pagination information
     * @return a {@link Page} of exercises associated with the specified muscle engagement IDs
     */
    @Query("SELECT ex FROM ExerciseEntity ex JOIN ex.musclesEngagement me WHERE me.id IN :ids")
    Page<ExerciseEntity> findByMuscleEngagementIds(@Param("ids") List<Long> ids, Pageable pageable);

    /**
     * Finds a page of exercises associated with a specific equipment.
     *
     * @param id the ID of the equipment
     * @param pageable the pagination information
     * @return a {@link Page} of exercises associated with the specified equipment
     */
    Page<ExerciseEntity> findByEquipmentId(Long id, Pageable pageable);

    /**
     * Checks whether an exercise with the specified name and equipment ID exists.
     *
     * @param name the name of the exercise
     * @param equipmentId the ID of the associated equipment
     * @return {@code true} if an exercise with the specified name and equipment exists, {@code false} otherwise
     */
    boolean existsByNameAndEquipmentId(String name, Long equipmentId);

}

