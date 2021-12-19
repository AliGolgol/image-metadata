package com.up42.imagemetadata;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(version = "V1.0.0", title = "image-metadata Service API"))
@SpringBootApplication
public class ImageMetadataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageMetadataApplication.class, args);
    }

}
