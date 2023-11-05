package bots.crew.university.console.manager.service;

import bots.crew.university.console.manager.exception.EntityNotFoundException;
import bots.crew.university.console.manager.model.Department;
import bots.crew.university.console.manager.model.Lector;
import bots.crew.university.console.manager.model.enumiration.Degree;
import bots.crew.university.console.manager.repository.DepartmentRepository;
import bots.crew.university.console.manager.repository.LectorRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UniversityServiceTest {
    @Autowired
    UniversityService universityService;

    @BeforeAll
    static void beforeAll(@Autowired LectorRepository lectorRepository,
                          @Autowired DepartmentRepository departmentRepository) {
        Lector firstLector = new Lector();
        firstLector.setDegree(Degree.PROFESSOR);
        firstLector.setFirstName("Bob");
        firstLector.setLastName("Nichol");
        firstLector.setSalary(new BigDecimal("100"));

        Lector secondLector = new Lector();
        secondLector.setDegree(Degree.PROFESSOR);
        secondLector.setFirstName("Alice");
        secondLector.setLastName("Smith");
        secondLector.setSalary(new BigDecimal("200"));

        Lector thirdLector = new Lector();
        thirdLector.setDegree(Degree.ASSISTANT);
        thirdLector.setFirstName("Mary");
        thirdLector.setLastName("Smith");
        thirdLector.setSalary(new BigDecimal("50"));

        Lector fourthLector = new Lector();
        fourthLector.setDegree(Degree.ASSOCIATE_PROFESSOR);
        fourthLector.setFirstName("David");
        fourthLector.setLastName("Brown");
        fourthLector.setSalary(new BigDecimal("75"));

        lectorRepository.saveAll(List.of(firstLector, secondLector, thirdLector, fourthLector));

        Department firstDepartment = new Department();
        firstDepartment.setName("first department");
        firstDepartment.setDepartmentHead(firstLector);
        firstDepartment.setLectors(Set.of(firstLector, secondLector, fourthLector));

        Department secondDepartment = new Department();
        secondDepartment.setName("second department");
        secondDepartment.setDepartmentHead(secondLector);
        secondDepartment.setLectors(Set.of(firstLector, thirdLector));

        Department thirdDepartment = new Department();
        thirdDepartment.setName("third department");
        thirdDepartment.setDepartmentHead(thirdLector);
        thirdDepartment.setLectors(Set.of(secondLector, fourthLector));

        Department fourthDepartment = new Department();
        fourthDepartment.setName("fourth department");
        fourthDepartment.setDepartmentHead(fourthLector);
        fourthDepartment.setLectors(Set.of(firstLector, secondLector));

        departmentRepository.saveAll(List.of(firstDepartment, secondDepartment, thirdDepartment, fourthDepartment));
    }

    @Test
    void whoIsHead_validDepartment_Success() {
        String expected = "Alice Smith";
        String actual = universityService.getDepartmentHead("seconD deparTment");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getStatistic_validDepartment_Success() {
        String professorLine = "professors - 2";
        String associateProfessorLine = "associate professors - 1";
        String actual = universityService.getStatistic("fiRst department");
        System.out.println(actual);
        Assertions.assertTrue(actual.contains(professorLine));
        Assertions.assertTrue(actual.contains(associateProfessorLine));
    }

    @Test
    void getAverageSalary_validDepartment_Success() {
        BigDecimal expected = new BigDecimal("137.5");
        BigDecimal actual = universityService.getAverageSalary("third Department");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getEmployeeCount_validDepartment_Success() {
        Integer expected = 2;
        Integer actual = universityService.getCountOfEmployee("Fourth department");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getEmployeeCount_invalidDepartment_Success() {
        Integer expected = 0;
        Integer actual = universityService.getCountOfEmployee("fifth department");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void searchEverywhere_validInput_Success() {
        String aliceLine = "Alice Smith";
        String maryLine = "Mary Smith";
        String firstActual = universityService.searchEverywhere("sMith");
        String secondActual = universityService.searchEverywhere("dEpartmenT");

        Assertions.assertTrue(firstActual.contains(aliceLine));
        Assertions.assertTrue(firstActual.contains(maryLine));
        Assertions.assertFalse(firstActual.contains("Bob"));

        Assertions.assertTrue(secondActual.contains("first"));
        Assertions.assertTrue(secondActual.contains("second"));
        Assertions.assertTrue(secondActual.contains("third"));
        Assertions.assertTrue(secondActual.contains("fourth"));
        Assertions.assertFalse(secondActual.contains("David"));
    }

    @Test
    void invalidDepartmentName_Fail() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> universityService.getDepartmentHead("first"));
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> universityService.getStatistic("second"));
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> universityService.getAverageSalary("third"));
    }

    @Test
    void getStatistic_invalidDepartment_Fail() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> universityService.getStatistic("department"));
    }

    @Test
    void searchEverywhere_invalidInput_Fail() {
        Assertions.assertEquals("", universityService.searchEverywhere("nonExistValueInDb"));
    }
}