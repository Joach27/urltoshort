package com.joach27.urltoshort.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joach27.urltoshort.service.LinkService;

import jakarta.servlet.http.HttpServletRequest;

@RestController 
@RequestMapping("")
public class RedirectController {

    private final LinkService linkService;
   
	public RedirectController(LinkService linkService){
	    this.linkService = linkService;
	}

	// Get target link for a slug [Click on the short link]
	@GetMapping ("/{slug}")
	public ResponseEntity<Void> getTargetUrl(@PathVariable String slug, HttpServletRequest request){
	    // Extract User-Agent
		String userAgent = request.getHeader("User-Agent");
		String xForwardedFor = request.getHeader("X-Forwarded-For");

		String remoteAddress = request.getRemoteAddr();
	
	    // Resolve and track click on the link 
	    String targetUrl = linkService.resolveAndTrack(slug, userAgent, xForwardedFor, remoteAddress);

		return ResponseEntity
		        .status(HttpStatus.FOUND)
				.location(URI.create(targetUrl))
				.build();
	}
}