package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
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
    String query = 
    "INSERT INTO seller " +
    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
    "VALUES (?, ?, ?, ?, ?)";

    PreparedStatement preparedStatement = null;

    try {
      preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, seller.getName());
      preparedStatement.setString(2, seller.getEmail());
      preparedStatement.setDate(3, new Date(seller.getBirthDate().getTime()));
      preparedStatement.setDouble(4, seller.getBaseSalary());
      preparedStatement.setInt(5, seller.getDepartment().getId());

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        ResultSet data = preparedStatement.getGeneratedKeys();

        if (data.next()) {
          int id = data.getInt(1);
          seller.setId(id);
          DB.closeResultSet(data);
        }
      } else {
        throw new DbException("Unexpected error! Seller not created");
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preparedStatement);
    }
  }

  @Override
  public void update(Seller seller) {
    String sqlQuery =
    "UPDATE seller " +
    "SET Name = ?, " + 
    "Email = ?, " + 
    "BirthDate = ?, " + 
    "BaseSalary = ?, " + 
    "DepartmentId = ? " +
    "WHERE Id = ? ";

    PreparedStatement query = null;

    try {
      query = this.connection.prepareStatement(sqlQuery);

      query.setString(1, seller.getName());
      query.setString(2, seller.getEmail());
      query.setDate(3, new Date(seller.getBirthDate().getTime()));
      query.setDouble(4, seller.getBaseSalary());
      query.setInt(5, seller.getDepartment().getId());
      query.setInt(6, seller.getId());

      query.executeUpdate();
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public void deleteById(Integer id) {
    String sqlQuery = "DELETE FROM seller WHERE id = ?";

    try {
      PreparedStatement query = this.connection.prepareStatement(sqlQuery);
      query.setInt(1, id);
      query.executeUpdate();
    } catch (Exception e) {
      throw new DbException(e.getMessage());
    }
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
          departmentsMap.put(department.getId(), department);
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
    String query = 
    "SELECT seller.*,department.Name as DepName " +
    "FROM seller INNER JOIN department " +
    "ON seller.DepartmentId = department.Id " +
    "ORDER BY Name";

    Statement statement = null; 
    ResultSet data = null;

    try {
      statement = this.connection.createStatement();

      data = statement.executeQuery(query);

      List<Seller> sellers = new ArrayList<>();
      Map<Integer, Department> departmentsMap = new HashMap<>();

      while (data.next()) {
        Department department = departmentsMap.get(data.getInt("DepartmentId"));

        if (department == null) {
          department = this.instantiateDepartment(data);
          departmentsMap.put(department.getId(), department);
        }

        sellers.add(this.instantiateSeller(data, department));
      }

      return sellers.isEmpty() ? null : sellers;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
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
    seller.setBirthDate(data.getDate("BirthDate"));
    seller.setDepartment(dep);

    return seller;
  }
}

/**
 * implementação das interfaces do DAO
 */
