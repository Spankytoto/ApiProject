package api.tests;

import api.core.BaseUrl;
import api.core.Specifications;
import api.dto.*;
import api.helpers.DateHelper;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;

import java.util.List;
import java.util.stream.Collectors;

public class Reqres implements BaseUrl {

    @Test
    public void getSingleUser() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        SingleUser user = given()
                            .when()
                            .get(String.format(USER, 2))
                            .then().log().all()
                            .extract().as(SingleUser.class);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getData().getFirstName(), "Janet");
        Assert.assertTrue(user.getData().getEmail().endsWith("@reqres.in"));
        Assert.assertEquals(user.getSupport().getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
        Assert.assertTrue(user.getData().getAvatar().contains(user.getData().getId().toString()));
    }

    @Test
    public void checkAvatarAndId() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        List<User> users = given()
                .when()
                .get(LIST_USERS)
                .then().log().all()
                .extract().body().jsonPath().getList("data", User.class);

        users.stream().forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(User::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void checkRegistration () {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        RegisterData user = new RegisterData();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");
        RegisterData registration = given()
                                        .body(user)
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
        RegisterData user = new RegisterData();
        user.setEmail("sydney@fife");
        user.setPassword("");
        RegisterData registration = given()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .extract().as(RegisterData.class);

        Assert.assertNotNull(registration);
        Assert.assertEquals(registration.getError(), error);
    }

    @Test
    public void checkListResources () {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());
        List<ResourceData> resource = given()
                .when()
                .get(LIST_RESOURCE)
                .then().log().all()
                .extract().body().jsonPath().getList("data", ResourceData.class);

        List<Integer> years = resource.stream().map(ResourceData :: getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);
    }

    @Test
    public void deleteUserTest () {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecUnique(204));
                        given()
                        .when()
                        .delete(String.format(USER, 2))
                        .then().log().all();
    }

    @Test
    public void getUserUpdateTime () {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());
        User user = new User();
        user.setName("morpheus");
        user.setJob("zion resident");
        User userResponse = given()
                .body(user)
                .when()
                .patch(String.format(USER, ""))
                .then().log().all()
                .extract().as(User.class);

        Assert.assertEquals(userResponse.getUpdatedAt().substring(0, 10), new DateHelper().getCurrentDate());
    }

    @Test
    public void getSingleUserNotFound() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecUnique(404));
        given()
                .when()
                .get(String.format(USER, 23))
                .then().log().all();
    }

    @Test
    public void checkResourcesList() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        ResourceCollection resourceCollection = given()
                            .when()
                            .get(LIST_RESOURCE)
                            .then().log().all()
                            .extract().as(ResourceCollection.class);

        Assert.assertNotNull(resourceCollection);
        Assert.assertTrue(resourceCollection.getData().size()  == 6);
        Assert.assertTrue(resourceCollection.getTotal() == 12);
        Assert.assertEquals(resourceCollection.getSupport().getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
        Assert.assertTrue(resourceCollection.getSupport().getUrl().endsWith("support-heading"));
        Assert.assertEquals(resourceCollection.getData().get(5).getColor(), "#53B0AE");
        List<String> names = resourceCollection.getData().stream().map(ResourceData :: getName).collect(Collectors.toList());
        Assert.assertTrue(names.contains("true red"));
        List<Integer> ids = resourceCollection.getData().stream().map(ResourceData :: getId).collect(Collectors.toList());
        List<Integer> sortedIds = ids.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(ids, sortedIds);
    }

    @Test
    public void getSingleResource() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        ResourceSingle resourceSingle = given()
                            .when()
                            .get(String.format(SINGLE_RESOURCE, 2))
                            .then().log().all()
                            .extract().as(ResourceSingle.class);

        Assert.assertEquals(resourceSingle.getData().getName(), "fuchsia rose");
        Assert.assertEquals(resourceSingle.getSupport().getUrl(), "https://reqres.in/#support-heading");
    }

    @Test
    public void singleResourceNotFound() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecUnique(404));

        ResourceSingle resourceSingle = given()
                                        .when()
                                        .get(String.format(SINGLE_RESOURCE, 23))
                                        .then().log().all()
                                        .extract().as(ResourceSingle.class);

        Assert.assertNotNull(resourceSingle);
    }

    @Test
    public void createUser() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecUnique(201));

        User testUser = new User();
        testUser.setName("morpheus");
        testUser.setJob("leader");

        User userData = given()
                                .body(testUser)
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

        RegisterData testUser = new RegisterData();
        testUser.setEmail("eve.holt@reqres.in");
        testUser.setPassword("cityslicka");

        RegisterData userData = given()
                                .body(testUser)
                                .when()
                                .post(LOGIN)
                                .then().log().all()
                                .extract().as(RegisterData.class);

        Assert.assertEquals(userData.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void loginWithWrongData() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecError());

        RegisterData testUser = new RegisterData();
        testUser.setEmail("peter@klaven");

        RegisterData userData = given()
                .body(testUser)
                .when()
                .post(LOGIN)
                .then().log().all()
                .extract().as(RegisterData.class);

        Assert.assertEquals(userData.getError(), "Missing password");
    }

    @Test
    public void getDelayedResponse() {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec());

        User users = given()
                            .when()
                            .get(DELAYED_RESPONSE)
                            .then().log().all()
                            .extract().as(User.class);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.getData().size(), 6);
    }
}
