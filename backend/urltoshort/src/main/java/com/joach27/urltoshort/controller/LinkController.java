package com.joach27.urltoshort.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joach27.urltoshort.dto.CreateLinkRequest;
import com.joach27.urltoshort.dto.LinkResponse;
import com.joach27.urltoshort.service.LinkService;


@RestController 
@RequestMapping("/api/v1/links")
public class LinkController {

	private final LinkService linkService;

	public LinkController(LinkService linkService){
	    this.linkService = linkService;
	}

	// Create a short link
	@PostMapping
	public ResponseEntity<LinkResponse> ceateShortLink(@RequestBody CreateLinkRequest request){

	    // Create the short link
        LinkResponse response = linkService.createShortUrl(request);

        // Formatted answer
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
	}
}