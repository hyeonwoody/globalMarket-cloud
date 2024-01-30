package com.toyproject.globalMarket.service.product;

import com.google.gson.Gson;
import com.toyproject.globalMarket.DTO.Product;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;

import com.toyproject.globalMarket.configuration.platform.Naver;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.libs.EventManager;
import com.toyproject.globalMarket.service.product.store.StoreInterface;
import com.toyproject.globalMarket.service.product.store.aliExpress.AliExpress;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductService extends BaseObject {
    private final ProductRegisterVO productRegisterVO;
    private static int objectId = 0;

    public ProductService(ProductRegisterVO productRegisterVO) {

        super("ProductService", objectId++);
        LogOutput(LOG_LEVEL.DEBUG, ObjectName(),MethodName(),0, " productRegister platform {0}", productRegisterVO.getPlatform());
        this.productRegisterVO = productRegisterVO;
    }


    public void search(String accessToken) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.commerce.naver.com/external/v1/product-brands?name=나이키")
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
        } else {
            LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Request is Successful with code : {0}", response.code());
        }
        int a = 0;
    }

    public int register (ProductRegisterVO productRegisterVO, String accessToken){
        Product product = new Product (productRegisterVO.getPlatform().ordinal());
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String time = dateFormat.format(now);
        productRegisterVO.setCurrentTime (time);
        product.setDTO(productRegisterVO);

        OkHttpClient client = new OkHttpClient();
        try {
            MediaType mediaType = MediaType.parse("application/json");
            Gson gson = new Gson();
            String jsonBody = gson.toJson(product.getDTO());
            System.out.println(jsonBody);
            RequestBody body = RequestBody.create(mediaType, jsonBody);
            Request request = new Request.Builder()
                    .url("https://api.commerce.naver.com/external/v2/products")
                    .post(body)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Request is Successful with code : {0}", response.code());
            } else {
                // If the response is not successful
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Request is Failed with code : {0}", response.code());
            }
            return response.code();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNewProductInfo (ProductRegisterVO productRegisterVO){
        String url = this.productRegisterVO.getUrl();
        StoreInterface store = null;
        if (url.contains("aliexpress")){
            LogOutput(LOG_LEVEL.INFO, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "aaaREGISTER: " + "HERE");
            store = new AliExpress();
        }

        if (store != null){
            try {
                store.getProductInfo(productRegisterVO, url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }
}
