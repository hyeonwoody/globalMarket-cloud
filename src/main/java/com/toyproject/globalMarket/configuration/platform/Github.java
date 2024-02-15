package com.toyproject.globalMarket.configuration.platform;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;

import java.io.IOException;
import java.util.Arrays;

public class Github {

    public static final String uploadThumbnailDirectory = System.getProperty("user.dir") + "/src/main/resources/detail/thumbnail";

    public void uploadImages(ProductRegisterVO productSource) {
        final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources";
        final String commit = "/commit.sh";
        ProcessBuilder processBuilder = new ProcessBuilder();
        String id = "unique";
        String nickname = "branch";
        String id_nickname = id + "_" + nickname;
        String[] params = { id_nickname, productSource.getName()}; //unique branch messsage
        processBuilder.command(Arrays.asList("sh", uploadDirectory+commit, params[0], params[1]));

        final String gitUrl = "https://raw.githubusercontent.com/GlobalMarketKOR/Images/" + id_nickname;
        getRawGithubImageUrl (productSource.getImages(), gitUrl);

        Process process = null;
        try {
            process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Script executed successfully.");
            } else {
                System.err.println("Script execution failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void getRawGithubImageUrl(Images images, String gitUrl) {
        images.representativeImage.setUrl(gitUrl + images.representativeImage.url);
        for (int i = 0; i < images.optionalImages.size(); ++i){
            images.optionalImages.get(i).setUrl(gitUrl + images.optionalImages.get(i).url);
        }
    }
}
