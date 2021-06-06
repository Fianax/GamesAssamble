package com.example.gamesassamble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamesassamble.sopaletras.SopaActivity;
import com.example.gamesassamble.sudoku.SudokuActivity;

public class MainActivity extends AppCompatActivity {

    public TextView sopa,sudoku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sopa=findViewById(R.id.tv_sopaEmpezar);
        sudoku=findViewById(R.id.tv_sudokuEmpezar);
    }



    //metodo para viajar a cada modo o juego
    public void jugar(View view){

        Intent i;

        if(view.getId()==sopa.getId()){
            i=new Intent(this, SopaActivity.class);
            startActivity(i);

        }else if(view.getId()==sudoku.getId()){
            i=new Intent(this, SudokuActivity.class);
            startActivity(i);
        }
    }

    //metodo para ir al repositorio de git
    public void gitHub(View view){
        String url="https://github.com/Fianax/GamesAssamble";
        Uri uri=Uri.parse(url);
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    //metodo para enlazar la pagina de google play
    public void googlePlay(View view){
        String url="http://play.google.com/store/apps/details?id=com.google.android.apps.maps";
        Uri uri=Uri.parse(url);
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }


    //mostrar un toast con la informacion del creador del modulo
    public void infoCreador(View view){
        Toast.makeText(this, "Creador: FIANAX", Toast.LENGTH_SHORT).show();
    }


    //Para obtener toda la pantalla
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}