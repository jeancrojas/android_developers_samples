package com.scriptingsystems.intentscomunes2;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity()";
    static final int REQUEST_IMAGE_OPEN = 1;
    private Intent intent;
    private Uri targetUri = null;

    private TextView textUri;
    private ImageView imagePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUri = (TextView) findViewById(R.id.textUri);
        imagePhoto = (ImageView) findViewById(R.id.imagePhoto);

    }

    public void generarCorreoElectronico(View view) {

        String[] addresses = {"jean@prueba.com"};
        String subject = "Mensaje de prueba";
        String body = "Este es un mensaje de prueba";

        composeEmail(addresses, subject, body );

    }

    public void composeEmail (String[] addresses, String subject, String body) {

        Log.d(TAG, "composeEmail()...");
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:"))
                .putExtra(Intent.EXTRA_EMAIL, addresses)
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }


    public void abrirArchivo(View view) {
        Log.d(TAG, "*** abrirArchivo()...");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        else
            intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, REQUEST_IMAGE_OPEN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Log.d(TAG, "*** onActivityResult()..."+fullPhotoUri.toString());
            textUri.setText (fullPhotoUri.toString());
        }


    }
}
