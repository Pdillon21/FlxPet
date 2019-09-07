package com.example.fluxpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public void bringAvailablePets () {
        PetController petController = new PetController();
        petController.getAllAvailablePets(new ResultListener<PetContainer>() {
            @Override
            public void finish(PetContainer result) {
                Toast.makeText(MainActivity.this, result.getPets().get(0).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bringAvailablePets();





    }
}
