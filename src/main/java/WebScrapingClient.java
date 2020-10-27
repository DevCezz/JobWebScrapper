import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WebScrapingClient {

    private static final Logger LOGGER = Logger.getLogger( WebScrapingClient.class.getName() );

    private final PortalStrategy portalStrategy;

    public WebScrapingClient(PortalStrategy portalStrategy) {
        this.portalStrategy = portalStrategy;
    }

    public List<JobPostion> scrape(SearchParams params) {
        List<JobPostion> results = new ArrayList<>();
        String url = portalStrategy.createPageUrl(params);

        try (WebClient webClient = setUpNewWebClient()) {
            HtmlPage page = webClient.getPage(url);
            Document document = Jsoup.parse(page.asXml());

            Elements select = document.select(portalStrategy.cssSelectorToLinkOffers());

            List<String> links = select.stream()
                    .map(element -> element.attr("href"))
                    .collect(Collectors.toList());


            for (String link : links) {

                try (WebClient subWebClient = setUpNewWebClient()) {
                    HtmlPage offerPage = subWebClient.getPage(link);

                    JobPostion jobPostion = portalStrategy.assembleJobFrom(offerPage);

                    results.add(jobPostion);
                } catch (Exception ex) {
                    LOGGER.warning("Cannot handle " + link);
                }
            }
        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + url);
        }

        return results;
    }

    private WebClient setUpNewWebClient() {
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }
}
