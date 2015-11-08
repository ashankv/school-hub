package cf.schoolhub.schoolhub;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ashank on 11/7/2015.
 */
public class Helper {
    public static String myUserToken;
    public static Integer myUserSchool;
    public static String myUserFirstName;
    public static String myUserLastName;
    public static String myUserEmail;
    public static String myUserNameId;

    public static void sendRegPost(String firstName, String lastName, String emailAddress, String userId, String userPass) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String regUrl = "https://api.schoolhub.cf/users";
        URL myUrl = new URL(regUrl);
        HttpsURLConnection con = (HttpsURLConnection) myUrl.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        String urlParameters="first_name=" + firstName + "&last_name=" + lastName + "&email=" + emailAddress + "&username=" + userId + "&password=" + userPass;

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        System.out.println(responseCode);
        if (responseCode == 422){
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String inputLine;
            StringBuffer jsonString = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                jsonString.append(inputLine);
            }
            in.close();

            Log.d("JSONSTRING", "INCOMING");
            System.out.println(jsonString);

            JSONObject reader = new JSONObject(jsonString.toString());
            Iterator<String> keys = reader.keys();
            while(keys.hasNext()) {
                String tempKey = keys.next();
                JSONObject jsonErrors = reader.getJSONObject(tempKey);
                if(jsonErrors.has("username")){
                    String tempError = jsonErrors.getString("username");
                    String[] tempErrorArray = tempError.split("\"");
                    String lastParsedError = tempErrorArray[1];
                    Exception e = new Exception(lastParsedError);
                    throw e;
                }

                else if(jsonErrors.has("email")){
                    String tempError = jsonErrors.getString("email");
                    String[] tempErrorArray = tempError.split("\"");
                    String lastParsedError = tempErrorArray[1];
                    Exception e = new Exception(lastParsedError);
                    throw e;
                }

                else if(jsonErrors.has("first_name")){
                    String tempError = jsonErrors.getString("first_name");
                    String[] tempErrorArray = tempError.split("\"");
                    String lastParsedError = tempErrorArray[1];
                    Exception e = new Exception(lastParsedError);
                    throw e;
                }

                else if(jsonErrors.has("last_name")){
                    String tempError = jsonErrors.getString("last_name");
                    String[] tempErrorArray = tempError.split("\"");
                    String lastParsedError = tempErrorArray[1];
                    Exception e = new Exception(lastParsedError);
                    throw e;
                }

                else if(jsonErrors.has("password")){
                    String tempError = jsonErrors.getString("password");
                    String[] tempErrorArray = tempError.split("\"");
                    String lastParsedError = tempErrorArray[1];
                    Exception e = new Exception(lastParsedError);
                    throw e;
                }


            }

        }

        System.out.println(responseCode);

    }

    public static void sendLoginPost(String myOwnUsername, String myOwnPassword) throws Exception{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String regUrl = "https://api.schoolhub.cf/token";
        URL myUrl = new URL(regUrl);
        HttpsURLConnection con = (HttpsURLConnection) myUrl.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        String urlParameters="grant_type=password&username=" + myOwnUsername + "&password=" +myOwnPassword;

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        Log.d("RESPONSE CODE", "INCOMING");
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        Log.d("RESPONSE", "RESPONSE");
        System.out.println(response.toString());

        JSONObject reader = new JSONObject(response.toString());
        JSONObject authenticatedStatus = reader.getJSONObject("authenticated");
        String myToken = authenticatedStatus.getString("access_token");
        String mySplittedTokenArray[] = myToken.split("\"");
        Helper.myUserToken = mySplittedTokenArray[0];
        System.out.println(Helper.myUserToken);



        if(responseCode == 200){
            String myTokenUrl = "https://api.schoolhub.cf/users/me";
            URL url = new URL(myTokenUrl);
            HttpsURLConnection myTokenCon = (HttpsURLConnection) url.openConnection();


            myTokenCon.setRequestMethod("GET");
            myTokenCon.setRequestProperty("Authorization", "Bearer " + Helper.myUserToken);

         //   int myTokenResponseCode = myTokenCon.getResponseCode();
         //   System.out.println(myTokenResponseCode);

            BufferedReader myIn = new BufferedReader(
                    new InputStreamReader(myTokenCon.getInputStream()));
            String myTokenInputLine;
            StringBuffer myTokenResponse = new StringBuffer();

            while ((myTokenInputLine = myIn.readLine()) != null) {
                myTokenResponse.append(myTokenInputLine);
            }
            myIn.close();

            //print result
            System.out.println(myTokenResponse.toString());

            JSONObject myFirstJsonObject = new JSONObject(myTokenResponse.toString());
            JSONObject usersJsonObject = myFirstJsonObject.getJSONObject("users");
            System.out.println(usersJsonObject.getString("first_name"));

            Helper.myUserFirstName = usersJsonObject.getString("first_name");
            Helper.myUserLastName = usersJsonObject.getString("last_name");
            Helper.myUserNameId = usersJsonObject.getString("username");
            Helper.myUserSchool = usersJsonObject.getInt("id");

            System.out.println(Helper.myUserFirstName);

        }
    }
}
