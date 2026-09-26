package com.joach27.urltoshort.service;

import org.springframework.stereotype.Service;

import com.joach27.urltoshort.repository.ClickRepository;

@Service 
public class ClickService {
    private final ClickRepository clickRepository;

    public ClickService(ClickRepository clickRepository){
        this.clickRepository = clickRepository;
    }

    // Number of click for a link
    public Long getNumberOfClickByLink(Long linkId){
        Long numberOfclick = clickRepository.countByLinkId(linkId);

        return numberOfclick;
    }
}