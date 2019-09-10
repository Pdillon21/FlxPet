package com.example.fluxpet.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.fluxpet.R;
import com.example.fluxpet.model.pojo.Category;
import com.example.fluxpet.model.pojo.Pet;
import com.example.fluxpet.model.pojo.Tag;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_WebView extends Fragment {
    private WebView aWebView;
    private String searchBaseURl = "https://www.google.com/search?q=";
    private String petNameForSearch;
    private Pet pet;
    private ProgressBar aProgressBar;



    public Fragment_WebView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__web_view, container, false);
        aProgressBar = view.findViewById(R.id.fragment_web_view_progress_bar);
        Bundle bundle = getArguments();
        pet = (Pet) bundle.getSerializable("pet");
        pet = nullPetValuesHandler(pet);
        petNameForSearch = pet.getName();
        aProgressBar.setVisibility(View.INVISIBLE);


        aWebView = view.findViewById(R.id.fragmen_web_view_webview);
        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                aProgressBar.setVisibility(View.INVISIBLE);
            }
        };
        aWebView.setWebViewClient(webViewClient);

        String urlToSearch = searchBaseURl+petNameForSearch;
        aWebView.loadUrl(urlToSearch);




        return view;





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
