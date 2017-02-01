package com.example.sisyphus.leagueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void startEquip(View view){
        Intent intent = new Intent(this, Equip_Menu.class);
        intent.putExtra("ECJSON", "https://na.api.pvp.net/api/lol/na/v1.2/champion?api_key=RGAPI-45bb9777-4159-4a35-b6df-26d25d0376e2");
        startActivity(intent);
    }
}
