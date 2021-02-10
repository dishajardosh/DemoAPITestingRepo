import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;
import files.reUsableMethods;

public class basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Validate if AddPlace API is working as expected	
		
		
		//First set Base URI
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Add place --> Update Place with New address -> Get Place to validate if New address is present in response
		
		
		//given - all input details
		//when - submit the API  - resource and http method
		//then - validate the response
		String response = given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();
	
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);  //For parsing JSON
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		//Update Place
		String newAddress = "Summer walk, SA";
		
		given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		/*
		 * // Get Place
		 * 
		 * given().log().all().queryParam("key", "qaclick123") .queryParam("place_id",
		 * ""+placeId+"") .when().get("maps/api/place/get/json")
		 * .then().assertThat().log().all().statusCode(200).body("address",
		 * equalTo(newAddress));
		 */
		
		// get place using JsonPath
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", ""+placeId+"")
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().asString();
		
		//JsonPath js1 = new JsonPath(getPlaceResponse);
		
		JsonPath js1 =reUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		//TestNG Assertions
		Assert.assertEquals(actualAddress, newAddress);
	}

}
