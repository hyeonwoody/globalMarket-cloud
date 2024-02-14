package com.toyproject.globalMarket.configuration.platform;

import com.google.api.client.http.FileContent;
import com.toyproject.globalMarket.configuration.AuthConfig;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.InputStream;


public class Google extends AuthConfig {

    private static int objectId = 0;
    public Google(@Value("${naver.clientId}") String clientId,
                 @Value("${naver.clientSecret}") String clientSecret) {
        super("PlatformNaver", objectId++,clientId, clientSecret, "https://api.commerce.naver.com/external/v1/oauth2/token");
        this.kind = PlatformList.GOOGLE.ordinal();
        int a = 0;
    }
    public int driveUpload(){
        Drive drive = getDrive();
        File fileMetaData = new File();
        fileMetaData.setName("uploadtest.txt");
        java.io.File f = new java.io.File ("/uploadtest.txt");
        FileContent fileContent = new FileContent("text/plain", f);
        try {
            drive.files().create(fileMetaData, fileContent).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    Drive getDrive (){
        final String APPLICATION_NAME = "Google Drive API Java Quickstart";
        final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            return drive;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) {
        final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        final String TOKENS_DIRECTORY_PATH = "tokens";

//        final List<String> SCOPES =
//                Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);

        final List<String> SCOPES = Arrays.asList(
                DriveScopes.DRIVE,
                DriveScopes.DRIVE_APPDATA,
                DriveScopes.DRIVE_METADATA,
                DriveScopes.DRIVE_FILE,
                DriveScopes.DRIVE_SCRIPTS
        );
        final String CREDENTIALS_FILE_PATH = "/credentials.json";
        InputStream in = Google.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null){
            try {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        GoogleClientSecrets clientSecrets =
                null;
        try {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Build flow and trigger user authorization request.

        GoogleAuthorizationCodeFlow flow = null;
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8881).build();
        Credential credential = null;
        try {
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //returns an authorized Credential object.
        return credential;
    }

    @Override
    public String getOAuth() {
        return null;
    }

}
