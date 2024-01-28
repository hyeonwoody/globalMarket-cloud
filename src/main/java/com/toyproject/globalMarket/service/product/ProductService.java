package com.toyproject.globalMarket.service.product;

import com.google.gson.Gson;
import com.toyproject.globalMarket.VO.ProductRegisterVO;
import com.toyproject.globalMarket.VO.ProductVO;
import com.toyproject.globalMarket.libs.EventManager;
import com.toyproject.globalMarket.service.product.store.Store;
import com.toyproject.globalMarket.service.product.store.aliExpress.AliExpress;
import okhttp3.*;

import java.io.IOException;


public class ProductService {
    ProductVO productVO;
    private ProductRegisterVO productRegister;
    public ProductService(ProductRegisterVO productRegister){
        this.productRegister = productRegister;
    }

    public void search(String accessToken) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.commerce.naver.com/external/v1/product-brands?name=나이키")
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().toString());
        if (response.isSuccessful()) {
            // If the response is successful (status code in the range of 200-299)
            // Print the response body
            System.out.println(response.body().string());
        } else {
            // If the response is not successful
            System.out.println("Request failed with status code: " + response.code());
        }
        int a = 0;
    }

    public int register(String accessToken, ProductVO productVO) {
        productVO.originProduct = new ProductVO.OriginProduct();
        productVO.originProduct.setName("fdfd");
        productVO.originProduct.setStatusTypedddd(ProductVO.OriginProduct.StatusType.SALE);

        OkHttpClient client = new OkHttpClient();
        try {
            MediaType mediaType = MediaType.parse("application/json");
            Gson gson = new Gson();
            String jsonBody = gson.toJson(productVO);
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
                // If the response is successful (status code in the range of 200-299)
                // Print the response body
                System.out.println(response.body().string());
            } else {
                // If the response is not successful
                System.out.println("Request failed with status code: " + response.code());
                System.out.println(response.body().string());
            }
            return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public ProductVO getNewProductInfo (){
        String url = productRegister.getUrl();
        Store store = null;
        if (url.contains("aliexpress")){
            EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "aaaREGISTER: " + "HERE");
            store = new AliExpress();
        }

        if (store != null){
            productVO = store.getProductInfo(url);
        }
        if (productVO.originProduct != null)
            productVO.originProduct.setName(productRegister.getName());
        return productVO;
    }


}
