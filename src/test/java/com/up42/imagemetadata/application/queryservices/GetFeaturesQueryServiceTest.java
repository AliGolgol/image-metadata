package com.up42.imagemetadata.application.queryservices;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.up42.imagemetadata.application.dtos.FeatureDTO;
import com.up42.imagemetadata.domain.FeatureRepository;
import com.up42.imagemetadata.domain.exceptions.FeatureException;
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
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Optional<List<Feature>> features = of(getAll().collect(Collectors.toList()));
        when(repository.getAll()).thenReturn(features);
        var featureList = queryService.getFeatures();

        assertThat(featureList.size()).isGreaterThan(0);
    }

    @Test
    void should_throws_FeatureException_when_list_is_empty() {
        Optional<List<Feature>> features = Optional.empty();
        when(repository.getAll()).thenReturn(features);
        FeatureException featureException = assertThrows(FeatureException.class,
                () -> queryService.getFeatures(),
                "There is not any feature!");

        assertThat(featureException.getMessage()).contains("There is not any feature!");
    }

    @Test
    void should_returns_feature_by_id() throws FileNotFoundException {
        String id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        when(repository.getAll()).thenReturn(of(getAll().collect(Collectors.toList())));
        when(repository.getById(id)).thenReturn(getAll().findFirst());
        var feature = queryService.getFeatureById(id);

        assertThat(feature.getId()).isEqualTo(id);
    }

    @Test
    void should_trow_FeatureException_when_feature_not_found() throws FileNotFoundException {
        String id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        when(repository.getAll()).thenReturn(of(getAll().collect(Collectors.toList())));
        when(repository.getById(id)).thenReturn(empty());

        var featureException = assertThrows(FeatureException.class,
                () -> queryService.getFeatureById(id),
                "The feature not found!");
        assertThat(featureException.getMessage()).contains("The feature not found!");
    }

    @Test
    void should_return_byte_array_of_image_base64() throws FileNotFoundException {
        String id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        when(repository.getAll()).thenReturn(of(getAll().collect(Collectors.toList())));
        when(repository.getById(id)).thenReturn(getAll().findFirst());
        var imageByteArray = queryService.getImageById(id);

        assertThat(imageByteArray).isNotEmpty();
    }

    @Test
    void should_throws_FeatureException_when_the_id_is_not_valid() throws FileNotFoundException {
        var featureException = assertThrows(FeatureException.class,
                () -> queryService.getImageById(""),
                "The id is not valid!");
        assertThat(featureException.getMessage()).contains("The id is not valid!");
    }
    private Stream<Feature> getAll() throws FileNotFoundException {
        Gson gson = new Gson();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String geoJsonFile = Objects.requireNonNull(classloader.getResource("source-data.txt")).getFile();
        JsonElement geoJsonElements = JsonParser.parseReader(new FileReader(geoJsonFile));
        SourceData[] sourceData = gson.fromJson(geoJsonElements.toString(), SourceData[].class);

        return Arrays.stream(sourceData).map(SourceData::getFeatures).flatMap(List::stream);
    }
}
