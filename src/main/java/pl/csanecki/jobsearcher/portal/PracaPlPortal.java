package pl.csanecki.jobsearcher.portal;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import pl.csanecki.jobsearcher.client.PortalStrategy;
import pl.csanecki.jobsearcher.model.JobPosition;
import pl.csanecki.jobsearcher.model.SearchParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PracaPlPortal implements PortalStrategy {

    private static final String URL_PAGE = "https://www.praca.pl";
    private static final String URL_SEARCH = URL_PAGE + "/s-%s,%s_%d.html";
    private static final String CSS_PAGINATION_SELECTOR = ".pagination__item";
    private static final String CSS_ITEM_SELECTOR = ".listing__list .listing__item .listing__offer-title";

    private static final String QUERY_JOB_TITLE = "p[class='app-offer__title']";
    private static final String QUERY_EMPLOYER = "p[class='app-offer__employer']";
    private static final String QUERY_WORKPLACE = "p[class='app-offer__location']";

    @Override
    public String createPageUrl(SearchParams params) {
        return String.format(URL_SEARCH, params.language, params.city, 1);
    }

    @Override
    public String createPageUrl(SearchParams params, int pageNum) {
        return String.format(URL_SEARCH, params.language, params.city, pageNum);
    }

    @Override
    public String cssSelectorForPagination() {
        return CSS_PAGINATION_SELECTOR;
    }

    @Override
    public String cssSelectorToLinkOffers() {
        return CSS_ITEM_SELECTOR;
    }

    @Override
    public JobPosition assembleJobFrom(HtmlPage page) {

        Document subDocument = Jsoup.parse(page.asXml());

        Element jobNameElement = subDocument.selectFirst(QUERY_JOB_TITLE);
        Element employerNameElement = subDocument.selectFirst(QUERY_EMPLOYER);

        Element workplaceElement = subDocument.selectFirst(QUERY_WORKPLACE);

        return JobPosition.of(page.getUrl().toString(), jobNameElement.text(), employerNameElement.text())
                .addWorkplace(workplaceElement != null ? workplaceElement.text() : "brak");
    }

    @Override
    public String createAbsolutePath(String path) {
        return path.startsWith("/") ? URL_PAGE + path : path;
    }
}
