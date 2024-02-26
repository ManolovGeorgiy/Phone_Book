package e2e.utils;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Object;

public class DataProviders {

    Faker faker = new Faker();
    //[{"Georgiy","Manolov","QA Pro curs"},{"Georgiy","Manolov","QA Pro curs"},{"Georgiy","Manolov","QA Pro curs"}
    @DataProvider
    public Iterator<Object[]> newContact(){

        List<Object[]> list = new ArrayList<>();

        list.add(new Object[]{faker.name().firstName(),faker.name().lastName(),faker.lorem().paragraph()});
        list.add(new Object[]{faker.internet().uuid(),faker.internet().uuid(),faker.lorem().sentence()});
        list.add(new Object[]{faker.internet().uuid(),"Manolov","QA pro course"});
        return list.iterator();
    }
    @DataProvider
    public Iterator<Object[]> userCanNotLoginTest(){

        List<Object[]> list = new ArrayList<>();

        list.add(new Object[]{"newtestgmail.com", "newtest@gmail.com","invalid_email"});
        list.add(new Object[]{"newtest@gmail.com", "newtestgmail.com","invalid_password"});
        list.add(new Object[]{"12333","437373", "with_invalid_data"});
        return list.iterator();
    }
}
