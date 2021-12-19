package com.up42.imagemetadata.infrastructure.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.up42.imagemetadata.domain.models.SourceData;
import com.up42.imagemetadata.infrastructure.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
public class FeatureRepositoryImplTest {

    @Autowired
    FeatureRepositoryImpl featureRepository;

    @MockBean
    Config config;

    @Test
    void should_return_all_features() throws FileNotFoundException {
        when(config.getSourceData()).thenReturn(getAll());
        assertThat(featureRepository.getAll().get().size()).isGreaterThan(0);
    }

    private Optional<List<SourceData>> getAll() throws FileNotFoundException {
        Gson gson = new Gson();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String geoJsonFile = Objects.requireNonNull(classloader.getResource("source-data.txt")).getFile();
        JsonElement geoJsonElements = JsonParser.parseReader(new FileReader(geoJsonFile));
        SourceData[] sourceData = gson.fromJson(geoJsonElements.toString(), SourceData[].class);

        return Optional.of(List.of(sourceData));
    }
}
