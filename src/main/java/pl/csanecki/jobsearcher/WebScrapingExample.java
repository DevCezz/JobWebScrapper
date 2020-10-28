package pl.csanecki.jobsearcher;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class WebScrapingExample {

    public static void main(String[] args) {
        String url = String.format(
                "https://www.pracuj.pl/praca/%s;kw/%s;wp?rd=30",
                "java",
                "warszawa"
        );

        try (WebClient webClient = setUpWebClient()) {

            HtmlPage htmlPage = webClient.getPage(url);
            Document parsedDocument = Jsoup.parse(htmlPage.asXml());
            Elements jobLinks = parsedDocument
                    .select("#results .offer-details__title-link");

            Set<String> employersNames = jobLinks.stream()
                    .map(jobLink -> jobLink.attr("href"))
                    .map(WebScrapingExample::scrapeThroughOfferPage)
                    .collect(Collectors.toSet());

            employersNames.forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException("Cannot connect to " + url);
        }
    }

    private static String scrapeThroughOfferPage(String subUrl) {
        try (WebClient subWebClient = setUpWebClient()) {

            HtmlPage subHtmlPage = subWebClient.getPage(subUrl);
            Document subParsedDocument = Jsoup.parse(subHtmlPage.asXml());

            Element employerElement = subParsedDocument
                    .selectFirst("h2[data-test='text-employerName']");

            return employerElement.ownText();
        } catch (IOException e) {
            throw new RuntimeException("Cannot connect to " + subUrl);
        }
    }

    private static WebClient setUpWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }
}
