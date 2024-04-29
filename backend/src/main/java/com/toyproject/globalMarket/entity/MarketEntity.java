package com.toyproject.globalMarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "market")
public class MarketEntity {

    @JsonIgnore
    @Id
    @Column(name = "market_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 20, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", length = 100)
    private String password;


    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @JsonIgnore
    @Column(name = "api_secret")
    private String apiScret;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "market_id", referencedColumnName = "market_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<AuthorityEntity> authorities;

    @OneToOne
    @JoinTable(
            name = "api_secret",
            joinColumns = {@JoinColumn(name = "market_id", referencedColumnName = "market_id")},
            inverseJoinColumns = {@JoinColumn(name = "secret_id", referencedColumnName = "secret_id")})
    private APISecretEntity secret;

    @OneToMany
    @JoinTable(
            name = "product",
            joinColumns = {@JoinColumn(name = "market_id", referencedColumnName = "market_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")})

    private List<ProductEntity> products;
}
