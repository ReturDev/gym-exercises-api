package com.returdev.gym_exercises_api.service;

import com.returdev.gym_exercises_api.entities.MuscleEngagementEntity;

import java.util.List;
import java.util.Optional;

public interface MuscleEngagementService {

    Optional<Long> getMuscleEngagementId(MuscleEngagementEntity muscleEngagement);

    MuscleEngagementEntity save(MuscleEngagementEntity muscleEngagement);

    List<MuscleEngagementEntity> getMuscleEngagementsWithId(List<MuscleEngagementEntity> muscleEngagements);

}
