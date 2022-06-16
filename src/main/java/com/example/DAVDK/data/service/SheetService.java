package com.example.DAVDK.data.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.json.simple.parser.JSONParser;
import com.google.api.client.json.JsonFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

public class SheetService {
    private static final String APPLICATION_NAME = "Hugo GenZ";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKEN_DIRECTORY_PATH = "tokens";
    // if delete this will delete your token
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    JSONParser parser;
    public SheetService() {
        parser = new JSONParser();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = SheetService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // build flow and trigger user authorization request
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKEN_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1SU3YwRVRT5IJvrlXgIHMGBA2PIP7EkCa_nbLqyc50xo";
        final String range = "Sheet1!A2:C";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void updateData(Sheets service, String spreadsheetId, String range, String id,
                           Map<String, String> logData) throws Exception {

        List<List<Object>> values = new ArrayList<>();
        for (Map.Entry<String, String> entry : logData.entrySet()) {
            List<Object> list = Arrays.asList(
                    id, entry.getKey(), entry.getValue());
            values.add(list);
        }
        List<ValueRange> data = new ArrayList<>();
        data.add(new ValueRange()
                .setRange(range)
                .setValues(values));

        BatchUpdateValuesRequest body = new BatchUpdateValuesRequest()
                .setValueInputOption("RAW")
                .setData(data);
        BatchUpdateValuesResponse result =
                service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute();
        System.out.printf("%d cells updated.", result.getTotalUpdatedCells());
    }

     private Map<String, String> generateTestMap() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            String title = "Testing - Title " + i;
            String text = "testing number: " + i;
            map.put(title, text);
        }
        return map;
    }

    public void pushDataToSheet(String id, String text) {
        try {
            Map<String, String> map = convertStringToMap(text);
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String spreadsheetId = "1SU3YwRVRT5IJvrlXgIHMGBA2PIP7EkCa_nbLqyc50xo";
            final String range = "Sheet1!A2:C";
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            updateData(service, spreadsheetId, range, id, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> convertStringToMap(String text) {
        try {
            Map<String, String> map = (Map<String, String>) parser.parse(text);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
