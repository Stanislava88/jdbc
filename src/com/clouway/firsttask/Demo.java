package com.clouway.firsttask;

/**
 * Created by clouway on 16-2-24.
 */
public class Demo {
    public static void main(String[] args) {
        class FakeDisplay implements Display {

            @Override
            public void setFirstName(String firstName) {
                System.out.print("First name:" + firstName);
            }

            @Override
            public void setLastName(String lastName) {
                System.out.print(", Last name:" + lastName);
            }

            @Override
            public void setEgn(String egn) {
                System.out.print(", Egn:" + egn);
            }

            @Override
            public void setAge(int age) {
                System.out.println(", Age:"+age);
            }
        }

        PersonDao personDao = new PersonDao("jdbc:mysql://localhost/firsttask","root","clouway.com",new FakeDisplay());
        personDao.connectToDatabase();
        //

        //
        personDao.closeAllConnections();


    }
}
