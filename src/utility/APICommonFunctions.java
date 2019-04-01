package utility;

import java.io.PrintWriter;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.google.gson.JsonSyntaxException;
import io.restassured.response.Response;


public class APICommonFunctions {
	
	 public static boolean isEmailValid(String Body) throws ParseException {
		 String email=elementExtractor(Body,"Email");
	     String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
	                            "[a-zA-Z0-9_+&*-]+)*@" +
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
	                            "A-Z]{2,7}$";
	                             
	        Pattern pat = Pattern.compile(emailRegex);
	        if (email == null)
	            return false;
	        return pat.matcher(email).matches();
	    }
	 
	 public static JSONObject jsonSchemaValidator(String Body) throws ParseException {
		 try{
			 	Object obj1 = new JSONParser().parse(Body);
			 	JSONObject jo1 = (JSONObject) obj1;
		  		return jo1;
		  		} 
		  		catch(JsonSyntaxException jse){
		  		System.out.println("Not a valid Json String:"+jse.getMessage());
		  		return null;
		  		}
	 }
	 
	 public static void responseValidator(Response actualResponse,String expectedResponse) {
//	 	try {
	 		
	 		String actResponse=actualResponse.getBody().asString().trim();
	 		String replacedResponse=expectedResponse.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "").trim();
	 		Log.addMessage("EXPECTED RESPONSE IS : "+replacedResponse);
	 		Log.addMessage("ACTUAL RESPONSE IS   :"+actResponse);
	 		Assert.assertEquals(actResponse, replacedResponse,"Actual and the expected responses do not match");
//	    	}
//	 	catch(Exception e) {
//	    		Log.addMessage("Response received is: "+actualResponse.getBody().asString());
//	    		errorResponseValidator(actualResponse.getBody().asString());
//	    	}
	 }
	 
	 public static String elementExtractor(String body, String value) throws ParseException {
		 try {
			 JSONObject jo=jsonSchemaValidator(body);
			 jo.toJSONString();
			 String element= String.valueOf(jo.get(value));
			 return element;
		 }
		 catch(Exception e) {
			 Log.addMessage("Field not found");
			 Assert.assertTrue(false, "");
			 return null;
		 }
	 }
	 
	 
	 public static void checkElementLength(String body, String value,int minSize,int maxSize) {
		try {
			String element=elementExtractor(body,value);
			Assert.assertTrue(element.length()>minSize&&element.length()<maxSize, "element is within limits");	
			}
		catch(Exception e) {
			 Log.addMessage("Element is not within length constraints");
			 Assert.assertTrue(false, "Character limit exceeded or below minimum");
			 }
		 }
	 
	 public static void caseCheck(String body) { 
		 try {
			 char[] charArray = body.toCharArray();
		      for(char letter:charArray){
		            Assert.assertTrue( !Character.isLowerCase(letter ),"All keys are not lowercase");
		        }
		 	 }
		  catch(Exception e) {
			  Log.addMessage("All the keys in payload are not lowercase");
		    	  Assert.assertTrue(false, "");
		      }  
		 }
	 
	 public static void mandatoryFieldCheck(String body, String field[]) {
		 
	 }
	 
	 public static void validateStatusCode(int actualStatus, int expectedStatus) {
		 try {
			 Assert.assertEquals(actualStatus, expectedStatus,"Status code is verified");
		 }
		 catch(Exception e) {
			 if (actualStatus != 200) {
				if(String.valueOf(actualStatus).contains("4")) {
					Log.addMessage("OOPs...User errors!!!");
				}else if(String.valueOf(actualStatus).contains("5")){
						Log.addMessage("Server errors!!!");
				 }else {
						Log.addMessage("OOPs...Got some other errors!!!");
				  }
			}
	      }
	   }
	 
	 public static void errorResponseValidator(String resp) {
		 
		 if (resp == "{\"error\":{\"code\":\"BAD_ARGUMENT\",\"target\":\"VerifyCode\"}}") {
			 Log.addMessage("Field validation error. Please try again");
			 Assert.assertTrue(false);
		 }else if (resp == "{\"error\":{\"code\":\"FAILED\",\"target\":\"VerifyCode\"}}") {
			 Log.addMessage("Request failed. Please try again");
			 Assert.assertTrue(false);
		 }else if (resp =="{\"error\":{\"code\":\"CODE_NOT_FOUND\",\"target\":\"VerifyCode\"}}") {
			 Log.addMessage("Code not found. Please try again");
			 Assert.assertTrue(false);
		 }else if(resp == "{\"error\":{\"code\":\"CODE_EXPIRED\",\"target\":\"VerifyCode\"}}") {
			 Log.addMessage("Code expired. Please generate another code");
			 Assert.assertTrue(false);
		 }else if (resp == "{\"error\":{\"code\":\"CODE_OLDER\",\"target\":\"VerifyCode\"}}") {
			 Log.addMessage("Code is older than 24 hours. Please generate another code");
			 Assert.assertTrue(false);
		 }	 
	 }
}


