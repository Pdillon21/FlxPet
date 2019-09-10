package com.example.fluxpet.pojo;


import com.example.fluxpet.pojo.Pet;

import java.util.List;

public class PetContainer {


    private List <Pet> pets;

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
