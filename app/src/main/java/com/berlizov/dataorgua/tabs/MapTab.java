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

public class MapTab extends TableReaderFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private int longIndex;
    private int altIndex;

    public void startMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean can(JSONTable table) {
        return (table.getColumIndex("lat")>=0)||(table.getColumIndex("lon")>=0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng pos=new LatLng(0,0);
        for (int i=0;i<mTable.getLinesCount();i++) {
            pos = new LatLng(Double.parseDouble(mTable.getRow(i, longIndex)),Double.parseDouble(mTable.getRow(i, altIndex)));
            mMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title(mTable.getRow(i, 0))
                            .snippet(mTable.getRow(i,1))
            );
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_tab, container, false);
        return view;
    }

    @Override
    public int getIdName() {
        return R.string.map_tab;
    }

    @Override
    public void applyTable(View view) {
        altIndex = mTable.getColumIndex("lon");
        longIndex =  mTable.getColumIndex("lat");
        if((altIndex>=0)||(longIndex>=0)){
            startMap();
        }
    }
}
