package com.joach27.urltoshort.ipresolver;

import java.io.IOException;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;

import org.springframework.core.io.Resource;

@Service 
public class GeoIpService {

	private final DatabaseReader databaseReader;

    public GeoIpService(
                @Value("${geoip.database}") Resource database
        ) throws IOException {

        this.databaseReader = new DatabaseReader.Builder(
                database.getInputStream()
        ).build();
    } 

    public String getCountryName(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);

            CountryResponse response = databaseReader.country(ipAddress);

            return response.country().name(); // can get ISO code by isoCode()

        } catch (AddressNotFoundException e) {
            return null;
        } catch (IOException | GeoIp2Exception e) {
            throw new RuntimeException(
                    "Erreur lors de la résolution GeoIP pour : " + ip,
                    e
            );
        }
    }
}