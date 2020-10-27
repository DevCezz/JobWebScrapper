public class JobPosition {

    public final String link;
    public final String jobTitle;
    public final String employer;
    public String salary;
    public String workplace;
    public String expiration;
    public String contract;
    public String schedule;
    public String employmentType;

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
