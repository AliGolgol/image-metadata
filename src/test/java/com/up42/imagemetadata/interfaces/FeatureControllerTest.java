package com.up42.imagemetadata.interfaces;

import com.google.gson.Gson;
import com.up42.imagemetadata.application.dtos.FeatureDTO;
import com.up42.imagemetadata.application.queryservices.GetFeaturesQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeatureController.class)
public class FeatureControllerTest {

    private static final String API_BASE_PATH = "/features";
    @MockBean
    GetFeaturesQueryService queryService;
    @Autowired
    private MockMvc mockMvc;
    Gson gson;
    String imageBase64;

    @BeforeEach
    void setup() throws FileNotFoundException {
        gson = new Gson();
        imageBase64 = getQuickLook();
    }

    @Test
    void should_return_all_features() throws Exception {
        var featureDTOs = List.of(FeatureDTO.builder()
                .id("39c2f29e-c0f8-4a39-a98b-deed547d6aea")
                .beginViewingDate(1554831167697L)
                .endViewingDate(1554831202043L)
                .timestamp(1554831167697L)
                .missionName("Sentinel-1B").build());
        when(queryService.getFeatures()).thenReturn(featureDTOs);

        mockMvc.perform(get(API_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(featureDTOs)));
    }

    @Test
    void should_return_the_feature_by_id() throws Exception {
        var featureId = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        var featureDTO = FeatureDTO.builder()
                .id(featureId)
                .beginViewingDate(1554831167697L)
                .endViewingDate(1554831202043L)
                .timestamp(1554831167697L)
                .missionName("Sentinel-1B").build();

        when(queryService.getFeatureById(featureId))
                .thenReturn(featureDTO);

        mockMvc.perform(get(API_BASE_PATH + "/" + featureId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(featureDTO)));
    }

    @Test
    void should_return_byt_array_of_image_base64() throws Exception {
        var featureId = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        var imageByteArray = Base64.getDecoder().decode(imageBase64);
        when(queryService.getImageById(featureId))
                .thenReturn(imageByteArray);

        mockMvc.perform(get(API_BASE_PATH + "/" + featureId + "/quicklook").accept(MediaType.IMAGE_PNG_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().bytes(imageByteArray));
    }

    private String getQuickLook() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("src/test/resources/quicklook.txt");
        return new BufferedReader(
                new InputStreamReader(fis, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining());
    }
}
