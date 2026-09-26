package com.joach27.urltoshort.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joach27.urltoshort.entity.Click;

public interface ClickRepository extends JpaRepository<Click, Long>{

    // Number of click
    Long countByLinkId(Long linkId);
}