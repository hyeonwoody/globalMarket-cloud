package com.toyproject.globalMarket.service.category;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import com.toyproject.globalMarket.VO.option.StandardOptionVO;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import com.toyproject.globalMarket.entity.CategoryNaverEntity;
import com.toyproject.globalMarket.repository.CategoryRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


import static org.junit.jupiter.api.Assertions.*;


class CategoryServiceTest {


    private CategoryService categoryService;


    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = new CategoryRepository() {
            @Override
            public List<CategoryNaverEntity> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<CategoryNaverEntity> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends CategoryNaverEntity> S save(S entity) {
                return null;
            }

            @Override
            public <S extends CategoryNaverEntity> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<CategoryNaverEntity> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public List<CategoryNaverEntity> findAll() {
                return null;
            }

            @Override
            public List<CategoryNaverEntity> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(CategoryNaverEntity entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends CategoryNaverEntity> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends CategoryNaverEntity> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends CategoryNaverEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<CategoryNaverEntity> entities) {
                CategoryRepository.super.deleteInBatch(entities);
            }

            @Override
            public void deleteAllInBatch(Iterable<CategoryNaverEntity> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public CategoryNaverEntity getOne(Long aLong) {
                return null;
            }

            @Override
            public CategoryNaverEntity getById(Long aLong) {
                return null;
            }

            @Override
            public CategoryNaverEntity getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends CategoryNaverEntity> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends CategoryNaverEntity> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends CategoryNaverEntity> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends CategoryNaverEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends CategoryNaverEntity> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends CategoryNaverEntity> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends CategoryNaverEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }

            @Override
            public Long findIdByName(String lastCategory) {
                return 22L;
            }

            @Override
            public String findNameByCategory_naver_id(Long id) {
                return "aa";
            }

            @Override
            public List<CategoryNaverDTO> getCategoryNaverList() {
                return CategoryRepository.super.getCategoryNaverList();
            }

            @Override
            public void APItoSave(List<CategoryNaverDTO> cateogryNaverDTOList) {
                CategoryRepository.super.APItoSave(cateogryNaverDTOList);
            }
        }; // or create a mock if needed
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void getCategoryId (){
        StandardOptionVO standardOptionVO = new StandardOptionVO();
        String[] category = {"스포츠/레저","마라톤용품"};
        standardOptionVO.setCategory(category);
        categoryService.getCategoryId(standardOptionVO);
        assertNotEquals(standardOptionVO.getCategoryId(), -1);
    }

    @Test
    void getCategoryNaver() {
        //given
        List<CategoryNaverDTO> cateogryNaverDTOList = new ArrayList<CategoryNaverDTO>();
        APINaver naver = new APINaver();
        String accessToken = naver.getOAuth();

        //when
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.commerce.naver.com/external/v1/categories?last=false")
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();

            ObjectMapper objectMapper = new ObjectMapper();
            cateogryNaverDTOList.addAll(objectMapper.readValue(response.body().string(), new TypeReference<List<CategoryNaverDTO>>() {
            })); // Add elements to the original list
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotEquals(0, cateogryNaverDTOList.size());

        for (CategoryNaverDTO cateogry : cateogryNaverDTOList){
            System.out.println (cateogry.getId() + cateogry.getWholeCategoryName() + cateogry.getName() + cateogry.isLast());
        }
    }
}