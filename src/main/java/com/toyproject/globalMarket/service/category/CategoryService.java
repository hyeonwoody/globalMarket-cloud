package com.toyproject.globalMarket.service.category;


import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import com.toyproject.globalMarket.VO.option.StandardOptionVO;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.repository.CategoryRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CategoryService extends BaseObject {
    private static int objectId = 0;
    private static List <CategoryNaverDTO> m_categoryNaverDTOList;
    private final CategoryRepository categoryRepository;

    public static List<CategoryNaverDTO> getCateogryNaverDTOList() {
        return m_categoryNaverDTOList;
    }

    public CategoryService(CategoryRepository categoryRepository) {
        super("CategoryService", objectId++);
        this.categoryRepository = categoryRepository;
    }

    public int getCategoryId (StandardOptionVO standardOption){
        String category = standardOption.getCategory();
        Long idOptional = categoryRepository.findIdByWhole_category_name(category);
        if (idOptional != null){
            standardOption.setCategoryId(idOptional);
            return 0;
        }
        else {
            standardOption.setCategoryId(-1);
        }
        return -1;
    }

    public int getCategoryNaverDB (List<CategoryNaverDTO> categoryNaverDTOList){
        List<CategoryNaverDTO> _categoryNaverDTOList = categoryRepository.getCategoryNaverList();
        categoryNaverDTOList.addAll(_categoryNaverDTOList);
        return 0;
    }

    public int getCategoryNaverAPI(List<CategoryNaverDTO> categoryNaverDTOList) {
        APINaver naver =new APINaver();
        naver.getCategory(categoryNaverDTOList);
        categoryRepository.APItoSave(categoryNaverDTOList);
        return 0;
    }

    public int getNaverMap(Map<String, List<String>> categoryNaver, List <CategoryNaverDTO> categoryNaverDTOList) {
        for (CategoryNaverDTO category : categoryNaverDTOList) {
            String[] categories = category.getWholeCategoryName().split(">");
            String parent = categories.length > 1 ? categories[categories.length - 2] : "FIRST";
            String child = categories.length > 1 ? categories[categories.length - 1] : categories[0];

            List<String> children = categoryNaver.getOrDefault(parent, new ArrayList<>());
            children.add(child);
            categoryNaver.put(parent, children);
        }
        return 0;
    }

    public String findNaverLeafCategoryId(String category) {
        for (CategoryNaverDTO cateogryNaverDTO : m_categoryNaverDTOList){
            if (category.equals(cateogryNaverDTO.getName()))
                return cateogryNaverDTO.getId();
        }
        return "50000000";
    }

    public void getNewCategoryInfo(ProductRegisterVO productSource) {
        if (m_categoryNaverDTOList == null)
            m_categoryNaverDTOList = categoryRepository.getCategoryNaverList();
        if (!m_categoryNaverDTOList.isEmpty()) {
            for (CategoryNaverDTO categoryNaverDTO :m_categoryNaverDTOList){
                if (categoryNaverDTO.getWholeCategoryName().equals(String.join(">", productSource.getCategory()))){
                    productSource.setWholeCategoryName(categoryNaverDTO.getWholeCategoryName());
                    productSource.setLeafCategoryId(categoryNaverDTO.getId());
                }
            }
        }
    }


    public ArrayList<String> getAdditionalInfoList(ProductRegisterVO productSource) {
        String[] productCategory = productSource.getCategory();
        productSource.setAdditionalInfoList(new ArrayList<>());
        switch (productCategory[0]) {
            case "패션의류":
                productSource.getAdditionalInfoList().add("제품 소재");
                productSource.getAdditionalInfoList().add("색상");
                productSource.getAdditionalInfoList().add("치수");
                productSource.getAdditionalInfoList().add("제조사");
                productSource.getAdditionalInfoList().add("세탁 방법 및 취급 시 주의사항");
                productSource.getAdditionalInfoList().add("제조연월 : YYYY-MM");
                break;
            case "패션잡화":
                if (productCategory[1].contains("신발")) {
                    productSource.getAdditionalInfoList().add("제품 소재");
                    productSource.getAdditionalInfoList().add("색상");
                    productSource.getAdditionalInfoList().add("치수");
                    productSource.getAdditionalInfoList().add("제조사");
                    productSource.getAdditionalInfoList().add("세탁 방법 및 취급 시 주의사항");

                } else if (productCategory[1].contains("가방")) {
                    productSource.getAdditionalInfoList().add("제품 소재");
                    productSource.getAdditionalInfoList().add("색상");
                    productSource.getAdditionalInfoList().add("치수");
                    productSource.getAdditionalInfoList().add("제조사");
                    productSource.getAdditionalInfoList().add("세탁 방법 및 취급 시 주의사항");
                } else if (productCategory[1].contains("주얼리")) {

                    productSource.getAdditionalInfoList().add("소재");
                    productSource.getAdditionalInfoList().add("순도");
                    productSource.getAdditionalInfoList().add("중량");
                    productSource.getAdditionalInfoList().add("제조사");
                    productSource.getAdditionalInfoList().add("치수");
                    productSource.getAdditionalInfoList().add("착용 시 주의사항");
                    productSource.getAdditionalInfoList().add("주요 사양");
                    productSource.getAdditionalInfoList().add("보증서 제공 여부");
                    productSource.getAdditionalInfoList().add("착용 시 주의사항");
                } else {
                    productSource.getAdditionalInfoList().add("종류");
                    productSource.getAdditionalInfoList().add("소재");
                    productSource.getAdditionalInfoList().add("치수");
                    productSource.getAdditionalInfoList().add("제조사");
                    productSource.getAdditionalInfoList().add("취급 시 주의사항");
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
                    productSource.getAdditionalInfoList().add("품명");
                    productSource.getAdditionalInfoList().add("모델명");
                    productSource.getAdditionalInfoList().add("KC 인증정보");
                    productSource.getAdditionalInfoList().add("동일 모델의 출시연월 'yyyy-MM'");
                    productSource.getAdditionalInfoList().add("제조자");
                    productSource.getAdditionalInfoList().add("크기");
                    productSource.getAdditionalInfoList().add("크기");
                    productSource.getAdditionalInfoList().add("중량");
                    productSource.getAdditionalInfoList().add("주요 사양");

                }
                else if (productCategory[2].contains("MP3") || productCategory[2].contains("PMP")){

                }
                else if (productCategory[1].contains("휴대폰액세서리")){
                    if (productCategory[1].contains("휴대폰액세서리")){
                        productSource.getAdditionalInfoList().add("아이템이름");
                        productSource.getAdditionalInfoList().add("모델명");
                        productSource.getAdditionalInfoList().add("제작사");
                    }
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
                if (productCategory[1].contains("자동차용품")){
                    productSource.getAdditionalInfoList().add("품명");
                    productSource.getAdditionalInfoList().add("모델명");
                    productSource.getAdditionalInfoList().add("제품 사용으로 인한 위험 및 유의 사항");
                    productSource.getAdditionalInfoList().add("제조자");
                    productSource.getAdditionalInfoList().add("크기");
                    productSource.getAdditionalInfoList().add("적용 차종");
                    productSource.getAdditionalInfoList().add("KC 인증정보");
                    productSource.getAdditionalInfoList().add("검사합격증 번호");
                    productSource.getAdditionalInfoList().add("동일 모델의 출시연월 'yyyy-MM'");
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
                    productSource.getAdditionalInfoList().add("아이템 이름");
                    productSource.getAdditionalInfoList().add("모델명");
                    productSource.getAdditionalInfoList().add("제조사");
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
                productSource.getAdditionalInfoList().add("품명");
                productSource.getAdditionalInfoList().add("모델명");
                productSource.getAdditionalInfoList().add("KC 인증정보");
                productSource.getAdditionalInfoList().add("크기");
                productSource.getAdditionalInfoList().add("중량");
                productSource.getAdditionalInfoList().add("색상");
                productSource.getAdditionalInfoList().add("재질");
                productSource.getAdditionalInfoList().add("재품 구성");
                productSource.getAdditionalInfoList().add("동일 모델의 출시연월 'yyyy-MM'");
                productSource.getAdditionalInfoList().add("제조사");
                productSource.getAdditionalInfoList().add("상품별 세부 사양");
                break;
            case "도서":

                break;
            default:
                break;
        }

        return productSource.getAdditionalInfoList();
    }
}


