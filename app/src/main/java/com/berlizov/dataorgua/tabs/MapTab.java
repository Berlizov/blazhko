package com.berlizov.dataorgua.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by 350z6_000 on 17.10.2015. 
 * таба с картой
 */
public class MapTab extends TableReaderFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private int longIndex;
    private int altIndex;
    private JSONTable table;
/**
* старт загрузки карты с ее апи
*/
    public void startMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
/**
*проверяем есть ли в JSONTable поля координат
*/
    @Override
    public boolean can(JSONTable table) {
        return (table.getColumIndex("lat")>=0)||(table.getColumIndex("lon")>=0);
    }
/**
* устанавливаем в табу JSONTable
* и стартуем карту
*/
    @Override
    public void setTable(JSONTable table) {
        this.table = table;
        altIndex =  table.getColumIndex("lon");
        longIndex =  table.getColumIndex("lat");
        if((altIndex>=0)||(longIndex>=0)){
            startMap();
        }
    }
/**
* тут ставим точки на карте после ее загрузки 
*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng pos=new LatLng(0,0);
        for (int i=0;i<table.getLinesCount();i++) {
            pos = new LatLng(Double.parseDouble(table.getRow(i, longIndex)),Double.parseDouble(table.getRow(i,altIndex)));
            mMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title(table.getRow(i,0))
                            .snippet(table.getRow(i,1))
            );
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_tab, container, false);
        return view;
    }

    @Override
    public int getIdName() {
        return R.string.map_tab;
    }
}
