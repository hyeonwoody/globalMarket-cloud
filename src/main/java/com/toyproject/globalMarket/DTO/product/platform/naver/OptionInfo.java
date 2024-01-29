package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.ArrayList;

public class OptionInfo{
    public String simpleOptionSortType;
    public ArrayList<OptionSimple> optionSimple;
    public class OptionSimple{
        public int id;
        public String groupName;
        public String name;
        public boolean usable;
    }
    public ArrayList<OptionCustom> optionCustom;
    public class OptionCustom{
        public int id;
        public String groupName;
        public String name;
        public boolean usable;
    }
    public String optionCombinationSortType;
    public OptionCombinationGroupNames optionCombinationGroupNames;
    public class OptionCombinationGroupNames{
        public String optionGroupName1;
        public String optionGroupName2;
        public String optionGroupName3;
        public String optionGroupName4;
    }
    public ArrayList<OptionCombination> optionCombinations;
    public class OptionCombination{
        public int id;
        public String optionName1;
        public String optionName2;
        public String optionName3;
        public String optionName4;
        public int stockQuantity;
        public int price;
        public String sellerManagerCode;
        public boolean usable;
    }
    public ArrayList<StandardOptionGroup> standardOptionGroups;
    public class StandardOptionGroup{
        public String groupName;
        public ArrayList<StandardOptionAttribute> standardOptionAttributes;
        public class StandardOptionAttribute{
            public int attributeId;
            public int attributeValueId;
            public String attributeValueName;
            public ArrayList<String> imageUrls;
        }
    }
    public ArrayList<OptionStandard> optionStandards;
    public class OptionStandard{
        public int id;
        public String optionName1;
        public String optionName2;
        public int stockQuantity;
        public String sellerManagerCode;
        public boolean usable;
    }
    public boolean useStockManagement;
    public ArrayList<String> optionDeliveryAttributes;
}
