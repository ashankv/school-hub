package cf.schoolhub.schoolhub;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ashank on 11/7/2015.
 */
public class SignUp extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button btnSignUpAccount;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userNameIdEditText;
    private EditText passwordEditText;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        userNameIdEditText = (EditText) findViewById(R.id.userNameIdEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        btnSignUpAccount = (Button) findViewById(R.id.btnSignUpAccount);
        btnSignUpAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameText = firstNameEditText.getText().toString();
                String lastNameText = lastNameEditText.getText().toString();
                String userNameText = userNameIdEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();
                String emailText = emailEditText.getText().toString();

                try{
                    Helper.sendRegPost(firstNameText, lastNameText, emailText, userNameText, passwordText);
                } catch(Exception e){
                    e.printStackTrace();
                }

                Intent signUpIntent = new Intent(SignUp.this, HomeActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}
