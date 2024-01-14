package com.example.poliparkesp32;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BusquedaActivity extends AppCompatActivity {
    EditText barraBusqueda;
    TextView indicacionTextView;
    String lat;
    String lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        barraBusqueda=findViewById(R.id.barraBusqueda);
        indicacionTextView=findViewById(R.id.indicacionTextView);

    }

    public void obtenerCoordenadas(View v){
        Geocoder geocoder = new Geocoder(BusquedaActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(barraBusqueda.getText().toString(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(list.size() > 0){
            Address address = list.get(0);
            lat=""+address.getLatitude();
            lon=""+address.getLongitude();
        }
        else{
            lat=null;
            lon=null;

        }
    }

    public void buscar(View v){
        obtenerCoordenadas(v);
        if (lat !=null || lon!=null) {
            Intent i = new Intent(this, MapsActivity.class);
            i.putExtra("titulo", barraBusqueda.getText().toString());
            i.putExtra("lat", Double.parseDouble(lat));
            i.putExtra("lng", Double.parseDouble(lon));


            startActivity(i);
        }
    }


}