package com.tripadvisor.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tripadvisor.bean.AttractionBean;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * create json files for attraction details
 * */
public class ScrapperFileWriter {

    private static final String DIRECTORY_LOCATION = "C:/Users/Ruwini/Documents/research/attractionDetails";
    private static final String FAILED_URL_FILE = "C:/Users/Ruwini/Documents/research/attractionDetails/FailedURLs.txt";
    private static final String JSON_FILE_EXTENSION = ".json";

    /**
     * write the attaction details to a json file
     *
     * @param attractionBean attraction details
     * */
    public void writeJSONFile(AttractionBean attractionBean) {
        try ( BufferedWriter writer = Files.newBufferedWriter(Paths.get(DIRECTORY_LOCATION,
                (attractionBean.getAttractionName() + JSON_FILE_EXTENSION)))) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            writer.write(gson.toJson(attractionBean));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFailedURLS(String url) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(FAILED_URL_FILE, true))) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            writer.newLine();
            writer.write(gson.toJson(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
