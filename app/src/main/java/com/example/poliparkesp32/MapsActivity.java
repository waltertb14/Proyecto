package com.example.poliparkesp32;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.poliparkesp32.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenciaUbicaciones = database.getReference("Ubicaciones");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void marcarParqueaderos(GoogleMap gmap){
// Lee los datos de los usuarios

        referenciaUbicaciones.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Verifica si existen datos en el nodo "Ubicaciones"
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ubiSnapshot : dataSnapshot.getChildren()) {
                        //Se obtiene el nombre de cada parqueadero
                        String tag= ubiSnapshot.getKey().toString();
                        //Se obtiene las coordenadas de cada parqueadero
                        String[] latlng = ubiSnapshot.getValue().toString().split(";");
                        Double lat= Double.parseDouble(latlng[0]);
                        Double lng=Double.parseDouble(latlng[1]);
                        LatLng mark = new LatLng(lat,lng);
                        // Se guarda las coordenadas con los nombres en dos listas paralelas
                        gmap.addMarker(new MarkerOptions().position(mark).title(tag));
                    }

                }else{
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar error de lectura de la base de datos
                Log.e("FirebaseError", "Error al leer datos: " + databaseError.getMessage());
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Bundle bundle = getIntent().getExtras();
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        marcarParqueaderos(mMap);
        LatLng marcador = new LatLng(bundle.getDouble("lat"), bundle.getDouble("lng"));
        MarkerOptions busquedaOptions=new MarkerOptions().position(marcador).title(bundle.getString("titulo").toUpperCase());
        Marker busqueda=mMap.addMarker(busquedaOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcador, 20));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if(!marker.equals(busqueda)) {
                    String parqueadero = marker.getTitle();
                    mostrarParqueadero(parqueadero);
                }
                return false;
                }
        });


    }
    private void mostrarParqueadero(String nombre) {
        Intent intent = new Intent(this, ParqueaderoActivity.class);
        intent.putExtra("Parqueadero", nombre);
        startActivity(intent);
    }

}