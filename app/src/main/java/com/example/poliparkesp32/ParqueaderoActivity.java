package com.example.poliparkesp32;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParqueaderoActivity extends AppCompatActivity {
    ImageView parqueadero;
    ImageView car;
    ImageView car2;
    ImageView camion;
    TextView txt;
    int redCar= R.drawable.carred;
    int greenCar=R.drawable.cargreen;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenciaFiec = database.getReference("Parqueos/FIEC");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueadero);
        parqueadero=findViewById(R.id.parqueadero);
        car=findViewById(R.id.car);
        car2=findViewById(R.id.car2);
        camion=findViewById(R.id.car4);
        txt= findViewById(R.id.textView);
      /*  referenciaFiec.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Verifica si existen datos en el nodo "Ubicaciones"
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("1").getValue().equals(true)){
                        cambiarimagen(redCar, car);
                    }else{
                        cambiarimagen(greenCar,car);
                    }

                }else{
                    Log.d("no funciona", "es null");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar error de lectura de la base de datos
                Log.e("FirebaseError", "Error al leer datos: " + databaseError.getMessage());
            }
        });*/
        disponible("1",car);
        disponible("2",camion);
    }
    public void cambiarimagen(int rsc, ImageView img){
        if ( img!=null) {
            img.setImageResource(rsc);

        } else{
            Log.e("ImagenActivity", "imagen es nulo");
        }
    }

    public void disponible(String spot, ImageView carro){

        referenciaFiec.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Verifica si existen datos en el nodo "Ubicaciones"
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child(spot).getValue().equals(false)){
                        cambiarimagen(redCar, carro);
                    }else{
                        cambiarimagen(greenCar,carro);
                    }

                }else{
                    Log.d("no funciona", "es null");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar error de lectura de la base de datos
                Log.e("FirebaseError", "Error al leer datos: " + databaseError.getMessage());
            }
        });
    }



}