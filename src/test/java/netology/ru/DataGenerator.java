package netology.ru;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import lombok.val;
import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    static void sendRequest(RegistrationUserData user) {
                given()
                .spec(requestSpec)
                        .body(user)
                        .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        String login=faker.name().firstName();
        return login;
    }

    public static String getRandomPassword() {
        String password=faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationUserData getUser (String status) {
            RegistrationUserData user = new RegistrationUserData(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static RegistrationUserData getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }
    @Value
    public static class RegistrationUserData {
        String login;
        String password;
        String status;
    }
}