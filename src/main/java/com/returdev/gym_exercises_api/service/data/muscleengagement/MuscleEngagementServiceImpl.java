package com.returdev.gym_exercises_api.service.data.muscleengagement;

import com.returdev.gym_exercises_api.model.entities.MuscleEngagementEntity;
import com.returdev.gym_exercises_api.repositories.data.MuscleEngagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuscleEngagementServiceImpl implements MuscleEngagementService {

    private final MuscleEngagementRepository muscleEngagementRepository;

    public MuscleEngagementServiceImpl(MuscleEngagementRepository muscleEngagementRepository) {
        this.muscleEngagementRepository = muscleEngagementRepository;
    }

    @Override
    public Optional<Long> getMuscleEngagementId(MuscleEngagementEntity muscleEngagement) {
        return muscleEngagementRepository.findIdByMuscleAndActivationLevel(muscleEngagement.getMuscle(), muscleEngagement.getMuscleActivationLevel());
    }

    @Override
    public MuscleEngagementEntity save(MuscleEngagementEntity muscleEngagement) {
        return muscleEngagementRepository.save(muscleEngagement);
    }

    @Override
    public List<MuscleEngagementEntity> getMuscleEngagementsWithId(List<MuscleEngagementEntity> muscleEngagements) {
        return muscleEngagementRepository.findByMusclesAndActivationLevels(
                muscleEngagements.stream().map(MuscleEngagementEntity::getMuscle).toList(),
                muscleEngagements.stream().map(MuscleEngagementEntity::getMuscleActivationLevel).toList()
        );
    }

}
