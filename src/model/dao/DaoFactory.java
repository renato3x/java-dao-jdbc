package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
  public static SellerDao createSellerDao() {
    return new SellerDaoJDBC(DB.getConnection());
  }

  public static DepartmentDao createDepartmentDao() {
    return new DepartmentDaoJDBC(DB.getConnection());
  }
}

/**
 * A Factory tem como objetivo proteger a implementação
 * das interfaces DAO e deixar exposto somente a interface
 */
