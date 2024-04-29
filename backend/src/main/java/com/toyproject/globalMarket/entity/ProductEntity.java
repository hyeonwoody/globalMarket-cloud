package com.toyproject.globalMarket.entity;

import com.toyproject.globalMarket.DTO.Product;

import jakarta.persistence.*;

@Entity
@Table(name="product")
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String platform_type;

    private String outsourced_url;
    private Long product_number;
    private int sale_price;
    private int sell_count;

    public void setEntity(Product product, String outSourcedUrl, Long productNumber) {
        this.platform_type = product.getPlatformType().toString();
        this.outsourced_url = outSourcedUrl;
        this.sale_price = product.getSalePrice();
        this.sell_count = 0;
        this.product_number = productNumber;
    }


    public void setEntity(Product _product) {
        this.platform_type = _product.getPlatformType().toString();
        this.sale_price = _product.getSalePrice();
    }

    public ProductEntity() {
        
    }


    public void setId(Long id) {
        this.product_id = id;
    }

    public Long getId() {
        return product_id;
    }

}
