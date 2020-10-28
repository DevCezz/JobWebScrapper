package pl.csanecki.jobsearcher.portal;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.csanecki.jobsearcher.client.PortalStrategy;
import pl.csanecki.jobsearcher.model.JobPosition;
import pl.csanecki.jobsearcher.model.SearchParams;

public class BulldogJobPortal implements PortalStrategy {

    private static final String URL_PAGE = "https://bulldogjob.pl";
    private final static String URL_SEARCH = URL_PAGE + "/companies/jobs/s/city,%s/skills,%s?page=%d";
    private static final String CSS_PAGINATION_SELECTOR = ".pagination li a";
    private static final String CSS_ITEM_SELECTOR = ".job-item";

    public static final String QUERY_JOB_TITLE = ".job-top .data h1";
    public static final String QUERY_EMPLOYER = ".job-top .data .company-name";
    public static final String QUERY_SALARY = ".salary";
    public static final String QUERY_WORKPLACE = ".flex.flex-end.details:nth-child(3)";
    public static final String QUERY_CONTRACT_INFO = ".flex.flex-end.details:nth-child(2)";
    private static final String QUERY_EMPLOYMENT_TYPE = ".seniority strong";

    @Override
    public String createPageUrl(SearchParams params) {
        return String.format(URL_SEARCH, params.city, params.language, 1);
    }

    @Override
    public String createPageUrl(SearchParams params, int pageNum) {
        return String.format(URL_SEARCH, params.city, params.language, pageNum);
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
        Element salaryElement = subDocument.selectFirst(QUERY_SALARY);

        Element workplaceElement = subDocument.selectFirst(QUERY_WORKPLACE);
        Element contractElement = subDocument.selectFirst(QUERY_CONTRACT_INFO);
        Element employmentTypeElement = subDocument.selectFirst(QUERY_EMPLOYMENT_TYPE);

        return JobPosition.of(page.getUrl().toString(), jobNameElement.text(), employerNameElement.text())
                .addSalary(salaryElement != null ? salaryElement.text() : "brak")
                .addWorkplace(workplaceElement != null ? workplaceElement.text() : "brak")
                .addContract(contractElement != null ? contractElement.text() : "brak")
                .addEmploymentType(employmentTypeElement != null ? employmentTypeElement.text() : "brak");
    }

    @Override
    public String createAbsolutePath(String path) {
        return path.startsWith("/") ? URL_PAGE + path : path;
    }
}
