package com.toyproject.globalMarket.repository;

import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import com.toyproject.globalMarket.entity.CategoryNaverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryNaverEntity, Long> {

    @Query("SELECT u.category_naver_id FROM CategoryNaverEntity u WHERE u.name = :name")
    Long findIdByName(@Param("name") String lastCategory);

    @Query("SELECT u.category_naver_id FROM CategoryNaverEntity u WHERE u.whole_category_name = :whole_category_name")
    Long findIdByWhole_category_name(@Param("whole_category_name") String wholeName);

    @Query("SELECT u.name FROM CategoryNaverEntity u WHERE u.category_naver_id = :category_naver_id")
    String findNameByCategory_naver_id(@Param("category_naver_id") Long id);

    default List<CategoryNaverDTO> getCategoryNaverList () {
        return this.findAll().stream()
                .map (entity -> {
                    CategoryNaverDTO categoryNaverDTO = new CategoryNaverDTO();
                    categoryNaverDTO.setId(String.valueOf(entity.getCategory_naver_id()));
                    categoryNaverDTO.setWholeCategoryName(entity.getWhole_category_name());
                    categoryNaverDTO.setName(entity.getName());
                    categoryNaverDTO.setLast(entity.isLast());
                    return categoryNaverDTO;
                })
                .collect(Collectors.toList());
    }
    default void APItoSave(List<CategoryNaverDTO> cateogryNaverDTOList){
        for (CategoryNaverDTO categoryNaverDTO : cateogryNaverDTOList){
            CategoryNaverEntity categoryNaverEntity = new CategoryNaverEntity();
            categoryNaverEntity.setWhole_category_name(categoryNaverDTO.getWholeCategoryName());
            categoryNaverEntity.setName(categoryNaverDTO.getName());
            categoryNaverEntity.setLast(categoryNaverDTO.isLast());
            categoryNaverEntity.setCategory_naver_id (Long.parseLong(categoryNaverDTO.getId()));
            save(categoryNaverEntity);
        }
    }
}