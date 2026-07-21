package test;

import dao.ReportDao;
import java.util.ArrayList;

public class ReportDaoTest {

    public static void main(String[] args) {

        ReportDao dao = new ReportDao();

        System.out.println("===== INVENTORY REPORT =====");

        ArrayList<String[]> inventory = dao.getInventoryReport();

        for (String[] row : inventory) {

            System.out.println(
                    row[0] + " | " +
                    row[1] + " | " +
                    row[2] + " | " +
                    row[3] + " | " +
                    row[4]
            );
        }


        System.out.println("\n===== LOW STOCK REPORT =====");

        ArrayList<String[]> lowStock = dao.getLowStockReport();

        for (String[] row : lowStock) {

            System.out.println(
                    row[0] + " | " +
                    row[1] + " | " +
                    row[2] + " | " +
                    row[3]
            );
        }


        System.out.println("\n===== ISSUANCE HISTORY =====");

        ArrayList<String[]> history = dao.getIssuanceHistory();

        for (String[] row : history) {

            System.out.println(
                    row[0] + " | " +
                    row[1] + " | " +
                    row[2] + " | " +
                    row[3] + " | " +
                    row[4]
            );
        }


        System.out.println("\n===== MATERIAL USAGE =====");

        ArrayList<String[]> usage = dao.getMaterialUsageReport();

        for (String[] row : usage) {

            System.out.println(
                    row[0] + " | " +
                    row[1]
            );
        }

    }

}