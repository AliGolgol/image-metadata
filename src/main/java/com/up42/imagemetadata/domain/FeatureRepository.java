package com.up42.imagemetadata.domain;

import com.up42.imagemetadata.domain.models.Feature;

import java.util.List;
import java.util.Optional;

public interface FeatureRepository {
    Optional<List<Feature>> getAll();
}
