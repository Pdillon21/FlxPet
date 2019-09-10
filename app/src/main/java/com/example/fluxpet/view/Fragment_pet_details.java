package com.example.fluxpet;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_pet_details extends Fragment {
    private Pet pet;
    private TextView namePet;
    private ImageView photoPet;
    private TextView idPet;
    private TextView statusPet;
    private ImageView statusIconPet;
    private TextView tagsPet;
    private TextView categoryPet;


    public Fragment_pet_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_details, container, false);




        namePet = view.findViewById(R.id.fragment_pet_details_name);
        photoPet = view.findViewById(R.id.fragment_pet_details_imageview);
        idPet = view.findViewById(R.id.fragment_pet_details_id);
        statusPet = view.findViewById(R.id.fragment_pet_details_status);
        statusIconPet = view.findViewById(R.id.fragment_pet_details_status_icon);
        tagsPet = view.findViewById(R.id.fragment_pet_details_tags);
        categoryPet = view.findViewById(R.id.fragment_pet_details_category);

        Bundle bundle = getArguments();
        pet = (Pet) bundle.getSerializable("pet");
        pet = nullPetValuesHandler(pet);


        if (pet.getName().equals("")) {

            namePet.setText("[No name Available]");
        } else {
            namePet.setText(firstChrToUpCase(pet.getName()));
        }
        if (Long.toString(pet.getId()).equals("")) {
            idPet.setText("[No Id Available]");
        } else {
            idPet.setText(Long.toString(pet.getId()));
        }

        statusPet.setText(firstChrToUpCase(pet.getStatus()));


        if (pet.getStatus().equals("available")) {
            statusIconPet.setImageResource(R.drawable.circle_green);
        } else if (pet.getStatus().equals("pending")) {
            statusIconPet.setImageResource(R.drawable.circleyellowstatus);
        } else {
            statusIconPet.setImageResource(R.drawable.circleredstatus);
        }


        if (pet.getPhotoUrls().size()==0){
            List<String> s = new ArrayList<>();
            s.add("");
            pet.setPhotoUrls(s);
        }

        if (pet.getPhotoUrls().get(0).equals("string") | pet.getPhotoUrls().get(0).equals("") | pet.getPhotoUrls().size()==0 | pet.getPhotoUrls()==null) {

            photoPet.setImageResource(R.drawable.noimagefound);
        } else {
            try {
                Glide.with(photoPet.getContext())
                        .load(pet.getPhotoUrls().get(0))
                        .into(photoPet);
            } catch (Exception e) {

                photoPet.setImageResource(R.drawable.noimagefound);
            }
        }


        String tagsSoFar = "";
        List<String> tagsname = new ArrayList<>();
        for (Tag t : pet.getTags()) {
            tagsname.add(t.getName());
        }
        if (tagsname.size() != 0) {
            for (String s : tagsname) {
                tagsSoFar = tagsSoFar + " " + s;
            }
            if (tagsSoFar.equals(" string")) {
                tagsPet.setText("[No tags Available]");
            } else {
                tagsPet.setText(tagsSoFar);
            }
        } else {
            tagsPet.setText("[No tags Available]");
        }

        try {
            if (pet.getCategory().getName().equals("string") | pet.getCategory().getName().equals("")) {
                categoryPet.setText("[No Category Available]");
            } else {
                categoryPet.setText(firstChrToUpCase(pet.getCategory().getName()));
            }
        }catch (Exception e){
            categoryPet.setText("[No Category Available]");
        }



        return view;


    }

    public String firstChrToUpCase(String aString) {
        if (aString.length()==0){
            return "";
        } else if (aString.length()==1){
            return aString.toUpperCase();
        }else {
            return aString.substring(0, 1).toUpperCase() + aString.substring(1);
        }
    }

    public Pet nullPetValuesHandler(Pet pet) {

        if (pet.getName()==(null)) {
            pet.setName("");
        }
        if (pet.getPhotoUrls()==(null)) {
            List<String> aList = new ArrayList<>();
            aList.add("");
            pet.setPhotoUrls(aList);
        }
        if (pet.getCategory()==(null)){

            Category aCategory = new Category();
            aCategory.setId(0);
            aCategory.setName("");
            pet.setCategory(aCategory);
        }

        if (pet.getTags()==(null)){
            Tag tag = new Tag();
            tag.setId(0);
            tag.setName("");
            List<Tag> aListOfTags = new ArrayList<>();
            aListOfTags.add(tag);
            pet.setTags(aListOfTags);
        } /*else if (pet.getTags().get(0)==(null)){
            Tag tag = new Tag();
            tag.setId(0);
            tag.setName("");
            List<Tag> aListOfTags = new ArrayList<>();
            aListOfTags.add(tag);
            pet.setTags(aListOfTags);
        }*/


        if (pet.getCategory().getName()==(null)) {
            pet.getCategory().setName("");
        }

        try {
            String petPhotoURl = pet.getPhotoUrls().get(0);
        } catch (IndexOutOfBoundsException e){
            List<String> aListOfPhotoUrls = new ArrayList<>();
            String aString = "";
            aListOfPhotoUrls.add(aString);
            pet.setPhotoUrls(aListOfPhotoUrls);
        }

        return pet;


    }


}
