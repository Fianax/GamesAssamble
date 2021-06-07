package com.example.gamesassamble.sopaletras;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import com.example.gamesassamble.R;

public class SopaActivity extends AppCompatActivity {

    ArrayList<String> palabras;
    public TextView[][] tablero;
    String[][] p;
    GridView grid;
    String inicio="",fin="";
    SopaLetra sopaLetra;
    AlertDialog.Builder alert;
    TextView atras,nuevo,marcarInicio,marcarFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        alert = new AlertDialog.Builder(this);
        sopaLetra= new SopaLetra();
        atras=findViewById(R.id.tv_sopaatras);
        nuevo=findViewById(R.id.tv_sopanuevo);
        marcarInicio=new TextView(this);
        marcarFin=new TextView(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopa);
        //
        tablero=new TextView[7][7];
        palabras=new ArrayList<String>();
        grid=findViewById(R.id.gridView);
        /*palabras.add("LETRAS");
        palabras.add("ASALTO");
        palabras.add("ADIOS");
        palabras.add("PAPEL");
        palabras.add("AZUL");
        palabras.add("TRES");
        palabras.add("HOLA");
        palabras.add("DIA");*/
        listaPalabras();
        grid.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,palabras));
        //obtenemos la referencia
        tableroRef();
        //creamos la sopa y la solucion
        p=sopaLetra.crearSopa(palabras);
        //rellenamos el tablero
        rellenar();
    }


    public void rellenar(){
        for(int i=0;i<tablero.length;i++)
            for(int j=0;j<tablero[i].length;j++)
                tablero[i][j].setText(p[i][j]);
    }



    //metodo para coger las palabras de forma aleatoria
    public void listaPalabras(){
        int pos=0,i=0;
        ArrayList<Integer> repetidas=new ArrayList<Integer>();
        Random r=new Random();
        InputStream in = null;
        try {
            //vamos a ir añadiendo las palabras
            //2 de 6 (101-145)
            while(i<2){
                if(Locale.getDefault().getLanguage().equals("es"))
                    in=getResources().openRawResource(R.raw.spanish);
                else
                    in=getResources().openRawResource(R.raw.english);
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                pos=101+r.nextInt((145+1)-101);
                if(!repetidas.contains(pos)) {
                    for (int j = 0; j < pos - 1; j++) {
                        reader.readLine();
                    }
                    palabras.add(reader.readLine().toUpperCase());
                    repetidas.add(pos);
                    i++;
                }
                reader.close();
            }
            //2 de 5 (52-100)
            i=0;
            while(i<2){
                if(Locale.getDefault().getLanguage().equals("es"))
                    in=getResources().openRawResource(R.raw.spanish);
                else
                    in=getResources().openRawResource(R.raw.english);
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                pos=52+r.nextInt((100+1)-52);
                if(!repetidas.contains(pos)) {
                    for (int j = 0; j < pos - 1; j++) {
                        reader.readLine();
                    }
                    palabras.add(reader.readLine().toUpperCase());
                    repetidas.add(pos);
                    i++;
                }
                reader.close();
            }
            //3 de 4 (24-51)
            i=0;
            while(i<3){
                if(Locale.getDefault().getLanguage().equals("es"))
                    in=getResources().openRawResource(R.raw.spanish);
                else
                    in=getResources().openRawResource(R.raw.english);
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                pos=24+r.nextInt((51+1)-24);
                if(!repetidas.contains(pos)) {
                    for (int j = 0; j < pos - 1; j++) {
                        reader.readLine();
                    }
                    palabras.add(reader.readLine().toUpperCase());
                    repetidas.add(pos);
                    i++;
                }
                reader.close();
            }
            //2 de 3 (1-23)
            i=0;
            while(i<2){
                if(Locale.getDefault().getLanguage().equals("es"))
                    in=getResources().openRawResource(R.raw.spanish);
                else
                    in=getResources().openRawResource(R.raw.english);
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                pos=1+r.nextInt((23+1)-1);
                if(!repetidas.contains(pos)) {
                    for (int j = 0; j < pos - 1; j++) {
                        reader.readLine();
                    }
                    palabras.add(reader.readLine().toUpperCase());
                    repetidas.add(pos);
                    i++;
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //guardar la casillas que hayamos marcado
    public void marcar(View view) {
        String palabra="";
        if(inicio.equals("")) {
            inicio=casillaPos(view.getId());
            marcarInicio=(TextView)view;
            marcarInicio.setBackgroundColor(Color.parseColor("#9ef147"));
        }else{
            fin=casillaPos(view.getId());
            marcarFin=(TextView)view;
            palabra=comprobar();
            if(!palabra.equals("")){
                palabras.remove(palabra);
                grid.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,palabras));
                isGanado();
            }else
                marcarInicio.setBackgroundColor(Color.parseColor("#00000000"));
            inicio="";
            fin="";
        }
    }

    //marca la palabra de verde
    public void colorear(int orientacion,int tamPalabra){
        int fila=Integer.parseInt(inicio.charAt(0)+"");
        int columna=Integer.parseInt(inicio.charAt(2)+"");
        //System.out.println(fila+"     "+columna+"     "+orientacion+"     "+tamPalabra);
        if (orientacion == 0) {
            for (int x = 1; x < tamPalabra; x++) {
                fila--;
                columna--;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        } else if (orientacion == 1) {
            for (int x = 1; x < tamPalabra; x++) {
                fila--;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        } else if (orientacion == 2) {
            for (int x = 1; x < tamPalabra; x++) {
                fila--;
                columna++;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        } else if (orientacion == 3) {
            for (int x = 1; x < tamPalabra; x++) {
                columna--;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        } else if (orientacion == 4) {
            for (int x = 1; x < tamPalabra; x++) {
                columna++;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        } else if (orientacion == 5) {
            for (int x = 1; x < tamPalabra; x++) {
                fila++;
                columna--;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        } else if (orientacion == 6) {
            for (int x = 1; x < tamPalabra; x++) {
                fila++;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        } else if (orientacion == 7) {
            for (int x = 1; x < tamPalabra; x++) {
                fila++;
                columna++;
                tablero[fila][columna].setBackgroundColor(Color.parseColor("#9ef147"));
            }
        }
    }


    //metodo para obteenr la posicon de una casilla
    public String casillaPos(int id){
        String inicio="";

        for(int i=0;i<tablero.length;i++){
            for (int j=0;j<tablero[i].length;j++){
                if(tablero[i][j].getId()==id)
                    inicio=i+"-"+j;
            }
        }

        return inicio;
    }

    //metodo para comprobar la palabra
    public String comprobar(){
        String palabra="",aux="";
        String[] datos,borrar = new String[3];
        int j=0;
        boolean salir=false;

        for(int i=0;i<sopaLetra.solucion.size();i++){
            datos=sopaLetra.solucion.get(i);
            j=0;
            salir=false;
            while(j<datos.length && !salir){
                if((datos[1].equals(inicio) || datos[2].equals(inicio)) && (datos[1].equals(fin) || datos[2].equals(fin))){
                    if(datos[2].equals(inicio)){//ponemos en inicio el inicio de la palabra para poder pintarla mas facil
                        aux=inicio;
                        inicio=fin;
                        fin=aux;
                        marcarFin.setBackgroundColor(Color.parseColor("#9ef147"));
                    }else if(!inicio.equals(fin)){
                        System.out.println(inicio+"    "+fin);
                        palabra=datos[0];
                        System.out.println(palabra);
                        salir=true;
                        borrar=datos;
                        colorear(Integer.parseInt(datos[3]),palabra.length());
                        musica(0);
                    }else
                        marcarInicio.setBackgroundColor(Color.parseColor("#00000000"));
                }
                j++;
            }
        }
        sopaLetra.solucion.remove(borrar);

        return palabra;
    }

    //para combrobar si has ganado
    public void isGanado(){
        if(palabras.size()==0){
            Toast.makeText(this, getResources().getString(R.string.sopa_generando), Toast.LENGTH_SHORT).show();
            musica(1);
            recreate();
            new CountDownTimer(5000,1000){//generamos un contador de espera, por si tardara en crearse

                @Override
                public void onTick(long millisUntilFinished) {
                    System.out.println(millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
    }

    public void nuevaPartida(View view){
        Toast.makeText(this, getResources().getString(R.string.sopa_generando), Toast.LENGTH_SHORT).show();
        this.recreate();
    }

    //para controlar el boton back
    @Override
    public void onBackPressed() {
        crearAlert();
    }
    public void atras(View view){
        crearAlert();
    }

    public void crearAlert(){
        alert.setMessage(getResources().getString(R.string.alert_msg));
        alert.setTitle(getResources().getString(R.string.alert_titulo));
        alert.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.alert_cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //no hacemos nada
            }
        });

        AlertDialog dialog= alert.create();
        dialog.show();
    }

    //metodo para sonar un sonido
    public void musica(int id){
        MediaPlayer mediaPlayer;
        switch (id){
            case 0://acierto de palabra
                mediaPlayer = MediaPlayer.create(this, R.raw.acierto);
                mediaPlayer.start();
                break;

            case 1://ganador
                mediaPlayer = MediaPlayer.create(this, R.raw.ganador);
                mediaPlayer.start();
                break;
        }
    }


    public void tableroRef() {
        //primera fila
        this.tablero[0][0]=findViewById(R.id.tv_sopa_00);
        this.tablero[0][1]=findViewById(R.id.tv_sopa_01);
        this.tablero[0][2]=findViewById(R.id.tv_sopa_02);
        this.tablero[0][3]=findViewById(R.id.tv_sopa_03);
        this.tablero[0][4]=findViewById(R.id.tv_sopa_04);
        this.tablero[0][5]=findViewById(R.id.tv_sopa_05);
        this.tablero[0][6]=findViewById(R.id.tv_sopa_06);
        //segunda fila
        this.tablero[1][0]=findViewById(R.id.tv_sopa_10);
        this.tablero[1][1]=findViewById(R.id.tv_sopa_11);
        this.tablero[1][2]=findViewById(R.id.tv_sopa_12);
        this.tablero[1][3]=findViewById(R.id.tv_sopa_13);
        this.tablero[1][4]=findViewById(R.id.tv_sopa_14);
        this.tablero[1][5]=findViewById(R.id.tv_sopa_15);
        this.tablero[1][6]=findViewById(R.id.tv_sopa_16);
        //tercera fila
        this.tablero[2][0]=findViewById(R.id.tv_sopa_20);
        this.tablero[2][1]=findViewById(R.id.tv_sopa_21);
        this.tablero[2][2]=findViewById(R.id.tv_sopa_22);
        this.tablero[2][3]=findViewById(R.id.tv_sopa_23);
        this.tablero[2][4]=findViewById(R.id.tv_sopa_24);
        this.tablero[2][5]=findViewById(R.id.tv_sopa_25);
        this.tablero[2][6]=findViewById(R.id.tv_sopa_26);
        //cuarta fila
        this.tablero[3][0]=findViewById(R.id.tv_sopa_30);
        this.tablero[3][1]=findViewById(R.id.tv_sopa_31);
        this.tablero[3][2]=findViewById(R.id.tv_sopa_32);
        this.tablero[3][3]=findViewById(R.id.tv_sopa_33);
        this.tablero[3][4]=findViewById(R.id.tv_sopa_34);
        this.tablero[3][5]=findViewById(R.id.tv_sopa_35);
        this.tablero[3][6]=findViewById(R.id.tv_sopa_36);
        //quinta fila
        this.tablero[4][0]=findViewById(R.id.tv_sopa_40);
        this.tablero[4][1]=findViewById(R.id.tv_sopa_41);
        this.tablero[4][2]=findViewById(R.id.tv_sopa_42);
        this.tablero[4][3]=findViewById(R.id.tv_sopa_43);
        this.tablero[4][4]=findViewById(R.id.tv_sopa_44);
        this.tablero[4][5]=findViewById(R.id.tv_sopa_45);
        this.tablero[4][6]=findViewById(R.id.tv_sopa_46);
        //sexta fila
        this.tablero[5][0]=findViewById(R.id.tv_sopa_50);
        this.tablero[5][1]=findViewById(R.id.tv_sopa_51);
        this.tablero[5][2]=findViewById(R.id.tv_sopa_52);
        this.tablero[5][3]=findViewById(R.id.tv_sopa_53);
        this.tablero[5][4]=findViewById(R.id.tv_sopa_54);
        this.tablero[5][5]=findViewById(R.id.tv_sopa_55);
        this.tablero[5][6]=findViewById(R.id.tv_sopa_56);
        //septima fila
        this.tablero[6][0]=findViewById(R.id.tv_sopa_60);
        this.tablero[6][1]=findViewById(R.id.tv_sopa_61);
        this.tablero[6][2]=findViewById(R.id.tv_sopa_62);
        this.tablero[6][3]=findViewById(R.id.tv_sopa_63);
        this.tablero[6][4]=findViewById(R.id.tv_sopa_64);
        this.tablero[6][5]=findViewById(R.id.tv_sopa_65);
        this.tablero[6][6]=findViewById(R.id.tv_sopa_66);
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