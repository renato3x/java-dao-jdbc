package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ResultSet data = null;

    try {
      preparedStatement = this.connection.prepareStatement(query);
      preparedStatement.setInt(1, id);

      data = preparedStatement.executeQuery();

      if (data.next()) {
        Department dep = this.instantiateDepartment(data);
        Seller seller = this.instantiateSeller(data, dep);
        
        return seller;
      }

      return null;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public List<Seller> findByDepartment(Department dep) {
    String query = 
    "SELECT seller.*,department.Name as DepName " +
    "FROM seller INNER JOIN department " +
    "ON seller.DepartmentId = department.Id " +
    "WHERE seller.Id = ?";

    PreparedStatement preparedStatement = null; 
    ResultSet data = null;

    try {
      preparedStatement = this.connection.prepareStatement(query);
      preparedStatement.setInt(1, dep.getId());

      data = preparedStatement.executeQuery();

      List<Seller> sellers = new ArrayList<>();
      Map<Integer, Department> departmentsMap = new HashMap<>();

      while (data.next()) {
        Department department = departmentsMap.get(data.getInt("DepartmentId"));

        if (department == null) {
          department = this.instantiateDepartment(data);
          departmentsMap.put(data.getInt("DepartmentId"), department);
        }

        sellers.add(this.instantiateSeller(data, department));
      }

      return sellers.isEmpty() ? null : sellers;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public List<Seller> findAll() {
    return null;
  }

  private Department instantiateDepartment(ResultSet data) throws SQLException {
    Department dep = new Department();

    dep.setId(data.getInt("DepartmentId"));
    dep.setName(data.getString("DepName"));

    return dep;
  }

  private Seller instantiateSeller(ResultSet data, Department dep) throws SQLException {
    Seller seller = new Seller();

    seller.setId(data.getInt("Id"));
    seller.setName(data.getString("Name"));
    seller.setEmail(data.getString("Email"));
    seller.setBaseSalary(data.getDouble("BaseSalary"));
    seller.setbirthDate(data.getDate("BirthDate"));
    seller.setDepartment(dep);

    return seller;
  }
}

/**
 * implementação das interfaces do DAO
 */
