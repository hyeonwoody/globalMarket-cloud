package com.toyproject.globalMarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authority")
public class AuthorityEntity {
    @Id
    @Column(name = "authority_name", length = 50)
    private String authroityName;
}
