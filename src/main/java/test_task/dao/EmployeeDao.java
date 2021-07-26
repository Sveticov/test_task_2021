package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query(
            value = "select * from employee as e inner join employee e2 on e.boss_id = e2.id where e.salary>e2.salary;",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query(
            value = "select * from employee where (department_id,salary) in (select department_id,max(salary) from employee group by department_id)",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query(
            value = "select * from employee where boss_id is null",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();

    @Query(
            value = "select * from employee where department_id =" +
                    "(select department_id from (select department_id,count(department_id) from employee\n" +
                    "group by department_id having count(department_id) <4) as empl)",
            nativeQuery = true
    )
    List<Employee> findAllEmployeeWhereCount();
}
