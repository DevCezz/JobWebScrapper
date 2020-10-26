public class PracujPlPortal implements PortalStrategy {

    private final static String urlPage = "https://www.pracuj.pl/praca/%s;kw/%s;wp?rd=%d";

    private final WebScrapingClient client;

    public PracujPlPortal(WebScrapingClient client) {
        this.client = client;
    }

    @Override
    public void foo(SearchParams params) {

    }
}
