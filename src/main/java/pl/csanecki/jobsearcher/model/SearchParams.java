package pl.csanecki.jobsearcher.model;

public class SearchParams {

    public final String language;
    public final String city;

    public SearchParams(String language, String city) {
        this.language = language;
        this.city = city;
    }
}
