package com.clouway.secondtask.core;


import com.clouway.secondtask.dataaccesslayer.PeopleTripJdbcImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by clouway on 16-2-23.
 */
public class Demo {
    public static void main(String[] args) throws ParseException {

        DateFormat date = new SimpleDateFormat("yyyyy-MM-dd");

        PeopleTripJdbcImpl peopleTripJdbc = new PeopleTripJdbcImpl("jdbc:mysql://localhost/secondtask", "root", "clouway.com");
        peopleTripJdbc.connectToDatabase();

        peopleTripJdbc.create(new Person("Ivan Garelov", "8104221234", 37, "ivangarelov@abv.bg"));
        peopleTripJdbc.update(new Person("Ivan Angelov", "8104221234", 38, "ivanangelov@abv.bg"));
        peopleTripJdbc.create(new Trip("8104221234", date.parse("2016-04-12"), date.parse("2016-04-22"), "Ahtopol"));
        peopleTripJdbc.update(new Trip("8104221234", date.parse("2016-04-13"), date.parse("2016-04-23"), "Chernomorets"));
        System.out.println(peopleTripJdbc.getMostVisitedCities());
        System.out.println(peopleTripJdbc.getAllPeopleNameWhichStartWithSameCharacters("Ivan"));
        System.out.println(peopleTripJdbc.getPeople());
        System.out.println(peopleTripJdbc.getTrips());

        peopleTripJdbc.create(new Person("Ivan Tonev", "8104221212", 37, "ivantonev@abv.bg"));
        peopleTripJdbc.create(new Person("Ivan Ivanov", "6612112354", 37, "ivanivanov@abv.bg"));
        peopleTripJdbc.create(new Person("Ivan Georgiev", "7803251576", 37, "ivangeorgiev@abv.bg"));
        peopleTripJdbc.create(new Person("Ivan Petkov", "8511122302", 37, "ivanpetkov@abv.bg"));



        peopleTripJdbc.create(new Trip("6612112354", date.parse("2016-04-12"), date.parse("2016-04-22"), "Ahtopol"));
        peopleTripJdbc.create(new Trip("7803251576", date.parse("2016-04-12"), date.parse("2016-04-22"), "Ahtopol"));
        peopleTripJdbc.create(new Trip("8104221212", date.parse("2016-04-12"), date.parse("2016-04-22"), "Ahtopol"));
        peopleTripJdbc.create(new Trip("8511122302", date.parse("2016-04-12"), date.parse("2016-04-22"), "Ahtopol"));

        peopleTripJdbc.update(new Trip("6612112354", date.parse("2016-03-01"), date.parse("2016-03-05"), "Bansko"));
        peopleTripJdbc.update(new Trip("7803251576", date.parse("2016-03-10"), date.parse("2016-03-20"), "Bansko"));
        peopleTripJdbc.update(new Trip("8104221212", date.parse("2016-03-15"), date.parse("2016-03-25"), "Bansko"));
        peopleTripJdbc.update(new Trip("8511122302", date.parse("2016-03-30"), date.parse("2016-04-05"), "Bansko"));

        System.out.println(peopleTripJdbc.findAllPeopleInTheSameCityAtTheSameTime(date.parse("2016-03-10"),date.parse("2016-03-25")));


        peopleTripJdbc.closeConnection();
    }
}
