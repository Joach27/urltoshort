package com.joach27.urltoshort.analyzer;

import org.springframework.stereotype.Service;

import com.joach27.urltoshort.entity.Device;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Service 
public class UserAgentService {
    private final UserAgentAnalyzer analyzer;
    
    public UserAgentService() {
        this.analyzer = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withField("DeviceClass")
                .build();
    }

    // Get the device type directly
    public Device getDeviceType(String userAgent) {
        UserAgent agent = analyzer.parse(userAgent);

        String device = agent.getValue("DeviceClass");

        if (device == null) return Device.UNKNOWN;
        
        return switch (device) {
           	case "Phone" -> Device.MOBILE;
            case "Tablet" -> Device.TABLET;
            case "Desktop" -> Device.DESKTOP;                    
           	default -> Device.UNKNOWN;
        };
    }	
}