package com.toyproject.globalMarket.DTO.product.platform.naver;

import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import java.util.ArrayList;

public class OptionInfo{
    public OptionInfo (ProductRegisterVO object){
        switch (object.getOptionType()){
            case 0://단독
                this.optionSimple = new ArrayList<>();
                for (ProductRegisterVO.Option option : object.getOptionList()){
                    String[] nameList = option.getName().split(",");
                    for (String name : nameList){
                        OptionSimple optionSimple = new OptionSimple ();
                        optionSimple.groupName = option.getGroupName();
                        optionSimple.name = name;
                        optionSimple.usable = true;
                        this.optionSimple.add(optionSimple);
                    }
                }
                break;
            case 1://조합
                break;
            default:
                break;
        }
    }
    public String simpleOptionSortType;
    public ArrayList<OptionSimple> optionSimple;
    public class OptionSimple{
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
        OptionCombinationGroupNames (ArrayList<ProductRegisterVO.Option> optionList){
            if (optionList.size() > 0) {
                this.optionGroupName1 = optionList.get(0).getGroupName();
            }
            if (optionList.size() > 1) {
                this.optionGroupName2 = optionList.get(1).getGroupName();
            }
            if (optionList.size() > 2) {
                this.optionGroupName3 = optionList.get(2).getGroupName();
            }
        }
        public String optionGroupName1;
        public String optionGroupName2;
        public String optionGroupName3;
        public String optionGroupName4;
    }
    public ArrayList<OptionCombination> optionCombinations;
    public class OptionCombination{
        OptionCombination (ArrayList<ProductRegisterVO.Option> optionList) {
            if (optionList.size() > 0) {
                this.optionName1 = optionList.get(0).getGroupName();
            }
            if (optionList.size() > 1) {
                this.optionName2 = optionList.get(1).getGroupName();
            }
            if (optionList.size() > 2) {
                this.optionName3 = optionList.get(2).getGroupName();
            }
        }
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
