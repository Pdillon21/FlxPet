package com.example.fluxpet;

import android.app.Activity;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetDAO extends MyRetrofit {
    private PetService service;


    public PetDAO() {
        super(PetHelper.BASE_URL);
        service = retrofit.create(PetService.class);
    }


    public void getAllAvailablePets(final ResultListener<PetContainer> listenerController) {
        Call<PetContainer> call = service.getAvailablePets();
        call.enqueue(new Callback<PetContainer>() {
            //Falla por que necesita una apikey, ver como hago eso, creo que es con el authorize de swagger
            @Override
            public void onResponse(Call<PetContainer> call, Response<PetContainer> response) {
                PetContainer petContainer = response.body();
                listenerController.finish(petContainer);
            }

            @Override
            public void onFailure(Call<PetContainer> call, Throwable t) {
                System.out.println("Falla pedido");
            }
        });
    }
}
