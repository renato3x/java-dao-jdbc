package application;

import model.entities.Department;

public class Main {
  public static void main(String[] args) {
    Department dep = new Department(1, "Books");

    System.out.println(dep);
  }
}
