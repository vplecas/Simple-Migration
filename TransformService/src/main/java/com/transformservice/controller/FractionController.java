package com.transformservice.controller;

import com.transformservice.mapper.FractionMapper;
import com.transformservice.domain.dto.FractionDto;
import com.transformservice.domain.dto.FractionsDto;
import com.transformservice.service.FractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/profile/{profileId}/fraction")
public class FractionController {

    private final FractionService fractionService;

    @Autowired
    public FractionController(FractionService fractionService) {
        this.fractionService = fractionService;
    }

    @GetMapping
    public ResponseEntity<List<FractionDto>> getAllByProfile(@PathVariable Long profileId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FractionMapper.toDtoList(fractionService.getAllByProfile(profileId)));
    }

    @GetMapping(path = "/{fractionId}")
    public ResponseEntity<FractionDto> getById(@PathVariable Long profileId,
                                               @PathVariable Long fractionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FractionMapper.toDto(fractionService.getById(profileId, fractionId)));
    }

    @PostMapping
    public ResponseEntity<List<FractionDto>> create(@PathVariable Long profileId,
                                                    @RequestBody @Validated FractionsDto fractionsDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(FractionMapper.toDtoList(fractionService.create(profileId, fractionsDto)));
    }

    @PutMapping
    public ResponseEntity<List<FractionDto>> update(@PathVariable Long profileId,
                                           @RequestBody FractionsDto fractionsDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FractionMapper.toDtoList(fractionService.update(profileId, fractionsDto)));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long profileId) {
        fractionService.delete(profileId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}