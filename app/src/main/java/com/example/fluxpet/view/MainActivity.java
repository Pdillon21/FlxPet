package com.example.fluxpet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fluxpet.R;
import com.example.fluxpet.controller.PetController;
import com.example.fluxpet.model.DAO.PetDAO;
import com.example.fluxpet.model.pojo.Category;
import com.example.fluxpet.model.pojo.Pet;
import com.example.fluxpet.model.pojo.Tag;
import com.example.fluxpet.util.ResultListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterRVMainActivity.SelectedPetListener, PetDAO.RequestFailListener {
    private ProgressBar aProgressBar;
    private RecyclerView recyclerView;
    private List<Pet> aListOfPets = new ArrayList<>();
    private List<Pet> aFilteredListOfPets = new ArrayList<>();
    private AdapterRVMainActivity adapterRVMainActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView contextualMenuTextview;
    private String listArrangement;
    private SearchView searchView;
    private String currentQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.main_activity_refreshlayout);
        aProgressBar = findViewById(R.id.main_activity_progressbar);
        searchView = findViewById(R.id.main_activity_search_view);
        contextualMenuTextview = findViewById(R.id.main_activity_textview_list_arrange);
        listArrangement = "az";
        currentQuery = "";



        recyclerView = findViewById(R.id.main_activity_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        adapterRVMainActivity = new AdapterRVMainActivity(this);
        recyclerView.setAdapter(adapterRVMainActivity);
        registerForContextMenu(contextualMenuTextview);


        bringAvailablePets();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (currentQuery == null) {
                    currentQuery = "";
                } else {
                    currentQuery = query;
                }
                if (currentQuery.equals("")) {
                    aFilteredListOfPets = aListOfPets;
                    checkForSelectedArrange(listArrangement);
                    adapterRVMainActivity.setaListOfPets(aFilteredListOfPets);
                    adapterRVMainActivity.notifyDataSetChanged();
                } else {
                    filterListByString(currentQuery);
                    checkForSelectedArrange(listArrangement);
                    adapterRVMainActivity.setaListOfPets(aFilteredListOfPets);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //ToDo Aca hay que hacer el filtering de la lista, convendria hacerlo en una funcion separada
                //ToDo que tome una lista y devuelva otra si contains.


                if (currentQuery == null) {
                    currentQuery = "";
                } else {
                    currentQuery = newText;
                }
                if (currentQuery.equals("")) {
                    aFilteredListOfPets = aListOfPets;
                    checkForSelectedArrange(listArrangement);
                    adapterRVMainActivity.setaListOfPets(aFilteredListOfPets);
                    adapterRVMainActivity.notifyDataSetChanged();
                } else {
                    filterListByString(currentQuery);
                    checkForSelectedArrange(listArrangement);
                    adapterRVMainActivity.setaListOfPets(aFilteredListOfPets);
                }


                return false;
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bringAvailablePets();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aListOfPets.size() != 0) {
            aProgressBar.setVisibility(View.INVISIBLE);
        }
        if (searchView.getQuery().toString() == (null)) {
            currentQuery = "";
        } else {
            currentQuery = searchView.getQuery().toString();
        }

    }




    public void bringAvailablePets() {
        PetController petController = new PetController( );
        petController.getAllAvailablePets(new ResultListener<List<Pet>>() {
            @Override
            public void finish(List<Pet> result) {
                Toast.makeText(MainActivity.this, "Pets Fetched", Toast.LENGTH_SHORT).show();
                aListOfPets = result;
                correctPetsWithNullValues();
                aFilteredListOfPets = aListOfPets;
                filterListByString(currentQuery);
                checkForSelectedArrange(listArrangement);
                aProgressBar.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                adapterRVMainActivity.setaListOfPets(aFilteredListOfPets);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextualmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contextual_menu_az_arrange:
                if (listArrangement.equals("az")) {
                    Toast.makeText(this, "A to Z is already set", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    listArrangement = "az";
                    contextualMenuTextview.setText("(A>Z) Hold to change");
                    arrangeListAZ();
                    return true;
                }
            case R.id.contextual_menu_za_arrange:
                if (listArrangement.equals("za")) {
                    Toast.makeText(this, "Z to A is already set", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    listArrangement = "za";
                    contextualMenuTextview.setText("(Z>A) Hold to change");
                    arrangeListZA();
                    return true;
                }
            default:
                return false;
        }
    }

    public void filterListByString(String s) {
        if (s != null && !s.isEmpty()) {
            List<Pet> aFilteredList = new ArrayList<>();
            for (Pet p : aListOfPets) {
                if (p.getName().toUpperCase().contains(s.toUpperCase())) {
                    aFilteredList.add(p);
                }
            }
            aFilteredListOfPets = aFilteredList;
        }

    }


    public void checkForSelectedArrange(String s) {
        if (listArrangement.equals("az")) {
            arrangeListAZ();
        } else {
            arrangeListZA();
        }
    }

    public void arrangeListZA() {
        Collections.sort(aFilteredListOfPets, new Comparator<Pet>() {
            @Override
            public int compare(Pet pet, Pet t1) {
                return nullPetValuesHandler(t1).getName().compareTo(nullPetValuesHandler(pet).getName());
            }
        });
        adapterRVMainActivity.setaListOfPets(aFilteredListOfPets);
        adapterRVMainActivity.notifyDataSetChanged();
    }

    public void arrangeListAZ() {
        Collections.sort(aFilteredListOfPets, new Comparator<Pet>() {
            @Override
            public int compare(Pet pet, Pet t1) {
                return nullPetValuesHandler(pet).getName().compareTo(nullPetValuesHandler(t1).getName());
            }
        });
        adapterRVMainActivity.setaListOfPets(aFilteredListOfPets);
        adapterRVMainActivity.notifyDataSetChanged();
    }


    @Override
    public void SelectedPet(long petId) {
        Intent intent = new Intent(MainActivity.this, PetDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("petId", Long.toString(petId));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public List<Pet> correctPetsWithNullValues() {
        List<Pet> cleanListOfPets = new ArrayList<>();
        for (Pet p : aListOfPets) {
            cleanListOfPets.add(nullPetValuesHandler(p));
        }
        return cleanListOfPets;
    }


    public Pet nullPetValuesHandler(Pet pet) {

        if (pet.getName() == (null)) {
            pet.setName("");
        } else if (pet.getName().equals("\uD83D\uDC15")){
            pet.setName("Someone named this pet " + "\uD83D\uDC15");
        }
        try {
            String petPhotoURl = pet.getPhotoUrls().get(0);
        } catch (IndexOutOfBoundsException e) {
            List<String> aListOfPhotoUrls = new ArrayList<>();
            String aString = "";
            aListOfPhotoUrls.add(aString);
            pet.setPhotoUrls(aListOfPhotoUrls);
        }

        if (pet.getPhotoUrls() == (null)) {
            List<String> aList = new ArrayList<>();
            aList.add("");
            pet.setPhotoUrls(aList);
        } else if (pet.getPhotoUrls().get(0).equals("someUrl")){
            List<String> aList = new ArrayList<>();
            aList.add("");
            pet.setPhotoUrls(aList);
        }

        if (pet.getCategory() == (null)) {

            Category aCategory = new Category();
            aCategory.setId(0);
            aCategory.setName("");
            pet.setCategory(aCategory);
        }

        if (pet.getTags() == (null)) {
            Tag tag = new Tag();
            tag.setId(0);
            tag.setName("");
            List<Tag> aListOfTags = new ArrayList<>();
            aListOfTags.add(tag);
            pet.setTags(aListOfTags);
        }

        if (pet.getCategory().getName() == (null)) {
            pet.getCategory().setName("");
        }



        return pet;


    }

    @Override
    public void notifyMainForToast(String s) {
        Toast.makeText(this, "Api Request Failed", Toast.LENGTH_SHORT).show();
    }
}
