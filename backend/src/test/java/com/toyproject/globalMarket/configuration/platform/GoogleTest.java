package com.toyproject.globalMarket.configuration.platform;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.services.drive.Drive;
import com.toyproject.globalMarket.configuration.platform.APIGoogle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

class GoogleTest {
    @Test
    public void getCredential() throws GeneralSecurityException, IOException {
        //given
        APIGoogle google = new APIGoogle();

        //when
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = google.getCredentials(HTTP_TRANSPORT);

        //then
        assertNotEquals(null, credential);
    }

    @Test
    void driveUpload() {
        APIGoogle google = new APIGoogle();
        int ret = google.driveUpload();

        assertEquals(0, ret);
    }

    @Test
    void getDrive() {
        APIGoogle google = new APIGoogle();
        Drive drive = google.getDrive();
        assertNotEquals(null, drive);
    }

    @Test
    void getCredentials() {
    }

    @Test
    void getOAuth() {
    }
}