package com.example.gamesassamble.sudoku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamesassamble.R;

import java.util.ArrayList;
import java.util.Random;

public class SudokuActivity extends AppCompatActivity {

    public String datoSelecionado;
    public ArrayList<String[][]> atras;

    public TextView[][] tablero;
    public TextView[] numeros;
    public TextView aux;
    public String[][] sudoku;
    public AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        alert = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        datoSelecionado="0";
        tablero=new TextView[9][9];
        numeros=new TextView[9];//donde guardaremos las referencias a los numeros
        aux=new TextView(this);
        sudoku=Sudoku.crearSudoku();//la solucion
        atras=new ArrayList<>();

        numerosRef();//ya teemos los numeros referenciados
        tableroRef();//ya tenemos el tablero referenciado

        //le damos los valores del sudoku creado
        setTablero(sudoku);
        for(int i=0;i<sudoku.length;i++){
            System.out.println();
            for(int j=0;j<sudoku[i].length;j++){
                System.out.print(sudoku[i][j]+"-");
            }
        }
    }


    //metodo para comprobar la solucion
    public void comprobar(View view){
        aux.setTypeface(Typeface.DEFAULT);
        int i=0,j;
        boolean resuelto=true;

        //miramos cada casilla haber si concuerda, y sino, nos salimos
        do{
            j=0;
            do {
                if(!tablero[i][j].getText().equals(sudoku[i][j])) {
                    resuelto = false;
                }
                j++;
            }while(j<9 && resuelto);
            i++;
        }while(i<9 && resuelto);

        if(resuelto){
            //lo que haya que hacer
            MediaPlayer mediaPlayer= MediaPlayer.create(this, R.raw.ganador);
            mediaPlayer.start();
            Toast.makeText(this, getResources().getString(R.string.completado), Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, getResources().getString(R.string.fallo), Toast.LENGTH_SHORT).show();
    }

    //metodo para borrarTodo
    public void borrarTodo(View view){
        aux.setTypeface(Typeface.DEFAULT);
        String vacio="";
        for (int i=0;i<tablero.length;i++)
            for(int j=0;j<tablero[i].length;j++)
                if(tablero[i][j].isEnabled())
                    tablero[i][j].setText(vacio);
    }

    //metodo para obtener el borrar
    public void borarUno(View view){
        aux.setTypeface(Typeface.DEFAULT);
        datoSelecionado="";//para borrar
    }

    //metodo para obtener el numero de los TextView con los datos
    public void getValue(View view){
        TextView tv=(TextView)view;
        int id = view.getId();
        aux.setTypeface(Typeface.DEFAULT);
        if (id == R.id.tv_1) {
            datoSelecionado = (String) numeros[0].getText();
        } else if (id == R.id.tv_2) {
            datoSelecionado = (String) numeros[1].getText();
        } else if (id == R.id.tv_3) {
            datoSelecionado = (String) numeros[2].getText();
        } else if (id == R.id.tv_4) {
            datoSelecionado = (String) numeros[3].getText();
        } else if (id == R.id.tv_5) {
            datoSelecionado = (String) numeros[4].getText();
        } else if (id == R.id.tv_6) {
            datoSelecionado = (String) numeros[5].getText();
        } else if (id == R.id.tv_7) {
            datoSelecionado = (String) numeros[6].getText();
        } else if (id == R.id.tv_8) {
            datoSelecionado = (String) numeros[7].getText();
        } else if (id == R.id.tv_9) {
            datoSelecionado = (String) numeros[8].getText();
        }
        //para resaltar el numero que has seleccionado
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        aux=tv;
    }

    //metodo para obtener una instantanea de los datos del tablero
    public String[][] instantanea(){
        String[][] ins=new String[9][9];

        for(int i=0;i<tablero.length;i++)
            for(int j=0;j<tablero[i].length;j++)
                ins[i][j]= (String) tablero[i][j].getText();

        return ins;
    }

    //metodo para darle los valores al tablero referenciado
    public void setTablero(String[][] sudoku){
        //Rellena 20-24 casillas en funcion del sudoku creado
        int i,j,a;
        Random random=new Random();
        boolean salir;

        a=25+random.nextInt((31+1)-25);//entre 25-31
        for(int b=0;b<a;b++){
            salir=false;
            do{
                i=random.nextInt(9);//entre 0-8
                j=random.nextInt(9);//entre 0-8
                if(tablero[i][j].getText().equals("")){
                    salir=true;
                    tablero[i][j].setText(sudoku[i][j]);//coloca de forma aleatoria un valor
                    tablero[i][j].setEnabled(false);
                }
            }while(!salir);
        }

    }

    //metodo atras para volver a un movimiento anterior
    public void atrasInstantanea(View view){
        //Toast.makeText(this, String.valueOf(atras.size()), Toast.LENGTH_SHORT).show();
        //aux.setTypeface(Typeface.DEFAULT);
        if(atras.size()>0){
            volverPasado(atras.get(atras.size()-1));
            atras.remove(atras.size()-1);
        }else{
            atras.clear();//para limpiarla de datos residuales, el ultimo en concreto
        }
    }

    //metodo para volver un paso hacia atras, simlo hubiere
    public void volverPasado(String[][] ins){
        for(int i=0;i<tablero.length;i++)
            for(int j=0;j<tablero[i].length;j++)
                tablero[i][j].setText(ins[i][j]);
    }

    public void nuevaPartida(View view){
        Toast.makeText(this, getResources().getString(R.string.sudoku_generando), Toast.LENGTH_SHORT).show();
        this.recreate();
    }

    //metodo para darle el valor selecionado a la ficha del tablero
    public void setValue(View view){
        int id = view.getId();
        //fila 1
        if (id == R.id.tv_11) {
            if (!datoSelecionado.equals("0") && tablero[0][0].isEnabled() && !datoSelecionado.contentEquals(tablero[0][0].getText())) {
                tablero[0][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_12) {
            if (!datoSelecionado.equals("0") && tablero[0][1].isEnabled() && !datoSelecionado.contentEquals(tablero[0][1].getText())) {
                tablero[0][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_13) {
            if (!datoSelecionado.equals("0") && tablero[0][2].isEnabled() && !datoSelecionado.contentEquals(tablero[0][2].getText())) {
                tablero[0][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_14) {
            if (!datoSelecionado.equals("0") && tablero[0][3].isEnabled() && !datoSelecionado.contentEquals(tablero[0][3].getText())) {
                tablero[0][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_15) {
            if (!datoSelecionado.equals("0") && tablero[0][4].isEnabled() && !datoSelecionado.contentEquals(tablero[0][4].getText())) {
                tablero[0][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_16) {
            if (!datoSelecionado.equals("0") && tablero[0][5].isEnabled() && !datoSelecionado.contentEquals(tablero[0][5].getText())) {
                tablero[0][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_17) {
            if (!datoSelecionado.equals("0") && tablero[0][6].isEnabled() && !datoSelecionado.contentEquals(tablero[0][6].getText())) {
                tablero[0][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_18) {
            if (!datoSelecionado.equals("0") && tablero[0][7].isEnabled() && !datoSelecionado.contentEquals(tablero[0][7].getText())) {
                tablero[0][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_19) {
            if (!datoSelecionado.equals("0") && tablero[0][8].isEnabled() && !datoSelecionado.contentEquals(tablero[0][8].getText())) {
                tablero[0][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 2
        } else if (id == R.id.tv_21) {
            if (!datoSelecionado.equals("0") && tablero[1][0].isEnabled() && !datoSelecionado.contentEquals(tablero[1][0].getText())) {
                tablero[1][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_22) {
            if (!datoSelecionado.equals("0") && tablero[1][1].isEnabled() && !datoSelecionado.contentEquals(tablero[1][1].getText())) {
                tablero[1][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_23) {
            if (!datoSelecionado.equals("0") && tablero[1][2].isEnabled() && !datoSelecionado.contentEquals(tablero[1][2].getText())) {
                tablero[1][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_24) {
            if (!datoSelecionado.equals("0") && tablero[1][3].isEnabled() && !datoSelecionado.contentEquals(tablero[1][3].getText())) {
                tablero[1][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_25) {
            if (!datoSelecionado.equals("0") && tablero[1][4].isEnabled() && !datoSelecionado.contentEquals(tablero[1][4].getText())) {
                tablero[1][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_26) {
            if (!datoSelecionado.equals("0") && tablero[1][5].isEnabled() && !datoSelecionado.contentEquals(tablero[1][5].getText())) {
                tablero[1][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_27) {
            if (!datoSelecionado.equals("0")  && tablero[1][6].isEnabled() && !datoSelecionado.contentEquals(tablero[1][6].getText())) {
                tablero[1][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_28) {
            if (!datoSelecionado.equals("0") && tablero[1][7].isEnabled() && !datoSelecionado.contentEquals(tablero[1][7].getText())) {
                tablero[1][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_29) {
            if (!datoSelecionado.equals("0") && tablero[1][8].isEnabled() && !datoSelecionado.contentEquals(tablero[1][8].getText())) {
                tablero[1][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 3
        } else if (id == R.id.tv_31) {
            if (!datoSelecionado.equals("0") && tablero[2][0].isEnabled() && !datoSelecionado.contentEquals(tablero[2][0].getText())) {
                tablero[2][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_32) {
            if (!datoSelecionado.equals("0") && tablero[2][1].isEnabled() && !datoSelecionado.contentEquals(tablero[2][1].getText())) {
                tablero[2][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_33) {
            if (!datoSelecionado.equals("0") && tablero[2][2].isEnabled() && !datoSelecionado.contentEquals(tablero[2][2].getText())) {
                tablero[2][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_34) {
            if (!datoSelecionado.equals("0") && tablero[2][3].isEnabled() && !datoSelecionado.contentEquals(tablero[2][3].getText())) {
                tablero[2][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_35) {
            if (!datoSelecionado.equals("0") && tablero[2][4].isEnabled() && !datoSelecionado.contentEquals(tablero[2][4].getText())) {
                tablero[2][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_36) {
            if (!datoSelecionado.equals("0") && tablero[2][5].isEnabled() && !datoSelecionado.contentEquals(tablero[2][5].getText())) {
                tablero[2][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_37) {
            if (!datoSelecionado.equals("0") && tablero[2][6].isEnabled() && !datoSelecionado.contentEquals(tablero[2][6].getText())) {
                tablero[2][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_38) {
            if (!datoSelecionado.equals("0") && tablero[2][7].isEnabled() && !datoSelecionado.contentEquals(tablero[2][7].getText())) {
                tablero[2][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_39) {
            if (!datoSelecionado.equals("0") && tablero[2][8].isEnabled() && !datoSelecionado.contentEquals(tablero[2][8].getText())) {
                tablero[2][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 4
        } else if (id == R.id.tv_41) {
            if (!datoSelecionado.equals("0") && tablero[3][0].isEnabled() && !datoSelecionado.contentEquals(tablero[2][8].getText())) {
                tablero[3][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_42) {
            if (!datoSelecionado.equals("0") && tablero[3][1].isEnabled() && !datoSelecionado.contentEquals(tablero[3][1].getText())) {
                tablero[3][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_43) {
            if (!datoSelecionado.equals("0") && tablero[3][2].isEnabled() && !datoSelecionado.contentEquals(tablero[3][2].getText())) {
                tablero[3][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_44) {
            if (!datoSelecionado.equals("0") && tablero[3][3].isEnabled() && !datoSelecionado.contentEquals(tablero[3][3].getText())) {
                tablero[3][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_45) {
            if (!datoSelecionado.equals("0") && tablero[3][4].isEnabled() && !datoSelecionado.contentEquals(tablero[3][4].getText())) {
                tablero[3][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_46) {
            if (!datoSelecionado.equals("0") && tablero[3][5].isEnabled() && !datoSelecionado.contentEquals(tablero[3][5].getText())) {
                tablero[3][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_47) {
            if (!datoSelecionado.equals("0") && tablero[3][6].isEnabled() && !datoSelecionado.contentEquals(tablero[3][6].getText())) {
                tablero[3][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_48) {
            if (!datoSelecionado.equals("0") && tablero[3][7].isEnabled() && !datoSelecionado.contentEquals(tablero[3][7].getText())) {
                tablero[3][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_49) {
            if (!datoSelecionado.equals("0") && tablero[3][8].isEnabled() && !datoSelecionado.contentEquals(tablero[3][8].getText())) {
                tablero[3][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 5
        } else if (id == R.id.tv_51) {
            if (!datoSelecionado.equals("0") && tablero[4][0].isEnabled() && !datoSelecionado.contentEquals(tablero[4][0].getText())){
                tablero[4][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_52) {
            if (!datoSelecionado.equals("0") && tablero[4][1].isEnabled() && !datoSelecionado.contentEquals(tablero[4][1].getText())){
                tablero[4][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_53) {
            if (!datoSelecionado.equals("0") && tablero[4][2].isEnabled() && !datoSelecionado.contentEquals(tablero[4][2].getText())){
                tablero[4][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_54) {
            if (!datoSelecionado.equals("0") && tablero[4][3].isEnabled() && !datoSelecionado.contentEquals(tablero[4][3].getText())){
                tablero[4][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_55) {
            if (!datoSelecionado.equals("0") && tablero[4][4].isEnabled() && !datoSelecionado.contentEquals(tablero[4][4].getText())){
                tablero[4][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_56) {
            if (!datoSelecionado.equals("0") && tablero[4][5].isEnabled() && !datoSelecionado.contentEquals(tablero[4][5].getText())){
                tablero[4][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_57) {
            if (!datoSelecionado.equals("0") && tablero[4][6].isEnabled() && !datoSelecionado.contentEquals(tablero[4][6].getText())){
                tablero[4][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_58) {
            if (!datoSelecionado.equals("0") && tablero[4][7].isEnabled() && !datoSelecionado.contentEquals(tablero[4][7].getText())){
                tablero[4][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_59) {
            if (!datoSelecionado.equals("0") && tablero[4][8].isEnabled() && !datoSelecionado.contentEquals(tablero[4][8].getText())){
                tablero[4][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 6
        } else if (id == R.id.tv_61 ) {
            if (!datoSelecionado.equals("0") && tablero[5][0].isEnabled() && !datoSelecionado.contentEquals(tablero[5][0].getText())){
                tablero[5][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_62) {
            if (!datoSelecionado.equals("0") && tablero[5][1].isEnabled() && !datoSelecionado.contentEquals(tablero[5][1].getText())){
                tablero[5][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_63) {
            if (!datoSelecionado.equals("0") && tablero[5][2].isEnabled() && !datoSelecionado.contentEquals(tablero[5][2].getText())){
                tablero[5][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_64) {
            if (!datoSelecionado.equals("0") && tablero[5][3].isEnabled() && !datoSelecionado.contentEquals(tablero[5][3].getText())){
                tablero[5][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_65) {
            if (!datoSelecionado.equals("0") && tablero[5][4].isEnabled() && !datoSelecionado.contentEquals(tablero[5][4].getText())){
                tablero[5][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_66) {
            if (!datoSelecionado.equals("0") && tablero[5][5].isEnabled() && !datoSelecionado.contentEquals(tablero[5][5].getText())){
                tablero[5][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_67) {
            if (!datoSelecionado.equals("0") && tablero[5][6].isEnabled() && !datoSelecionado.contentEquals(tablero[5][6].getText())){
                tablero[5][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_68) {
            if (!datoSelecionado.equals("0") && tablero[5][7].isEnabled() && !datoSelecionado.contentEquals(tablero[5][7].getText())){
                tablero[5][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_69) {
            if (!datoSelecionado.equals("0") && tablero[5][8].isEnabled() && !datoSelecionado.contentEquals(tablero[5][8].getText())){
                tablero[5][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 7
        } else if (id == R.id.tv_71) {
            if (!datoSelecionado.equals("0") && tablero[6][0].isEnabled() && !datoSelecionado.contentEquals(tablero[6][0].getText())){
                tablero[6][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_72) {
            if (!datoSelecionado.equals("0") && tablero[6][1].isEnabled() && !datoSelecionado.contentEquals(tablero[6][1].getText())){
                tablero[6][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_73) {
            if (!datoSelecionado.equals("0") && tablero[6][2].isEnabled() && !datoSelecionado.contentEquals(tablero[6][2].getText())){
                tablero[6][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_74) {
            if (!datoSelecionado.equals("0") && tablero[6][3].isEnabled() && !datoSelecionado.contentEquals(tablero[6][3].getText())){
                tablero[6][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_75) {
            if (!datoSelecionado.equals("0") && tablero[6][4].isEnabled() && !datoSelecionado.contentEquals(tablero[6][4].getText())){
                tablero[6][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_76) {
            if (!datoSelecionado.equals("0") && tablero[6][5].isEnabled() && !datoSelecionado.contentEquals(tablero[6][5].getText())){
                tablero[6][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_77) {
            if (!datoSelecionado.equals("0") && tablero[6][6].isEnabled() && !datoSelecionado.contentEquals(tablero[6][6].getText())){
                tablero[6][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_78) {
            if (!datoSelecionado.equals("0") && tablero[6][7].isEnabled() && !datoSelecionado.contentEquals(tablero[6][7].getText())){
                tablero[6][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_79) {
            if (!datoSelecionado.equals("0") && tablero[6][8].isEnabled() && !datoSelecionado.contentEquals(tablero[6][8].getText())){
                tablero[6][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 8
        } else if (id == R.id.tv_81) {
            if (!datoSelecionado.equals("0") && tablero[7][0].isEnabled() && !datoSelecionado.contentEquals(tablero[7][0].getText())){
                tablero[7][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_82) {
            if (!datoSelecionado.equals("0") && tablero[7][1].isEnabled() && !datoSelecionado.contentEquals(tablero[7][1].getText())){
                tablero[7][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_83) {
            if (!datoSelecionado.equals("0") && tablero[7][2].isEnabled() && !datoSelecionado.contentEquals(tablero[7][2].getText())){
                tablero[7][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_84) {
            if (!datoSelecionado.equals("0") && tablero[7][3].isEnabled() && !datoSelecionado.contentEquals(tablero[7][3].getText())){
                tablero[7][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_85) {
            if (!datoSelecionado.equals("0") && tablero[7][4].isEnabled() && !datoSelecionado.contentEquals(tablero[7][4].getText())){
                tablero[7][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_86) {
            if (!datoSelecionado.equals("0") && tablero[7][5].isEnabled() && !datoSelecionado.contentEquals(tablero[7][5].getText())){
                tablero[7][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_87) {
            if (!datoSelecionado.equals("0") && tablero[7][6].isEnabled() && !datoSelecionado.contentEquals(tablero[7][6].getText())){
                tablero[7][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_88) {
            if (!datoSelecionado.equals("0") && tablero[7][7].isEnabled() && !datoSelecionado.contentEquals(tablero[7][7].getText())){
                tablero[7][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_89) {
            if (!datoSelecionado.equals("0") && tablero[7][8].isEnabled() && !datoSelecionado.contentEquals(tablero[7][8].getText())){
                tablero[7][8].setText(datoSelecionado);
                atras.add(instantanea());
            }

            //fila 9
        } else if (id == R.id.tv_91) {
            if (!datoSelecionado.equals("0") && tablero[8][0].isEnabled() && !datoSelecionado.contentEquals(tablero[8][0].getText())){
                tablero[8][0].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_92) {
            if (!datoSelecionado.equals("0") && tablero[8][1].isEnabled() && !datoSelecionado.contentEquals(tablero[8][1].getText())){
                tablero[8][1].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_93) {
            if (!datoSelecionado.equals("0") && tablero[8][2].isEnabled() && !datoSelecionado.contentEquals(tablero[8][2].getText())){
                tablero[8][2].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_94) {
            if (!datoSelecionado.equals("0") && tablero[8][3].isEnabled() && !datoSelecionado.contentEquals(tablero[8][3].getText())){
                tablero[8][3].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_95) {
            if (!datoSelecionado.equals("0") && tablero[8][4].isEnabled() && !datoSelecionado.contentEquals(tablero[8][4].getText())){
                tablero[8][4].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_96) {
            if (!datoSelecionado.equals("0") && tablero[8][5].isEnabled() && !datoSelecionado.contentEquals(tablero[8][5].getText())){
                tablero[8][5].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_97) {
            if (!datoSelecionado.equals("0") && tablero[8][6].isEnabled() && !datoSelecionado.contentEquals(tablero[8][6].getText())){
                tablero[8][6].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_98) {
            if (!datoSelecionado.equals("0") && tablero[8][7].isEnabled() && !datoSelecionado.contentEquals(tablero[8][7].getText())){
                tablero[8][7].setText(datoSelecionado);
                atras.add(instantanea());
            }
        } else if (id == R.id.tv_99) {
            if (!datoSelecionado.equals("0") && tablero[8][8].isEnabled() && !datoSelecionado.contentEquals(tablero[8][8].getText())){
                tablero[8][8].setText(datoSelecionado);
                atras.add(instantanea());
            }
        }
    }

    //metodo para obtener los textView del tablero en el xml
    public void tableroRef() {
        //primera fila
        this.tablero[0][0]=findViewById(R.id.tv_11);
        this.tablero[0][1]=findViewById(R.id.tv_12);
        this.tablero[0][2]=findViewById(R.id.tv_13);
        this.tablero[0][3]=findViewById(R.id.tv_14);
        this.tablero[0][4]=findViewById(R.id.tv_15);
        this.tablero[0][5]=findViewById(R.id.tv_16);
        this.tablero[0][6]=findViewById(R.id.tv_17);
        this.tablero[0][7]=findViewById(R.id.tv_18);
        this.tablero[0][8]=findViewById(R.id.tv_19);
        //segunda fila
        this.tablero[1][0]=findViewById(R.id.tv_21);
        this.tablero[1][1]=findViewById(R.id.tv_22);
        this.tablero[1][2]=findViewById(R.id.tv_23);
        this.tablero[1][3]=findViewById(R.id.tv_24);
        this.tablero[1][4]=findViewById(R.id.tv_25);
        this.tablero[1][5]=findViewById(R.id.tv_26);
        this.tablero[1][6]=findViewById(R.id.tv_27);
        this.tablero[1][7]=findViewById(R.id.tv_28);
        this.tablero[1][8]=findViewById(R.id.tv_29);
        //tercera fila
        this.tablero[2][0]=findViewById(R.id.tv_31);
        this.tablero[2][1]=findViewById(R.id.tv_32);
        this.tablero[2][2]=findViewById(R.id.tv_33);
        this.tablero[2][3]=findViewById(R.id.tv_34);
        this.tablero[2][4]=findViewById(R.id.tv_35);
        this.tablero[2][5]=findViewById(R.id.tv_36);
        this.tablero[2][6]=findViewById(R.id.tv_37);
        this.tablero[2][7]=findViewById(R.id.tv_38);
        this.tablero[2][8]=findViewById(R.id.tv_39);
        //cuarta fila
        this.tablero[3][0]=findViewById(R.id.tv_41);
        this.tablero[3][1]=findViewById(R.id.tv_42);
        this.tablero[3][2]=findViewById(R.id.tv_43);
        this.tablero[3][3]=findViewById(R.id.tv_44);
        this.tablero[3][4]=findViewById(R.id.tv_45);
        this.tablero[3][5]=findViewById(R.id.tv_46);
        this.tablero[3][6]=findViewById(R.id.tv_47);
        this.tablero[3][7]=findViewById(R.id.tv_48);
        this.tablero[3][8]=findViewById(R.id.tv_49);
        //quinta fila
        this.tablero[4][0]=findViewById(R.id.tv_51);
        this.tablero[4][1]=findViewById(R.id.tv_52);
        this.tablero[4][2]=findViewById(R.id.tv_53);
        this.tablero[4][3]=findViewById(R.id.tv_54);
        this.tablero[4][4]=findViewById(R.id.tv_55);
        this.tablero[4][5]=findViewById(R.id.tv_56);
        this.tablero[4][6]=findViewById(R.id.tv_57);
        this.tablero[4][7]=findViewById(R.id.tv_58);
        this.tablero[4][8]=findViewById(R.id.tv_59);
        //sexta fila
        this.tablero[5][0]=findViewById(R.id.tv_61);
        this.tablero[5][1]=findViewById(R.id.tv_62);
        this.tablero[5][2]=findViewById(R.id.tv_63);
        this.tablero[5][3]=findViewById(R.id.tv_64);
        this.tablero[5][4]=findViewById(R.id.tv_65);
        this.tablero[5][5]=findViewById(R.id.tv_66);
        this.tablero[5][6]=findViewById(R.id.tv_67);
        this.tablero[5][7]=findViewById(R.id.tv_68);
        this.tablero[5][8]=findViewById(R.id.tv_69);
        //septima fila
        this.tablero[6][0]=findViewById(R.id.tv_71);
        this.tablero[6][1]=findViewById(R.id.tv_72);
        this.tablero[6][2]=findViewById(R.id.tv_73);
        this.tablero[6][3]=findViewById(R.id.tv_74);
        this.tablero[6][4]=findViewById(R.id.tv_75);
        this.tablero[6][5]=findViewById(R.id.tv_76);
        this.tablero[6][6]=findViewById(R.id.tv_77);
        this.tablero[6][7]=findViewById(R.id.tv_78);
        this.tablero[6][8]=findViewById(R.id.tv_79);
        //octaba fila
        this.tablero[7][0]=findViewById(R.id.tv_81);
        this.tablero[7][1]=findViewById(R.id.tv_82);
        this.tablero[7][2]=findViewById(R.id.tv_83);
        this.tablero[7][3]=findViewById(R.id.tv_84);
        this.tablero[7][4]=findViewById(R.id.tv_85);
        this.tablero[7][5]=findViewById(R.id.tv_86);
        this.tablero[7][6]=findViewById(R.id.tv_87);
        this.tablero[7][7]=findViewById(R.id.tv_88);
        this.tablero[7][8]=findViewById(R.id.tv_89);
        //novena fila
        this.tablero[8][0]=findViewById(R.id.tv_91);
        this.tablero[8][1]=findViewById(R.id.tv_92);
        this.tablero[8][2]=findViewById(R.id.tv_93);
        this.tablero[8][3]=findViewById(R.id.tv_94);
        this.tablero[8][4]=findViewById(R.id.tv_95);
        this.tablero[8][5]=findViewById(R.id.tv_96);
        this.tablero[8][6]=findViewById(R.id.tv_97);
        this.tablero[8][7]=findViewById(R.id.tv_98);
        this.tablero[8][8]=findViewById(R.id.tv_99);
    }

    public void numerosRef(){
        this.numeros[0]=findViewById(R.id.tv_1);
        this.numeros[1]=findViewById(R.id.tv_2);
        this.numeros[2]=findViewById(R.id.tv_3);
        this.numeros[3]=findViewById(R.id.tv_4);
        this.numeros[4]=findViewById(R.id.tv_5);
        this.numeros[5]=findViewById(R.id.tv_6);
        this.numeros[6]=findViewById(R.id.tv_7);
        this.numeros[7]=findViewById(R.id.tv_8);
        this.numeros[8]=findViewById(R.id.tv_9);
    }

    //para controlar el boton back
    @Override
    public void onBackPressed() {
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