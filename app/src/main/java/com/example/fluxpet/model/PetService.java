package com.example.fluxpet.model;


import com.example.fluxpet.model.pojo.Pet;
import com.example.fluxpet.util.PetHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PetService {
    @GET (PetHelper.AVAILABLE_PET_ENDPOINT)
    Call<List<Pet>> getAvailablePets ();

    @GET (PetHelper.BASE_URL+"pet/{id}")
    Call<Pet> getAPet (@Path("id")long id);



}
