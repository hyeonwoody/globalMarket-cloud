package com.toyproject.globalMarket.libs;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.webp.WebPImageMetadata;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManager extends BaseObject {
    private static int objectId = 0;

    public FileManager() {
        super("FileManager", objectId++);
    }

    public void convertImageToJpeg(Images images) {
        final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/";
        final String convert = "convert.sh";
        String webpFile = images.representativeImage.url;
        String jpgFile = webpFile.replace("webp", "jpg");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(Arrays.asList("sh", uploadDirectory+convert, webpFile, jpgFile));
        Process process = null;
        int exitCode = 0;
        try {
            process = processBuilder.start();
            exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Script executed successfully.");
            } else {
                System.err.println("Script execution failed with exit code: " + exitCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Images.OptionalImage optionalImage : images.optionalImages) {
            String webpFilePath = optionalImage.url;
            processBuilder.command(Arrays.asList("sh", uploadDirectory+convert, webpFile, jpgFile));
            try {
                process = processBuilder.start();
                exitCode = 0;
                exitCode = process.waitFor();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void _convertImageToJpeg(Images images) {
        List<File> WebPImageList = new ArrayList<>();
        List<File> JpgImageList = new ArrayList<>();
        File file = new File(images.representativeImage.url);
        WebPImageList.add(file);
        for (Images.OptionalImage optionalImage : images.optionalImages) {
            String filePath = optionalImage.url;
            WebPImageList.add(new File(filePath));
            JpgImageList.add(new File(filePath.replace("webp", "jpg")));
        }
        for (int i = 0; i < WebPImageList.size(); ++i) {
            BufferedImage image = null;
            try {
                image = Imaging.getBufferedImage(WebPImageList.get(i));
                ImageMetadata metadata = Imaging.getMetadata(WebPImageList.get(i));
                try {
                    Imaging.writeImage(image, JpgImageList.get(i), ImageFormats.JPEG);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (ImagingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void downloadImages(Images images, String destinationDirectory) {
        try {
            URL url = new URL(images.representativeImage.url);
            String fileName = "image" + 0 + ".webp";
            Path destinationPath = Paths.get(destinationDirectory, fileName);
            try (InputStream inputStream = url.openStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                String path = destinationPath.toString();
                images.representativeImage.setUrl(path);
                LogOutput(EventManager.LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Representative Image Downloaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < images.optionalImages.size(); ++i) {
            try {
                URL url = new URL(images.optionalImages.get(i).url);
                String fileName = "image" + String.valueOf(i + 1) + ".webp";
                Path destinationPath = Paths.get(destinationDirectory, fileName);
                try (InputStream inputStream = url.openStream()) {
                    Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    String path = destinationPath.toString();
                    images.optionalImages.get(i).setUrl(path);
                    LogOutput(EventManager.LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Image Downloaded successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isWEBPImageFile(File file) {
        return file.getName().toUpperCase().endsWith(ImageFormats.WEBP.getName());
    }

    private boolean isValidImageFile(File file) {
        String[] validExtensions = new String[]{"jpg", "jpeg", "png"};
        if (Arrays.asList(validExtensions).stream().anyMatch(extension -> file.getName().toUpperCase().endsWith(extension))) {
            return true;
        }
        return false;
    }

    public List<File> listImageFiles(String imageDirectory){
        List<File> imageList = new ArrayList<>();
        File directory = new File(imageDirectory);
        if (directory.isDirectory()){
            for (File file : directory.listFiles()){
                if (file.isFile() && isValidImageFile(file)) {
                    imageList.add(file);
                }
            }
        }
        return imageList;
    }
}
