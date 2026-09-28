package com.joach27.urltoshort.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.joach27.urltoshort.dto.LinkAnalyticsResponse;
import com.joach27.urltoshort.entity.Device;
import com.joach27.urltoshort.entity.Link;
import com.joach27.urltoshort.entity.User;
import com.joach27.urltoshort.repository.ClickRepository;
import com.joach27.urltoshort.repository.LinkRepository;


@Service 
public class AnalyticsService {

	private final ClickRepository clickRepository;
	private final LinkRepository linkRepository;

	public AnalyticsService(ClickRepository clickRepository, LinkRepository linkRepository){
	    this.clickRepository = clickRepository;
		this.linkRepository = linkRepository;
	}

	// Get total clik
	public LinkAnalyticsResponse getAnalytics(Long linkId) {

        // 1. Get current authenticated user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
        // 2. Verify if link exists
        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Lien introuvable"));
	
        // 3. Verify is link belongs to user
        if (!link.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to view details about this link.");
        }

        
        // 4. If verification passes, we can continue
        Long totalClicks = clickRepository.countByLinkId(linkId);
    	
        Map<Device, Long> clicksByDevice = clickRepository.countByDeviceType(linkId)
            .stream()
            .collect(Collectors.toMap(
                row -> (Device) row[0],
                row -> (Long) row[1]
            ));
    	
        Map<String, Long> clicksByCountry = clickRepository.countByCountry(linkId)
            .stream()
            .collect(Collectors.toMap(
                row -> row[0] == null ? "UNKNOWN" : (String) row[0],
                row -> (Long) row[1]
            ));
    	
        return new LinkAnalyticsResponse(
            totalClicks,
            clicksByDevice,
            clicksByCountry
        );
	}
	
}