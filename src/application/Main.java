package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Main {
  public static void main(String[] args) {
    Department dep = new Department(1, "Books");
    Seller seller = new Seller(1, "oiafo", "email", new Date(), 300.0, dep);

    System.out.println(dep);
    System.out.println(seller);
  }
}
