package com.up42.imagemetadata.application.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeatureDTO {
    private final String id;
    private final long timestamp;
    private final long beginViewingDate;
    private final long endViewingDate;
    private final String missionName;
}
