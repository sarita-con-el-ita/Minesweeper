package buscaminas;

import java.util.Random;

public class Tablero{

    //CONSTANTES
    public static final int FACIL = 1;
    public static final int MEDIO = 2;
    public static final int DIFICIL = 3;
    public static final int PERSONALIZADO = 4;

    public static final int NUMERO_BASE = 10;

    //ATRIBUTOS
    private Casilla[][]casillas;
    private int filas;
    private int columnas;
    private int numMinas;
    private String nombreNivel;
    private int casillasSegurasTotal;
    private int casillasSegurasDescubiertas;
    private int banderasDisponibles;

    //CONSTRUCTOR
    public Tablero(int nivel){
        switch (nivel){
            case FACIL:
                configurar("Facil", 9, 9, 10);
                break;
            case MEDIO:
                configurar("Medio", 16, 16, 40);
                break;
            case DIFICIL:
                configurar("Dificil", 16, 30, 99);
                break;
            default:
                configurar("Facil", 9, 9, 10);
        }
        inicializar();
    }

    //CONSTRUCTOR PARA EL PERSONALIZADO
    public Tablero(int filas, int columnas, int numMinas){
        configurar("Personalizado", filas, columnas, numMinas);
        inicializar();
    }

    //MÉTODOS PRIVADOS
    private void configurar(String nombre, int filas, int columnas, int numMinas){
        this.nombreNivel = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.numMinas= numMinas;
        this.casillas = new Casilla[filas][columnas];
        this.casillasSegurasTotal = filas * columnas - numMinas;
        this.casillasSegurasDescubiertas = 0;
        this.banderasDisponibles = numMinas;
    }

    private void inicializar(){
        for ( int f = 0; f < filas;f++){
            for ( int c = 0; c < columnas; c++){
                casillas[f][c] = new SinMina(f, c);
            }
        }
        colocarMinas();
        calcularContadores();
    }

    private void colocarMinas(){
        Random rand = new Random();
        int colocadas = 0;

        while(colocadas < numMinas){
            int f = rand.nextInt(filas);
            int c = rand.nextInt(columnas);
            if(!(casillas[f][c] instanceof Mina)){
                casillas [f][c] = new Mina(f, c);
                colocadas++;
            }
        }
    }

    private void calcularContadores(){
        for( int f = 0; f < filas; f++){
            for( int c = 0; c < columnas; c++){
                if (casillas[f][c] instanceof SinMina){
                    int cuenta = contarMinasAdyacentes(f,c);
                    for(int i = 0; i < cuenta; i++){
                        ((SinMina)casillas[f][c]).incrementarContador();
                    }
                }
            }  
        }
    }

    private int contarMinasAdyacentes( int f, int c){
        int count = 0;
        for(int df = -1; df <= 1; df++){
            for( int dc = -1; dc <= 1;dc++){
                if(df == 0 && dc == 0) continue;
                int nf = f + df;
                int nc = c + dc;
                if(nf >= 0 && nf < filas && nc >= 0 && nc < columnas){
                    if(casillas[nf][nc]instanceof Mina) count++;
                }
            }
        }
        return count;
    }

    //MÉTODOS PÚBLICOS

    public int numeroDeCasilla(int fila, int columna){
        return NUMERO_BASE + fila * columnas + columna;
    }

    public int[] coordenadasDeNumero( int numero){
        int idx = numero - NUMERO_BASE;
        int fila = idx/columnas;
        int columna = idx % columnas;
        return new int[]{ fila, columna};
    }

    public boolean esNumeroValido(int numero){
        int idx= numero - NUMERO_BASE;
        return idx >= 0 && idx < filas * columnas;
    }

    public int descubrir( int numero){
        int[] coords = coordenadasDeNumero(numero);
        return descubrirCoordenadas(coords[0], coords[1]);
    }

    private int descubrirCoordenadas( int f, int c){
        Casilla cas = casillas[f][c];

        if (cas.isDescubierta()){
            return 2;
        }
        if (cas.isBandera()){
            return 0;
        }
        boolean exploto = cas.descubrir();
        if(exploto){
            return 1;
        }
        casillasSegurasDescubiertas++;
        if(cas instanceof SinMina && ((SinMina)cas).isVacia()){
            expandirCascada(f, c );
        }
        return 0;
    }

    private void expandirCascada(int f, int c){
        for(int df = -1; df <= 1;df++){
            for(int dc= -1; dc <= 1;dc++){
                if(df == 0 && dc == 0)continue;
                int nf = f + df;
                int nc = c+ dc;
                if(nf >= 0 && nf < filas && nc >= 0 && nc < columnas){
                    Casilla vecino = casillas[nf][nc];
                    if(!vecino.isDescubierta() && !vecino.isBandera() && vecino instanceof SinMina){
                        vecino.descubrir();
                        casillasSegurasDescubiertas++;
                        if(((SinMina)vecino).isVacia()){
                            expandirCascada(nf, nc);
                        }
                    }
                }
            }
        }
    }

        public int ponerBandera (int numero){
            int[]coords = coordenadasDeNumero(numero);
            Casilla cas = casillas[coords[0]][coords[1]];

            if(cas.isBandera()){
                return 2;
            }
            if(banderasDisponibles <= 0){
                return 1;
            }
            cas.ponerBandera();
            banderasDisponibles--;
            return 0;
        }

    public void quitarBandera(int numero){
        int[] coords = coordenadasDeNumero(numero);
        Casilla cas = casillas[coords[0]][coords[1]];

        if(cas.isBandera()){
            cas.quitarBandera();
            banderasDisponibles++;
        }
    }

    public boolean juegoGanado(){
        return casillasSegurasDescubiertas >= casillasSegurasTotal;
    }

    public void imprimir(boolean revelarMinas){
        int anchoNum = String.valueOf(numeroDeCasilla(filas - 1, columnas - 1)).length();

        String espacioFila = " ".repeat(anchoNum + 2);
        String celdaLinea = "-".repeat(anchoNum + 2);
        String separador = espacioFila + ("+" + celdaLinea).repeat(columnas)+ "+";
        System.out.println(separador);

        for ( int f = 0; f < filas; f++){
            System.out.print(espacioFila + "|");

            for( int c = 0; c< columnas; c++){
                Casilla cas= casillas[f][c];
                int numero = numeroDeCasilla(f, c);
                String simbolo;

                if(cas.isBandera()){
                    simbolo = centrar("p",anchoNum);
                }else if (revelarMinas && cas instanceof Mina){
                    simbolo = centrar("*",anchoNum);
                }else if (!cas.isDescubierta()){
                    simbolo = centrar(String.valueOf(numero), anchoNum);   
                }else if (cas instanceof  SinMina){
                    SinMina sm = (SinMina) cas;
                    if(sm.isVacia()){
                        simbolo = " ".repeat(anchoNum + 2);
                    }else{
                        simbolo = centrar(String.valueOf(sm.getContadorMinas()), anchoNum);
                    }   
                }else{
                    simbolo =" ".repeat(anchoNum + 2);
                }
                System.out.print(simbolo + "|");
            }
            System.out.println();
            System.out.println(separador);
        }
        System.out.println("  Banderas disponibles:"+ banderasDisponibles + " / " + numMinas);
        System.out.println();
    }

    private String centrar (String texto, int anchoNum){
        int totalAncho = anchoNum + 2;
        int espacios = totalAncho - texto.length();
        int izq = espacios / 2;
        int der = espacios - izq;
        return " ".repeat(izq) + texto + " ".repeat(der);
    }

    //GETTERS AND SETTERS

    public String getNombreNivel(){
        return nombreNivel;
    }
    public int getFilas(){
        return filas;
    }
    public int getColumnas(){
        return columnas;
    }
    public int getNumMinas(){
        return numMinas;
    }
    public int getNumeroBase(){
        return NUMERO_BASE;
    }
    public int getUltimoNumero(){
        return NUMERO_BASE + (filas * columnas)-1;
    }
    public int getBanderasDisponibles(){
        return banderasDisponibles;
    }
    public int getCasillasSegurasTotal(){
        return casillasSegurasTotal;
    }
    public int getCasillasSegurasDescubiertas(){
        return casillasSegurasDescubiertas;
    }
    public void setNombreNivel(String nombreNivel){
        this.nombreNivel=nombreNivel;
    }
}

