package com.toyproject.globalMarket.DTO.category;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryNaverDTO {
    @Column(name="whole_category_name", nullable = false)
    public String wholeCategoryName;

    private String id;

    @Column(name="name", nullable = false)
    public String name;

    @Column(name="last", nullable = false)
    public boolean last;
}
