package com.tripadvisor.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scraper {
    public static final String PHANTOM_JS_EXEC = "C:/Users/Ruwini/IdeaProjects/phantomjs/bin/phantomjs.exe";
    public static final int PAGE_COUNT = 100;
    public static final List<String> URLS = new ArrayList<String>(Arrays.asList(
//            "https://www.tripadvisor.com/Attraction_Review-g187147-d188151-Reviews-Eiffel_Tower-Paris_Ile_de_France.html",
//            "https://www.tripadvisor.com/Attraction_Review-g187147-d188150-Reviews-Musee_d_Orsay-Paris_Ile_de_France.html",
//            "https://www.tripadvisor.com/Attraction_Review-g187147-d188757-Reviews-Musee_du_Louvre-Paris_Ile_de_France.html"
//            "https://www.tripadvisor.com/Attraction_Review-g187147-d188679-Reviews-Notre_Dame_Cathedral-Paris_Ile_de_France.html"
//            "https://www.tripadvisor.com/Attraction_Review-g187147-d292257-Reviews-Le_Marais-Paris_Ile_de_France.html"
            "https://www.tripadvisor.com/Attraction_Review-g187147-d2397509-Reviews-Tours_de_la_Cathedrale_Notre_Dame-Paris_Ile_de_France.html"
    ));
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException {
        for (final String url : URLS) {
            Runnable worker = new Runnable() {
                public void run() {
                    AttractionReview review = new AttractionReview(url);
                    review.scrape();
                }
            };
            executorService.execute(worker);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}
