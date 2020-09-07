package com.example.altimetrik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    List<Results> modelArrayList = new ArrayList<>();
    HashSet<Results> hashSet = new HashSet<Results>();
    AdapterData adapterData;
    List<String> searchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        adapterData = new AdapterData(MainActivity.this, modelArrayList);

        recycler_view.setAdapter(adapterData);

        getJsonFileFromLocally();
    }

    //Load JSON data for assets folder i.e locally
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("data.json");       //TODO Json File  name from assets folder
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getJsonFileFromLocally() {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("results");                  //TODO pass array object name

            for (int i = 0; i < jsonArray.length(); i++) {
                Results employeeModel = new Results();
                JSONObject jsonObjectEmployee = jsonArray.getJSONObject(i);
                String empId = jsonObjectEmployee.getString("artistName");
                String trackName = jsonObjectEmployee.getString("trackName");
                String releaseDate = jsonObjectEmployee.getString("releaseDate");
                String imageurl = jsonObjectEmployee.getString("artworkUrl100");
                String collectionPrice = jsonObjectEmployee.getString("collectionPrice");
                employeeModel.setArtistName(empId);
                employeeModel.setTrackName(trackName);
                employeeModel.setReleaseDate(releaseDate);
                employeeModel.setArtworkUrl100(imageurl);
                employeeModel.setCollectionPrice(collectionPrice);

                modelArrayList.add(employeeModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.actionSearch);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*for(int i=0;i<employeeModelArrayList.size();i++) {
                    if (employeeModelArrayList.get(i).getArtistName().contains(query)) {
                        searchList.add(employeeModelArrayList.get(i).getArtistName());
                        adapterData.getFilter().filter(query);
                    }*//*else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*//*
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterData.getFilter().filter(newText);
                Log.d("Rupesh ", "If part Non empty");
                return false;
            }
        });
        return true;
    }
}
