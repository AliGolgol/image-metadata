package com.up42.imagemetadata.application.queryservices;

import com.up42.imagemetadata.domain.FeatureRepository;
import com.up42.imagemetadata.domain.exceptions.ErrorCode;
import com.up42.imagemetadata.domain.exceptions.FeatureException;
import com.up42.imagemetadata.domain.models.Feature;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFeaturesQueryService {

    private final FeatureRepository featureRepository;

    public GetFeaturesQueryService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    /**
     * Returns all the features.
     *
     * @return a {@link List<Feature>}
     */
    public List<Feature> getFeatures() {
        return featureRepository.getAll()
                .orElseThrow(() ->
                        new FeatureException(ErrorCode.FEATURE_NOT_FOUND, null, "There is not any feature!"));
    }
}
