package com.toyproject.globalMarket.entity;

import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="category_naver")
public class CategoryNaverEntity extends CategoryNaverDTO{

    @Id
    private Long category_naver_id;

    @Column(name="whole_category_name", nullable = false)
    public String wholeCategoryName;

    @Column(name="name", nullable = false)
    public String name;

    @Column(name="last", nullable = false)
    public boolean last;
}
