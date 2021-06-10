package com.example.gamesassamble.sopaletras;
import java.util.ArrayList;
import java.util.Random;

public class SopaLetra {

    static String[] abecedario={"A","B","C","D","E","D","F","G","H","I","J","K","L","M","N","O","P","Q",
                        "R","S","T","U","V","W","X","Y","Z"};
    public ArrayList<String[]> solucion=new ArrayList<>();

    public String[][] crearSopa(ArrayList<String> letras){
        String[] posicionPalabra;
        Random r=new Random();
        boolean salir_posicion=false,salir_orientacion=false,salir_global=false,salir_limite=false,palabra_ok=false;
        int fila=0,columna=0,orientacion,topeMax=0,fSustituto,cSustituto,tamPalabra=0,casilla_usada=0,a=2;
        String[][] sopa=new String[7][7];
        String palabra;

        for(int i=0;i<sopa.length;i++)
            for(int j=0;j<sopa[i].length;j++)
                sopa[i][j]=" ";

        for(int i=0;i<letras.size();i++){
            palabra= letras.get(i);
            tamPalabra=letras.get(i).length();//el tamaño de la palabra
            do {
                //elegir una casilla de salida para la palabra
                do {
                    //System.out.println(palabra);
                    fila = r.nextInt(7);
                    columna = r.nextInt(7);
                    salir_posicion = false;

                    if (sopa[fila][columna].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(0)))){
                        salir_posicion = true;//para poder usar un inicio que coincida con una palabra ya escrita como hola-adios
                    }
                } while (!salir_posicion);

                //Elegir orientacion y comprobar que esa palabra cabe en dicha orientacion
                do {
                    //System.out.println(palabra+"-"+fila+"-"+columna);
                    salir_orientacion = false;
                    salir_limite=false;
                    palabra_ok=false;
                    orientacion = r.nextInt(8);
                    topeMax++;
                    casilla_usada = 1;
                    a=2;
                    fSustituto = fila;
                    cSustituto = columna;
                    //Tantos if como posibles orientaciones posibles==7(0-7)
                    if (orientacion == 0) {
                        while(a<=tamPalabra && !salir_limite) {
                            fSustituto--;
                            cSustituto--;
                            if ((fSustituto >= 0 && fSustituto <= 6) && (cSustituto >= 0 && cSustituto <= 6)) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    } else if (orientacion == 1) {
                        while(a<=tamPalabra && !salir_limite) {
                            fSustituto--;
                            if (fSustituto >= 0 && fSustituto <= 6) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    } else if (orientacion == 2) {
                        while(a<=tamPalabra && !salir_limite) {
                            fSustituto--;
                            cSustituto++;
                            if ((fSustituto >= 0 && fSustituto <= 6) && (cSustituto >= 0 && cSustituto <= 6)) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    } else if (orientacion == 3) {
                        while(a<=tamPalabra && !salir_limite) {
                            cSustituto--;
                            if (cSustituto >= 0 && cSustituto <= 6) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    } else if (orientacion == 4) {
                        while(a<=tamPalabra && !salir_limite) {
                            cSustituto++;
                            if (cSustituto >= 0 && cSustituto <= 6) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    } else if (orientacion == 5) {
                        while(a<=tamPalabra && !salir_limite) {
                            fSustituto++;
                            cSustituto--;
                            if ((fSustituto >= 0 && fSustituto <= 6) && (cSustituto >= 0 && cSustituto <= 6)) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    } else if (orientacion == 6) {
                        while(a<=tamPalabra && !salir_limite) {
                            fSustituto++;
                            if (fSustituto >= 0 && fSustituto <= 6) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    } else if (orientacion == 7) {
                        while(a<=tamPalabra && !salir_limite) {
                            fSustituto++;
                            cSustituto++;
                            if ((fSustituto >= 0 && fSustituto <= 6) && (cSustituto >= 0 && cSustituto <= 6)) {
                                if (sopa[fSustituto][cSustituto].equals(" ") || sopa[fila][columna].equals(String.valueOf(palabra.charAt(a - 1))))
                                    casilla_usada++;
                                if (casilla_usada == tamPalabra)
                                    palabra_ok=true;
                            }else
                                salir_limite=true;

                            a++;
                        }
                    }
                    if (palabra_ok) {
                        salir_orientacion = true;
                        salir_global = true;
                    } else if (topeMax >= 2) {
                        salir_orientacion = true;
                        salir_global = false;
                        topeMax = 0;//para refrescar la primera posicion
                    }
                } while (!salir_orientacion);
            } while (!salir_global);
            /*Hasta aqui para saber si la colocacion de la palabra es correcta;
            Ahora vamos a colocar la palabra sabiendo su orientacion y tamaño
             */

            posicionPalabra=new String[4];

            //Colocacion de la palabra
            fSustituto = fila;
            cSustituto = columna;
            //colocar la primera letra
            sopa[fila][columna]= String.valueOf(palabra.charAt(0));
            //System.out.println(palabra+"-"+fila+"-"+columna+"-"+orientacion);
            //añadimos la solucion de cada palabra (orientacion-inicio-fin)
            posicionPalabra[0]=palabra;
            posicionPalabra[1]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            posicionPalabra[3]=String.valueOf(orientacion);
            if (orientacion == 0) {
                for (int x = 1; x < tamPalabra; x++) {
                    fSustituto--;
                    cSustituto--;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            } else if (orientacion == 1) {
                for (int x = 1; x < tamPalabra; x++) {
                    fSustituto--;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            } else if (orientacion == 2) {
                for (int x = 1; x < tamPalabra; x++) {
                    fSustituto--;
                    cSustituto++;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            } else if (orientacion == 3) {
                for (int x = 1; x < tamPalabra; x++) {
                    cSustituto--;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            } else if (orientacion == 4) {
                for (int x = 1; x < tamPalabra; x++) {
                    cSustituto++;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            } else if (orientacion == 5) {
                for (int x = 1; x < tamPalabra; x++) {
                    fSustituto++;
                    cSustituto--;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            } else if (orientacion == 6) {
                for (int x = 1; x < tamPalabra; x++) {
                    fSustituto++;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            } else if (orientacion == 7) {
                for (int x = 1; x < tamPalabra; x++) {
                    fSustituto++;
                    cSustituto++;
                    sopa[fSustituto][cSustituto] = String.valueOf(palabra.charAt(x));
                }
                posicionPalabra[2]=String.valueOf(fSustituto)+"-"+String.valueOf(cSustituto);
            }
            solucion.add(posicionPalabra);
        }

        return rellenar(sopa);
    }

    public static String[][] rellenar(String[][] sopa){

        Random r=new Random();
        int letra;

        for(int i=0;i<sopa.length;i++){
            for(int j=0;j<sopa[i].length;j++){
                letra = r.nextInt(abecedario.length);
                if (sopa[i][j].equals(" "))
                    sopa[i][j] = abecedario[letra];
            }
        }

        return sopa;
    }

}
