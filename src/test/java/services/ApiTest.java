package services;

import body.PartialUpdateBookingBody;
import body.PostBookingBody;
import body.PostTokenBody;
import body.UpdateBookingBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest {

    static String baseURI="https://restful-booker.herokuapp.com";
    static String tokenId;
    static String bookingId;

    @Test
    public void getAllBookings () {
        String response=given()
                .header("Content-Type","application/json")
                .baseUri(baseURI)
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
                .baseUri(baseURI)
                .log()
                .all()
                .body(PostBookingBody.infoBody())
                .when()
                .post("/booking");

        bookingId = response.jsonPath().getString("bookingId");
        System.out.println(bookingId);
    }

    @Test
    public static void createToken () {
        Response response = given()
                .header("Content-Type", "application/json")
                .baseUri(baseURI)
                .log()
                .all()
                .body(PostTokenBody.tokenBody())
                .when()
                .post("/auth");

        tokenId = response.jsonPath().getString("token");
    }

    @Test
    public static void updateBooking () {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie","token="+tokenId+"")
                .baseUri(baseURI)
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
                .baseUri(baseURI)
                .log()
                .all()
                .body(PartialUpdateBookingBody.partiallyUpdatingBody())
                .when()
                .put("/booking/"+bookingId);
    }

    @Test
    public static void deleteBooking () {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie","token="+tokenId+"")
                .baseUri(baseURI)
                .log()
                .all()
                .body(PostTokenBody.tokenBody())
                .when()
                .delete("/booking/"+bookingId);
    }

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
}