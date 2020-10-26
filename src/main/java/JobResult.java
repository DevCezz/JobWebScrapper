public class JobResult {

    public final String link;
    public final String jobTitle;
    public final String employer;
    public String salary;
    public String workplace;
    public String expiration;
    public String contract;
    public String schedule;
    public String employmentType;

    public JobResult(String link, String jobTitle, String employer) {
        this.link = link;
        this.jobTitle = jobTitle;
        this.employer = employer;
    }

    @Override
    public String toString() {
        return "JobResult{" +
                "link='" + link + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", employer='" + employer + '\'' +
                ", salary='" + salary + '\'' +
                ", workplace='" + workplace + '\'' +
                ", expiration='" + expiration + '\'' +
                ", contract='" + contract + '\'' +
                ", schedule='" + schedule + '\'' +
                ", employment='" + employmentType + '\'' +
                '}';
    }
}
