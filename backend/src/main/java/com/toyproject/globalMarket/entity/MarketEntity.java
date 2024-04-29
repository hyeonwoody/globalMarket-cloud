package com.toyproject.globalMarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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
}
