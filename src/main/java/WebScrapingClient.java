import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebScrapingClient {

    public static final String QUERY_JOB_TITLE = "h2[data-test='text-positionName']";
    public static final String QUERY_EMPLOYER = "h2[data-test='text-employerName']";
    public static final String QUERY_SALARY = "div[data-test='section-salary']";
    public static final String QUERY_WORKPLACE_PRECISE = "div[data-test='sections-benefit-workplaces'] a[href='#map-frame']";
    public static final String QUERY_WORKPLACE_ALTERNATIVE = "div[data-test='sections-benefit-workplaces'] p[data-test='text-benefit']";
    public static final String QUERY_EXPIRATION_DATE = "div[data-test='sections-benefit-expiration'] div[data-test='text-benefit']";
    public static final String QUERY_CONTRACT_INFO = "div[data-test='sections-benefit-contracts']";
    public static final String QUERY_SCHEDULE_WORK = "div[data-test='sections-benefit-work-schedule']";
    public static final String QUERY_EMPLOYMENT_TYPE = "div[data-test='sections-benefit-employment-type-name']";

    private final WebClient webClient;
    private final PortalStrategy portalStrategy;

    public WebScrapingClient(WebClient webClient, PortalStrategy portalStrategy) {
        this.webClient = webClient;
        this.portalStrategy = portalStrategy;
    }

    public void scrape(SearchParams params) {
        String url = portalStrategy.createPageUrl(params);

        try {
            HtmlPage page = webClient.getPage(url);
            Document document = Jsoup.parse(page.asXml());

            Elements select = document.select(portalStrategy.cssSelectorToLinkOffers());
            List<JobResult> results = new ArrayList<>();

            for (Element e : select) {
                String link = e.attr("href");

                try (WebClient subWebClient = new WebClient()) {
                    HtmlPage offerPage = subWebClient.getPage(link);

                    Document subDocument = Jsoup.parse(offerPage.asXml());

                    Element jobNameElement = subDocument.selectFirst(QUERY_JOB_TITLE);
                    Element employerNameElement = subDocument.selectFirst(QUERY_EMPLOYER);
                    Element salaryElement = subDocument.selectFirst(QUERY_SALARY);

                    Element workplaceElement = subDocument.selectFirst(QUERY_WORKPLACE_PRECISE);
                    Element workplaceRepElement = subDocument.selectFirst(QUERY_WORKPLACE_ALTERNATIVE);
                    Elements expirationElements = subDocument.select(QUERY_EXPIRATION_DATE);
                    Element contractElement = subDocument.selectFirst(QUERY_CONTRACT_INFO);
                    Element scheduleElement = subDocument.selectFirst(QUERY_SCHEDULE_WORK);
                    Element employmentTypeElement = subDocument.selectFirst(QUERY_EMPLOYMENT_TYPE);

                    JobResult jobResult = new JobResult(link, jobNameElement.text(), employerNameElement.ownText());
                    jobResult.salary = salaryElement != null ? salaryElement.text() : "brak";
                    jobResult.workplace = workplaceElement != null || workplaceRepElement != null ? (jobResult.workplace = workplaceElement != null ? workplaceElement.text() : workplaceRepElement.text()) : "brak";
                    jobResult.expiration = expirationElements != null ? expirationElements.get(expirationElements.size() - 1).text() : "brak";
                    jobResult.contract = contractElement != null ? contractElement.text() : "brak";
                    jobResult.schedule = scheduleElement != null ? scheduleElement.text() : "brak";
                    jobResult.employmentType = employmentTypeElement != null ? employmentTypeElement.text() : "brak";

                    results.add(jobResult);
                }
            }

            results.forEach(System.out::println);
        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + url);
        } finally {
            webClient.close();
        }
    }

    public void enableCss() {
        webClient.getOptions().setCssEnabled(true);
    }

    public void disableCss() {
        webClient.getOptions().setCssEnabled(false);
    }

    public void enableJs() {
        webClient.getOptions().setJavaScriptEnabled(true);
    }

    public void disableJs() {
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    public void turnOffLogs() {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    }

    public void turnOnLogs() {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        Logger.getLogger("com.gargoylesoftware").setLevel(null);
        Logger.getLogger("org.apache.commons.httpclient").setLevel(null);

        webClient.getOptions().setThrowExceptionOnScriptError(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
    }
}
