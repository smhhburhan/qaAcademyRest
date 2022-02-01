package services;

import body.PartialUpdateBookingBody;
import body.PostBookingBody;
import body.PostTokenBody;
import body.UpdateBookingBody;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest<baseURL> {

    static String baseURL="https://restful-booker.herokuapp.com";
    static String tokenId;
    static String bookingId;

    @Test
    public void sample () {
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/");

        System.out.println("getBody:" + response.asString());
        System.out.println("getBody:" + response.getBody().asString());
        System.out.println("getStatusCode:" + response.getStatusCode());
        System.out.println("getContentType:" + response.getHeader("Content-Type"));
        System.out.println("getTime" + response.getTime());

        Assert.assertEquals(response.getStatusCode(),200);
    }

    @BeforeClass
    @Test
    public static void createToken () {
        Response response = given()
                .header("Content-Type", "application/json")
                .baseUri(baseURL)
                .log()
                .all()
                .body(PostTokenBody.tokenBody())
                .when()
                .post("/auth");

        tokenId = response.jsonPath().getString("token");
    }

    @Test
    public void getAllBookings () {
        String response = given()
                .header("Content-Type","application/json")
                .baseUri(baseURL)
                .log()
                .all()
                .when()
                .get("/booking")
                .then()
                .extract().response().asString();

        System.out.println(response);
    }

    @Test
    public static void postBooking () {
        Response response = given()
                .header("Content-Type", "application/json")
                .baseUri(baseURL)
                .log()
                .all()
                .body(PostBookingBody.infoBody())
                .when()
                .post("/booking");

        bookingId = response.jsonPath().getString("bookingId");
        System.out.println(bookingId);
    }

    @Test
    public static void updateBooking () {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie","token="+tokenId+"")
                .baseUri(baseURL)
                .log()
                .all()
                .body(UpdateBookingBody.updatingBody())
                .when()
                .put("/booking/"+bookingId);
    }

    @Test
    public static void partiallyUpdateBooking () {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie","token="+tokenId+"")
                .baseUri(baseURL)
                .log()
                .all()
                .body(PartialUpdateBookingBody.partiallyUpdatingBody())
                .when()
                .put("/booking/"+bookingId);
    }

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider(){
        return new Object[][]{
                {2,200},
                {4,200}
        };
    }

    @Test (dataProvider = "dataProvider")
    public void dataProviderGetBooking(int id,int statusCode){

        String response = given()
                .header("Content-Type","application/json")
                .baseUri(baseURL)
                .log()
                .all()
                .when()
                .get("/booking/" + id)
                .then()
                .statusCode(statusCode)
                .log()
                .all()
                .extract().response().asString();

        System.out.println(response);
    }

    @AfterClass
    @Test
    public static void deleteBooking () {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie","token="+tokenId+"")
                .baseUri(baseURL)
                .log()
                .all()
                .body(PostTokenBody.tokenBody())
                .when()
                .delete("/booking/"+bookingId);
    }

    @Test
    public void report(){
        RequestSpecification restAssuredReq = RestAssured.given()
                .header("Study","Test")
                .log()
                .all(true);
        Response response = restAssuredReq.get(baseURL);
        attachment(restAssuredReq, baseURL, response);
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    public String attachment(RequestSpecification httpRequest, String baseURL, Response response) {
        String html = "Url = " + ApiTest.baseURL + "\n \n" +
                "Request Headers = " + ((RequestSpecificationImpl) httpRequest).getHeaders() + "\n \n" +
                "Request Body = " + ((RequestSpecificationImpl) httpRequest).getBody() + "\n \n" +
                "Response Body = " + response.getBody().asString();

        Allure.addAttachment("Request Detail", html);
        return html;
    }
}