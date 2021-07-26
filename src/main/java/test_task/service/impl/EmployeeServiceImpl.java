package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.EmployeeDao;
import test_task.model.Employee;
import test_task.service.EmployeeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {

        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {

        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        Iterable<Employee> employees = employeeDao.findAll();
        List<Employee> employeeList = new ArrayList<Employee>((Collection<? extends Employee>) employees);
        Employee employee = employeeList.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
        employeeList.remove(employee);
        employeeDao.saveAll(employeeList);
        return employee.getId();
    }

    @Override
    public Long changeSalary(String name) {
        Iterable<Employee> employees = employeeDao.findAll();
        List<Employee> employeeList = new ArrayList<Employee>((Collection<? extends Employee>) employees);
        AtomicLong id = new AtomicLong(0l);
        employeeList = employeeList.stream()
                .peek(e -> {
                    if (e.getName().equals(name)) {
                        id.set(e.getId());
                        e.setSalary(new BigDecimal(20000));
                    }
                })
                .collect(Collectors.toList());

        employeeDao.saveAll(employeeList);

        return id.get();
    }

    @Override
    public Long hireEmployee(Employee employee) {

        long idNewEmployee = 0;
        Employee newEmployee = employeeDao.save(employee);
        idNewEmployee = newEmployee.getId();

        return idNewEmployee;
    }
}
