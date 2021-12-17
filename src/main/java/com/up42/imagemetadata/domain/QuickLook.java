package com.up42.imagemetadata.domain;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

@Log4j2
public class QuickLook {
    private final String imageBase64;

    public QuickLook(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    /**
     * Returns Byte array which is related to base64 of image
     *
     * @return a {@link Byte[]}
     */
    public byte[] toByteArray() {
        byte[] imageByte = null;
        try {
            InputStream imageStream = new ByteArrayInputStream(this.imageBase64.getBytes(StandardCharsets.UTF_8));
            String imageBase64 = new BufferedReader(
                    new InputStreamReader(imageStream))
                    .lines()
                    .collect(Collectors.joining());
            imageByte = Base64.getDecoder().decode(imageBase64);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return imageByte;
    }
}
