package test;

import dao.CleanerDao;
import model.Cleaner;
import java.util.ArrayList;

public class CleanerDaoTest {

    public static void main(String[] args) {

        CleanerDao dao = new CleanerDao();


        System.out.println("===== ALL CLEANERS =====");


        ArrayList<Cleaner> cleaners =
                dao.getAllCleaners();


        for (Cleaner cleaner : cleaners) {

            System.out.println(
                    cleaner.getCleanerId()
                    + " | "
                    + cleaner.getFirstName()
                    + " "
                    + cleaner.getLastName()
                    + " | "
                    + cleaner.getPhone()
                    + " | "
                    + cleaner.getEmail()
            );

        }

    }

}