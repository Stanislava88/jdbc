package com.clouway.thirdtask;

/**
 * Created by clouway on 16-2-25.
 */
public class Demo {
    public static void main(String[] args) {
        BackupData backupData = new BackupData("jdbc:mysql://localhost/thirdtask", "root", "clouway.com");

        backupData.connectToDatabase();

        backupData.update(new Customer(5, "Georgi Georgiev", "+359883883883", "ggeorgiev@abv.bg"));
        backupData.insert();

        backupData.closeConnection();
    }

}
