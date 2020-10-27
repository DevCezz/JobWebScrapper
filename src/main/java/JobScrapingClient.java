import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class JobScrapingClient {

    private static final Logger LOGGER = Logger.getLogger( JobScrapingClient.class.getName() );

    private final PortalStrategy portalStrategy;

    public JobScrapingClient(PortalStrategy portalStrategy) {
        this.portalStrategy = portalStrategy;
    }

    public List<JobPostion> scrapeForJobs(SearchParams params) {
        String url = portalStrategy.createPageUrl(params);

        try (WebClient webClient = setUpWebClient()) {
            HtmlPage htmlPage = webClient.getPage(url);
            Document parsedDocument = Jsoup.parse(htmlPage.asXml());

            Elements linkOffersElements = parsedDocument.select(portalStrategy.cssSelectorToLinkOffers());

            return Stream.ofAll(linkOffersElements)
                    .map(element -> element.attr("href"))
                    .map(this::scrapeForJobPosition)
                    .filter(Objects::nonNull)
                    .toList();

        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + url);
        }
    }

    private JobPostion scrapeForJobPosition(String link) {
        try (WebClient subWebClient = setUpWebClient()) {
            HtmlPage offerHtmlPage = subWebClient.getPage(link);

            return portalStrategy.assembleJobFrom(offerHtmlPage);
        } catch (Exception ex) {
            LOGGER.warning("Cannot handle " + link);
            return null;
        }
    }

    private WebClient setUpWebClient() {
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }
}
