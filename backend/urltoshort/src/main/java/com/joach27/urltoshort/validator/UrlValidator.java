package com.joach27.urltoshort.validator;

import com.joach27.urltoshort.exception.InvalidUrlException;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlValidator{

    public static void validateUrl(String targetUrl) {

        // 1. Is the URL present ?
        if (targetUrl == null || targetUrl.isBlank()){
            throw new InvalidUrlException("URL shoud not be blank.");
        }

        
        try{
            // 2. Vérifier URI est correctement formés
            URI uri = new URI(targetUrl);

            // 3. Verify HTTPS
            // uri.getSechma can be null or be case sensitive
            if (!"https".equalsIgnoreCase(uri.getScheme())){
                throw new InvalidUrlException("URL should contain HTTPS");
            }

            // Verify HOST
            if (uri.getHost() == null || uri.getHost().isBlank()){
                throw new InvalidUrlException("Host is not reachable.");
            }
        } catch (URISyntaxException e){
            throw new InvalidUrlException("URL is syntaxical malformed.");
        }
    }
	
}