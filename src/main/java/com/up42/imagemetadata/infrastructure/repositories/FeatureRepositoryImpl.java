package com.up42.imagemetadata.infrastructure.repositories;

import com.up42.imagemetadata.domain.FeatureRepository;
import com.up42.imagemetadata.domain.models.Feature;
import com.up42.imagemetadata.domain.models.SourceData;
import com.up42.imagemetadata.infrastructure.Config;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;

@Repository
public class FeatureRepositoryImpl implements FeatureRepository {

    private final Config config;

    public FeatureRepositoryImpl(Config config) {
        this.config = config;
    }

    /**
     * Returns all the features.
     *
     * @return {@link List<Feature>} which is optional.
     */
    @Override
    public Optional<List<Feature>> getAll() {
        return of(config.getSourceData().stream()
                .map(this::getFeatures)
                .flatMap(List::stream).collect(Collectors.toList()));
    }

    /**
     * Maps the {@link List<SourceData>} to the {@link List<Feature>}.
     *
     * @param sourceData is {@link List<SourceData>}.
     * @return a {@link List<Feature>}.
     */
    private List<Feature> getFeatures(List<SourceData> sourceData) {
        return sourceData.stream()
                .map(SourceData::getFeatures)
                .flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * Returns the feature by id.
     *
     * @param id is String.
     * @return a {@link Feature} which is optional.
     */
    @Override
    public Optional<Feature> getById(String id) {
        if (getAll().isEmpty()) return empty();

        return getAll().get().stream()
                .filter(f->f.getProperties().getId().equals(id)).findFirst();
    }
}
