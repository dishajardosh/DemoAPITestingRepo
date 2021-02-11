
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	
	@Test
	public void SumOfCourses() {
		int sum = 0;
		JsonPath js = new JsonPath(payload.coursePayload());
		for(int i=0; i<js.getInt("courses.size()");i++)
		{
			int copies = js.getInt("courses["+i+"].copies");
			int price = js.getInt("courses["+i+"].price");
			int amount= copies * price;
			sum = sum + amount;
		}
		System.out.println(sum);
		Assert.assertEquals(sum, js.getInt("dashboard.purchaseAmount"));
	}
}
