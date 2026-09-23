package com.joach27.urltoshort.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.joach27.urltoshort.entity.Click;

public interface ClickRepository extends JpaRepository<Click, Long>{

    List<Click> findByLinkId(Long linkId);
}