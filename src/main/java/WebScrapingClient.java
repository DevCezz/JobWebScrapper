import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class WebScrapingClient {

    private final WebClient webClient;

    public WebScrapingClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void scrape(String url) {
        try {
            HtmlPage page = webClient.getPage(url);
        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + url);
        }
    }
}
