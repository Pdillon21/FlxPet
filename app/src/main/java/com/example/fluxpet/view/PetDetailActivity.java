package com.example.fluxpet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.fluxpet.R;
import com.example.fluxpet.controller.PetController;
import com.example.fluxpet.model.pojo.Pet;
import com.example.fluxpet.util.ResultListener;

public class PetDetailActivity extends AppCompatActivity {
    private String petId;
    private Pet pet;
    private TextView fragment2Swithcer;
    private String currentFrag2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fragment2Swithcer = findViewById(R.id.activity_pet_detail_switcher);
        fragment2Swithcer.setText("");
        petId = bundle.getString("petId");
        fecthAPet(Long.parseLong(petId));
        currentFrag2 = "search";

        registerForContextMenu(fragment2Swithcer);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextual_menu_detail_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contextual_menu_detail_search:
                if (currentFrag2.equals("search")) {
                    Toast.makeText(this, "Search is already set", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    currentFrag2 = "search";
                    String aStringToDisplay = "More About "+pet.getName() + " (Hold to Change)";
                    fragment2Swithcer.setText(aStringToDisplay);
                    switchFragment(currentFrag2);
                    return true;
                }
            case R.id.contextual_menu_detail_map:
                if (currentFrag2.equals("map")) {
                    Toast.makeText(this, "Store Location is already set", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    currentFrag2 = "map";
                    String aStringToDisplay = "Come pick "+pet.getName() + " (Hold to Change)";
                    fragment2Swithcer.setText(aStringToDisplay);
                    switchFragment(currentFrag2);
                    return true;
                }
            default:
                return false;
        }
    }

    public void pasteFragments() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment_pet_details fragment_pet_details = new Fragment_pet_details();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pet", pet);
        fragment_pet_details.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.activity_pet_detail_container1, fragment_pet_details)
                .commit();

        Fragment_WebView fragment_webView = new Fragment_WebView();
        fragment_webView.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.activity_pet_detail_container2, fragment_webView)
                .commit();
    }

    public void fecthAPet(long id) {
        PetController petController = new PetController();
        petController.getSpecificPet(new ResultListener<Pet>() {
            @Override
            public void finish(Pet result) {
                pet = result;
                Toast.makeText(PetDetailActivity.this, "Pet Fetched", Toast.LENGTH_SHORT).show();
                pasteFragments();
                String aStringToDisplay = "More About "+pet.getName() + " (Hold to Change)";
                fragment2Swithcer.setText(aStringToDisplay);

            }
        }, id);
    }

    public void switchFragment(String s) {
        FragmentManager anotherFragmentManager = getSupportFragmentManager();
        if (s.equals("map")){
            Fragment_Map fragment_map = new Fragment_Map();
            anotherFragmentManager.beginTransaction()
                    .replace(R.id.activity_pet_detail_container2,fragment_map)
                    .commit();
        } else if (s.equals("search")){
            Fragment_WebView fragment_webView = new Fragment_WebView();
            Bundle bundle = new Bundle();
            bundle.putSerializable("pet", pet);
            fragment_webView.setArguments(bundle);
            anotherFragmentManager.beginTransaction()
                    .replace(R.id.activity_pet_detail_container2,fragment_webView)
                    .commit();
        } else {
            Toast.makeText(this, "Sorry, can´t perform action", Toast.LENGTH_SHORT).show();
        }


    }


}
