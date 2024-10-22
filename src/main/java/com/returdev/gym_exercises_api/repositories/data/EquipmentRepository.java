package com.returdev.gym_exercises_api.repositories.data;

import com.returdev.gym_exercises_api.model.entities.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link EquipmentEntity} that provides database access operations.
 *
 * <p>
 * This repository extends {@link JpaRepository}, inheriting several CRUD methods, and
 * defines custom queries to check the existence of equipment by name and to find
 * the ID of equipment by its name.
 * </p>
 */
@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {

    /**
     * Finds the ID of the equipment by its name.
     *
     * <p>
     * This custom query retrieves the ID of an {@link EquipmentEntity} based on the given name.
     * If no entity with the provided name exists, the method returns an empty {@link Optional}.
     * </p>
     *
     * @param name the name of the equipment
     * @return an {@link Optional} containing the ID of the equipment if found, or an empty {@link Optional} if not
     */
    @Query("SELECT e.id FROM EquipmentEntity e WHERE e.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

    /**
     * Checks whether equipment with the specified name exists.
     *
     * @param name the name of the equipment to check
     * @return {@code true} if equipment with the specified name exists, {@code false} otherwise
     */
    boolean existsByName(String name);

}

