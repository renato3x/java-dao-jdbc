package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
  public static SellerDao createSellerDao() {
    return new SellerDaoJDBC();
  }
}

/**
 * A Factory tem como objetivo proteger a implementação
 * das interfaces DAO e deixar exposto somente a interface
 */
