public class SinMina extends Casilla {

    private int contadorMinas;
    private boolean vacia;

    public SinMina(int fila, int columna) {
        super(fila, columna);
        this.contadorMinas = 0;
        this.vacia = true;
    }

    @Override
    public boolean descubrir() {
        setDescubierta(true);
        return false;
    }

    public void incrementarContador() {
        contadorMinas++;
        vacia = false;
    }

    @Override
    public String simbolo() {
        if (isBandera()) {
            return " P ";
        }
        if (!isDescubierta()) {
            return " # ";
        }
        if (vacia) {
            return " . ";
        }
        return " " + contadorMinas + " ";
    }

    public int getContadorMinas() { 
        return contadorMinas; 
    }
    public boolean isVacia() { 
        return vacia; 
    }

   
    public void setContadorMinas(int contadorMinas) {
        this.contadorMinas = contadorMinas;
        this.vacia = contadorMinas == 0;
    }
}
