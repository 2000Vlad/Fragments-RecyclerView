package com.course.myapplication.travel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;

import static com.course.myapplication.travel.Voyage.MOUNTAINS;
import static com.course.myapplication.travel.VoyageRecyclerActivity.*;

import com.course.myapplication.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import static com.course.myapplication.travel.Voyage.CITY_BREAK;
import static com.course.myapplication.travel.Voyage.SEASIDE;

public class VoyageActivity extends AppCompatActivity {

    EditText mTripNameText;
    EditText mDestinationText;
    RadioButton mCityBreakButton;
    RadioButton mSeasideButton;
    RadioButton mMountainsButton;
    SeekBar mPriceBar;
    RatingBar mRatingBar;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage);
        mIndex = getIntent().getIntExtra(INDEX_KEY, 0);
        initUi();

    }

    private void initUi() {
        mTripNameText = findViewById(R.id.tripname_text);
        mDestinationText = findViewById(R.id.destination_text);
        mCityBreakButton = findViewById(R.id.citybreak_button);
        mMountainsButton = findViewById(R.id.mountains_button);
        mSeasideButton = findViewById(R.id.seaside_button);
        mPriceBar = findViewById(R.id.price_bar);
        mRatingBar = findViewById(R.id.rating_bar);

        Bundle extras = getIntent().getExtras();
        String tripName = extras.getString(TRIPNAME_KEY);
        String destination = extras.getString(DESTINATION_KEY);
        Integer tripType = extras.getInt(TRIPTYPE_KEY);
        Integer price = extras.getInt(PRICE_KEY);
        Float rating = extras.getFloat(RATING_KEY);

        mTripNameText.setText(tripName);
        mDestinationText.setText(destination);
        setTripType(tripType);
        mPriceBar.setProgress(price);
        mRatingBar.setRating(rating);
    }

    public void save(View view) {
        Intent data = new Intent(VoyageActivity.this, VoyageRecyclerActivity.class);
        Bundle extras = new Bundle();
        extras.putString(TRIPNAME_KEY, mTripNameText.getText().toString());
        extras.putString(DESTINATION_KEY, mDestinationText.getText().toString());
        extras.putInt(TRIPTYPE_KEY, getTripType());
        extras.putInt(PRICE_KEY, mPriceBar.getProgress());
        extras.putInt(INDEX_KEY, mIndex);
        extras.putFloat(RATING_KEY, mRatingBar.getRating());
        data.putExtras(extras);
        setResult(RESULT_OK, data);
        finish();
    }

    private int getTripType() {
        if (mSeasideButton.isChecked()) return SEASIDE;
        if (mMountainsButton.isChecked()) return MOUNTAINS;
        if (mCityBreakButton.isChecked()) return CITY_BREAK;
        return 255;
    }

    private void setTripType(int tripCode) {
        if (tripCode == SEASIDE) mSeasideButton.setChecked(true);
        if (tripCode == MOUNTAINS) mMountainsButton.setChecked(true);
        if (tripCode == CITY_BREAK) mCityBreakButton.setChecked(true);

    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent(VoyageActivity.this, VoyageRecyclerActivity.class);
        Bundle extras = new Bundle();
        extras.putString(TRIPNAME_KEY, mTripNameText.getText().toString());
        extras.putString(DESTINATION_KEY, mDestinationText.getText().toString());
        extras.putInt(TRIPTYPE_KEY, getTripType());
        extras.putInt(PRICE_KEY, mPriceBar.getProgress());
        extras.putFloat(RATING_KEY, mRatingBar.getRating());
        data.putExtras(extras);
        setResult(RESULT_OK, data);
        finish();
        super.onBackPressed();
    }
}
