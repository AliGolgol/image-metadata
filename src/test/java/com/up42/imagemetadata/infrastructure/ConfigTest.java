package com.up42.imagemetadata.infrastructure;

import com.up42.imagemetadata.domain.models.SourceData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
public class ConfigTest {

    @Autowired
    Config config;

    @Test
    void should_returns_source_data_json() {
        Optional<List<SourceData>> sourceData = config.getSourceData();

        assertThat(sourceData.get().size()).isGreaterThan(0);
    }
}
