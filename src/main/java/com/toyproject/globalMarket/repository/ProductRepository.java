package com.toyproject.globalMarket.repository;

import com.toyproject.globalMarket.entity.ProductEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    default Long findMostResentId() {
        ProductEntity p = this.findTopByOrderByDescId();
        return p==null? 33: p.getId();
    }


    @Query(value = "SELECT product_id FROM product ORDER BY product_id DESC LIMIT 1", nativeQuery = true)
    ProductEntity findTopByOrderByDescId();


}
