package integration;


import com.github.javafaker.Faker;
import integration.user.UserApi;
import org.testng.annotations.Test;

public class UserApiTest {

    UserApi userApi;
    Faker faker = new Faker();

    @Test
    public void userRegistration(){

        String email = faker.internet().emailAddress();
        String password = faker.internet().password();


        userApi = new UserApi();
        String token = userApi.newUserRegistration(email,password,201);

        userApi.getNewUserActivation(200,token);
    userApi.login(email,password,200);
    }

}
