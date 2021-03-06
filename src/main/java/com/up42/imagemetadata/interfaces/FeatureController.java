package com.up42.imagemetadata.interfaces;

import com.up42.imagemetadata.application.dtos.FeatureDTO;
import com.up42.imagemetadata.application.queryservices.GetFeaturesQueryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeatureController {

    private final GetFeaturesQueryService queryService;

    public FeatureController(GetFeaturesQueryService queryService) {
        this.queryService = queryService;
    }


    @GetMapping(value = "/features")
    public ResponseEntity<List<FeatureDTO>> getAll(){
        return ResponseEntity.ok(queryService.getFeatures());
    }

    @GetMapping(value = "/features/{id}")
    public ResponseEntity<FeatureDTO> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(queryService.getFeatureById(id));
    }

    @GetMapping(value = "/features/{id}/quicklook",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id){
        return ResponseEntity.ok(queryService.getImageById(id));
    }
}
