package com.joach27.urltoshort.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.joach27.urltoshort.dto.LinkAnalyticsResponse;
import com.joach27.urltoshort.entity.Device;
import com.joach27.urltoshort.repository.ClickRepository;

@Service 
public class AnalyticsService {

	private final ClickRepository clickRepository;

	public AnalyticsService(ClickRepository clickRepository){
	    this.clickRepository = clickRepository;
	}

	// Get total clik
	public LinkAnalyticsResponse getAnalytics(Long linkId) {
	
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