package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
//    //TODO Get a list of department IDS where the number of employees doesn't exceed 3 people
    @Query(
            value = "select dep.id from\n" +
                    "(\n" +
                    "    select department.id,count(department_id) as count_emp from department inner join employee e on department.id = e.department_id\n" +
                    "    group by department.id\n" +
                    "    having count(department_id)<4\n" +
                    "    ) as dep",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    //TODO Get a list of departments IDs with the maximum total salary of employees
    @Query(
            value = "select department_id from employee\n" +
                    "group by department_id\n" +
                    "having sum(salary)= (select max(emp.sum_s) from (\n" +
                    "            select department_id as id,sum(salary)  as sum_s from employee group by department_id\n" +
                    "             ) as emp)",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
