package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

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

    private static void sendRequest(RegistrationInfo user) {
        given() // "дано"
            .spec(requestSpec) // указываем, какую спецификацию используем
            .body(new RegistrationDto("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
            .when() // "когда"
            .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
            .then() // "тогда ожидаем"
            .statusCode(200); // код 200 OK
        // TODO: отправить запрос на указанный в требованиях path, передав в body запроса объект user и
        //  передать подготовленную спецификацию requestSpec.
    }

    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
        // TODO: логика для объявления переменной login и заданные её значения, для генерации
        //  случайного логина используется faker
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
        // TODO: логика для объявления переменной password и заданные её значения, для генерации
        //  случайного пароля используется faker
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationInfo getUser(String status) {
            RegistrationInfo user = new RegistrationInfo(getRandomLogin(), getRandomPassword(), status);
            return user;
            // TODO: создать пользователя user используя методы getRandomLogin(), getRandomPassword() и параметр status
        }

        public static RegistrationInfo getRegisteredUser(String status) {
            RegistrationInfo registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
            // TODO: объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
            //  Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
        }
    }

    @Value
    public static class RegistrationInfo {
        String login;
        String password;
        String status;
    }
}
