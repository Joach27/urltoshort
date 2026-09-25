package com.joach27.urltoshort.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;


/**
 * Click
 */

 @Entity 
 @Data 
 @AllArgsConstructor 
 @NoArgsConstructor 
 @Table (name = "clicks")
public class Click {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "link_id", nullable = false)
    private Link link;

    @CreationTimestamp 
    @Column (name = "created_at", updatable = false)
    private Instant CreatedAt;

    private String country;
    private Device device;
    
}
