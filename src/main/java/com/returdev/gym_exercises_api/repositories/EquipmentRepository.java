package com.returdev.gym_exercises_api.repositories;

import com.returdev.gym_exercises_api.entities.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {

    @Query("SELECT e.id FROM EquipmentEntity e WHERE e.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

    boolean existsByName(String name);

}
