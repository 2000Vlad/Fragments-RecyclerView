package com.course.myapplication.travel;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.course.myapplication.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.course.myapplication.travel.Voyage.CITY_BREAK;
import static com.course.myapplication.travel.Voyage.SEASIDE;

public class VoyageRecyclerActivity extends AppCompatActivity implements ItemListener {
    public static final String FILE_NAME = "data";
    public static final String TEMP = "temp";
    public static final int ITEM_COUNT = 6;
    public static final int REQUEST_CODE = 255;
    private static final int DEFAULT_PRICE = 35;
    public static final boolean DEBUG = true;

    public static final String INDEX_KEY = "index";
    public static final String TRIPNAME_KEY = "tripname";
    public static final String DESTINATION_KEY = "destination";
    public static final String TRIPTYPE_KEY = "triptype";
    public static final String PRICE_KEY = "price";
    public static final String RATING_KEY = "rating";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_recycler);
        if (DEBUG) seed();
        initUi();
    }

    private void initUi() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(VoyageRecyclerActivity.this));
        mRecyclerView.setAdapter(new VoyageAdapter(this, getSource()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();

        String tripName = extras.getString(TRIPNAME_KEY);
        String destination = extras.getString(DESTINATION_KEY);
        Integer tripType = extras.getInt(TRIPTYPE_KEY);
        Integer price = extras.getInt(PRICE_KEY);
        Float rating = extras.getFloat(RATING_KEY);
        int i = extras.getInt(INDEX_KEY);

        Voyage voyage = new Voyage();
        voyage.setTripName(tripName);
        voyage.setDestination(destination);
        voyage.setTripType(tripType);
        voyage.setPrice(price);
        voyage.setRating(rating);
        mHandles.get(i).setParameters(voyage);
        replace(i, voyage);


    }

    private List<ViewHandle> mHandles;

    public void addViewHandle(ViewHandle handle) {
        if (mHandles == null) mHandles = new ArrayList<>();
        mHandles.add(handle);

    }

    public void onClick(View view, int i) {
        List<Voyage> source = getSource();
        Intent intent = new Intent(VoyageRecyclerActivity.this, VoyageActivity.class);
        Bundle extras = new Bundle();

        extras.putString(TRIPNAME_KEY, source.get(i).getTripName());
        extras.putString(DESTINATION_KEY, source.get(i).getDestination());
        extras.putInt(TRIPTYPE_KEY, source.get(i).getTripType());
        extras.putInt(PRICE_KEY, source.get(i).getPrice());
        extras.putFloat(RATING_KEY, source.get(i).getRating());
        extras.putInt(INDEX_KEY, i);

        intent.putExtras(extras);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public List<Voyage> getSource() {
        ArrayList<Voyage> voyages = new ArrayList<>();
        try {
            DataInputStream stream = new DataInputStream(openFileInput(FILE_NAME));

            for (int i = 0; i < ITEM_COUNT; i++) {
                voyages.add(next(stream));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return voyages;
    }

    public static int getImageResource(int i) {
        switch (i) {
            case 0:
                return R.drawable.voyage_1;
            case 1:
                return R.drawable.voyage_2;
            case 2:
                return R.drawable.voyage_3;
            case 3:
                return R.drawable.voyage_4;
            case 4:
                return R.drawable.voyage_5;
            case 5:
                return R.drawable.voyage_6;

        }
        return 0;
    }

    private Voyage next(DataInputStream stream) {
        Voyage next = new Voyage();
        try {


            String tripName = stream.readUTF();
            String destination = stream.readUTF();
            Integer tripType = stream.readInt();
            Integer price = stream.readInt();
            Float rating = stream.readFloat();

            next.setTripName(tripName);
            next.setDestination(destination);
            next.setTripType(tripType);
            next.setPrice(price);
            next.setRating(rating);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return next;
    }

    private void write(DataOutputStream stream, Voyage voyage) {
        try {

            stream.writeUTF(voyage.getTripName());
            stream.writeUTF(voyage.getDestination());
            stream.writeInt(voyage.getTripType());
            stream.writeInt(voyage.getPrice());
            stream.writeFloat(voyage.getRating());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void replace(int i, Voyage voyage) {
        try {
            DataInputStream stream = new DataInputStream(openFileInput(FILE_NAME));
            DataOutputStream temp = new DataOutputStream(openFileOutput(TEMP, MODE_PRIVATE));
            for (int j = 0; j < i; j++) {
                write(temp, next(stream));
            }
            write(temp, voyage);
            for (int j = i + 1; j < ITEM_COUNT; j++) {
                write(temp, next(stream));
            }
            stream.close();
            temp.close();
            File oldFile = getFileStreamPath(FILE_NAME);
            File newFile = getFileStreamPath(TEMP);
            oldFile.delete();
            newFile.renameTo(oldFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void seed() {
        DataOutputStream stream = null;
        try {
            stream = new DataOutputStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
            List<String> tripNames = getTripNames();
            List<String> destinations = getDestinations();
            List<Integer> tripTypes = getTripTypes();
            List<Integer> prices = getPrices();
            List<Float> ratings = getRatings();
            for (int i = 0; i < ITEM_COUNT; i++) {

                String tripName = tripNames.get(i);
                String destination = destinations.get(i);
                Integer tripType = tripTypes.get(i);
                Integer price = prices.get(i);
                Float rating = ratings.get(i);


                try {
                    stream.writeUTF(tripName);
                    stream.writeUTF(destination);
                    stream.writeInt(tripType);
                    stream.writeInt(price);
                    stream.writeFloat(rating);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            Log.e(VoyageActivity.class.getName(), Log.getStackTraceString(e));
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<String> getTripNames() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.tname_1));
        list.add(getString(R.string.tname_2));
        list.add(getString(R.string.tname_3));
        list.add(getString(R.string.tname_4));
        list.add(getString(R.string.tname_5));
        list.add(getString(R.string.tname_6));
        return list;
    }

    public List<String> getDestinations() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.dest_1));
        list.add(getString(R.string.dest_2));
        list.add(getString(R.string.dest_3));
        list.add(getString(R.string.dest_4));
        list.add(getString(R.string.dest_5));
        list.add(getString(R.string.dest_6));
        return list;
    }

    public List<Integer> getTripTypes() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(SEASIDE);
        list.add(CITY_BREAK);
        list.add(CITY_BREAK);
        list.add(CITY_BREAK);
        list.add(SEASIDE);
        list.add(SEASIDE);
        return list;
    }

    public List<Integer> getPrices() {
        List<Integer> list = new ArrayList<>();
        list.add(DEFAULT_PRICE);
        list.add(DEFAULT_PRICE);
        list.add(DEFAULT_PRICE);
        list.add(DEFAULT_PRICE);
        list.add(DEFAULT_PRICE);
        list.add(DEFAULT_PRICE);
        return list;


    }

    public List<Float> getRatings() {
        ArrayList<Float> list = new ArrayList();
        list.add(3f);
        list.add(3f);
        list.add(3f);
        list.add(3f);
        list.add(3f);
        list.add(3f);
        return list;
    }
}
