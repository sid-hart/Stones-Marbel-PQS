package com.sid_hart.stones_marbelpqs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class KotaData extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DBHandler dbHandlerData;
    private DisplayMetrics metrics;
    private EditText editDp;
    private EditText editEp;
    private EditText editHr;
    private EditText editFr;
    private EditText editWidth;
    private EditText editLength;
    private EditText editLengthPrice;
    private KotaMeasuresPrice getAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kota_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lengthList();

        fillExtras();
    }

    private TextView createNewTextViewInput(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        //textView.setId(i);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setGravity(Gravity.CENTER);
        textView.setWidth(metrics.widthPixels / 2);
        textView.setMinimumWidth(metrics.widthPixels / 2);
        return textView;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kota_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addUpdateKota(View view) {
        editLength = (EditText) findViewById(R.id.editLength);
        editLengthPrice = (EditText) findViewById(R.id.editPrice);
        if (editLength.getText() != null && editLengthPrice.getText() != null)
            dbHandlerData.changePrice(Integer.valueOf(editLength.getText().toString()), Integer.parseInt(editLengthPrice.getText().toString()));
        lengthList();
    }

    public void updateExtras(View view) {
        ArrayList<Integer> extraPrices = new ArrayList<Integer>();
        extraPrices.add(Integer.valueOf(editWidth.getText().toString()));
        extraPrices.add(Integer.valueOf(editDp.getText().toString()));
        extraPrices.add(Integer.valueOf(editEp.getText().toString()));
        extraPrices.add(Integer.valueOf(editHr.getText().toString()));
        extraPrices.add(Integer.valueOf(editFr.getText().toString()));
        dbHandlerData.updateExtraCost(extraPrices);
        fillExtras();
    }

    public void fillExtras() {
        editWidth = new EditText(this);
        editWidth = (EditText) findViewById(R.id.editWidthPrice);
        editWidth.setText(String.valueOf(getAll.get_kotaExtraPrice("Width")));

        editDp = new EditText(this);
        editDp = (EditText) findViewById(R.id.editDPPrice);
        editDp.setText(String.valueOf(getAll.get_kotaExtraPrice("DP")));

        editEp = new EditText(this);
        editEp = (EditText) findViewById(R.id.editEPPrice);
        editEp.setText(String.valueOf(getAll.get_kotaExtraPrice("EdgePolish")));

        editHr = new EditText(this);
        editHr = (EditText) findViewById(R.id.editHRPrice);
        editHr.setText(String.valueOf(getAll.get_kotaExtraPrice("HalfRound")));

        editFr = new EditText(this);
        editFr = (EditText) findViewById(R.id.editFRPrice);
        editFr.setText(String.valueOf(getAll.get_kotaExtraPrice("FullRound")));
    }

    public void lengthList() {
        ArrayList<Integer> length = new ArrayList<Integer>();
        ArrayList<Integer> price = new ArrayList<Integer>();
        getAll = new KotaMeasuresPrice(this);
        GridLayout gridList = new GridLayout(this);
        TextView txtLength = new TextView(this);
        TextView txtPrice = new TextView(this);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        dbHandlerData = new DBHandler(this, null, null, 2);

        gridList = (GridLayout) findViewById(R.id.grid_kota_list);
        gridList.removeAllViews();

        length = getAll.get_kotameasures();
        price = getAll.get_kotaprice();

        Collections.sort(length);
        Collections.sort(price);

        for (int i = 0; i < length.size(); i++) {
            gridList.addView(createNewTextViewInput(String.valueOf(length.get(i)) + "*23"));
            gridList.addView(createNewTextViewInput(String.valueOf(price.get(i))));
        }
    }
}
