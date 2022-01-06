package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {
  public static void main(String[] args) {

    System.out.println("-> Seller - FindById <-");
    SellerDao sellerDao = DaoFactory.createSellerDao();
    Seller seller = sellerDao.findById(1);
    System.out.println(seller);

    System.out.println("\n-> Seller - FindByDepartment <-");
    Department dep = new Department(1, null);
    List<Seller> sellers = sellerDao.findByDepartment(dep);
    sellers.forEach(System.out::println);

    System.out.println("\n-> Seller - FindAll <-");
    sellers = sellerDao.findAll();
    sellers.forEach(System.out::println);

    System.out.println("\n-> Seller - Insert <-");
    Seller newSeller = new Seller(null, "lorem", "lorem@gmail.com", new Date(), 4000.0, dep);
    sellerDao.insert(newSeller);

    System.out.println("\n-> Seller - FindAll <-");
    sellers = sellerDao.findAll();
    sellers.forEach(System.out::println);
  }
}
