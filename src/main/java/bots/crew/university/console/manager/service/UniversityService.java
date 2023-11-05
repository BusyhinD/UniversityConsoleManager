package bots.crew.university.console.manager.service;

import bots.crew.university.console.manager.exception.EntityNotFoundException;
import bots.crew.university.console.manager.model.Department;
import bots.crew.university.console.manager.model.Lector;
import bots.crew.university.console.manager.repository.DepartmentRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final DepartmentRepository departmentRepository;

    public String getDepartmentHead(String name) {
        Department department = departmentRepository.getDepartmentByNameIgnoreCase(name).orElseThrow(
                () -> new EntityNotFoundException("Not found department with name: " + name));
        return department.getDepartmentHead().getFirstName()
                + " " + department.getDepartmentHead().getLastName();
    }

    public String getStatistic(String name) {
        Department department = departmentRepository.getDepartmentByNameIgnoreCase(name).orElseThrow(
                () -> new EntityNotFoundException("Not found department with name: " + name));
        Map<String, Long> statistic = department.getLectors()
                .stream()
                .map(Lector::getDegree)
                .collect(Collectors.groupingBy(degree ->
                                degree.toString().replace("_", " ").toLowerCase(),
                        Collectors.counting()));

        List<String> result = new ArrayList<>();
        for (var degree : statistic.entrySet()) {
            result.add(degree.getKey() + "s - " + degree.getValue());
        }
        return String.join(System.lineSeparator(), result);
    }

    public BigDecimal getAverageSalary(String name) {
        return departmentRepository.getAverageSalaryByName(name).orElseThrow(
                () -> new EntityNotFoundException("Not found department with name: " + name));
    }

    public Integer getCountOfEmployee(String name) {
        return departmentRepository.countAllByName(name);
    }

    public String searchEverywhere(String template) {
        return String.join(", ", departmentRepository.searchEverywhere(template));
    }
}
