import com.gargoylesoftware.htmlunit.WebClient;

public class WebScrapper {

    public static void main(String[] args) {
        WebClient webClient = new WebClient();
        PortalStrategy portalStrategy = new PracujPlPortal();
        WebScrapingClient client = new WebScrapingClient(webClient, portalStrategy);

        client.turnOffLogs();
        client.scrape(new SearchParams("Java", "Warszawa"));
    }
}
