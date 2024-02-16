package com.toyproject.globalMarket.configuration.platform;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;

import java.io.IOException;
import java.util.Arrays;

public class Github {
    public final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/";
    public final String uploadThumbnailDirectory = System.getProperty("user.dir") + "/src/main/resources/detail/thumbnail";

    private String id;
    private String nickname;
    private String id_nickname;
    private String[] params;
    public Github(ProductRegisterVO productSource){
        this.id = "unique"; //Product repository to get Id
        this.nickname = "branch";
        this.params = new String[]{id + "_" + nickname, productSource.getName()};
    }
    public void initBranch (){
        final String init = "init.sh";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(Arrays.asList("sh", uploadDirectory+init, this.params[0], this.params[1]));
    }

    public void uploadImages() {
        final String commit = "commit.sh";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(Arrays.asList("sh", uploadDirectory+commit, this.params[0], this.params[1]));

        //final String gitUrl = "https://raw.githubusercontent.com/GlobalMarketKOR/Images/" + branch;
        //getRawGithubImageUrl (productSource.getImages(), gitUrl);

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
