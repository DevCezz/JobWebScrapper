public class JobPostion {

    public final String link;
    public final String jobTitle;
    public final String employer;
    public String salary;
    public String workplace;
    public String expiration;
    public String contract;
    public String schedule;
    public String employmentType;

    private JobPostion(String link, String jobTitle, String employer) {
        this.link = link;
        this.jobTitle = jobTitle;
        this.employer = employer;
    }
    
    public static JobPostion of(String link, String jobTitle, String employer) {
        return new JobPostion(link, jobTitle, employer);
    }

    public JobPostion addSalary(String salary) {
        this.salary = salary;
        return this;
    }

    public JobPostion addWorkplace(String workplace) {
        this.workplace = workplace;
        return this;
    }

    public JobPostion addExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    public JobPostion addContract(String contract) {
        this.contract = contract;
        return this;
    }

    public JobPostion addSchedule(String schedule) {
        this.schedule = schedule;
        return this;
    }

    public JobPostion addEmploymentType(String employmentType) {
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
