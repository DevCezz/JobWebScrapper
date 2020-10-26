import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PracujPlPortal implements PortalStrategy {

    private final static String URL_PAGE = "https://www.pracuj.pl/praca/%s;kw/%s;wp?rd=%d";

    private final WebScrapingClient client;

    public PracujPlPortal(WebScrapingClient client) {
        this.client = client;
        this.client.disableCss();
        this.client.disableJs();
    }

    @Override
    public void foo(SearchParams params) {
        HtmlPage page = client.scrape(URL_PAGE);
    }
}
