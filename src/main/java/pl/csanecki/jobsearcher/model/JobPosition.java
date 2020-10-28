package pl.csanecki.jobsearcher.model;

public class JobPosition {

    private final String link;
    private final String jobTitle;
    private final String employer;
    private String salary;
    private String workplace;
    private String expiration;
    private String contract;
    private String schedule;
    private String employmentType;

    private JobPosition(String link, String jobTitle, String employer) {
        this.link = link;
        this.jobTitle = jobTitle;
        this.employer = employer;
    }
    
    public static JobPosition of(String link, String jobTitle, String employer) {
        return new JobPosition(link, jobTitle, employer);
    }

    public JobPosition addSalary(String salary) {
        this.salary = salary;
        return this;
    }

    public JobPosition addWorkplace(String workplace) {
        this.workplace = workplace;
        return this;
    }

    public JobPosition addExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    public JobPosition addContract(String contract) {
        this.contract = contract;
        return this;
    }

    public JobPosition addSchedule(String schedule) {
        this.schedule = schedule;
        return this;
    }

    public JobPosition addEmploymentType(String employmentType) {
        this.employmentType = employmentType;
        return this;
    }

    public String getLink() {
        return link;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmployer() {
        return employer;
    }

    public String getSalary() {
        return salary;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getContract() {
        return contract;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    @Override
    public String toString() {
        return "JobPosition{" +
                "link='" + link + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", employer='" + employer + '\'' +
                ", salary='" + salary + '\'' +
                ", workplace='" + workplace + '\'' +
                ", expiration='" + expiration + '\'' +
                ", contract='" + contract + '\'' +
                ", schedule='" + schedule + '\'' +
                ", employmentType='" + employmentType + '\'' +
                '}';
    }
}
