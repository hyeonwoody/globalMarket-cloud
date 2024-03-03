package com.toyproject.globalMarket.VO.option;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Data
public class StandardOptionVO {

    public void set (StandardOptionVO tmp){
        this.useStandardOption = tmp.useStandardOption;
        this.standardOptionCategoryGroups = tmp.standardOptionCategoryGroups;
    }

    @JsonIgnoreProperties
    private String category;
    @JsonIgnoreProperties
    private long categoryId;

    private boolean useStandardOption;
    private ArrayList<StandardOptionCategoryGroup> standardOptionCategoryGroups;



    @Setter
    @Getter
    public static class StandardOptionCategoryGroup {
        private Long attributeId;
        private String attributeName;
        private String groupName;
        private boolean imageRegistrationUsable;
        private boolean realValueUsable;
        private boolean optionSetRequired;
        private ArrayList<StandardOptionAttribute> standardOptionAttributes;

        @Setter
        @Getter
        public static class StandardOptionAttribute {
            private long attributeId;
            private long attributeValueId;
            private String attributeValueName;
            private String attributeColorCode;
            private ArrayList<String> imageUrls;
        }
    }





}
