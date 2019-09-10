package com.example.fluxpet.controller;


import com.example.fluxpet.model.DAO.PetDAO;
import com.example.fluxpet.model.pojo.Pet;
import com.example.fluxpet.util.ResultListener;

import java.util.List;

public class PetController {


    public PetController (){


    }


    public void getAllAvailablePets (final ResultListener<List<Pet>> listener){
        PetDAO petDAO = new PetDAO();
        petDAO.getAllAvailablePets(new ResultListener<List<Pet>>() {
            @Override
            public void finish(List<Pet> result) {
                listener.finish(result);
            }
        });
    }
    public void getSpecificPet (final ResultListener<Pet> listener,long id){
        PetDAO petDAO = new PetDAO();
        petDAO.getSpecificPet(new ResultListener<Pet>() {
            @Override
            public void finish(Pet result) {
                listener.finish(result);
            }
        },id);
    }
}
