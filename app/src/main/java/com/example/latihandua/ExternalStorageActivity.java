package com.example.latihandua;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.latihandua.databinding.ActivityExternalStorageBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityExternalStorageBinding binding;
    public static final String FILENAME = "externalFile.txt";
    public static final int REQUEST_CODE_STORAGE = 100;
    public int selectEvent = 0;
    public boolean successCreated, successDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExternalStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("External Persistence");

        binding.btnBuatFile.setOnClickListener(this);
        binding.btnBacaFile.setOnClickListener(this);
        binding.btnHapusFile.setOnClickListener(this);
        binding.btnUbahFile.setOnClickListener(this);
    }

    public boolean periksaIzinPenyimpanan() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                jalankanPerintah(selectEvent);
            }
        }
    }

    void buatFile() {
        String isiFile = "Coba Isi Data File Text";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream;

        try {
            successCreated = file.createNewFile();
            if (successCreated) {
                Toast.makeText(this, "File " + FILENAME + " Berhasil Dibuat",
                        Toast.LENGTH_SHORT).show();
            }
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ubahFile() {
        String ubah = "Update Isi Data File Text";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream;

        try {
            if(!file.exists()) {
                Toast.makeText(this, "Tidak ada file yang perlu diedit", Toast.LENGTH_SHORT).show();
                return;
            }
            outputStream = new FileOutputStream(file, false);
            outputStream.write(ubah.getBytes());
            Toast.makeText(this, "Berhasil Mengedit Isi File " + FILENAME, Toast.LENGTH_SHORT).show();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bacaFile() {
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);

        if(file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = br.readLine();

                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            binding.tvBacaFile.setText(text.toString());
        } else {
            Toast.makeText(this, "Tidak ada file ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    void hapusFile() {
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        if (file.exists()) {
            successDeleted = file.delete();
            if (successDeleted) {
                binding.tvBacaFile.setText(R.string.hasil_baca_file);
                Toast.makeText(this, "File " + FILENAME +" Berhasil Dihapus",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Tidak ada file yang harus dihapus",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuatFile:
            case R.id.btnBacaFile:
            case R.id.btnUbahFile:
            case R.id.btnHapusFile:
                if (periksaIzinPenyimpanan()) {
                    selectEvent = v.getId();
                    jalankanPerintah(v.getId());
                }
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void jalankanPerintah(int id) {
        switch (id) {
            case R.id.btnBuatFile:
                buatFile();
                break;
            case R.id.btnBacaFile:
                bacaFile();
                break;
            case R.id.btnUbahFile:
                ubahFile();
                break;
            case R.id.btnHapusFile:
                hapusFile();
                break;
        }
    }
}