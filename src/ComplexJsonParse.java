import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		
		//First set Base URI
				//RestAssured.baseURI="https://rahulshettyacademy.com";
		//Add place --> Update Place with New address -> Get Place to validate if New address is present in response
							
		//given - all input details
		//when - submit the API  - resource and http method
		//then - validate the response
		/*
		 * String response = given().log().all().queryParam("key",
		 * "qaclick123").header("content-type", "application/json")
		 * .body(payload.AddPlace()) .when().post("maps/api/place/add/json")
		 * .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		 * .header("server",
		 * equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();
		 * 
		 * System.out.println(response);
		 * 
		 * JsonPath js = new JsonPath(response); //For parsing JSON
		 */		
		// Print No of courses returned by API
		JsonPath js = new JsonPath(payload.coursePayload());
		System.out.println(js.getInt("courses.size()"));

		//Print Purchase Amount		
		System.out.println(js.getInt("dashboard.purchaseAmount"));		
		
		//Print Title of the first course
		System.out.println(js.get("courses[0].title").toString());
		
		//Print All course titles and their respective Prices
		for(int i=0; i<js.getInt("courses.size()");i++)
		{
			System.out.println(js.get("courses["+i+"].title").toString());
			System.out.println(js.getInt("courses["+i+"].price"));
		}
			
		
		//Print no of copies sold by RPA Course
		for(int i=0; i<js.getInt("courses.size()");i++)
		{
			System.out.println(js.get("courses["+i+"].title").toString());
			if(js.get("courses["+i+"].title").equals("RPA"))
			{
				System.out.println(js.getInt("courses["+i+"].copies"));
				break;
			}
		}
			
	}

}
