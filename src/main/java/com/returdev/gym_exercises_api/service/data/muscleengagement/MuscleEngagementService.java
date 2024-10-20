package com.returdev.gym_exercises_api.service.data.muscleengagement;

import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;

import java.util.List;
import java.util.Optional;

public interface MuscleEngagementService {

    Optional<Long> getMuscleEngagementId(MuscleEngagementEntity muscleEngagement);

    MuscleEngagementEntity save(MuscleEngagementEntity muscleEngagement);

    List<MuscleEngagementEntity> getMuscleEngagementsWithId(List<MuscleEngagementEntity> muscleEngagements);

}
