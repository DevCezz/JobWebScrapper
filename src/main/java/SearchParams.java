public class SearchParams {

    private static final int DEFAULT_KM_FAR = 30;

    public final String job;
    public final String city;

    public SearchParams(String job, String city) {
        this.job = job;
        this.city = city;
    }
}
