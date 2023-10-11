package com.food1.whateat.presentation;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.food1.whateat.MainActivity;

public class SelectFoodActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
//        String[] myMenu =myLoc2;
//        intent.putExtra("CheckBox", myMenu);
        setResult(RESULT_OK, intent);
        finish();
    }
}
