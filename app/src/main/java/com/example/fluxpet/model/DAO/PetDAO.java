package com.example.fluxpet.model.DAO;



import com.example.fluxpet.model.MyRetrofit;
import com.example.fluxpet.model.PetService;
import com.example.fluxpet.model.pojo.Pet;
import com.example.fluxpet.util.PetHelper;
import com.example.fluxpet.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetDAO extends MyRetrofit {
    private PetService service;
    private RequestFailListener requestFailListener;


    public PetDAO() {
        super(PetHelper.BASE_URL);
        service = retrofit.create(PetService.class);

    }



    public void getAllAvailablePets(final ResultListener<List<Pet>> listenerController) {
        Call<List<Pet>> call = service.getAvailablePets();
        call.enqueue(new Callback<List<Pet>>() {
            //Falla por que necesita una apikey, ver como hago eso, creo que es con el authorize de swagger
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                List<Pet> list = response.body();
                listenerController.finish(list);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                System.out.println("Falla pedido available");
            }
        });
    }

    public void getSpecificPet (final ResultListener<Pet> listenerController, long id){
        //modificar service, para incluir numero de id
        Call<Pet> call = service.getAPet(id);
        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                Pet thisPet = response.body();
                listenerController.finish(thisPet);
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {



            }
        });
    }

    public interface RequestFailListener {
        public void notifyMainForToast (String s);

    }
}
