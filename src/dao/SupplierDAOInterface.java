/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Supplier;

/**
 *
 * @author Muhammad
 */
public interface SupplierDAOInterface {

    public Supplier getSupplierById(int id);
    
    public Supplier getSupplierByName(String supplierName);

    public List<Supplier> getSuppliers();

    public int insertSupplier(Supplier supplier);

    public int updateSupplier(int id, Supplier supplier);

    public int deleteSupplier(int id);
}
