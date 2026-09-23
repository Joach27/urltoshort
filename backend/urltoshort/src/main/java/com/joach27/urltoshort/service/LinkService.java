package com.joach27.urltoshort.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import com.joach27.urltoshort.repository.LinkRepository;
import com.joach27.urltoshort.validator.UrlValidator;
import com.joach27.urltoshort.dto.CreateLinkRequest;
import com.joach27.urltoshort.dto.LinkResponse;
import com.joach27.urltoshort.entity.Link;
import com.joach27.urltoshort.generator.SlugGenerator;


@Service 
public class LinkService {

	private LinkRepository linkRepository;
	private final SlugGenerator slugGenerator;

	private final String baseUrl;

	public LinkService(
        LinkRepository linkRepository, 
        SlugGenerator slugGenerator, 
		@Value("${app.base-url}") String baseUrl) 
	{
	    this.linkRepository = linkRepository;
		this.slugGenerator = slugGenerator;
		this.baseUrl = baseUrl;
	}

	// Get all links
	public List<LinkResponse> getAllLinks(){
	    List<Link> links = linkRepository.findAll();

		return links.stream()
		            .map(link -> new LinkResponse(
						link.getTargetUrl(), 
						link.getSlug()
					))
					.toList();
	}

	// get link for particular user
	public List<LinkResponse> getLinkByUserId(Long userId){
	    List<Link> links = linkRepository.findByUserId(userId);

		return links.stream()
	        .map(link -> new LinkResponse(
				link.getTargetUrl(), 
				link.getSlug()
			))
			.toList();
	}

	// Create a short URL
	/* 
	1. Validate URL
	2. Generate slug
	3. Create the link
	4. Save the link 
	5. Construct the response (LinkResponse)
	*/

	public LinkResponse createShortUrl(CreateLinkRequest request){
	    String targetLink = request.targetUrl();

		// Validation 
		UrlValidator.validateUrl(targetLink);

		// Create and save link so we can have id
		Link link = new Link();
		link.setTargetUrl(targetLink);
		linkRepository.save(link);
		
		// Slug generation
		String slug = slugGenerator.encodeId(link.getId());

		// Create the link
		String shortUrl = baseUrl + "/" + slug;

		// Construct the response
		LinkResponse response = new LinkResponse(
		    targetLink, 
			shortUrl
		);

		// Save Link
		link.setSlug(slug);
		linkRepository.save(link);
		
		return response;
	}

}