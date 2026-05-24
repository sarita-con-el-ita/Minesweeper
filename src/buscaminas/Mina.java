public class Mina extends Casilla {
    public Mina(int fila, int columna){
        super(fila, columna);
    }

    @Override
    public boolean descubrir(){
        setDescubierta = true;
        return true;
    }

    @Override
    public String simbolo(){
        if(!isDescubierta()){
            return " # ";
        }
        
        if(isBandera()){
            return " P ";
        }

        return " * ";
    }


}