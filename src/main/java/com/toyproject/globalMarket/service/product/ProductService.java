package com.toyproject.globalMarket.service.product;

import com.google.gson.Gson;
import com.toyproject.globalMarket.DTO.Product;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;

import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.service.product.store.StoreInterface;
import com.toyproject.globalMarket.service.product.store.aliExpress.AliExpress;
import okhttp3.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductService extends BaseObject {
    private static int objectId = 0;

    public ProductService(ProductRegisterVO productRegisterVO) {
        super("ProductService", objectId++);
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

        //product.setDTO("50002628");
        product.setTime (time);
        //product.setImage (productRegisterVO.getImages());
        //product.setDetailContent (productRegisterVO.getDetailContent());


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
                    .addHeader("content-type", "image/jpeg")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Request is Successful with code : {0}", response.code());
            } else {
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Request is Failed with code : {0}", response.code());
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Response message : {0}", response.message());
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Response body : {0}", response.body().string());

            }
            return response.code();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNewProductInfo (ProductRegisterVO productRegisterVO){
        StoreInterface store = null;
        if (productRegisterVO.getUrl().contains("aliexpress")){
            LogOutput(LOG_LEVEL.INFO, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "aaaREGISTER: " + "HERE");
            store = new AliExpress();
        }

        if (store != null){
            try {
                store.getProductInfo(productRegisterVO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }
}
