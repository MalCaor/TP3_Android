package com.example.tp3;

import android.os.Bundle;
import android.os.Build;

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

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import android.net.Uri;

import android.content.DialogInterface;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;

import android.os.AsyncTask;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;


public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {
    private Fragment leFragment;
    private ImageView limage;

    private boolean permissionOK=false;
    private static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.READ_CONTACTS};
    private List<String> listPermissionsNeeded;

    public void demanderPermission() {
        int result;
        listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = checkSelfPermission(p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            for (String permission : listPermissionsNeeded) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission) == true) {
                    //Auorisations déja demandées et refusées
                    // explique pourquoi l'autorisation est nécessaire
                    new AlertDialog.Builder(this)
                            .setTitle("Permissions")
                            .setMessage("Les permissions internet et écriture sont nécessaires pour le bon fonctionnement de l'application")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // si ok on demande l'autorisation
                                    ActivityCompat.requestPermissions(MainActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Toast.makeText(getApplicationContext(), "Nous ne pouvons pas continuer l'application car ces permissions sont nécéssaires",Toast.LENGTH_LONG).show();

                                    // Perform Your Task Here--When No is pressed
                                    dialog.cancel();
                                }
                            }).show();
                } else {
                    // autorisations jamais demandées on demande l'autorisation
                    ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                }
            }
        }
        else {
            // toutes les permissions sont ok
            permissionOK=true;
        }
    }
    private  void checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = checkSelfPermission(p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
        }
        else
        {
            // Toutes les permissions sont ok
            permissionOK=true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;

                        }

                    }
                    // Show permissionsDenied
                    if(permissionsDenied.length()>0) {
                        Toast.makeText(getApplicationContext(),    "Nous ne pouvons pas continuer l'application car ces permissions sont nécéssaires : \n"+permissionsDenied,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        permissionOK=true;
                    }
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkPermissionAlert();

/*        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        leFragment = (Fragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment);
        leFragment.getView().setVisibility(View.GONE);
        limage= (ImageView) findViewById(R.id.imageView);
        Button bOk = (Button) leFragment.getView().findViewById(R.id.bFragOk);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // test des saisies
                boolean inf2 = false;
                if (((EditText)findViewById(R.id.etFragId)).getText().length() < 2)
                {
                    Toast.makeText(getApplicationContext(), "plus de 2", Toast.LENGTH_SHORT).show();
                    inf2 = true;
                }
                if(!inf2)
                {
                    String url="http://www.btssio-carcouet.fr/ppe4/public/connect2/";
                    EditText login=(EditText)findViewById(R.id.etFragId);
                    EditText pass=(EditText)findViewById(R.id.etFragPassword);
                    url=((String) url).concat(login.getText().toString().trim()).concat("/").concat(pass.getText().toString().trim()).concat("/infirmiere");
                    String[] mesparams = { url };
                    mThreadCon = new Async (MainActivity.this).execute(mesparams);
                }
                Toast.makeText(getApplicationContext(), "clic sur ok", Toast.LENGTH_SHORT
                ).show();
            }
        });
        Button bCancel = (Button) leFragment.getView().findViewById(R.id.bFragCancel);
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
                Toast.makeText(getApplicationContext(), "clic sur connect", Toast.LENGTH_LONG).show();

                leFragment.getView().setVisibility(View.VISIBLE);
                limage.setVisibility(View.GONE);
                return true;
            case R.id.menu_deconnect:
                Toast.makeText(getApplicationContext(), "clic sur deconnect", Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_list:
                Toast.makeText(getApplicationContext(), "clic sur list", Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_import:
                Toast.makeText(getApplicationContext(), "clic sur import", Toast.LENGTH_LONG).show();

                return true;
            case R.id.menu_export:

                Toast.makeText(getApplicationContext(), "clic sur export", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public void onFragmentInteraction (Uri uri){

    }
    public static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;
    public void checkPermissionAlert() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // on regarde quelle Activity a répondu

        switch (requestCode) {
            case ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        alertmsg("Permission ALERT","Permission OK");
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Pbs demande de permissions"
                                , Toast.LENGTH_SHORT).show();
                    }
                }

        }
    }
    // appel internet
    private AsyncTask<String, String, Boolean> mThreadCon = null;
    public void alertmsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(msg)
                .setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.
                TYPE_SYSTEM_ALERT);
        dialog.show();
    }
    public void retourConnexion(StringBuilder sb)
    {
        alertmsg("retour Connexion", sb.toString());
    }
}
