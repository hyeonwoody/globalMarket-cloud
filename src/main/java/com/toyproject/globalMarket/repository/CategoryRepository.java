package com.toyproject.globalMarket.repository;

import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import com.toyproject.globalMarket.entity.CategoryNaverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryNaverEntity, Long> {

    default List<CategoryNaverDTO> getCategoryNaverList () {
        return this.findAll().stream()
                .map (entity -> {
                    CategoryNaverDTO categoryNaverDTO = new CategoryNaverDTO();
                    categoryNaverDTO.setId(String.valueOf(entity.getCategory_naver_id()));
                    categoryNaverDTO.setWholeCategoryName(entity.getWholeCategoryName());
                    categoryNaverDTO.setName(entity.getName());
                    categoryNaverDTO.setLast(entity.isLast());
                    return categoryNaverDTO;
                })
                .collect(Collectors.toList());
    }
    default void APItoSave(List<CategoryNaverDTO> cateogryNaverDTOList){
        for (CategoryNaverDTO categoryNaverDTO : cateogryNaverDTOList){
            CategoryNaverEntity categoryNaverEntity = new CategoryNaverEntity();
            categoryNaverEntity.setWholeCategoryName(categoryNaverDTO.getWholeCategoryName());
            categoryNaverEntity.setName(categoryNaverDTO.getName());
            categoryNaverEntity.setId(categoryNaverDTO.getId());
            categoryNaverEntity.setLast(categoryNaverDTO.isLast());
            categoryNaverEntity.setCategory_naver_id (Long.parseLong(categoryNaverDTO.getId()));
            save(categoryNaverEntity);
        }
    }
}