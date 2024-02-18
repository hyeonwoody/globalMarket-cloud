package com.toyproject.globalMarket.entity;

import com.toyproject.globalMarket.DTO.Product;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import jakarta.persistence.*;

@Entity
@Table(name="product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String platform_type;

    private String outsourced_url;
    private int sale_price;
    private int sell_count;

    public void setEntity(Product _product, String _outSourced_url) {
        this.platform_type = _product.getPlatformType().toString();
        this.outsourced_url = _outSourced_url;
        this.sale_price = _product.getSalePrice();
        this.sell_count = 0;
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
