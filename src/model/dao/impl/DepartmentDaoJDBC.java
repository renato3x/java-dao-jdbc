package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
  Connection connection = null;

  public DepartmentDaoJDBC(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void insert(Department dep) {
    String sqlQuery = "INSERT INTO department (Name) VALUES (?)";

    try {
      PreparedStatement query = this.connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
      query.setString(1, dep.getName());

      int rowAffected = query.executeUpdate();

      if (rowAffected > 0) {
        ResultSet result = query.getGeneratedKeys();

        if (result.next()) {
          dep.setId(result.getInt(1));
        }
      } else {
        throw new DbException("Unexpected error! Seller not created");
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public void update(Department dep) {
    String sqlQuery = "UPDATE department SET Name = ? WHERE Id = ?";

    try {
      PreparedStatement query = this.connection.prepareStatement(sqlQuery);

      query.setString(1, dep.getName());
      query.setInt(2, dep.getId());

      query.executeUpdate();
    } catch (Exception e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public void deleteById(Integer id) {
    String sqlQuery = "DELETE FROM department WHERE Id = ?";

    try {
      PreparedStatement query = this.connection.prepareStatement(sqlQuery);
      query.setInt(1, id);
      query.executeUpdate();
    } catch (Exception e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public Department findById(Integer id) {
    String sqlQuery = "SELECT * FROM department WHERE Id = ?";

    try {
      PreparedStatement query = this.connection.prepareStatement(sqlQuery);
      query.setInt(1, id);

      ResultSet departmentData = query.executeQuery();

      if (departmentData.next()) {
        return this.instantiateDepartment(departmentData);
      }

      return null;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  @Override
  public List<Department> findAll() {
    String sqlQuery = "SELECT * FROM department";

    try {
      Statement query = this.connection.createStatement();
      
      ResultSet departmentsData = query.executeQuery(sqlQuery);

      List<Department> departments = new ArrayList<>();

      while (departmentsData.next()) {
        departments.add(this.instantiateDepartment(departmentsData));
      }

      return departments.isEmpty() ? null : departments;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  private Department instantiateDepartment(ResultSet data) throws SQLException {
    Department dep = new Department();

    dep.setId(data.getInt("Id"));
    dep.setName(data.getString("Name"));

    return dep;
  }
}
