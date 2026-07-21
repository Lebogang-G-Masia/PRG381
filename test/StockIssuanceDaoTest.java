package test;

import dao.StockIssuanceDao;

public class StockIssuanceDaoTest {

    public static void main(String[] args) {

        StockIssuanceDao dao = new StockIssuanceDao();


        System.out.println("===== STOCK ISSUANCE TEST =====");


        /*
           Change these IDs to match your database.

           cleanerId = cleaner receiving items
           userId    = staff member issuing items
           materialId = material being issued
           quantity = amount issued
        */


        boolean result = dao.issueMaterial(
                1,  // cleaner_id
                1,  // user_id
                1,  // material_id
                5   // quantity
        );


        if (result) {

            System.out.println(
                    "Stock issuance completed successfully!"
            );

        } else {

            System.out.println(
                    "Stock issuance failed."
            );

        }

    }

}