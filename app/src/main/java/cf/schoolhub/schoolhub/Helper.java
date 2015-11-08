package cf.schoolhub.schoolhub;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ashank on 11/7/2015.
 */
public class Helper {

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

    }
}
