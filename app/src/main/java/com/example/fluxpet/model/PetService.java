package com.example.fluxpet;

import com.example.fluxpet.pojo.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PetService {
    @GET (PetHelper.AVAILABLE_PET_ENDPOINT)
    Call<List<Pet>> getAvailablePets ();

    @GET (PetHelper.BASE_URL+"pet/{id}")
    Call<Pet> getAPet (@Path("id")long id);



}
