package com.toyproject.globalMarket.entity;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "api_secret")
public class APISecretEntity {

    @Id
    @Column(name = "secret_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long secret_id;

    @Column(name = "secret", length = 50)
    private String secret;

    @OneToOne
    @PrimaryKeyJoinColumn (name ="prev")
    @JoinTable(
            name = "api_secret",
            joinColumns = {@JoinColumn(name = "prev", referencedColumnName = "prev")},
            inverseJoinColumns = {@JoinColumn(name = "secret_id", referencedColumnName = "secret_id")})
    private APISecretEntity prev;
}
