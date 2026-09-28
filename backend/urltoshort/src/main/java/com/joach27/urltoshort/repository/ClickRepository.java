package com.joach27.urltoshort.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.joach27.urltoshort.entity.Click;

public interface ClickRepository extends JpaRepository<Click, Long>{

    // Number of click
    Long countByLinkId(Long linkId);

    // Number of clicks by device type
    @Query("SELECT c.device, COUNT(c) FROM Click c WHERE c.link.id = :linkId GROUP BY c.device")
    List<Object[]> countByDeviceType(@Param("linkId") Long linkId);
    
    // Number of clicks by country
    @Query("SELECT c.country, COUNT(c) FROM Click c WHERE c.link.id = :linkId GROUP BY c.country")
    List<Object[]> countByCountry(@Param("linkId") Long linkId);
}