package com.tripadvisor.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Util classed used for web scraping
 * */
public class Util {

    private static final Random random = new Random();

    /**
     * threadSleepTimeRandomizer method is used to obtain a random sleep time
     *
     * @return sleep time between 5000-10000
     * */
    public static int threadSleepTimeRandomizer (){
        int min = 3000;
        int max = 10000;
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Randomly pick a user agent
     *
     * @return a user agent
     * */
    public static String userAgentRandomizer() {
        List<String> userAgents = new ArrayList<String>(Arrays.asList(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1",
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"));
        return userAgents.get(random.nextInt(userAgents.size()));
    }

    /**
     * Randomly pick a proxy
     *
     * @return a proxy
     * */
    public static String proxyRandomizer() {
//        todo prior to scraping update the proxy list with proxies for the day
        List<String> proxies = new ArrayList<String>(Arrays.asList(
                "45.32.1.61:3128",
                "47.88.50.181:808"));
        return proxies.get(random.nextInt(proxies.size()));
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath, String name){
        try {
            //Convert web driver object to TakeScreenshot
            TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
            //Call getScreenshotAs method to create image file
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            //Move image file to new destination
            File DestFile = new File(String.valueOf(Paths.get(fileWithPath, name)));
            //Copy file at destination
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
//             do nothing
        }
    }
}
