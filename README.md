# University Console Application

This is a simple Spring Boot Java project with a console interface for managing university departments and lectors. The application allows you to perform various operations related to university departments and their associated data.

## Getting Started

### Prerequisites

Before running the application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK)
- Spring Boot
- Maven
- An Integrated Development Environment (IDE) like IntelliJ IDEA

### Installation

1. Clone the repository to your local machine:
```command
git clone https://github.com/BusyhinD/UniversityConsoleManager.git
```

2. Open the project in your preferred IDE.

3. Build the project using Maven:
```command
mvn clean install
```
4. Run the application:
```command
java -jar target/university-console-manager-0.0.1-SNAPSHOT.jar
```


Now the application should be up and running in your console.

## Usage

The application implements several commands to interact with university data. Here are some example commands:

1. To find the head of a department:
```command
User Input: Who is head of department {department_name}
Answer: Head of {department_name} department is {head_of_department_name}
```

2. To show statistics for a department:
```command
User Input: Show {department_name} statistics.
Answer: assistans - {assistams_count}.
associate professors - {associate_professors_count}
professors -{professors_count}
```
3. To show the average salary for a department:
```command
User Input: Show the average salary for the department {department_name}.
Answer: The average salary of {department_name} is {average_salary}
```
4. To show the count of employees in a department:
```command
User Input: Show count of employees for {department_name}.
Answer: {employee_count}
```
5. To perform a global search:
```command
User Input: Global search by {template}.
Example: Global search by mit
Answer: Alice Smith, Mary Smith
```
## Acknowledgments

Special thanks for providing this test task.

Good luck with your university console application! 🚀
