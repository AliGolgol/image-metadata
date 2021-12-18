package com.up42.imagemetadata.infrastructure;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.up42.imagemetadata.domain.models.Feature;
import com.up42.imagemetadata.domain.models.SourceData;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.of;

@Component
@Log4j2
public class Config {
    private final ResourceLoader resourceLoader;
    private Optional<List<SourceData>> features;
    Gson gson;

    public Config(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        gson = new Gson();
        readSourceData();
    }

    /**
     * @return {@link List< Feature >} which is optional
     */
    public Optional<List<SourceData>> getFeatures() {
        return features;
    }

    /**
     * Read source-data.txt
     */
    private void readSourceData() {
        try {
            features = of(List.of(gson.fromJson(readJson().toString(), SourceData[].class)));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * @return a {@link JsonElement} which includes Features.
     *
     * @throws IOException when the file not found.
     */
    private JsonElement readJson() throws IOException {
        String FILE_NAME = "source-data.txt";
        Resource resource = resourceLoader.getResource("classpath:/" + FILE_NAME);
        InputStream inputStream = resource.getInputStream();
        return JsonParser.parseReader(new InputStreamReader(inputStream, UTF_8));    }
}
