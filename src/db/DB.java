package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
  private static Connection connection;

  public static Connection getConnection() {
    if (connection == null) {
      try {
        Properties props = loadProperties();
        String dburl = props.getProperty("dburl"); // retorna uma propriedade a parti de seu nome
        connection = DriverManager.getConnection(dburl, props);
        /**
         * estabelece a conexão com o banco informando a url e também as demais propriedades
         * (usuário e senha)
         */
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }

    return connection;
  }

  private static Properties loadProperties() {
    try (FileInputStream fis = new FileInputStream("db.properties")) { // lê o arquivo com as propriedades do banco
      Properties props = new Properties(); // objeto que armazena as propriedades
      props.load(fis); // carrega as propriedades do arquivo e armazena-as

      return props;
    } catch (IOException e) {
      throw new DbException(e.getMessage());
    }
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close(); // finaliza a conexão com o banco de dados
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }

  public static void closeStatement(java.sql.Statement st) {
    if (st != null) {
      try {
        st.close();
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }

  public static void closeResultSet(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }
}
