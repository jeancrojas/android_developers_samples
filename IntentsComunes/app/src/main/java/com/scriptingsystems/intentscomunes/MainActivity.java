package com.scriptingsystems.intentscomunes;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static int REQUEST_SELECT_PHONE_NUMBER = 1;
    private static final int REQUEST_SELECT_CONTACT = 1;
    Intent intent;
    private EditText hour, minute, editCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Alarma
        hour = (EditText) findViewById(R.id.hour);
        minute = (EditText) findViewById(R.id.minute);


        hour.setFilters(new InputFilter[] {new InputFilterMinMax(0, 23)});
        minute.setFilters(new InputFilter[] {new InputFilterMinMax(0, 59)});

        //Calendario
        editCalendar = (EditText) findViewById(R.id.editCalendar);

        //Capturar imagen y video


    }

    public void aplicarAlarma(View view) {

        createAlarm("Trabajo"
                , Integer.parseInt( hour.getText().toString() )
                , Integer.parseInt( minute.getText().toString() )
        );
    }


    public void createAlarm (String message, int hour, int minutes) {
        intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addCalendar(View view) {

        //14/4/2016
        String fecha;
        int mes, dia, anyo;


        fecha = editCalendar.getText().toString();

        dia = Integer.parseInt(fecha.substring(0, fecha.indexOf('/')));
        mes = Integer.parseInt(fecha.substring(fecha.indexOf('/')+1,fecha.lastIndexOf('/')));
        mes--;
        anyo = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/')+1));


        String title = "Trabajo";
        String location = "SERMICRO";
        String description = "Este evento se realizara en Cuatro Caminos";

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(anyo, mes , dia);

        addEvent(title, location, description , beginTime);
        /*
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 2, 21, 7, 30);

        addEvent(title, location, description, beginTime, endTime);
        */

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addEvent (String title, String location, String description , Calendar begin) {

        intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(Events.TITLE, title)
                .putExtra(Events.EVENT_LOCATION, location)
                .putExtra(Events.DESCRIPTION, description )
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.getTimeInMillis());
                //.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis());

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);

    }

    public void capturarImagen(View view) {
        capturePhoto();

    }


    public void capturePhoto () {
        intent = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity (intent);
        }

    }

    //Contactos


    public void selectContact (View view){

        insertContact("Jean Carlos", "jean@prueba.com");

        /*
        //Hay que seleccionar un Numero de la lista para que nos de la información
        //va ligado al metodo onActivityResult(...)

        intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

        //Hay que seleccionar un Numero de la lista para que nos de la información
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
            */
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Seleccionar datos de un contacto especifico
        if ( requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK ) {
            Uri contactUri = data.getData();
            String[] projection = new String[] {
                    ContactsContract.CommonDataKinds.Phone._ID
                    , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    , ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri,projection,null,null, null);

            if (cursor != null && cursor.moveToFirst()){

                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
                String id = cursor.getString(numberIndex);

                numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String displayName = cursor.getString(numberIndex);

                numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);

                Log.d(TAG, "***AQUÍ***\n\tID: "+id+"\n\tNombre: "+displayName+"\n\tNumero: "+number);
            }
        }

        Log.d(TAG, "onActivityResult()...\nrequestCode: "+ requestCode+"\nresultCode: "+resultCode+"\ndata: "+data.toString());
    }

*/

    public void insertContact (String name, String email) {
        intent = new Intent(Intent.ACTION_INSERT)
                .setType(ContactsContract.Contacts.CONTENT_TYPE)
                .putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.EMAIL, email);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
}
