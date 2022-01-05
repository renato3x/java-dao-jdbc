package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
  void insert(Seller seller);
  void update(Seller seller);
  void deleteById(Integer id);
  Seller findById(Integer id);
  List<Seller> findAll();
}

/**
 * as interfaces DAO possuem os métodos que suas implementações
 * devem possuir
 */
