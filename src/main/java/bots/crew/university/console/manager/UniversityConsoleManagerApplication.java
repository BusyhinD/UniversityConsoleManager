package bots.crew.university.console.manager;

import bots.crew.university.console.manager.exception.EntityNotFoundException;
import bots.crew.university.console.manager.service.UniversityService;
import java.util.Objects;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UniversityConsoleManagerApplication {
    private static final String WHO_IS_HEAD = "Who is head of department ";
    private static final String STATISTIC = "Show ";
    private static final String STATISTIC_ENDING = " statistics";
    private static final String AVERAGE_SALARY = "Show the average salary for the department ";
    private static final String EMPLOYEE_COUNT = "Show count of employee for ";
    private static final String GLOBAL_SEARCH = "Global search by ";


    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(UniversityConsoleManagerApplication.class, args);
        UniversityService service = context.getBean(UniversityService.class);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome! Write command (or 'exit' for exit):");
        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("exit")) {
                System.out.println("Exit from application.");
                break;
            }
            try {
                if (isCommandStartsWith(userInput, WHO_IS_HEAD)) {
                    String departmentName = userInput.substring(WHO_IS_HEAD.length());
                    System.out.printf("Head of %s department is %s" + System.lineSeparator(),
                            departmentName,
                            service.getDepartmentHead(departmentName));

                } else if (isCommandStartsWith(userInput, STATISTIC) && userInput.endsWith(STATISTIC_ENDING)) {
                    String departmentName =
                            userInput.substring(STATISTIC.length(), userInput.indexOf(STATISTIC_ENDING));
                    System.out.println(service.getStatistic(departmentName));

                } else if (isCommandStartsWith(userInput, AVERAGE_SALARY)) {
                    String departmentName = userInput.substring(AVERAGE_SALARY.length());
                    System.out.printf("Show the average salary for the department %s is %s"
                                    + System.lineSeparator(),
                            departmentName,
                            service.getAverageSalary(departmentName));

                } else if (isCommandStartsWith(userInput, EMPLOYEE_COUNT)) {
                    String departmentName = userInput.substring(EMPLOYEE_COUNT.length());
                    System.out.println(service.getCountOfEmployee(departmentName));

                } else if (isCommandStartsWith(userInput, GLOBAL_SEARCH)) {
                    String template = userInput.substring(GLOBAL_SEARCH.length());
                    String response = service.searchEverywhere(template);
                    System.out.println(Objects.isNull(response) ? "Not found any match" : response);

                } else {
                    System.out.println("Not found user command: " + userInput);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

    private static boolean isCommandStartsWith(String userInput, String command) {
        return userInput.startsWith(command) && userInput.length() >= command.length();
    }
}
