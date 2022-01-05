package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

  private Connection connection = null;

  public SellerDaoJDBC(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void insert(Seller seller) {

  }

  @Override
  public void update(Seller seller) {

  }

  @Override
  public void deleteById(Integer id) {

  }

  @Override
  public Seller findById(Integer id) {
    String query = 
    "SELECT seller.*,department.Name as DepName " +
    "FROM seller INNER JOIN department " +
    "ON seller.DepartmentId = department.Id " +
    "WHERE seller.Id = ?";

    PreparedStatement preparedStatement = null; 
    ResultSet sellerData = null;

    try {
      preparedStatement = this.connection.prepareStatement(query);
      preparedStatement.setInt(1, id);

      sellerData = preparedStatement.executeQuery();

      if (sellerData.next()) {
        Department dep = new Department();

        dep.setId(sellerData.getInt("DepartmentId"));
        dep.setName(sellerData.getString("DepName"));

        Seller seller = new Seller();

        seller.setId(sellerData.getInt("Id"));
        seller.setName(sellerData.getString("Name"));
        seller.setEmail(sellerData.getString("Email"));
        seller.setBaseSalary(sellerData.getDouble("BaseSalary"));
        seller.setbirthDate(sellerData.getDate("BirthDate"));
        seller.setDepartment(dep);

        return seller;
      }

      return null;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public List<Seller> findAll() {
    return null;
  }
}

/**
 * implementação das interfaces do DAO
 */
