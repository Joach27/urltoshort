package com.joach27.urltoshort.ipresolver;

import org.springframework.stereotype.Service;

@Service
public class IpAddressService {

    public String getClientIp(String xForwardedFor, String remoteAddr) {
        String rawIp = (xForwardedFor != null && !xForwardedFor.isBlank())
                ? xForwardedFor.split(",")[0].trim()
                : remoteAddr;

        return stripPort(rawIp);
    }

    private String stripPort(String ip) {
        if (ip == null || ip.isBlank()) {
            return ip;
        }
        // IPv6 avec port entre crochets : [2001:db8::1]:35156
        if (ip.startsWith("[") && ip.contains("]:")) {
            return ip.substring(1, ip.indexOf("]:"));
        }
        // IPv4 avec port ajouté par Azure : 196.117.156.224:35156
        if (ip.contains(".") && ip.contains(":")) {
            return ip.substring(0, ip.indexOf(":"));
        }
        return ip.trim();
    }
}