package com.journal.doorsensor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journal.doorsensor.model.DoorStatus;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView animation;
    TransitionDrawable transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        animation = findViewById(R.id.candado);
        animation.setSpeed(4.0f);
        transition = (TransitionDrawable) findViewById(R.id.background).getBackground();
        transition.reverseTransition(250);
        getWindow().setBackgroundDrawableResource(R.drawable.gris);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/Door");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoorStatus status = snapshot.getValue(DoorStatus.class);
                if(status.Open == 1){
                    abrirPuerta();
                }else{
                    cerrarPuerta();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void abrirPuerta(){
        transition.reverseTransition(250);
        if(animation.isAnimating()){
            animation.cancelAnimation();
        }
        animation.reverseAnimationSpeed();
        animation.playAnimation();
    }

    private void cerrarPuerta(){
        transition.startTransition(250);
        if(animation.isAnimating()){
            animation.cancelAnimation();
        }
        animation.reverseAnimationSpeed();
        animation.playAnimation();
    }





}