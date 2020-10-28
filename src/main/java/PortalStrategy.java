import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface PortalStrategy {

    String createPageUrl(SearchParams params);

    String createPageUrl(SearchParams params, int pageNum);

    String cssSelectorForPagination();

    String cssSelectorToLinkOffers();

    JobPosition assembleJobFrom(HtmlPage page);

    String createAbsolutePath(String path);
}
