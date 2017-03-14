package com.cowge.anim.bloodview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BloodView mBloodView = (BloodView) findViewById(R.id.bloodview_pking);
        mBloodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBloodView.startAnim(0.97f, 3000);
            }
        });
    }
}
