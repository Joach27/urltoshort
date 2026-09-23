package com.joach27.urltoshort.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.joach27.urltoshort.entity.Link;

public interface LinkRepository extends JpaRepository<Link, Long>{
    List<Link> findByUserId(Long userId);
}