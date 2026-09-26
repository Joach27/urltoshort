package com.joach27.urltoshort.ipresolver;

import org.springframework.stereotype.Service;

@Service 
public  class IpAddressService {

    public String getClientIp(String xForwardedFor, String remoteAddr){
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        return remoteAddr;
    }
}