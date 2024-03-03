package com.toyproject.globalMarket.VO.option;

import lombok.Data;

import java.util.ArrayList;

@Data
public class StandardOptionVO {

    public void set (StandardOptionVO tmp){
        this.useStandardOption = tmp.useStandardOption;
        this.standardOptionCategoryGroups = tmp.standardOptionCategoryGroups;
    }

    private String category;
    private long categoryId;
    private boolean useStandardOption;
    private ArrayList<StandardOptionCategoryGroup> standardOptionCategoryGroups;



    static private class StandardOptionCategoryGroup {
        private int attributeId;
        private String attributeName;
        private String groupNames;
        private boolean imageRegistrationUsable;
        private boolean realValueUsable;
        private boolean optionSetRequired;
        private ArrayList<StandardOptionAttribute> standardOptionAttributes;
        static private class StandardOptionAttribute {
            private long attributeId;
            private long attributeValueId;
            private String attributeValueName;
            private String attributeColorCOde;
            private ArrayList<String> imageUrls;
        }
    }





}
