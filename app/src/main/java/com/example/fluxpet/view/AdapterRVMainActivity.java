package com.example.fluxpet.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fluxpet.R;
import com.example.fluxpet.model.pojo.Category;
import com.example.fluxpet.model.pojo.Pet;
import com.example.fluxpet.model.pojo.Tag;

import java.util.ArrayList;
import java.util.List;



public class AdapterRVMainActivity extends RecyclerView.Adapter<AdapterRVMainActivity.PetViewHolder> {
    private List<Pet> aListOfPets = new ArrayList<>();
    private SelectedPetListener selectedPetListener;


    public AdapterRVMainActivity (SelectedPetListener selectedPetListener){
        this.selectedPetListener=selectedPetListener;
    }



    public void setaListOfPets(List<Pet> aListOfPets) {
        this.aListOfPets = aListOfPets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_rv_cell,parent,false);
        PetViewHolder petViewHolder = new PetViewHolder(view);
        return petViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = aListOfPets.get(position);
        holder.bindPet(pet);
    }

    @Override
    public int getItemCount() {
        return aListOfPets.size();
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView petPhotoIV;
        TextView petNameTV;



        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petPhotoIV = itemView.findViewById(R.id.pet_rv_cell_imageview);
            petNameTV = itemView.findViewById(R.id.pet_rv_cell_textview_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToDo
                    //Aca habria que hacer el listener para la activity, cosa de que pase por intent y bundle el pet en cuestion
                    selectedPetListener.SelectedPet(aListOfPets.get(getAdapterPosition()).getId());

                }
            });
        }

        public void bindPet (Pet pet){
            //ToDo
            //Aca habria que hacer un try catch para que intente de hacer glide con el photo url
            //petPhotoIV con glide y sino una imagen de no image found
            pet = nullPetValuesHandler(pet);

            if (pet.getName()==null | pet.getName().equals("")){
                petNameTV.setText("[No name Available]");
            } else {
                petNameTV.setText(pet.getName());
            }


            if (pet.getPhotoUrls().get(0).equals("string") | pet.getPhotoUrls().get(0).equals("") | pet.getPhotoUrls().size()==0 | pet.getPhotoUrls()==null) {
                petPhotoIV.setScaleType(ImageView.ScaleType.FIT_XY);
                petPhotoIV.setImageResource(R.drawable.noimagefound);
            } else {
                try {
                    Glide.with(petPhotoIV.getContext())
                            .load(pet.getPhotoUrls().get(0))
                            .into(petPhotoIV);
                } catch (Exception e) {
                    petPhotoIV.setScaleType(ImageView.ScaleType.FIT_XY);
                    petPhotoIV.setImageResource(R.drawable.noimagefound);
                }
            }

        }
    }

    public interface SelectedPetListener{
        public void SelectedPet (long petId);
    }

    public Pet nullPetValuesHandler(Pet pet) {

        if (pet.getName()==(null)) {
            pet.setName("");
        }
        if (pet.getPhotoUrls()==(null)) {
            List<String> aList = new ArrayList<>();
            aList.add("");
            pet.setPhotoUrls(aList);
        }else if (pet.getPhotoUrls().get(0).equals("someUrl")){
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
