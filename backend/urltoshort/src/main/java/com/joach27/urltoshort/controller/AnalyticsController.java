package com.joach27.urltoshort.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joach27.urltoshort.dto.LinkAnalyticsResponse;
import com.joach27.urltoshort.service.AnalyticsService;

@RestController 
@RequestMapping("/api/v1/links")
public class AnalyticsController {

	private final AnalyticsService analyticsService;

	public AnalyticsController(AnalyticsService analyticsService){
	    this.analyticsService = analyticsService;
	}

	@GetMapping("/{id}/analytics")
	public ResponseEntity<LinkAnalyticsResponse> getLinkAnalytics(@PathVariable("id") Long linkId){
        LinkAnalyticsResponse response = analyticsService.getAnalytics(linkId);

        return ResponseEntity.ok(response);
	}
}