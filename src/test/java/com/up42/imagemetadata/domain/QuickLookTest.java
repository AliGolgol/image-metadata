package com.up42.imagemetadata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuickLookTest {
    String imageBase64;
    @BeforeEach
    void setup() throws FileNotFoundException {
        imageBase64 = getQuickLook();
    }

    @Test
    void should_returns_byte_array_of_image_base64(){
        QuickLook quickLook = new QuickLook(imageBase64);
        byte[] imageArray = quickLook.toByteArray();
        assertThat(imageArray).isNotEmpty();
    }

    private String getQuickLook() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("src/test/resources/quicklook.txt");
        return new BufferedReader(
                new InputStreamReader(fis, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining());
    }
}
