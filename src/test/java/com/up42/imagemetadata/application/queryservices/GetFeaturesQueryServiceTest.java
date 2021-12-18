package com.up42.imagemetadata.application.queryservices;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.up42.imagemetadata.domain.FeatureRepository;
import com.up42.imagemetadata.domain.models.Feature;
import com.up42.imagemetadata.domain.models.SourceData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
public class GetFeaturesQueryServiceTest {

    @Autowired
    GetFeaturesQueryService queryService;
    @MockBean
    FeatureRepository repository;

    @Test
    void should_returns_list_of_features() throws FileNotFoundException {
        Optional<List<Feature>> features = getAll();
        when(repository.getAll()).thenReturn(features);
        List<Feature> featureList = queryService.getFeatures();

        assertThat(featureList.size()).isGreaterThan(0);
    }

    private  Optional<List<Feature>> getAll() throws FileNotFoundException {
        Gson gson = new Gson();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String geoJsonFile = Objects.requireNonNull(classloader.getResource("source-data.txt")).getFile();
        JsonElement geoJsonElements = JsonParser.parseReader(new FileReader(geoJsonFile));
        SourceData[] sourceData = gson.fromJson(geoJsonElements.toString(), SourceData[].class);

        return of(Arrays.stream(sourceData).map(SourceData::getFeatures).flatMap(List::stream).collect(Collectors.toList()));
    }
}