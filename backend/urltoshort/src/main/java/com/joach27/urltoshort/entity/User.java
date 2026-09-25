package com.joach27.urltoshort.entity;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Users
 */

@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor 
@Entity 
@Table (name = "users")
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @Column (name = "username", nullable = false, unique = true)
    private String username;

    private String email;
    private String passwordStringHash;

    @CreationTimestamp 
    @Column (name = "created_at", updatable = false)
    private Instant createdAt;

    @OneToMany (mappedBy = "user")
    private List<Link> links = new ArrayList<>();
    
	
}

