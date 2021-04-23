package com.msh.kotlincoroutines;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * @author : 马世豪
 * time : 3/31/21 13
 * email : ma_shihao@yeah.net
 * des :
 */
class Test extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Toast.makeText(this, "fs", Toast.LENGTH_SHORT).show();
    }
}
