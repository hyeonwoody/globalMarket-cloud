package com.toyproject.globalMarket.configuration.platform;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.libs.BaseObject;

import java.io.IOException;
import java.util.Arrays;

public class APIGithub extends BaseObject {

    public APIGithub(){
        super("Github", objectId++);
    }

    public APIGithub(String _id, String productName){
        super("Github", objectId++);
        this.id = _id;
        this.nickname = "branch";
        this.id_nickname = id + "_" + nickname;
        this.params = new String[]{id + "_" + nickname, productName};
    }
    private static int objectId = 0;
    public final String uploadDirectory = System.getProperty("user.dir") + "/src/Images";
    public final String uploadThumbnailDirectory = System.getProperty("user.dir") + "/src/Images/detail/thumbnail";
    public final String uploadPageDirectory = System.getProperty("user.dir") + "/src/Images/detail/page";


    private String id;
    private String nickname;
    private String id_nickname;
    private String[] params;

    public void initBranch (){
        final String init = "/init.sh";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(Arrays.asList("sh", uploadDirectory+init, this.params[0], this.params[1]));
        Process process = null;
        try {
            process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Init script executed successfully.");
            } else {
                LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 2, "Init script execution failed with exit code: {0}",exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void commitImages() {
        final String commit = "/commit.sh";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(Arrays.asList("sh", uploadDirectory+commit, this.params[0], this.params[1]));

        //final String gitUrl = "https://raw.githubusercontent.com/GlobalMarketKOR/Images/" + branch;
        //getRawGithubImageUrl (productSource.getImages(), gitUrl);

        Process process = null;
        try {
            process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Commit script executed successfully.");
            } else {
                LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 2, "Commit script execution failed with exit code: {0}",exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void turnToMaster(){
        final String master = "/master.sh";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(Arrays.asList("sh", uploadDirectory+master));

        //final String gitUrl = "https://raw.githubusercontent.com/GlobalMarketKOR/Images/" + branch;
        //getRawGithubImageUrl (productSource.getImages(), gitUrl);

        Process process = null;
        try {
            process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Commit script executed successfully.");
            } else {
                LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 2, "Commit script execution failed with exit code: {0}",exitCode);
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
