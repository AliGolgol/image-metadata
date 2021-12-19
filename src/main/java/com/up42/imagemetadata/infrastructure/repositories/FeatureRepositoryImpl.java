package com.up42.imagemetadata.infrastructure.repositories;

import com.up42.imagemetadata.domain.FeatureRepository;
import com.up42.imagemetadata.domain.models.Feature;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FeatureRepositoryImpl implements FeatureRepository {

    /**
     * Returns all the features.
     *
     * @return {@link List<Feature>} which is optional.
     */
    @Override
    public Optional<List<Feature>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Feature> getById(String id) {
        return Optional.empty();
    }
}
