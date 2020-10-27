import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface PortalStrategy {

    String createPageUrl(SearchParams params);

    String cssSelectorToLinkOffers();

    JobPostion assembleJobFrom(HtmlPage page);
}
