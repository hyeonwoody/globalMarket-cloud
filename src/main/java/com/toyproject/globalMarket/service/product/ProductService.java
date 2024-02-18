package com.toyproject.globalMarket.service.product;

import com.google.gson.Gson;
import com.toyproject.globalMarket.DTO.Product;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;

import com.toyproject.globalMarket.configuration.platform.Github;
import com.toyproject.globalMarket.entity.ProductEntity;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.libs.FileManager;
import com.toyproject.globalMarket.repository.ProductRepository;
import com.toyproject.globalMarket.service.product.store.StoreInterface;
import com.toyproject.globalMarket.service.product.store.aliExpress.AliExpress;
import okhttp3.*;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ProductService extends BaseObject {
    private static int objectId = 0;


    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super("ProductService", objectId++);
        this.productRepository = productRepository;
    }

    public int register (ProductRegisterVO productRegisterVO, String accessToken){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String time = dateFormat.format(now);
        productRegisterVO.setCurrentTime (time);

        Product product = new Product ();
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
            ProductEntity productEntity = new ProductEntity();
            productEntity.setEntity(product, productRegisterVO.getUrl());
            productRepository.save(productEntity);
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

    private int downloadAndConvertImageToJpeg(Images images, Github github) {;
        final String destinationDirectory = github.uploadThumbnailDirectory;
        FileManager fileManager = new FileManager();
        fileManager.downloadImages(images, destinationDirectory);
          fileManager.convertImageToJpeg(images);

        return 0;
    }



    public int downloadImages(ProductRegisterVO productSource) {
        int ret = 0;
        switch (productSource.getPlatform()){
            case 네이버 -> {
                String _id = String.valueOf(productRepository.findUpcommingId());
                Github github = new Github(_id, productSource.getName());
                github.initBranch();
                ret = downloadAndConvertImageToJpeg(productSource.getImages(), github);
                github.uploadImages();
                break;
            }
            case 지마켓 -> {
            }
            default ->{
                break;
            }
        }

        return 0;
    }


}
