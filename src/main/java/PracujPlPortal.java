import com.gargoylesoftware.htmlunit.WebClient;

public class PracujPlPortal implements PortalStrategy {

    private final WebClient webClient;

    public PracujPlPortal(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void foo(SearchParams params) {

    }
}
