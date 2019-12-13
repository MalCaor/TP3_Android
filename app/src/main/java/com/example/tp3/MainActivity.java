package com.example.tp3;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.Toast;

import android.net.Uri;


public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {
private Fragment leFragment;
private ImageView limage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        leFragment = (Fragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment);
        leFragment.getView().setVisibility(View.GONE);
        limage= (ImageView) findViewById(R.id.imageView);
        Button bOk=(Button) leFragment.getView().findViewById(R.id.bFragOk);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),    "clic sur ok", Toast.LENGTH_SHORT
                ).show();
            }
        });
        Button bCancel=(Button) leFragment.getView().findViewById(R.id.bFragCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leFragment.getView().setVisibility(View.GONE);
                limage.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                Toast.makeText(getApplicationContext(),    "clic sur connect",Toast.LENGTH_LONG).show();

                leFragment.getView().setVisibility(View.VISIBLE);
                limage.setVisibility(View.GONE);
                return true;
            case R.id.menu_deconnect:
                Toast.makeText(getApplicationContext(),    "clic sur deconnect",Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_list:
                Toast.makeText(getApplicationContext(),    "clic sur list",Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_import:
                Toast.makeText(getApplicationContext(),    "clic sur import",Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_export:

                Toast.makeText(getApplicationContext(),    "clic sur export",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
