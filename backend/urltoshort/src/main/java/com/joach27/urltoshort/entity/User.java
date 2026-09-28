package com.joach27.urltoshort.entity;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails {

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


    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities(){

        // Define role ; by defaut standard access
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override 
    public String getUsername(){
        // Get username
        return this.username;
    }

    @Override 
    public String getPassword(){
        // Get password
        return this.passwordStringHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return true; 
    }
}

