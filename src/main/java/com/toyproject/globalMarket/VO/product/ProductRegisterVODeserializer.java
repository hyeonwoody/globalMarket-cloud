package com.toyproject.globalMarket.VO.product;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.DTO.product.platform.naver.OptionInfo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProductRegisterVODeserializer extends JsonDeserializer<ProductRegisterVO> {


    private ArrayList<Object> getAsArray (JsonNode node){
        Class<?> elementType = null;

        ArrayNode arrayNode = (ArrayNode) node;
        ArrayList<Object> ret = new ArrayList<>();
        if (!arrayNode.isEmpty()){
            JsonNode firstElement = arrayNode.get(0);
            for (JsonNode element : arrayNode){
                if (element.isTextual()){
                    ret.add(element.asText());
                }
                else if (element.isInt()){
                    ret.add(element.asInt());
                }
                else if (element.isBoolean()){
                    ret.add(element.asBoolean());
                }
                else if (element.isBigInteger()) {
                    ret.add(element.asLong());
                }
                else if (element.isObject()){

                    ret.add(element);
                }
                else {
                    throw new IllegalStateException("Unsupported element type in ArrayNode");
                }
            }
        }
        return ret;
    }


    @Override
        public ProductRegisterVO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        ProductRegisterVO productRegisterVO = new ProductRegisterVO();
        JsonToken token = jsonParser.getCurrentToken();
        try {
            Class<ProductRegisterVO> thisClass = ProductRegisterVO.class;
            JsonNode node = null;
            try {
                node = jsonParser.readValueAs(JsonNode.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Field classField : thisClass.getDeclaredFields()) {
                classField.setAccessible(true);
                JsonNode jsonField = node.get(classField.getName());
                if (jsonField == null) {
                    continue;
                }
                Class<?> fieldType = classField.getType();
                try {
                    if (jsonField.isTextual()) {
                        String value = node.get(classField.getName()).asText();
                        classField.set(productRegisterVO, value);
                        int a = 0;
                    } else if (jsonField.isInt() && fieldType == int.class) {
                        int value = node.get(classField.getName()).asInt();
                        classField.set(productRegisterVO, value);
                    } else if (jsonField.isLong() && fieldType == Long.class) {
                        long value = node.get(classField.getName()).asLong();
                        classField.set(productRegisterVO, value);
                    } else if (fieldType == boolean.class) {
                        boolean value = node.get(classField.getName()).asBoolean();
                        classField.set(productRegisterVO, value);
                    } else if (fieldType == ArrayList.class) {
                        ArrayNode arrayNode = (ArrayNode) node.get(classField.getName());
                        classField.set(productRegisterVO, getAsArray(arrayNode).stream()
                                .filter(obj -> obj instanceof String)
                                .map(obj -> (String) obj)
                                .collect(Collectors.toCollection(ArrayList::new)));
                    } else if (fieldType == String[].class){
                        ArrayNode arrayNode = (ArrayNode) node.get(classField.getName());
                        classField.set(productRegisterVO, getAsArray(arrayNode).stream()
                                .filter(obj -> obj instanceof String)
                                .map(obj -> (String) obj)
                                .toArray(String[]::new));
                        int a = 0;
                    } else if (fieldType == ProductRegisterVO.Option.class) {
                        productRegisterVO.setOption(new ProductRegisterVO.Option());
                        switch (productRegisterVO.getOptionType()) {
                            case 0:

                                break;
                            case 2:
                                ObjectMapper objectMapper = new ObjectMapper();
                                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                productRegisterVO.getOption().setStandardOptionGroups(objectMapper.readValue
                                        (jsonField.get("standardOptionCategoryGroups").toString(), OptionInfo.StandardOptionGroup[].class));
                                for (OptionInfo.StandardOptionGroup standardOptionGroup : productRegisterVO.getOption().getStandardOptionGroups()){
                                    standardOptionGroup.setGroupName(standardOptionGroup.getAttributeName());
                                }
                                break;
                        }
                    } else if (fieldType == ProductRegisterVO.Platform.class){
                        int value = node.get(classField.getName()).asInt();
                        classField.set(productRegisterVO, ProductRegisterVO.Platform.values()[value]);
                        int a = 0;
                    } else if (fieldType == Images.class){
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        productRegisterVO.setImages(objectMapper.readValue
                                (jsonField.toString(), Images.class));
                        int a = 0;
                    }
                } catch (IllegalAccessException e) {
                    // Handle the exception gracefully
                    e.printStackTrace();
                    // You can choose to throw a custom exception, skip the field, etc.
                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return productRegisterVO;
    }

    @Override
    public ProductRegisterVO deserialize(JsonParser p, DeserializationContext ctxt, ProductRegisterVO intoValue) {
        return this.deserialize(p, ctxt);
    }
}
