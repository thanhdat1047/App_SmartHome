package com.example.pbl5demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pbl5demo1.model.DataSingleton;
import com.example.pbl5demo1.model.DownloadImageTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.pm.ActivityInfo;

public class Sercurity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRefSer;

    private StorageReference storageRef ;

    Button btnNext ;
    private  String emailUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sercurity);
        database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("images/person.jpg");
//Tai anh
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                ImageView imageView = findViewById(R.id.image_view);
               // new DownloadImageTask(imageView).execute(imageURL);
                if (!isDestroyed()) {
                    Glide.with(Sercurity.this).load(imageURL).into(imageView);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Sercurity.this,"Không thể tải ảnh xuống",Toast.LENGTH_SHORT).show();
            }
        });

        emailUser = DataSingleton.getInstance().getData();
        if(emailUser.isEmpty()){
            Log.e("COOK",emailUser);
        }
        else {
            Log.e("GET Singleton",emailUser);
        }


        btnNext = findViewById(R.id.btn_boqua);

//        String path2 = emailUser.replace(".", ",");
        // Tham chiếu đến node cần thay đổi giá trị
        DatabaseReference myRef = database.getReference("devices/"+emailUser+"/is_alert");
        Log.e("DEBUGemail",emailUser);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.setValue(false)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.e("DEBUG", "Bỏ qua cảnh báo");
                                    Toast.makeText(Sercurity.this,"Bỏ qua cảnh báo", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Log.e("DEBUG", "Lôi Bỏ qua");
                                    Toast.makeText(Sercurity.this,"Lổi bỏ qua cảnh báo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                Intent i = new Intent(Sercurity.this,main.class);
                Bundle bundle = new Bundle();
                String emailUser1  = emailUser.replace(",",".");
                bundle.putString("email",emailUser1);
                i.putExtras(bundle);
                startActivity(i);
                }


        });





    }
}
