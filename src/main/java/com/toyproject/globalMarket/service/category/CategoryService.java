package com.toyproject.globalMarket.service.category;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import com.toyproject.globalMarket.DTO.product.platform.naver.*;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.entity.CategoryNaverEntity;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.repository.CategoryRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CategoryService extends BaseObject {
    private static int objectId = 0;
    private static List <CategoryNaverDTO> m_cateogryNaverDTOList;
    private final CategoryRepository categoryRepository;

    public static List<CategoryNaverDTO> getCateogryNaverDTOList() {
        return m_cateogryNaverDTOList;
    }

    public CategoryService(CategoryRepository categoryRepository) {
        super("CategoryService", objectId++);
        this.categoryRepository = categoryRepository;
    }

    public int getCategoryNaver(List<CategoryNaverDTO> cateogryNaverDTOList, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        int ret = 200;

        if (m_cateogryNaverDTOList == null || m_cateogryNaverDTOList.isEmpty()) {
            m_cateogryNaverDTOList = categoryRepository.getCategoryNaverList();
//                Request request = new Request.Builder()
//                        .url("https://api.commerce.naver.com/external/v1/categories?last=false")
//                        .get()
//                        .addHeader("Authorization", "Bearer " + accessToken)
//                        .build();
//                Response response = client.newCall(request).execute();
//                ObjectMapper objectMapper = new ObjectMapper();
//                m_cateogryNaverDTOList = new ArrayList<>();
//                m_cateogryNaverDTOList.addAll(objectMapper.readValue(response.body().string(), new TypeReference<List<CategoryNaverDTO>>() {
//                }));
//                ret = response.code();
        }
        //categoryRepository.APItoSave(m_cateogryNaverDTOList);


        cateogryNaverDTOList.addAll(m_cateogryNaverDTOList);
        return ret;


    }

    public int getNaverMap(Map<String, List<String>> categoryNaver) {
        for (CategoryNaverDTO cateogryNaverDTO : m_cateogryNaverDTOList) {
            String[] ret = cateogryNaverDTO.getWholeCategoryName().split(">");
            String key = null;
            String value = null;
            if (ret.length == 1){
                key = ret[0];
                List<String> tmp = categoryNaver.getOrDefault("FIRST", new ArrayList<>());
                tmp.add(key);
                categoryNaver.put("FIRST", tmp);
            }

            else {
                key = ret[ret.length-2];
                value = ret[ret.length-1];
            }
            List<String> tmp = categoryNaver.getOrDefault(key, new ArrayList<>());
            if (ret.length != 1)
                tmp.add(value);
            categoryNaver.put(key, tmp);
        }
        return 0;
    }

    public String findNaverLeafCategoryId(String category) {
        for (CategoryNaverDTO cateogryNaverDTO : m_cateogryNaverDTOList){
            if (category.equals(cateogryNaverDTO.getName()))
                return cateogryNaverDTO.getId();
        }
        return "50000000";
    }

    public void getNewCategoryInfo(ProductRegisterVO productSource) {
        if (m_cateogryNaverDTOList == null)
            m_cateogryNaverDTOList = categoryRepository.getCategoryNaverList();
        if (!m_cateogryNaverDTOList.isEmpty()) {
            for (CategoryNaverDTO categoryNaverDTO :m_cateogryNaverDTOList){
                if (categoryNaverDTO.getWholeCategoryName().equals(String.join(">", productSource.getCategory()))){
                    productSource.setWholeCategoryName(categoryNaverDTO.getWholeCategoryName());
                    productSource.setLeafCategoryId(categoryNaverDTO.getId());
                }
            }
        }
    }

    public void getAdditionalInfo(ProductRegisterVO productSource) {
        String[] productCategory = productSource.getCategory();
        switch (productCategory[0]) {
            case "패션의류":
                productSource.setAdditionalInfo(new ArrayList<>());
                productSource.getAdditionalInfo().add("제품 소재");
                productSource.getAdditionalInfo().add("색상");
                productSource.getAdditionalInfo().add("치수");
                productSource.getAdditionalInfo().add("제조사");
                productSource.getAdditionalInfo().add("세탁 방법 및 취급 시 주의사항");
                productSource.getAdditionalInfo().add("제조연월 : YYYY-MM");
                break;
            case "패션잡화":
                if (productCategory[1].contains("신발")) {
                    productSource.getAdditionalInfo().add("제품 소재");
                    productSource.getAdditionalInfo().add("색상");
                    productSource.getAdditionalInfo().add("치수");
                    productSource.getAdditionalInfo().add("제조사");
                    productSource.getAdditionalInfo().add("세탁 방법 및 취급 시 주의사항");

                } else if (productCategory[1].contains("가방")) {
                    productSource.getAdditionalInfo().add("제품 소재");
                    productSource.getAdditionalInfo().add("색상");
                    productSource.getAdditionalInfo().add("치수");
                    productSource.getAdditionalInfo().add("제조사");
                    productSource.getAdditionalInfo().add("세탁 방법 및 취급 시 주의사항");
                } else if (productCategory[1].contains("주얼리")) {

                    productSource.getAdditionalInfo().add("소재");
                    productSource.getAdditionalInfo().add("순도");
                    productSource.getAdditionalInfo().add("중량");
                    productSource.getAdditionalInfo().add("제조사");
                    productSource.getAdditionalInfo().add("치수");
                    productSource.getAdditionalInfo().add("착용 시 주의사항");
                    productSource.getAdditionalInfo().add("주요 사양");
                    productSource.getAdditionalInfo().add("보증서 제공 여부");
                    productSource.getAdditionalInfo().add("착용 시 주의사항");
                } else {
                    productSource.getAdditionalInfo().add("종류");
                    productSource.getAdditionalInfo().add("소재");
                    productSource.getAdditionalInfo().add("치수");
                    productSource.getAdditionalInfo().add("제조사");
                    productSource.getAdditionalInfo().add("취급 시 주의사항");
                }
                break;
            case "가구/인테리어":
                if (productCategory[1].contains("침구") || productCategory[1].contains("솜류") || productCategory[1].contains("커튼/블라인드")) {

                } else {

                }
                break;
            case "디지털/가전":

                if (productCategory[1].contains("영상가전")){

                }
                else if (productCategory[1].contains("주방가전") || productCategory[1].contains("생활가전")){

                }
                else if (productCategory[1].contains("계절가전")){

                }
                else if (productCategory[1].contains("PC") || productCategory[1].contains("노트북") || productCategory[1].contains("주변기기")){

                }
                else if (productCategory[1].contains("카메라")){




                }
                else if (productCategory[2].contains("MP3") || productCategory[2].contains("PMP")){

                }
                else if (productCategory[1].contains("휴대폰") || productCategory[1].contains("태블릿")){

                }
                else if (productCategory[2].contains("내비게이션")){

                }
                else if (productCategory[1].contains("자동차")){

                }
                else {


                }
                break;
            case "생활/건강":
                if (productCategory[1].contains("자동차")){

                }
                else if (productCategory[1].contains("악기")){

                }
                else if (productCategory[1].contains("의료") || productCategory[1].contains("재활")){

                }
                else if (productCategory[1].contains("주방용품")){

                }
                else if (productCategory[1].contains("생활용품")){
//                    ProductCertificationInfo productCertificationInfo = new ProductCertificationInfo();
//                    productCertificationInfo.setCertificationKindType("OVERSEAS");
//                    this.originProduct.getDetailAttribute().productCertificationInfos = new ArrayList<ProductCertificationInfo>();
//                    this.originProduct.getDetailAttribute().productCertificationInfos.add (productCertificationInfo);
//                    this.originProduct.getDetailAttribute().certificationTargetExcludeContent = new CertificationTargetExcludeContent();
//                    this.originProduct.getDetailAttribute().certificationTargetExcludeContent.setKcExemptionType("OVERSEAS");
//                    this.originProduct.getDetailAttribute().certificationTargetExcludeContent.setKcCertifiedProductExclusionYn("KC_EXEMPTION_OBJECT");


                }
                else {

                }
                break;
            case "화장품/미용":

                break;
            case "식품":
                if (productCategory[1].contains("다이어트")){

                }
                else {

                }
                break;
            case "스포츠/레저":

                break;
            case "도서":

                break;
            default:
                break;
        }
    }
}


