import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class DateUtil {
    /**
     * Parses a date string in DD-MM-YYYY or YYYY-MM-DD format and returns a LocalDate.
     * Returns null if format is invalid.
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null) return null;
        dateStr = dateStr.trim();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(dateStr, formatter1);
        } catch (DateTimeParseException e) {
            // Ignore and try next
        }

        try {
            return LocalDate.parse(dateStr, formatter2);
        } catch (DateTimeParseException e) {
            // Ignore and report failure below
        }

        return null;
    }

    /**
     * Calculates age in years given date of birth.
     */
    public static int calculateAge(LocalDate dob) {
        if (dob == null) return -1;
        LocalDate today = LocalDate.now();
        if (dob.isAfter(today)) return -1;
        return Period.between(dob, today).getYears();
    }
}

class Person {
    private String name;
    private LocalDate dob;

    public Person(String name, String dobString) {
        this.name = name;
        this.dob = DateUtil.parseDate(dobString);
        if (this.dob == null) {
            throw new IllegalArgumentException("Invalid date format for Date of Birth. Must be DD-MM-YYYY or YYYY-MM-DD");
        }
    }

    public void displayName() {
        System.out.println("Person Name: " + name);
    }

    public void displayAge() {
        int age = DateUtil.calculateAge(dob);
        if (age < 0) {
            System.out.println("Invalid Date of Birth.");
        } else {
            System.out.println("Age: " + age + " years");
        }
    }
}

class Employee extends Person {
    private String empId;
    private double salary;

    public Employee(String name, String dobString, String empId, double salary) {
        super(name, dobString);
        this.empId = empId;
        this.salary = salary;
    }

    public void displayEmployeeDetails() {
        displayName();
        displayAge();
        System.out.println("Employee Details:");
        System.out.println("Employee ID: " + empId);
        System.out.println("Salary: $" + salary);
    }
}

public class EmployeeApp {
    public static void main(String[] args) {
        try {
            Employee employee = new Employee("Alice Johnson", "15-05-1990", "E12345", 75000);
            employee.displayEmployeeDetails();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
