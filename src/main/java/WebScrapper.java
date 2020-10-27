import java.util.List;

public class WebScrapper {

    public static void main(String[] args) {
        PortalStrategy portalStrategy = new PracujPlPortal();
        WebScrapingClient client = new WebScrapingClient(portalStrategy);

        List<JobPostion> scrape = client.scrape(new SearchParams("Java", "Warszawa"));

        scrape.forEach(System.out::println);
    }
}
