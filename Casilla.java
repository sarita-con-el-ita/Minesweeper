public abstract class Casilla {
    private int fila;
    private int columna;
    private boolean descubierta;
    private boolean bandera;

    public Casilla(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
        this.descubierta = false;
        this.bandera= false; 
    }

    public abstract boolean  descubrir();
    public abstract String simbolo(); 
    public void ponerBandera() {
        if(!descubierta){
            bandera = true; 
        }
    }
    public void quitarBandera() {
        if(!descubierta){
            bandera = false; 
        }
    }

    public int getFila(){
        return fila; 
    }
    public int getColumna(){
        return columna;
    }
    public boolean isDescubierta(){
        return descubierta;
    }
    public boolean isBandera(){
        return bandera; 
    }


    public void setDescubierta(boolean descubierta){
        this.descubierta = descubierta;
    }
}