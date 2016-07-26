/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Product;
import model.Supplier;

/**
 *
 * @author Muhammad
 */
public interface ProductDAOInterface {

    public Product getProductsById(int id);

    public List<Product> getProducts();

    public int addProduct(Product product);

    public int updateProduct(int id, Product product);

    public int deleteProduct(int id);

}
