package com.example.javier.photosorter;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class actividadPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String UNABLE_TO_SAVE_PHOTO_FILE = "Unable to save photo file";
    private static String logtag = "CameraApp8";
    private Uri imageUri;
    private static final int TAKE_PICTURE = 0;
    private File imageFile;

    public void abrirActividad(View v){
        Intent i = new Intent(this,Actividad_Expandable.class);
        startActivity(i);
    }


    public void iniciarCamara(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.ACTION_IMAGE_CAPTURE");
        File photo =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),("IMG_"+ System.currentTimeMillis()+"_photo.jpg"));
        imageFile = new File(photo,"passpoints_image");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PICTURE);
       //startActivity(intent);

        System.out.println("Borrar");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
        setContentView(R.layout.activity_actividad_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton camara = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        camara.setOnClickListener(camaraListener);
    }

    public View.OnClickListener camaraListener = new View.OnClickListener(){
        public void onClick(View v){
            iniciarCamara(v);
        }
    };


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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(resultCode == Activity.RESULT_OK){             //****VERSION BUENA (Pone una imagen) *****
            Toast.makeText(actividadPrincipal.this,"Foto almacenada en el directorio 'Pictures'",Toast.LENGTH_LONG).show();
            Uri selectedImage = imageUri;
            getContentResolver().notifyChange(selectedImage,null);
            ImageView imageView = (ImageView) findViewById(R.id.viewPhoto);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(cr,selectedImage);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(actividadPrincipal.this,"Foto almacenada en el directorio 'Pictures'",Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Log.e(logtag,e.toString());

            }
        }   //   **** Ejemplo de cargar imagen en ImageView *****  */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actividad_principal, menu);
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
        android.app.FragmentManager fragmentManager = getFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
