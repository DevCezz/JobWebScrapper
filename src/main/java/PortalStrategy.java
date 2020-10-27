import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface PortalStrategy {

    String createPageUrl(SearchParams params);

    String cssSelectorToLinkOffers();

    JobPosition assembleJobFrom(HtmlPage page);
}
