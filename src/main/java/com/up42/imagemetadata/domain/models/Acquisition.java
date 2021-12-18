package com.up42.imagemetadata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Acquisition {
    private final long endViewingDate;
    private final String mission;
    private final String missionId;
    private final String missionCode;
    private final long beginViewingDate;
    private final String missionName;
    private final String polarization;
    private final String sensorMode;
    private final String sensorId;
}
