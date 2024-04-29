package com.toyproject.globalMarket.repository;

import com.toyproject.globalMarket.entity.ProductEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    default Long findUpcommingId() {
        Long p = this.findLatestProductId();
        return p==null? 0: p+1;
    }

    @Query(value = "SELECT product_id FROM product ORDER BY product_id DESC LIMIT 1", nativeQuery = true)
    Long findLatestProductId();

}
