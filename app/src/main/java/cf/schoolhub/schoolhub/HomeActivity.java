package cf.schoolhub.schoolhub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.apache.http.client.HttpClient;

/**
 * Created by Ashank on 11/7/2015.
 */
public class HomeActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView myUserNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        myUserNameTextView = (TextView) findViewById(R.id.myUserNameTextView);
        myUserNameTextView.setText(Helper.myUserFirstName + " " + Helper.myUserLastName);

        mDrawer = (NavigationView) findViewById(R.id.main_drawer);
    //    mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setScrimColor(Color.parseColor("#33000000"));
        mDrawerToggle.syncState();

    }
}
