import io.vavr.collection.List;

public class WebScrapper {

    public static void main(String[] args) {
        PortalStrategy portalStrategy = new PracujPlPortal();
        JobScrapingClient client = new JobScrapingClient(portalStrategy);

        List<JobPosition> jobPositions = client.scrapeForJobs(new SearchParams("Java", "Warszawa"));

        jobPositions.forEach(System.out::println);
    }
}
