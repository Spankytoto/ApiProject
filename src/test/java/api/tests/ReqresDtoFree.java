package api.tests;

import api.core.BaseUrl;
import api.core.Specifications;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class ReqresDtoFree implements BaseUrl {

    @Test
    public void checkAvatars() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());
        Response response = given()
                            .when()
                            .get(LIST_USERS)
                            .then().log().all()
                            .body("page", equalTo(2))
                            .body("data.id", notNullValue())
                            .body("data.email", notNullValue())
                            .body("data.first_name", notNullValue())
                            .body("data.first_name[1]", equalTo("Lindsay"))
                            .body("data.last_name", notNullValue())
                            .body("data.avatar", notNullValue())
                            .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");
        List<String> avatars = jsonPath.get("data.avatar");

        for(int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }

        Assert.assertTrue(emails.stream().allMatch(x->x.endsWith("@reqres.in")));
    }

    @Test
    public void successRegistration() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");

        Response response = given()
                            .body(user)
                            .when()
                            .post(REGISTER)
                            .then().log().all()
                            .body("id", equalTo(4))
                            .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                            .extract().response();

        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");

        Assert.assertEquals(id, 4);
        Assert.assertEquals(token, "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void unSuccessRegistration() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecError());
        Map<String,String> user = new HashMap<>();
        user.put("email", "sydney@fife");

        Response response = given()
                                .body(user)
                                .when()
                                .post(REGISTER)
                                .then().log().all()
                                .body("error", equalTo("Missing password"))
                                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String error = jsonPath.get("error");
        String expectedError = "Missing password";

        Assert.assertEquals(error, expectedError);
    }
}
