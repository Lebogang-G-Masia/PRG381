package test;

import dao.MaterialDao;
import model.Material;
import java.util.ArrayList;

public class MaterialDaoTest {

    public static void main(String[] args) {

        MaterialDao dao = new MaterialDao();


        System.out.println("===== ALL MATERIALS =====");

        ArrayList<Material> materials = dao.getAllMaterials();

        for (Material material : materials) {

            System.out.println(
                    material.getMaterialId()
                    + " | "
                    + material.getMaterialName()
                    + " | Quantity: "
                    + material.getQuantity()
            );

        }


        System.out.println("\n===== LOW STOCK MATERIALS =====");

        ArrayList<Material> lowStock =
                dao.getLowStockMaterials();


        for (Material material : lowStock) {

            System.out.println(
                    material.getMaterialName()
                    + " | Quantity: "
                    + material.getQuantity()
                    + " | Reorder Level: "
                    + material.getReorderLevel()
            );

        }

    }

}