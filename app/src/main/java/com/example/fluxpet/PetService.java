package com.example.fluxpet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PetService {
    @GET (PetHelper.AVAILABLE_PET_ENDPOINT)
    Call<PetContainer> getAvailablePets ();

    @GET (PetHelper.BASE_URL)
    Call<PetContainer> getAPet ();

}
