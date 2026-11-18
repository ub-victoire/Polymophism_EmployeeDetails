package org.emp;

public class Employee {
    public void empId(int id) {
        System.out.println("Employee ID (int): " + id);
    }

    public void empId(long id) {
        System.out.println("Employee ID (long): " + id);
    }

    public void empId(String id) {
        System.out.println("Employee ID (String): " + id);
    }

    public static void main(String[] args) {
        Employee employee = new Employee();

        employee.empId(1001);
        employee.empId(9000000001L);
        employee.empId("EMP001");
    }
}
