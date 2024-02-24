package com.toyproject.globalMarket.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.DTO.Product;
import com.toyproject.globalMarket.VO.response.ResponseVO;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.ProductRegisterVO;

import com.toyproject.globalMarket.configuration.platform.APIGithub;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import com.toyproject.globalMarket.entity.ProductEntity;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.libs.FileManager;
import com.toyproject.globalMarket.libs.HtmlManager;
import com.toyproject.globalMarket.libs.HtmlParser;
import com.toyproject.globalMarket.repository.ProductRepository;
import com.toyproject.globalMarket.service.product.store.StoreInterface;
import com.toyproject.globalMarket.service.product.store.aliExpress.AliExpress;
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

    public ResponseVO register (ProductRegisterVO productRegisterVO, int platform) throws JsonProcessingException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String time = dateFormat.format(now);
        productRegisterVO.setCurrentTime (time);
        Product product = new Product ();
        product.setDTO(productRegisterVO);
        ResponseVO response = null;
        switch (platform){
            case 0:
                response = naver.postProducts(product);
                break;
        }
        if (response.code() == 200){
            LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Product register request is Successful with code : {0}", response.code());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());
            long productNumber = jsonNode.get("smartstoreChannelProductNo").asLong();
            ProductEntity productEntity = new ProductEntity();
            productEntity.setEntity(product, productRegisterVO.getUrl(), productNumber);
            productRepository.save(productEntity);
        }
        return response;
    }

    public int getNewProductInfo (ProductRegisterVO productRegisterVO){
        StoreInterface store = null;
        String _id = String.valueOf(productRepository.findUpcommingId());
        productRegisterVO.setDBId(_id);
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
                String _id = productSource.getDBId();
                APIGithub github = new APIGithub(productSource);
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
        APIGithub github = new APIGithub();
        github.turnToMaster();
    }

    public void modifyDetailContent(ProductRegisterVO productSource) {
        HtmlManager htmlManager = new HtmlManager();
        htmlManager.replaceDetailContnetImages(productSource);
    }
}
