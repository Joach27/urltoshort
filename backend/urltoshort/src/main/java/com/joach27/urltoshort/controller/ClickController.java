package com.joach27.urltoshort.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joach27.urltoshort.service.ClickService;

@RestController 
@RequestMapping("/api/v1")
public class ClickController {
    private final ClickService clickService;

    public ClickController(ClickService clickService){
        this.clickService = clickService;
    }

    @GetMapping("/links/{id}")
    public ResponseEntity<Long> getNumberOfClickForLink(@PathVariable Long id){
        Long numberOfClick = clickService.getNumberOfClickByLink(id);
        return ResponseEntity.ok(numberOfClick);
    }
	
}