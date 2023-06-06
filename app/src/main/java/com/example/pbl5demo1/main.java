package com.example.pbl5demo1;

import static com.example.pbl5demo1.R.id.nav_settings;
import static com.example.pbl5demo1.R.id.swtAlert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pbl5demo1.model.DataSingleton;
import com.example.pbl5demo1.model.Note;
import com.example.pbl5demo1.model.rasp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public Switch swttb1, swttb2, swttb3, swttb4,swtalert;
    public ImageView moretb1, moretb2, moretb3, moretb4;
    public TextView txtnametb1, txtnametb2, txtnametb3, txtnametb4;
    private String emailUser;
    private ValueEventListener postListener;
    private DatabaseReference myRef2;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;
    ArrayList<itemMenu> arrayList;
    MenuAdapter adapter;



    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("devices");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            emailUser = bundle.getString("email");
        }
        // Đăng ký kênh Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Security Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel for Security Notifications");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        //DK menu
        drawerLayout = findViewById(R.id.drawlayout);
//
        navigationView = findViewById(R.id.navigationmenu);
        toolbar = findViewById(R.id.toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        /*-----------------Toolbar------------------*/
        setSupportActionBar(toolbar);
        /*-----------------Navigation------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //Xu ly


        String path2 = emailUser.replace(".", ",");
        //add data singleton
        DataSingleton.getInstance().setData(path2);
        DatabaseReference myRefSer = database.getReference("devices").child(path2);
        myRef2 = database.getReference("devices").child(path2);
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /// Lấy giá trị của nốt
                Log.e("DEBUG", path2);
                String nameTB1 = snapshot.child("name").child("st1").getValue(String.class);
                String nameTB2 = snapshot.child("name").child("st2").getValue(String.class);
                String nameTB3 = snapshot.child("name").child("st3").getValue(String.class);
                String nameTB4 = snapshot.child("name").child("st4").getValue(String.class);
                String alertmode = snapshot.child("alert_mode").getValue(String.class);


                String trangthai1 = snapshot.child("state").child("st1").getValue(String.class);
                String trangthai2 = snapshot.child("state").child("st2").getValue(String.class);
                String trangthai3 = snapshot.child("state").child("st3").getValue(String.class);
                String trangthai4 = snapshot.child("state").child("st4").getValue(String.class);

                txtnametb1.setText(nameTB1);
                txtnametb2.setText(nameTB2);
                txtnametb3.setText(nameTB3);
                txtnametb4.setText(nameTB4);

                checkUpdate(swttb1,trangthai1);
                checkUpdate(swttb2,trangthai2);
                checkUpdate(swttb3,trangthai3);
                checkUpdate(swttb4,trangthai4);
                if(swtalert != null){
                    checkUpdate(swtalert,alertmode );
                    Log.d("DEBUG", "nhan duoc alert");
                }
                else {
                    Log.d("ERROR", "khong nhan duoc alert");
                }

                Log.d("DEBUG", "onDataChange: thay doi");
            }
//android:textOff="Tắt"
//                                        android:textOn="Bật"
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DEBUG", "loadPost:onCancelled", error.toException());
            }
        };

        // Gọi method addValueEventListener để đăng ký listener vào nốt "/users/user1"
        myRef2.addListenerForSingleValueEvent(postListener);

        //thong bao sercurity
        ValueEventListener valueEventListenerSer = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean state_alert = snapshot.child("is_alert").getValue(Boolean.class);
                if (state_alert) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(main.this, "channel_id")
                            .setSmallIcon(R.drawable.baseline_notifications_active_24)
                            .setContentTitle("Security Notification")
                            .setContentText("Something happened, please check.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Something happened, please check the Security for more info."));

// Thêm action cho notification
                    Intent securityIntent = new Intent(main.this, Sercurity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(main.this, 0, securityIntent, 0);
                    builder.addAction(R.drawable.baseline_image_search_24, "More", pendingIntent);

// Hiển thị notification
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(main.this);
                    if (ActivityCompat.checkSelfPermission(main.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    int notificationId = 1;
                    notificationManager.notify(notificationId, builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DEBUG", "ERROR get alert_state", error.toException());
            }
        };
        myRefSer.addValueEventListener(valueEventListenerSer);



        swttb1 = findViewById(R.id.swtButtonTB1);
        swttb2 = findViewById(R.id.swtButtonTB2);
        swttb3 = findViewById(R.id.swtButtonTB3);
        swttb4 = findViewById(R.id.swtButtonTB4);
        swtalert = findViewById(R.id.swtAlert);

        txtnametb1 = findViewById(R.id.nameTB1);
        txtnametb2 = findViewById(R.id.nameTB2);
        txtnametb3 = findViewById(R.id.nameTB3);
        txtnametb4 = findViewById(R.id.nameTB4);

        moretb1 =findViewById(R.id.moreTB1);
        moretb2 =findViewById(R.id.moreTB2);
        moretb3 =findViewById(R.id.moreTB3);
        moretb4 =findViewById(R.id.moreTB4);



        swttb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Nếu switch được bật
                    Log.d("DEBUG", "switch1 được bat");
                    updateData();
                } else {
                    // Nếu switch được tắt
                    Log.d("ERROR", "switch1 k bat");
                    updateData();
                }
            }
        });
        swttb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Nếu switch được bật
                    Log.d("DEBUG", "switch2 được bat");
                    updateData();
                } else {
                    // Nếu switch được tắt
                    Log.d("ERROR", "switch2 k bat");
                    updateData();
                }
            }
        });
        swttb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Nếu switch được bật
                    Log.d("DEBUG", "switch3 được bat");
                    updateData();
                } else {
                    // Nếu switch được tắt
                    Log.d("ERROR", "switch3 k bat");
                    updateData();
                }
            }
        });
        swttb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Nếu switch được bật
                    Log.d("DEBUG", "switch4 được bat");
                    updateData();
                } else {
                    // Nếu switch được tắt
                    Log.d("ERROR", "switch4 k bat");
                    updateData();
                }
            }
        });
        swtalert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Nếu switch được bật
                    Log.d("DEBUG", "switch alert được bat");
                    updateData();
                } else {
                    // Nếu switch được tắt
                    Log.d("DEBUG", "switch alert được tắt");
                    updateData();
                }
            }
        });
        moretb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu =new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit name").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        openSettingNameDialog(txtnametb1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        moretb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu =new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit name").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        openSettingNameDialog(txtnametb2);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        moretb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu =new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit name").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        openSettingNameDialog(txtnametb3);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        moretb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu =new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit name").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        openSettingNameDialog(txtnametb4);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }
    public void openSettingNameDialog(TextView txtname) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.setting_name, null);
        mDialog.setView(dialogView);

        String oldname = txtname.getText().toString().trim();

        Spinner spinnerDecives = dialogView.findViewById(R.id.devices);
        Spinner spinnerNumber = dialogView.findViewById(R.id.number);

        String[] devices_array = getResources().getStringArray(R.array.devices_array);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.devices_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerDecives.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.number_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerNumber.setAdapter(adapter2);
       // spinnerNumber.setSelection(0);

        TextView txtView = dialogView.findViewById(R.id.txt_oldname);
        txtView.setText(oldname);
        Button btnApply = dialogView.findViewById(R.id.btn_applyname);

        AlertDialog alertDialog = mDialog.create();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newname = spinnerDecives.getSelectedItem().toString().trim();
                String numberstr = spinnerNumber.getSelectedItem().toString().trim();
                txtname.setText(newname + " " + numberstr);
                updateData();

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            TextView tvHeaderemail = findViewById(R.id.headerMenuEmailUser);
            tvHeaderemail.setText(emailUser);
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void checkUpdate(Switch swt , String state){
        if(swt.isChecked() && state.equals("off")){
            swt.setChecked(false);
        }
        if(!swt.isChecked() && state.equals("on")){
            swt.setChecked(true);
        }


    }
    private void updateSwith(Switch sw, String trangthai){
        if(trangthai.equals("on")){
            sw.setChecked(true);
            Log.e("debug checked", "on");
        }else {
            sw.setChecked(false);
            Log.e("debug checked", "off");
        }

    }
    private void updateData(){
        String th1 = "off";
        String th2 = "off";
        String th3 = "off";
        String th4 = "off";
        String alert_mode = "off";
        String name1 , name2, name3,name4;
        if(swttb1.isChecked()){
             th1 = "on";
        }
        if(swttb2.isChecked()){
             th2 = "on";
        }
        if(swttb3.isChecked()){
             th3 = "on";
        }
        if(swttb4.isChecked()){
             th4 = "on";
        }
        if(swtalert.isChecked()){
            alert_mode ="on";
        }
//        String id = myRef.push().getKey();
        name1 = txtnametb1.getText().toString().trim();
        name2 = txtnametb2.getText().toString().trim();
        name3 = txtnametb3.getText().toString().trim();
        name4 = txtnametb4.getText().toString().trim();
        String path = emailUser.replace(".",",");
//        myRef.child("key").setValue(path);
        rasp R = new rasp(emailUser,th1,th2,th3,th4,name1,name2,name3,name4,alert_mode);
        Note nName = new Note(R.getNameTB1(),R.getNameTB2(),R.getNameTB3(),R.getNameTB4());
        Note nTT = new Note(R.getTrangthai1(),R.getTrangthai2(),R.getTrangthai3(),R.getTrangthai4());
        Note nAlert = new Note(R.getAlertmode());
        myRef.child(path).child("name").setValue(nName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("DEBUG", "UPDATE NAME OK");

                }else {
                    Log.d("ERROR", "ERROR CHANGE NAME");
                }
            }
        });
        myRef.child(path).child("state").setValue(nTT)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("DEBUG", "UPDATE STATE OK");

                        }else {
                            Log.d("ERROR", "ERROR CHANGE STATE");
                        }
                    }
                });
        myRef.child(path).child("email").setValue(R.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("DEBUG", "UPDATE EMAIL OK");

                        }else {
                            Log.d("ERROR", "ERROR CHANGE EMAIL");
                        }
                    }
                });
        myRef.child(path).child("alert_mode").setValue(R.getAlertmode())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("DEBUG", "UPDATE ALERT MODE OK");

                        }else {
                            Log.d("ERROR", "ERROR CHANGE ALERT MODE");
                        }
                    }
                });
    }


    private static final long UPDATE_INTERVAL = 5 * 1000; // 30 giay
    //Tạo một đối tượng Handler để quản lý tác vụ thực thi:
    private final Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            myRef2.addListenerForSingleValueEvent(postListener);
            mHandler.postDelayed(this, UPDATE_INTERVAL);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mHandler.postDelayed(mRunnable,UPDATE_INTERVAL);

    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_settings:
                Intent i = new Intent(main.this,Sercurity.class);
                startActivity(i);
                break;
            case R.id.nav_logout:
                Intent i2= new Intent(main.this,MainActivity.class);
                startActivity(i2);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}