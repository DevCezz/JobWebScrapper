import com.gargoylesoftware.htmlunit.WebClient;

import java.io.IOException;

public class WebScrapper {

    private final static String urlPage = "https://www.pracuj.pl/praca/%s;kw/%s;wp?rd=%d";

    private PortalStrategy portalStrategy;

    public WebScrapper(PortalStrategy portalStrategy) {
        this.portalStrategy = portalStrategy;
    }

    public void scrape(SearchParams params) {
        try {
            String urlWithParams = String.format(urlPage, params.job, params.city, params.kmFar);
            webClient.getPage(urlWithParams);
        } catch (IOException e) {
            throw new CannotReachPageException("Cannot connect to " + urlPage);
        }
    }

    public void disableCss() {
        webClient.getOptions().setCssEnabled(false);
    }

    public void enableCss() {
        webClient.getOptions().setCssEnabled(true);
    }

    public void disableJavasript() {
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    public void enableJavasript() {
        webClient.getOptions().setJavaScriptEnabled(true);
    }
}
