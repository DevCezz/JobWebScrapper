import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class WebScrapingClient {

    private final WebClient webClient;

    public WebScrapingClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public HtmlPage scrape(String url) {
        try {
            return webClient.getPage(url);
        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + url);
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
}
