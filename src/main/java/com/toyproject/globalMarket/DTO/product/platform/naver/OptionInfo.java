package com.toyproject.globalMarket.DTO.product.platform.naver;

import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import lombok.Getter;
import lombok.Setter;
import okio.Options;

import java.util.ArrayList;

@Getter
@Setter
public class OptionInfo{
    public OptionInfo (ProductRegisterVO object){
        switch (object.getOptionType()){
            case 0://단독
                this.optionSimple = new ArrayList<>();
                for (ProductRegisterVO.Option.Simple option : object.getOption().getSimple()){
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
            case 2:
                this.setStandardOptionGroups(new ArrayList<>());
                for (OptionInfo.StandardOptionGroup optionGroup : object.getOption().getStandardOptionGroups()){
                    if (optionGroup.getGroupName().contains("색상")){
                        optionGroup.setGroupName("색상");
                        optionGroup.setAttributeName("색상");
                    }
                    else if (optionGroup.getGroupName().contains("사이즈")){
                        optionGroup.setGroupName("사이즈");
                        optionGroup.setAttributeName("사이즈");
                    }
                    this.getStandardOptionGroups().add(optionGroup);
                }
                this.setOptionStandards(new ArrayList<>());
                for (OptionInfo.StandardOptionGroup.StandardOptionAttribute optionAttribute1 : object.getOption().getStandardOptionGroups()[0].getStandardOptionAttributes()){
                    OptionStandard optionStandard = new OptionStandard();
                    optionStandard.setOptionName1(optionAttribute1.getAttributeValueName());
                    optionStandard.setStockQuantity(832);
                    if (object.getOption().getStandardOptionGroups().length > 1 && object.getOption().getStandardOptionGroups()[1].getStandardOptionAttributes() != null) {
                        for (OptionInfo.StandardOptionGroup.StandardOptionAttribute optionAttribute2 : object.getOption().getStandardOptionGroups()[1].getStandardOptionAttributes()) {
                            OptionStandard combinedOption = new OptionStandard();
                            combinedOption.setOptionName2(optionAttribute1.getAttributeValueName());
                            combinedOption.setOptionName1(optionAttribute2.getAttributeValueName());
                            combinedOption.setStockQuantity(823);
                            combinedOption.setUsable(true);
                            optionStandards.add(combinedOption);
                        }
                        continue;
                    }
                    optionStandard.setUsable(true);
                    optionStandards.add(optionStandard);
                }
                break;
            default:
                break;
        }
    }
    public String simpleOptionSortType;
    private ArrayList<OptionSimple> optionSimple;

    private ArrayList<OptionCustom> optionCustom;
    private class OptionCustom{
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

    @Getter
    @Setter
    public static class StandardOptionGroup{
        private String groupName;
        private String attributeName;
        private ArrayList<StandardOptionAttribute> standardOptionAttributes;

        @Getter
        @Setter
        private static class StandardOptionAttribute{
            private int attributeId;
            private int attributeValueId;
            private String attributeValueName;
            private ArrayList<String> imageUrls;
        }
    }
    public ArrayList<OptionStandard> optionStandards;

    @Setter
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
