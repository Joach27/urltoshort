package com.joach27.urltoshort.dto;

import java.util.Map;

import com.joach27.urltoshort.entity.Device;


public record LinkAnalyticsResponse(Long totalClicks, Map<Device, Long> clicksByDevice, Map<String, Long> clicksByCountry){
}