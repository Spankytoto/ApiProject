package api.tests;

import api.core.BaseUrl;
import api.core.Specifications;
import api.dto.RegisterData;
import api.dto.User;
import api.helpers.DateHelper;
import api.helpers.JsonReader;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class ReqresDataFromFileTest implements BaseUrl {

    @Test
    public void checkRegistration () {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        RegisterData registration = given()
                .body(JsonReader.generateStringFromResource("src/test/resources/userCredentials.json"))
                .when()
                .post(REGISTER)
                .then().log().all()
                .extract().as(RegisterData.class);

        Assert.assertNotNull(registration);
        Assert.assertEquals(registration.getId(), id);
        Assert.assertEquals(registration.getToken(), token);
    }

    @Test
    public void checkNullRegistration () {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecError());

        String error = "Missing password";

        RegisterData registration = given()
                .body(JsonReader.generateStringFromResource("src/test/resources/userMail.json"))
                .when()
                .post(REGISTER)
                .then().log().all()
                .extract().as(RegisterData.class);

        Assert.assertNotNull(registration);
        Assert.assertEquals(registration.getError(), error);
    }

    @Test
    public void createUser() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecUnique(201));

        User userData = given()
                .body(JsonReader.generateStringFromResource("src/test/resources/userCreate.json"))
                .when()
                .post(CREATE)
                .then().log().all()
                .extract().as(User.class);

        Assert.assertNotNull(userData);
        Assert.assertEquals(userData.getName(), "morpheus");
        Assert.assertEquals(userData.getCreatedAt().substring(0, 10), new DateHelper().getCurrentDate());
    }

    @Test
    public void loginWithProperlyData() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        RegisterData userData = given()
                .body(JsonReader.generateStringFromResource("src/test/resources/userCredentials.json"))
                .when()
                .post(LOGIN)
                .then().log().all()
                .extract().as(RegisterData.class);

        Assert.assertEquals(userData.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void loginWithWrongData() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecError());

        RegisterData userData = given()
                .body(JsonReader.generateStringFromResource("src/test/resources/userMail.json"))
                .when()
                .post(LOGIN)
                .then().log().all()
                .extract().as(RegisterData.class);

        Assert.assertEquals(userData.getError(), "Missing password");
    }
}
