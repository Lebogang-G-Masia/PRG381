package test;

import dao.SupplierDao;
import model.Supplier;
import java.util.ArrayList;

public class SupplierDaoTest {

    public static void main(String[] args) {

        SupplierDao dao = new SupplierDao();


        System.out.println("===== ALL SUPPLIERS =====");


        ArrayList<Supplier> suppliers =
                dao.getAllSuppliers();


        for (Supplier supplier : suppliers) {

            System.out.println(
                    supplier.getSupplierId()
                    + " | "
                    + supplier.getSupplierName()
                    + " | "
                    + supplier.getContactPerson()
                    + " | "
                    + supplier.getPhone()
            );

        }

    }

}