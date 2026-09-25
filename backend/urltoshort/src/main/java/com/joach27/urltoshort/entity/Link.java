package com.joach27.urltoshort.entity;

import org.hibernate.annotations.CreationTimestamp;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;



/**
 * Links
 */

@Entity 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table (name = "links")
public class Link {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug", unique = true)
    private String slug;

    private String targetUrl;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name="user_id", nullable = false)
    private User user;

    @OneToMany (mappedBy = "link")
    private List<Click> clicks = new ArrayList<>();

    @CreationTimestamp 
    @Column (name = "created_at", updatable = false)
    private Instant createdAt;
}