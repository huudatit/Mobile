package com.example.testui.fcm;

import android.util.Log;

import com.google.api.client.util.Lists;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AccessToken {
    private static final String firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() {
        try {
            String strJson =
                    "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"testui-a2b16\",\n" +
                    "  \"private_key_id\": \"f339aef5e7fb8f7b1b195c74f320db9932174e0b\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDIsOasq3R/tNI6\\nHoOZ5Qq61teWf+j3GuqlMhwZ0ACuRpSguKyvnbaDU8bx+yPSfnbcAsvg2Hslayrn\\nx/Fx0f1birfxELn8aVfedr+TV3g6LV6wPez7QcGuo9IxkS44AHDUG/4+UCMFmSft\\n3LkI3LdrC7rIrriWDF2/be51Sg/foOzrY8uu+9hXhuF0af0iuFHvmbkJVs1X7Ch8\\nUmbHIGMtpLhoYAvQtCl2ImWpUsFCtsYzzN4bNxjVxidnjPf2gjDpdWPMyUYtRN9Y\\nR1lMW16Vr3HtHL9g14PkPftcPmlQLMS4XR0yinBsUMTqyytlmY90hTyGRiwKI+Hv\\nlRSp0mWXAgMBAAECggEAA3gLUX2LmSmAklo0rCEclMSVG8tAm55ur9+BEArB+EEz\\nQ62HG2hk3znZ40gUAzw5K1RhC2HlvwY7MTmjhfQhppNsDbhCse1tBDHJRFXrhHxR\\nE3rLV3LeuV+oVaYzLLfVO27zouAZVHkK16Tl2q4pMKoWcG5hgmmngfrEd5wjUICc\\njl0/gUyteWFA3+i8l8TBhLwEolJqMFYj1S1tBZ9ZV3VhFaJm9zrh+4kaCj7GVGSV\\nAWMOZKeDqUrrnp20bjUdqPlk1pEYQGFwOchNkFjBD9gYD0OB7w6mTG8CKXW+ahzG\\nJkLL5GDikZ7mgW0D2klbMYLlrF2ParAit/5/B63sAQKBgQDl+AeA6j43/jvYl3g6\\nBmurPSV/VOOLPWQ2qC/KC13b78U4syc4y68T1k09+1uOOKDgZnVY/8foPvLNHjF5\\nBjshiQcDSm1xkEbb9747ZZrAjNxUST6TlnagmKUPOhKD+8d2gdr3FyxLD/XEtDXo\\ndFwtiAd4miaLEV2W0Ufz7YA40QKBgQDfaHeH1663kUOnskZwjkEmuMvH0fQL976v\\nofPVLKufA5X5mmZtxPa1cH3WpulUT++dlUhiGWBLKfLI8eq3i8UZ7r8kftk0devM\\nDOQFh1A1Zu+z1324OlZGwbhV+JxgTM4a59TTfbkkEkK/DgNvkyLVV/N2teDxjGay\\n3PElIBVR5wKBgQCOqytsjOYAstbJ7l6GEQmm+I5UyoNIFNQhHIJZZGfcVIoUPQDu\\nOV7qfZ75/wCz4OndjXLrvfN1X+gd3Mdwf68bhPk2z2vKcmmjebUpAEXbgwgFulL6\\nGPSLeWedZLer7f22xaTkehnej443dF2lUtsE3ElWRrv2ttVLBZcP+536oQKBgAjC\\nxf0zvgxAzBBlc1GyomdsU8FwQEu/2WB1z2QKrmg78cOqBF5FnQ8cbDLgtMGzqMfb\\nNM10BUytRnIO2o98EaykqiY6OzZNcYO89awp1EFj2f6Jcsqorv1zV1E3wJoeKO7x\\nLG1mvr3lYWywlf3oHTOUkV2UYE/Ym6Zj96jnCy79AoGBAJ6X65Z37RFSud3ePDyi\\nf5k3hesF7fTPb8wFTdE+jnh042Y1sDGN1xXSjl+E+Nu+S6edqKtyZ3JoXDbzCrEC\\nQWhPq/NE7l82TTPPpGqG+XM2bOqME2UmefDyTg5b5qruZAJh3+liLtm4m+wJ1lY/\\nlVi6KfNdiAgW4QSgUJtztcXS\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-n6422@testui-a2b16.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"113585989552182159895\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-n6422%40testui-a2b16.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}";

            InputStream stream = new ByteArrayInputStream(strJson.getBytes(StandardCharsets.UTF_8));

            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(Arrays.asList(firebaseMessagingScope));

            googleCredentials.refresh();

            return googleCredentials.getAccessToken().getTokenValue();

        } catch (IOException e) {
            Log.e("error", "" + e.getMessage());
            return null;
        }
    }

}
