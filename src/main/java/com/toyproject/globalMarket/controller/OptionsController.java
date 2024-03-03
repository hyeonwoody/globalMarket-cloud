package com.toyproject.globalMarket.controller;
import com.toyproject.globalMarket.VO.option.StandardOptionVO;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.service.category.CategoryService;
import com.toyproject.globalMarket.service.option.OptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/options")
public class OptionsController extends BaseObject {

    private final OptionService optionService;
    private final CategoryService categoryService;


    @Autowired
    protected OptionsController(OptionService optionService, CategoryService categoryService) {
        super("CategoriesController", 0);
        this.optionService = optionService;
        this.categoryService = categoryService;
    }

    @Autowired
    APINaver naver;

    @GetMapping("/naver/standard-options")
    public ResponseEntity<StandardOptionVO> NaverStandardOptions (HttpServletRequest request) {
        StandardOptionVO standardOption = new StandardOptionVO();
        standardOption.setCategory(request.getParameter("category"));
        categoryService.getCategoryId(standardOption);
        optionService.getNaverProductStandardOption(standardOption);

        return ResponseEntity.ok(standardOption);
    }
}
