import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PracujPlPortal implements PortalStrategy {

    private final static String URL_PAGE = "https://www.pracuj.pl/praca/%s;kw/%s;wp?rd=10";
    private final static String CSS_SELECTOR = "#results .offer-details__title-link";

    public static final String QUERY_JOB_TITLE = "h2[data-test='text-positionName']";
    public static final String QUERY_EMPLOYER = "h2[data-test='text-employerName']";
    public static final String QUERY_SALARY = "div[data-test='section-salary']";
    public static final String QUERY_WORKPLACE_PRECISE = "div[data-test='sections-benefit-workplaces'] a[href='#map-frame']";
    public static final String QUERY_WORKPLACE_ALTERNATIVE = "div[data-test='sections-benefit-workplaces'] p[data-test='text-benefit']";
    public static final String QUERY_EXPIRATION_DATE = "div[data-test='sections-benefit-expiration'] div[data-test='text-benefit']";
    public static final String QUERY_CONTRACT_INFO = "div[data-test='sections-benefit-contracts']";
    public static final String QUERY_SCHEDULE_WORK = "div[data-test='sections-benefit-work-schedule']";
    public static final String QUERY_EMPLOYMENT_TYPE = "div[data-test='sections-benefit-employment-type-name']";

    @Override
    public String createPageUrl(SearchParams params) {
        return String.format(URL_PAGE, params.job, params.city);
    }

    @Override
    public String cssSelectorToLinkOffers() {
        return CSS_SELECTOR;
    }

    public JobPosition assembleJobFrom(HtmlPage page) {

        Document subDocument = Jsoup.parse(page.asXml());

        Element jobNameElement = subDocument.selectFirst(QUERY_JOB_TITLE);
        Element employerNameElement = subDocument.selectFirst(QUERY_EMPLOYER);
        Element salaryElement = subDocument.selectFirst(QUERY_SALARY);

        Element workplaceElement = subDocument.selectFirst(QUERY_WORKPLACE_PRECISE);
        Element workplaceRepElement = subDocument.selectFirst(QUERY_WORKPLACE_ALTERNATIVE);
        Elements expirationElements = subDocument.select(QUERY_EXPIRATION_DATE);
        Element contractElement = subDocument.selectFirst(QUERY_CONTRACT_INFO);
        Element scheduleElement = subDocument.selectFirst(QUERY_SCHEDULE_WORK);
        Element employmentTypeElement = subDocument.selectFirst(QUERY_EMPLOYMENT_TYPE);

        return JobPosition.of(page.getUrl().toString(), jobNameElement.text(), employerNameElement.ownText())
                .addSalary(salaryElement != null ? salaryElement.text() : "brak")
                .addWorkplace(workplaceElement != null || workplaceRepElement != null ? (workplaceElement != null ? workplaceElement.text() : workplaceRepElement.text()) : "brak")
                .addExpiration(expirationElements != null ? expirationElements.get(expirationElements.size() - 1).text() : "brak")
                .addContract(contractElement != null ? contractElement.text() : "brak")
                .addSchedule(scheduleElement != null ? scheduleElement.text() : "brak")
                .addEmploymentType(employmentTypeElement != null ? employmentTypeElement.text() : "brak");
    }
}
