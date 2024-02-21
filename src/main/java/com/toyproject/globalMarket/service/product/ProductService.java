package com.toyproject.globalMarket.service.product;

import com.google.gson.Gson;
import com.toyproject.globalMarket.DTO.Product;
import com.toyproject.globalMarket.DTO.product.platform.Naver;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;

import com.toyproject.globalMarket.configuration.platform.APIGithub;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import com.toyproject.globalMarket.entity.ProductEntity;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.libs.FileManager;
import com.toyproject.globalMarket.libs.HtmlParser;
import com.toyproject.globalMarket.repository.ProductRepository;
import com.toyproject.globalMarket.service.product.store.StoreInterface;
import com.toyproject.globalMarket.service.product.store.aliExpress.AliExpress;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ProductService extends BaseObject {
    private static int objectId = 0;


    private final ProductRepository productRepository;

    @Autowired
    APINaver naver;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super("ProductService", objectId++);
        this.productRepository = productRepository;
    }

    public int register (ProductRegisterVO productRegisterVO, int platform){
        int responseCode = 0;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String time = dateFormat.format(now);
        productRegisterVO.setCurrentTime (time);
        Product product = new Product ();
        product.setDTO(productRegisterVO);
        switch (platform){
            case 0:
                responseCode = naver.postProducts(product);
                break;
        }
        if (responseCode == 200){
            LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Request is Successful with code : {0}", responseCode);
            ProductEntity productEntity = new ProductEntity();
            productEntity.setEntity(product, productRegisterVO.getUrl());
            productRepository.save(productEntity);
        }
        return responseCode;
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

    public int downloadDetailContentImagesAndConvertImageToJpeg (String detailContent, APIGithub github){
        final String destinationDirectory = github.uploadPageDirectory;
        Images images = new Images();
        HtmlParser htmlParser = new HtmlParser();
        htmlParser.getDetailContentImages(detailContent, images);

        FileManager fileManager = new FileManager();
        fileManager.downloadImages(images, destinationDirectory);
        fileManager.convertImageToJpeg(images, destinationDirectory);
        return 0;
    }

    private int downloadThumbnailAndConvertImageToJpeg(Images images, APIGithub github) {;
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
                APIGithub github = new APIGithub(_id, productSource.getName());
                github.initBranch();
                ret = downloadThumbnailAndConvertImageToJpeg(productSource.getImages(), github);
                ret = downloadDetailContentImagesAndConvertImageToJpeg (productSource.getDetailContent(), github);
                github.commitImages();
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

    public void uploadImages(Images images) {
        naver.uploadImages(images);
        String _id = String.valueOf(productRepository.findUpcommingId());
        APIGithub github = new APIGithub();
        github.turnToMaster();
    }
}
