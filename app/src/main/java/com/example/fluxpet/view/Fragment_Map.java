package com.example.fluxpet.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.fluxpet.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Map extends Fragment implements OnMapReadyCallback {
    //ToDo corregir el xml
    //ToDo terminar de pasar todas las funciones necesarias para que se ejectute el programa
    //ToDo ver como puedo agregar mi ubicacion actual

    private SupportMapFragment mapFragment;
    private Double lat1;
    private Double lng1;
    private Double lat2;
    private Double lng2;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;

    public Fragment_Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__map, container, false);
        lat1 = -34.6188126;
        lng1 = -58.3677217;
        lat2 = -34.9208142;
        lng2 = -57.9518059;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLng1 = new LatLng(lat1, lng1);
        LatLng latLng2 = new LatLng(lat2, lng2);
        map.addMarker(new MarkerOptions().position(latLng1).title("Pet Store 1"));
        map.addMarker(new MarkerOptions().position(latLng2).title("Pet Store 2"));
        if ((ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            return;
        }
        map.setMyLocationEnabled(true);
        fetchLastLocation();



    }

    private void fetchLastLocation() {
        if ((ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    currentLocation = location;
                    LatLng myLastLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLastLocation, 8.0f));
                }
            }
        });
    }




}
