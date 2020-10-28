package pl.csanecki.jobsearcher;

import pl.csanecki.jobsearcher.client.JobScrapingClient;
import io.vavr.collection.List;
import pl.csanecki.jobsearcher.model.JobPosition;
import pl.csanecki.jobsearcher.model.SearchParams;
import pl.csanecki.jobsearcher.client.PortalStrategy;
import pl.csanecki.jobsearcher.portal.PracujPlPortal;

public class WebScrapper {

    public static void main(String[] args) {
        PortalStrategy portalStrategy = new PracujPlPortal();
        JobScrapingClient client = new JobScrapingClient(portalStrategy);

        List<JobPosition> jobPositions = client.scrapeForJobs(new SearchParams("Java", "Warszawa"));

        jobPositions.forEach(System.out::println);
    }
}
