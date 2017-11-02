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
            "https://www.tripadvisor.com/Attraction_Review-g187147-d190202-Reviews-Sainte_Chapelle-Paris_Ile_de_France.html",
            "https://www.tripadvisor.com/Attraction_Review-g187147-d189687-Reviews-Luxembourg_Gardens-Paris_Ile_de_France.html",
            "https://www.tripadvisor.com/Attraction_Review-g187147-d190204-Reviews-Palais_Garnier_Opera_National_de_Paris-Paris_Ile_de_France.html",
            "https://www.tripadvisor.com/Attraction_Review-g187147-d189683-Reviews-Seine_River-Paris_Ile_de_France.html",
            "https://www.tripadvisor.com/Attraction_Review-g187147-d190685-Reviews-Basilica_du_Sacre_Coeur_de_Montmartre-Paris_Ile_de_France.html",
            "https://www.tripadvisor.com/Attraction_Review-g187147-d188150-Reviews-Musee_d_Orsay-Paris_Ile_de_France.html",
            "https://www.tripadvisor.com/Attraction_Review-g187147-d188151-Reviews-Eiffel_Tower-Paris_Ile_de_France.html",
            "https://www.tripadvisor.com/Attraction_Review-g187147-d188757-Reviews-Musee_du_Louvre-Paris_Ile_de_France.html"
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
