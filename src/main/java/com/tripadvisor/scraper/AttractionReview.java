package com.tripadvisor.scraper;

import com.tripadvisor.bean.*;
import com.tripadvisor.util.DatabaseWriter;
import com.tripadvisor.util.DateConverter;
import com.tripadvisor.util.ScrapperFileWriter;
import com.tripadvisor.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttractionReview {
    private String url;
    private WebDriver driver;
    private WebDriverWait wait;
    private DateConverter dateConverter = new DateConverter();

    /**
     * Construct for the AttractionReview
     * An instance of this is used to scrape one attraction page
     *
     * @param url: URL of the Attraction ReviewBean
     */
    public AttractionReview(String url) {
        this.url = url;
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability("loadImages", false);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, Scraper.PHANTOM_JS_EXEC);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{
                "--web-security=false",
                "--ignore-ssl-errors=true",
                "--load-images=false"
//                "--proxy=" + Util.proxyRandomizer(),
//                "--proxy-type=http"
        });
        caps.setCapability("phantomjs.page.settings.userAgent", Util.userAgentRandomizer());
        driver = new PhantomJSDriver(caps);
        /*default timeout of 10 sec*/
        wait = new WebDriverWait(driver, 10);
    }

    /**
     * Disable default construct
     */
    private AttractionReview() {
        // do nothing
    }

    /**
     * Start Scraping
     */
    public void scrape() {
//
        driver.navigate().to(this.url);
        waitForJsToLoad();
        ScrapperFileWriter scrapperFileWriter = new ScrapperFileWriter();
        try {
            AttractionBean attractionBean = new AttractionBean();
            attractionBean.setAttractionName(getAttractionName());
            attractionBean.setOverallRating(getOverallRating());
            attractionBean.setReviewsCount(getReviewsCount());
            attractionBean.setPopularity(getPopularity());
            attractionBean.setAttractionType(getAttractionType());
            attractionBean.setAddress(getAddress());
            attractionBean.setTelephoneNumber(getPhoneNumber());
            attractionBean.setOverview(getOverview());
            attractionBean.setOpenHours(getOpeningHours());
            attractionBean.setSuggestedDuration(getSuggestedDuration());
            attractionBean.setWebsite(getWebsite());
            attractionBean.setReviews(getReviews());
            attractionBean.setQuestionsAndAnswers(getQuestionAndAnswers());
//            scrapperFileWriter.writeJSONFile(attractionBean);
            DatabaseWriter databaseWriter = new DatabaseWriter();
            databaseWriter.writeToDatabase(attractionBean);
            attractionBean = null;
        } catch (Exception e) {
            scrapperFileWriter.writeFailedURLS(this.url);
            String name = driver.getTitle().split("[^\\p{L}0-9']+")[0];
            Util.takeSnapShot(driver, "C:/Users/Ruwini/Documents/research/attractionDetails/snapshots", name);
        }
        close();
    }

    /**
     * Clean up the driver
     */
    private void close() {
        driver.close();
        driver = null;
    }

    /**
     * Get name of the Attraction
     *
     * @return attraction name
     */
    private String getAttractionName() {
        try {
            Util.takeSnapShot(driver, "C:/Users/Ruwini/Documents/research/attractionDetails/snapshots", "abc");
            System.out.println(driver.findElement(By.id("HEADING")).getText());
            return driver.findElement(By.id("HEADING")).getText();
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * Get overall review rating
     *
     * @return rating
     */
    private String getOverallRating() {
        try {
            System.out.println(driver.findElement(By.cssSelector("span[property=\"ratingValue\"]")).getAttribute("content"));
            return driver.findElement(By.cssSelector("span[property=\"ratingValue\"]")).getAttribute("content");
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return "0";
        }
    }

    /**
     * Get opening hours
     *
     * @return a map of opening hours
     * */
    private HashMap<String, String> getOpeningHours(){
        try {
            clickAllHours();
            List<WebElement> dayRange = driver.findElements(By.cssSelector("div.dayRange"));
            List<WebElement> timeRange = driver.findElements(By.cssSelector("div.timeRange"));
            if (dayRange != null && dayRange.size() > 0 && timeRange != null && timeRange.size() > 0) {
                HashMap<String, String> openingHours = new HashMap<String, String>();
                int size = dayRange.size();
                for (int i = 0; i < size; i++) {
                    openingHours.put(dayRange.get(i).getAttribute("innerText"),
                            timeRange.get(i).getAttribute("innerText"));
                }
                System.out.println(openingHours);
                return openingHours;
            } else {
                return null;
            }
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * click áll hours'to retrieve all opening hours data
     * */
    private void clickAllHours() {
        try {
            WebElement allHours = driver.findElement(By.cssSelector("span.allHoursCTA"));
            if (allHours!= null && "all hours".equalsIgnoreCase(allHours.getText())) {
                allHours.click();
            }
            waitForJsToLoad();
//            Thread.sleep(Util.threadSleepTimeRandomizer());
        } catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
        }
    }

    /**
     * Get number of reviews
     *
     * @return reviews count
     */
    private int getReviewsCount() {
        try {
            String reviewCount = driver.findElement(By.cssSelector("span[property=\"v:count\"]")).getText();
            reviewCount = reviewCount.replace(",", "");
            System.out.println(reviewCount);
            return Integer.parseInt(reviewCount.trim());
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return 0;
        }
    }

    /**
     * Get popularity of attraction
     *
     * @return popularity
     */
    private String getPopularity() {
        try {
            String popularity = driver.findElement(By.cssSelector("span.header_popularity")).getText();
            System.out.println(popularity);
            return popularity.trim();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * Get type of the attraction
     *
     * @return attraction type
     */
    private List<String>  getAttractionType() {
        try {
            WebElement attractionTypesElement = driver.findElement(By.cssSelector("div.detail"));
            List<WebElement> attractionTypes = attractionTypesElement.findElements(By.cssSelector("a"));
            List<String> attrTypes = new ArrayList<String>();
            for (WebElement type : attractionTypes) {
                String typeText = type.getText();
                if (typeText.length() > 0) {
                    if (typeText.equalsIgnoreCase("more")) {
                        type.click();
                        Thread.sleep(1000);
                        if (attrTypes != null) {
                            attrTypes.clear();
                        }
                        attrTypes = getAttractionType();
                    } else {
                        if (attrTypes != null) {
                            attrTypes.add(typeText);
                        }
                    }
                }
            }
            System.out.println(attrTypes);
            return attrTypes;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * Get address of the attraction
     *
     * @return address
     */
    private String getAddress() {
        try {
            String address = driver.findElement(By.cssSelector("div.address")).getText();
            address = address.startsWith("|") ? address.substring(1) : address;
            System.out.println(address);
            return address.trim();
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * Get phone number of the attraction
     *
     * @return phone number
     */
    private String getPhoneNumber() {
        try {
            System.out.println(driver.findElement(By.cssSelector("div.phone")).getText());
            return driver.findElement(By.cssSelector("div.phone")).getText();
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * Get website of the attraction
     *
     * @return website
     */
    private String getWebsite() {
        String website = null;
        try {
            //Get Current Page
            String currentPageHandle = driver.getWindowHandle();
//            click the website icon
            WebElement websiteClick = driver.findElement(By.cssSelector("div.blEntry.website"));
            if (!websiteClick.getText().startsWith("+")) {
                websiteClick.click();
                waitForJsToLoad();
//                Thread.sleep(Util.threadSleepTimeRandomizer());
                // Get all Open Tabs
                ArrayList<String> tabHandles = new ArrayList<String>(driver.getWindowHandles());
                for (String eachHandle : tabHandles) {
                    driver.switchTo().window(eachHandle);
                    // Check Your Page Title
                    String title = driver.getTitle().toLowerCase();
                    if (!title.contains("tripadvisor")) {
                        // if tab found retrieve url
                        website = driver.getCurrentUrl();
                        //Close the current tab
                        driver.close(); // Note driver.quit() will close all tabs
                        //Swithc focus to Old tab
                        driver.switchTo().window(currentPageHandle);
                    }
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        System.out.println(website);
        return website;
    }

    /**
     * Get overview about the attraction
     *
     * @return overview
     */
    private String getOverview() {
        try {
            System.out.println(driver.findElement(By.cssSelector("div.description > .text")).getText());
            return driver.findElement(By.cssSelector("div.description > .text")).getText();
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * Get suggested duration for the attraction
     *
     * @return suggested duration
     */
    private String getSuggestedDuration() {
        try {
            String duration = driver.findElement(By.cssSelector("div.duration")).getAttribute("innerText");
            System.out.println(duration);
            return duration.replace("Suggested Duration: ", "").trim();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return null;
        }
    }

    /**
     * retreieve user reviews
     *
     * @return reviews
     * */
    private List<ReviewBean> getReviews() {
        List<ReviewBean> reviewsList = new ArrayList<ReviewBean>();
        try {
            int numberOfPages = Integer.parseInt(driver.findElements(By.cssSelector("span.pageNum.last.taLnk")).get(0).getAttribute("data-page-number"));
            int limit = Math.min(Scraper.PAGE_COUNT, numberOfPages);
            while (limit > 0) {
//            while (hasMoreReviews) {
                clickLoadMore("span.taLnk");
                List<WebElement> reviews = driver.findElements(By.cssSelector("div.review-container"));
                for (WebElement review : reviews) {
                    String title = review.findElement(By.cssSelector("span.noQuotes")).getText();
                    String reviewBody = review.findElement(By.cssSelector("p.partial_entry")).getText();
                    String dateReviewed = review.findElement(By.cssSelector("span.ratingDate")).getText()
                            .replace("Reviewed ", "");
                    if (dateConverter.checkIfRelativeDate(dateReviewed)) {
                        dateReviewed = dateConverter.convertDate(dateReviewed);
                    }
                    int rating = getReviewRating(review);
                    ReviewBean reviewBean = new ReviewBean();
                    reviewBean.setTitle(title);
                    reviewBean.setDateReviewed(dateReviewed);
                    reviewBean.setRating(rating);
                    reviewBean.setReview(reviewBody);
                    reviewsList.add(reviewBean);
                    System.out.println(reviewBean);
                }
                clickNext(driver.findElements(By.cssSelector("span.nav.next.taLnk")).get(0));
                limit--;
            }
            System.out.println("review success");
            return reviewsList;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            if (reviewsList.size() > 0) {
                return reviewsList;
            } else {
                return null;
            }
        }
    }

    /**
     * Get the rating of the user review
     *
     * @return rating
     */
    private int getReviewRating(WebElement review) {
        try {
            WebElement ratingElem = review.findElement(By.cssSelector("span.ui_bubble_rating"));
            String classes = ratingElem.getAttribute("class");
            if (classes.contains("bubble_10")) return 1;
            else if (classes.contains("bubble_20")) return 2;
            else if (classes.contains("bubble_30")) return 3;
            else if (classes.contains("bubble_40")) return 4;
            else if (classes.contains("bubble_50")) return 5;
            else return 0;
        }  catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
            return 0;
        }
    }

    /**
     * Click load more
     */
    private void clickLoadMore(String linkClass) {
        try {
            List<WebElement> moreLinks = driver.findElements(By.cssSelector(linkClass));
            for (WebElement moreLink : moreLinks) {
                if (moreLink != null && "more".equalsIgnoreCase(moreLink.getText())) {
                    moreLink.click();
                    Thread.sleep(500);
                }
            }
            waitForJsToLoad();
            Thread.sleep(5000);
        } catch (Exception ignored) {
            // NoSuchElem or Interrupted Exception, can be ignored
        }
    }

    /**
     * Click the web element provided
     *
     * @param nextLink next icon to be clickedn
     */
    private void clickNext(WebElement nextLink) {
        try {
            nextLink.click();
            waitForJsToLoad();
            Thread.sleep(7000);
        } catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
        }
    }

    /**
     * Retrieve questions and answers of attraction
     *
     * @return question and answers
     **/
     private List<QuestionAndAnswersBean> getQuestionAndAnswers(){
        List<QuestionAndAnswersBean> questionAndAnswersList = new ArrayList<QuestionAndAnswersBean>();
        try {
            int numberOfPages = Integer.parseInt(driver.findElements(By.cssSelector("span.pageNum.last.taLnk")).get(1).getAttribute("data-page-number"));
            int limit = Math.min(Scraper.PAGE_COUNT, numberOfPages);
            while (limit > 0) {
//            while (hasMorQnA) {
                clickShowAllAnswers();
                clickLoadMore("span.moreLink");
                List<WebElement> questionAndAnswers = driver.findElements(By.cssSelector("div.question.ui_columns"));
                for (WebElement questionAndAnswer : questionAndAnswers) {
                    String dateAsked = questionAndAnswer.findElement(By.cssSelector("div.question_date"))
                            .getText().replace("|", "");
                    if (dateConverter.checkIfRelativeDate(dateAsked)) {
                        dateAsked = dateConverter.convertDate(dateAsked);
                    }
                    String question = questionAndAnswer.findElement(By.cssSelector("span.question_text")).getText().trim();
                    QuestionBean questionBean = new QuestionBean();
                    questionBean.setDateAsked(dateAsked);
                    questionBean.setQuestion(question);
                    List<WebElement> responses = questionAndAnswer.findElements(By.cssSelector("div.response"));
                    List<AnswerBean> answerList = new ArrayList<AnswerBean>();
                    for (WebElement response : responses) {
                        String answer = response.findElement(By.cssSelector("div.unabbreviated")).getText().trim();
                        String vote = response.findElement(By.cssSelector("div.score")).getText();
                        AnswerBean answerBean = new AnswerBean();
                        answerBean.setAnswer(answer);
                        answerBean.setVoteOnUsefulness(vote);
                        answerList.add(answerBean);
                    }
                    QuestionAndAnswersBean questionAndAnswersBean = new QuestionAndAnswersBean();
                    questionAndAnswersBean.setQuestion(questionBean);
                    questionAndAnswersBean.setAnswers(answerList);
                    questionAndAnswersList.add(questionAndAnswersBean);
                    System.out.println(questionAndAnswersBean);
                }
                List<WebElement> next =driver.findElements(By.cssSelector("span.nav.next.taLnk"));
                clickNext(next.get(Math.max(0, (next.size() - 1))));
                limit--;
            }
            System.out.println("qna success");
            return questionAndAnswersList;
        } catch (Exception ignored) {
            // NoSuchElem or Interrupted Exception, can be ignored
            ignored.printStackTrace();
            if (questionAndAnswersList.size() > 0) {
                return questionAndAnswersList;
            } else {
                return null;
            }
        }
    }

    /**
     * Click show all answers
     */
    private void clickShowAllAnswers() {
        try {
            List<WebElement> showAllLinks = driver.findElements(By.cssSelector("span.showAll"));
            for (WebElement showAllLink : showAllLinks) {
                if (showAllLink != null && showAllLink.getText().endsWith("answers")) {
                    showAllLink.click();
                    Thread.sleep(500);
                }
            }
            waitForJsToLoad();
            Thread.sleep(7000);
        } catch (Exception ignored) {
            ignored.printStackTrace();
            // NoSuchElem or Interrupted Exception, can be ignored
        }
    }

   /**
    * wait for the page to load
    * */
    private void waitForJsToLoad(){
        try {
            // wait for Javascript to load
            ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                            .toString().equals("complete");
                }
            };
            ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return ((PhantomJSDriver) driver).executeScript("return document.readyState")
                                    .toString().equals("complete");
                        }
                    };
            Thread.sleep(Util.threadSleepTimeRandomizer());
            wait.until(jsLoad);
            wait.until(expectation);
        } catch (Exception e) {
            e.printStackTrace();
//            ignore
        }
    }
}
