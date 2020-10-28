package pl.csanecki.jobsearcher.client;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import pl.csanecki.jobsearcher.exception.CannotReachPageException;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import pl.csanecki.jobsearcher.model.JobPosition;
import pl.csanecki.jobsearcher.model.SearchParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class JobScrapingClient {

    private static final Logger LOGGER = Logger.getLogger( JobScrapingClient.class.getName() );

    private static final String LINK_ATTRIBUTE = "href";

    private final PortalStrategy portalStrategy;

    public JobScrapingClient(PortalStrategy portalStrategy) {
        this.portalStrategy = portalStrategy;
    }

    public List<JobPosition> scrapeForJobs(SearchParams params) {
        String url = portalStrategy.createPageUrl(params);

        try (WebClient webClient = setUpWebClient()) {
            HtmlPage htmlPage = webClient.getPage(url);
            Document parsedDocument = Jsoup.parse(htmlPage.asXml());

            Elements pageNums = parsedDocument.select(portalStrategy.cssSelectorForPagination());
            Option<Integer> maxPageNum = Stream.ofAll(pageNums)
                    .map(Element::text)
                    .filter(this::isNumeric)
                    .map(Integer::parseInt)
                    .max();

            Option<List<JobPosition>> jobPositions = maxPageNum.map(maxPage -> Stream.iterate(1, i -> i + 1)
                    .take(maxPage)
                    .map(numPage -> portalStrategy.createPageUrl(params, numPage))
                    .map(this::scrapeSubPage)
                    .flatMap(List::toStream)
                    .toList());

            return jobPositions.getOrElse(List.empty());

        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + url);
        }
    }

    private List<JobPosition> scrapeSubPage(String subUrl) {
        try (WebClient subWebClient = setUpWebClient()) {
            HtmlPage subHtmlPage = subWebClient.getPage(subUrl);
            Document subParsedDocument = Jsoup.parse(subHtmlPage.asXml());
            Elements linkOffersElements = subParsedDocument.select(portalStrategy.cssSelectorToLinkOffers());

            return Stream.ofAll(linkOffersElements)
                    .map(element -> element.attr(LINK_ATTRIBUTE))
                    .map(portalStrategy::createAbsolutePath)
                    .map(this::scrapeForJobPosition)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + subUrl);
        }
    }

    private JobPosition scrapeForJobPosition(String link) {
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

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}
