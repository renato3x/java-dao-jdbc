package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Main2 {
  public static void main(String[] args) {
    DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

    System.out.println("-> Department - FindById <-");
    Department dep = departmentDao.findById(1);
    System.out.println(dep);

    System.out.println("\n-> Department - FindAll <-");
    List<Department> departments = departmentDao.findAll();
    departments.forEach(System.out::println);

    System.out.println("\n-> Department - Insert <-");
    Department newDep = new Department(null, "Music");
    departmentDao.insert(newDep);
    System.out.println("Department created: " + newDep);

    System.out.println("\n-> Deparment - Update <-");
    newDep.setName("Medicines");
    departmentDao.update(newDep);
    System.out.println("Department updated");

    System.out.println("\n-> Department - DeleteById <-");
    departmentDao.deleteById(newDep.getId());
    System.out.println("Department deleted");
  }
}
