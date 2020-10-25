import com.gargoylesoftware.htmlunit.WebClient;

public class WebScrapper {

    private final WebClient webClient;

    public WebScrapper(WebClient webClient) {
        this.webClient = webClient;
    }

    public void disableCss() {
        webClient.getOptions().setCssEnabled(false);
    }

    public void enableCss() {
        webClient.getOptions().setCssEnabled(true);
    }

    public void disableJavasript() {
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    public void enableJavasript() {
        webClient.getOptions().setJavaScriptEnabled(true);
    }
}
