package org.emp;

public void Employee {
    public void empId(ind id){
        System.out.println("Employee ID (int): " + id);
    }

    public void empId(long id){
        System.out.println("Employee ID: "+ id);
    }

    public void empId(String){
        System.out.println("Employee ID(String): "+ id);
    }

    public static void main (String[] args){
        var employee = new Employee();

        employee.empId(1001);
        employee.empId(9000000001L);
        employee.empId("EMP001");

    }

}
