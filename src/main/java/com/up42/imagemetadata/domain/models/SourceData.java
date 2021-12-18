package com.up42.imagemetadata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SourceData {
    private final List<Feature> features;
}
