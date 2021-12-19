package com.up42.imagemetadata.application.queryservices;

import com.up42.imagemetadata.application.dtos.FeatureDTO;
import com.up42.imagemetadata.domain.FeatureRepository;
import com.up42.imagemetadata.domain.QuickLook;
import com.up42.imagemetadata.domain.exceptions.ErrorCode;
import com.up42.imagemetadata.domain.exceptions.FeatureException;
import com.up42.imagemetadata.domain.models.Feature;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFeaturesQueryService {

    private final FeatureRepository featureRepository;

    public GetFeaturesQueryService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    /**
     * Returns all the features.
     *
     * @return a {@link List<FeatureDTO>}
     */
    public List<FeatureDTO> getFeatures() {
        return featureRepository.getAll()
                .map(this::mapToFeatureDTO)
                .orElseThrow(() ->
                        new FeatureException(ErrorCode.FEATURE_NOT_FOUND, null, "There is not any feature!"));
    }

    /**
     * Returns a single representation of a feature.
     *
     * @param id is String
     * @return a {@link FeatureDTO}
     */
    public FeatureDTO getFeatureById(String id) {
        return featureRepository.getById(id).stream()
                .map(this::mapToFeatureDTO)
                .filter(feature -> feature.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new FeatureException(ErrorCode.FEATURE_NOT_FOUND, id, "The feature not found!"));
    }

    /**
     * Returns Byte array which is related to base64 of image
     *
     * @param id is String
     * @return a {@link Byte[]}
     */
    public byte[] getImageById(String id) {
        Feature feature = featureRepository.getById(id).stream()
                .filter(f -> f.getProperties().getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new FeatureException(ErrorCode.FEATURE_NOT_FOUND, id, "The feature not found!"));

        return new QuickLook(feature.getProperties().getQuicklook()).toByteArray();
    }

    /**
     * Maps the {@link Feature} to a {@link FeatureDTO}.
     *
     * @param feature is a {@link Feature}.
     * @return a {@link FeatureDTO}
     */
    private FeatureDTO mapToFeatureDTO(Feature feature) {
        return FeatureDTO.builder()
                .id(feature.getProperties().getId())
                .timestamp(feature.getProperties().getTimestamp())
                .beginViewingDate(feature.getProperties().getAcquisition().getBeginViewingDate())
                .endViewingDate(feature.getProperties().getAcquisition().getEndViewingDate())
                .missionName(feature.getProperties().getAcquisition().getMissionName()).build();
    }

    /**
     * Maps the {@link List<Feature>} to the {@link List<FeatureDTO>}
     * @param features is the {@link List<Feature>}.
     * @return {@List List<FeatureDTO}
     */
    private List<FeatureDTO> mapToFeatureDTO(List<Feature> features) {
        return features.stream().map(this::mapToFeatureDTO).collect(Collectors.toList());
    }
}
