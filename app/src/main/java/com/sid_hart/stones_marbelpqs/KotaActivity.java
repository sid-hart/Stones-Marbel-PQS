package com.sid_hart.stones_marbelpqs;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class KotaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editLength;
    private EditText editWidth;
    private EditText editQty;
    private CheckBox chkDp;
    private CheckBox chkEp;
    private CheckBox chkFr;
    private CheckBox chkHr;
    private GridLayout gridLayDynaTxt;
    private GridLayout gridLayTotalTxt;
    DisplayMetrics metrics;
    boolean dp = false;
    boolean ep = false;
    boolean hr = false;
    boolean fr = false;
    static int total = 0;
    //private TextView txtTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editLength = (EditText) findViewById(R.id.editLength);
        editWidth = (EditText) findViewById(R.id.editWidth);
        editQty = (EditText) findViewById(R.id.editQty);
        chkDp = (CheckBox) findViewById(R.id.chkDp);
        chkEp = (CheckBox) findViewById(R.id.chkEp);
        chkFr = (CheckBox) findViewById(R.id.chkFr);
        chkHr = (CheckBox) findViewById(R.id.chkHr);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        gridLayDynaTxt = (GridLayout) findViewById(R.id.DynamicParentGrid);
        gridLayTotalTxt = (GridLayout) findViewById(R.id.DynamicTotaltGrid);

        gridLayDynaTxt.addView(createNewTextViewInput("L*W"));
        gridLayDynaTxt.addView(createNewTextViewInput("Extras"));
        gridLayDynaTxt.addView(createNewTextViewInput("Qty"));
        gridLayDynaTxt.addView(createNewTextViewInput("Cost"));

        editLength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    addQuote();
                }
                return false;
            }
        });

        editWidth.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    addQuote();
                }
                return false;
            }
        });

        editQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    addQuote();
                }
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#646f50")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                total();

            }
        });

        DrawerLayout drawer = new DrawerLayout(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = new CollapsingToolbarLayout(this);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_kota);
        collapsingToolbar.setTitle("Kota Stone");
        ImageView header = (ImageView) findViewById(R.id.imageViewkota);

        editQty.setText("1");
    }

    private String getInputs() {
        String inputs = "";
        if (!chkDp.isChecked() && !chkEp.isChecked() && !chkFr.isChecked() && !chkHr.isChecked()) {
            inputs = "-";
        } else {
            if (chkDp.isChecked()) {
                inputs += " -DP- ";
                dp = true;
            }
            if (chkEp.isChecked()) {
                inputs += " -EP- ";
                ep = true;
            }
            if (chkHr.isChecked()) {
                inputs += " -HR- ";
                hr = true;
            }
            if (chkFr.isChecked()) {
                inputs += " -FR- ";
                fr = true;
            }
        }
        return inputs;
    }

    private TextView createNewTextViewInput(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        //textView.setId(i);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        textView.setWidth(metrics.widthPixels / 4);
        textView.setMinimumWidth(metrics.widthPixels / 4);
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
        getMenuInflater().inflate(R.menu.kota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            gridLayTotalTxt.removeAllViews();
            gridLayDynaTxt.removeAllViews();
            total = 0;
            gridLayDynaTxt.addView(createNewTextViewInput("L*W"));
            gridLayDynaTxt.addView(createNewTextViewInput("Extras"));
            gridLayDynaTxt.addView(createNewTextViewInput("Qty"));
            gridLayDynaTxt.addView(createNewTextViewInput("Cost"));
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
            Intent intentKota = new Intent(this, KotaData.class);
            startActivity(intentKota);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addQuote() {
        if (editLength.getText().length() > 0 && editWidth.getText().length() > 0 && editQty.getText().length() > 0) {
            KotaMeasuresPrice calculations = new KotaMeasuresPrice(getBaseContext());
            int length = Integer.parseInt(editLength.getText().toString());
            int width = Integer.parseInt(editWidth.getText().toString());
            int qty = Integer.parseInt(editQty.getText().toString());
            if (calculations.calculatePrice(length, width, qty, false, false, false, false) == 0 || width > length) {
                Toast.makeText(getApplicationContext(),
                        "Please enter valid value", Toast.LENGTH_LONG).show();
            } else {
                String price = String.valueOf(calculations.calculatePrice(length, width, qty, chkDp.isChecked(), chkEp.isChecked(), chkHr.isChecked(), chkFr.isChecked()));
                gridLayDynaTxt.addView(createNewTextViewInput(editLength.getText().toString() + " * " + editWidth.getText().toString()));
                gridLayDynaTxt.addView(createNewTextViewInput(getInputs()));
                gridLayDynaTxt.addView(createNewTextViewInput(editQty.getText().toString()));
                gridLayDynaTxt.addView(createNewTextViewInput("₹" + price));
                gridLayTotalTxt.removeAllViews();
                total += Integer.valueOf(price);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter valid values", Toast.LENGTH_LONG).show();
        }
    }

    public void total() {
        gridLayTotalTxt.removeAllViews();
        gridLayTotalTxt.addView(createNewTextViewInput("Total"));
        gridLayTotalTxt.addView(createNewTextViewInput(""));
        gridLayTotalTxt.addView(createNewTextViewInput(""));
        gridLayTotalTxt.addView(createNewTextViewInput("₹" + String.valueOf(total)));
    }
}
