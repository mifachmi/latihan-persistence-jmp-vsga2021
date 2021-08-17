package com.example.latihandua;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.latihandua.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.latihandua.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInternalStorage.setOnClickListener(this);
        binding.btnExternalStorage.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInternalStorage:
                Intent internal = new Intent(MainActivity.this, InternalStorageActivity.class);
                startActivity(internal);
                break;
            case R.id.btnExternalStorage:
                Intent external = new Intent(MainActivity.this, ExternalStorageActivity.class);
                startActivity(external);
                break;
        }
    }
}