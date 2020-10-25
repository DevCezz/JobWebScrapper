public class SearchParams {

    private static final int DEFAULT_KM_FAR = 30;

    public final String job;
    public final String city;
    public final int kmFar;

    public SearchParams(String job, String city, int kmFar) {
        this.job = job;
        this.city = city;
        this.kmFar = kmFar;
    }

    public SearchParams(String job, String city) {
        this(job, city, DEFAULT_KM_FAR);
    }
}
