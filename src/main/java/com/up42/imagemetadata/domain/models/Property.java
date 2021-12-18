package com.up42.imagemetadata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Property {
    private final String id;
    private final Acquisition acquisition;
    private final String quicklook;
}
