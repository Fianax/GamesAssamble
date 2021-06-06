package com.example.gamesassamble.sudoku;

import java.util.Random;

//Clase donde estaran los metodos para utilizar en el SUDOKU
public class Sudoku {

    //Metodo para otener un sudoku, array [][] String
    public static String[][] crearSudoku(){
        String sudokuString[][]=new String[9][9];

        //Calculacion del sudoku como una matriz de enteros
        int sudoku[][]=new int [9][9];

        int submatriz[][][]=new int [3][3][3];

        int i=0,cont=45,a,b;

        boolean salir_final=false,bien_horizontal=true,repetir=false;


        do{
            salir_final=false;
            do{
                sudoku=linea(sudoku,i);//tengo la linea pintada
                //Vamos a comprobar las horizontales(para ver que no se repiten ningun numero). Solo de las que hayamos creado.

                //Para que no compruebe la primera linea
                if(i!=0)
                    bien_horizontal=horizontal(sudoku,i);

                if(i==7 && bien_horizontal){
					/*Calculamos la ultima linea sabiendo que numero va en cada lugar porque si la sumatoria de 1-9=45
						y le restas todos los demas numeros, obtiene de forma inequivoca el unico numero que cabe. Y como no se repetira en la
						horizontal, tampoco pasara en la vertical.*/
                    for(a=0;a<9;a++){
                        cont=45;
                        for(b=0;b<8;b++){
                            cont=cont-sudoku[b][a];
                        }
                        sudoku[b][a]=cont;
                    }
                }

                if(i==2 && bien_horizontal){
                    submatriz[0]=subMatrizcuadrada(sudoku,0,0,3);
                    submatriz[1]=subMatrizcuadrada(sudoku,0,3,3);
                    submatriz[2]=subMatrizcuadrada(sudoku,0,6,3);

                    repetir=repetirNumeroMatriz(submatriz);
                    if(repetir)
                        i-=3;
                }else if(i==5 && bien_horizontal){
                    submatriz[0]=subMatrizcuadrada(sudoku,3,0,3);
                    submatriz[1]=subMatrizcuadrada(sudoku,3,3,3);
                    submatriz[2]=subMatrizcuadrada(sudoku,3,6,3);

                    repetir=repetirNumeroMatriz(submatriz);
                    if(repetir)
                        i-=3;
                }

                if(bien_horizontal)
                    i++;

            }while(i!=8);

            salir_final=true;

        }while(!salir_final);

        //pasamos la matriz de enteros a una de String y la devolvemos
        for(int x=0;x<sudoku.length;x++)
            for(int y=0;y<sudoku[x].length;y++)
                sudokuString[x][y]=String.valueOf(sudoku[x][y]);


        return sudokuString;//TIEMPO=~1-2 segundos
    }
    //METODOS AUXILIARES

    //Vamos a rellenar una linea de 9 posiciones con numeros del 1-9 sin repeticiones
    public static int[][] linea(int[][] sudoku, int i){
        Random r=new Random();

        int j=0,a;

        boolean salir_numero=false;

        sudoku[i][j]=(r.nextInt(9))+1;

        for(j=1;j<9;j++){
            do{
                salir_numero=false;
                sudoku[i][j]=(r.nextInt(9))+1;
                //ver si en las j anteriores no haya numeros repetidos
                a=(j-1);
                do{
                    if(sudoku[i][j]!=sudoku[i][a])
                        salir_numero=true;
                    else
                        salir_numero=false;
                    a--;
                }while(salir_numero && a>=0);
            }while(!salir_numero);
        }

        return sudoku;
    }

    //Vamos a comprobar las horizontales con este metodo. SOLO DE LAS RELLENAS
    public static boolean horizontal(int[][] sudoku,int i){
        boolean bien_horizontal=true;

        int y,x,b;

        y=-1;
        x=-1;
        b=x+1;

        do{
            y++;
            x=-1;
            do{
                x++;
                b=x;
                do{
                    b++;
                    if(sudoku[x][y]==sudoku[b][y])
                        bien_horizontal=false;
                }while(b<i && bien_horizontal);
            }while(x<i-1 && bien_horizontal);
        }while(y<8 && bien_horizontal);

        return bien_horizontal;
    }

    //Metodo para obtener una submatriz,en nuestro caso, siempre sera 3x3
    public static int[][] subMatrizcuadrada(int[][] sudoku,int i,int j,int tamanno){
        int [][] submatriz=new int [tamanno][tamanno];

        int x,y;

        x=-1;

        for(int a=i;a<(i+tamanno);a++){
            x++;
            y=0;
            for(int b=j;b<(j+tamanno);b++){
                submatriz[x][y]=sudoku[a][b];
                y++;
            }
        }

        return submatriz;
    }

    //Metodo para ver si algun valor se repite en dicha matriz
    public static boolean repetirNumeroMatriz(int[][][] submatriz){

        boolean repetir=false;

        int [] verdadero=new int [3];

        int cont=0;

        for(int z=0;z<3;z++){//me mueve en la profundidad del 3x3x3
            cont=0;
            for(int a=0;a<3;a++){
                for(int b=0;b<3;b++){//estos dos para mirar elemento a elemento
                    for(int c=0;c<3;c++){
                        for(int d=0;d<3;d++){//y estos dos para mirar los siguientes elementos al compararlos con el (a,b)
                            if(!(a==c && b==d)){
                                if(submatriz[z][a][b]==submatriz[z][c][d])
                                    cont++;
                            }
                        }
                    }
                }
            }
            verdadero[z]=cont;
            //System.out.println(cont);
        }

        if(verdadero[0]==0 && verdadero[1]==0 && verdadero[2]==0)
            repetir=false;
        else
            repetir=true;

        return repetir;
    }
}
