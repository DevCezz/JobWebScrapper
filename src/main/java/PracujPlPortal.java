public class PracujPlPortal implements PortalStrategy {

    private final static String URL_PAGE = "https://www.pracuj.pl/praca/%s;kw/%s;wp?rd=10";
    private final static String CSS_SELECTOR = "#results .offer-details__title-link";

    @Override
    public String createPageUrl(SearchParams params) {
        return String.format(URL_PAGE, params.job, params.city);
    }

    @Override
    public String cssSelectorToLinkOffers() {
        return CSS_SELECTOR;
    }
}
