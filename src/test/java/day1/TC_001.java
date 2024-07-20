package day1;

import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class TC_001 {


    String baseUrl = "https://thinking-tester-contact-list.herokuapp.com";
    String login = "/users/login";
    String addContact = "/contacts";
    String getContact = "/contacts";
    String token = null;


    /**
     * Login and add new contact and verify
     */

    @Test
    public void TC001(){

        RestAssured.baseURI = baseUrl;

        JSONObject json = new JSONObject();
        json.put("email","tom@testmail.com");
        json.put("password","Password@123");

       Response response =  given().contentType(ContentType.JSON).body(json).log().all().post(login);

       response.prettyPrint();
       System.out.println(response.statusCode());
        token = response.then().extract().path("token");
       System.err.println("token: "+ token);




    }

    @Test
    public void TC002(){
        RestAssured.baseURI = baseUrl;


        Response response =  given().header("Authorization","Bearer "+token).contentType(ContentType.JSON).body("" +
                "{\n" +
                "    \"firstName\": \"Virat\",\n" +
                "    \"lastName\": \"Koili\",\n" +
                "    \"birthdate\": \"1970-01-01\",\n" +
                "    \"email\": \"jdoe1@fake.com\",\n" +
                "    \"phone\": \"8005555555\",\n" +
                "    \"street1\": \"1 Main St.\",\n" +
                "    \"street2\": \"Apartment A\",\n" +
                "    \"city\": \"Anytown\",\n" +
                "    \"stateProvince\": \"KS\",\n" +
                "    \"postalCode\": \"12345\",\n" +
                "    \"country\": \"USA\"\n" +
                "}").log().all().post(addContact);

        response.prettyPrint();
        System.out.println(response.statusCode());


    }

    @Test
    public void TC003(){
        RestAssured.baseURI = baseUrl;


        Response response =  given().header("Authorization","Bearer "+token).log().all().get(getContact);

        List<String> fn  = response.then().extract().path("firstName");

        response.prettyPrint();
        System.out.println(response.statusCode());

        System.err.println(fn);


    }
}
