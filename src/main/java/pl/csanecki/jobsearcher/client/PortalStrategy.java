package pl.csanecki.jobsearcher.client;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import pl.csanecki.jobsearcher.model.JobPosition;
import pl.csanecki.jobsearcher.model.SearchParams;

public interface PortalStrategy {

    String createPageUrl(SearchParams params);

    String createPageUrl(SearchParams params, int pageNum);

    String cssSelectorForPagination();

    String cssSelectorToLinkOffers();

    JobPosition assembleJobFrom(HtmlPage page);

    String createAbsolutePath(String path);
}
