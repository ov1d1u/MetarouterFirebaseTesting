package net.softadept.metarouterfirebasetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.segment.analytics.Analytics;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
//        final FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Analytics.with(MainActivity.this).track("Window Opened");
                Analytics.with(MainActivity.this).flush();

//                Bundle bundle = new Bundle();
//                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "button_a");
//                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Button A");
//                firebaseAnalytics.logEvent("button_a", bundle);

                Log.d("MainActivity", "Flushed analytics");
            }
        });
    }
}
