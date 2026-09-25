package com.joach27.urltoshort.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import com.joach27.urltoshort.repository.ClickRepository;
import com.joach27.urltoshort.repository.LinkRepository;
import com.joach27.urltoshort.repository.UserRepository;
import com.joach27.urltoshort.validator.UrlValidator;
import com.joach27.urltoshort.dto.CreateLinkRequest;
import com.joach27.urltoshort.dto.LinkResponse;
import com.joach27.urltoshort.entity.Click;
import com.joach27.urltoshort.entity.Link;
import com.joach27.urltoshort.entity.User;
import com.joach27.urltoshort.generator.SlugGenerator;
import com.joach27.urltoshort.exception.UrlNotFoundException;


@Service 
public class LinkService {

	private final LinkRepository linkRepository;
	private final SlugGenerator slugGenerator;
	private final ClickRepository clickRepository;
	private final UserRepository userRepository;

	private final String baseUrl;

	public LinkService(
        LinkRepository linkRepository, 
        SlugGenerator slugGenerator, 
        ClickRepository clickRepository,
        UserRepository userRepository,
		@Value("${app.base-url}") String baseUrl) 
	{
	    this.linkRepository = linkRepository;
		this.slugGenerator = slugGenerator;
		this.clickRepository = clickRepository;
		this.userRepository = userRepository;
		this.baseUrl = baseUrl;
	}

	// Get all links
	public List<LinkResponse> getAllLinks(){
	    List<Link> links = linkRepository.findAll();

		return links.stream()
            .map(link -> new LinkResponse(
				link.getTargetUrl(), 
				baseUrl + "/" + link.getSlug()
			))
			.toList();
	}

	// get link for particular user
	public List<LinkResponse> getLinkByUserId(Long userId){
	    List<Link> links = linkRepository.findByUserId(userId);

		return links.stream()
	        .map(link -> new LinkResponse(
				link.getTargetUrl(), 
				baseUrl + "/" + link.getSlug()
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

		// Get the user who's creating the link
		User user = userRepository.findById(request.userId())
		            .orElseThrow(() -> new RuntimeException("User Not Found"));
		
		// Create and save link so we can have id && associate user
		Link link = new Link();
		link.setTargetUrl(targetLink);
		link.setUser(user);

		// Save link to get ID and the use it to generate slug
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

	// Resolve (go from slug to target URL) and track
	public String resolveAndTrack(String slug){
	    // Get the id from the slug
		Long id = slugGenerator.decodeSlug(slug);

		// Get the link associated
		Link link = linkRepository.findById(id)
		                .orElseThrow(() -> new UrlNotFoundException("URL Not Found"));

		// Create clik
		Click click = new Click();
		click.setLink(link);

		// Save the click 
		clickRepository.save(click);

		return link.getTargetUrl();
	}

}