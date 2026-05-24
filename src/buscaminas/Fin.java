package buscaminas;

public class Fin{
  private boolean gano;
  private long tiempoSeg;

  public Fin(boolean gano, long tiempoSeg){
    this.gano = gano;
    this.tiempoSeg = tiempoSeg;
  }
public void mostrar(){
  System.out.println();
if(gano){
  System.out.println("+======================================+");
  System.out.println("|         Ganaste, bien jugado         |");
  System.out.println("+======================================+");
} else{
  System.out.println("+======================================+");
  System.out.println("|   ¡BOOM! Acabas de pisar una mina    |");
  System.out.println("+======================================+");
}
  System.out.println("Tiempo: " + tiempoSeg + " segundos");
  System.out.println();
}
public boolean isGano(){
  return gano;
}

public long getTiempoSeg(){
  return tiempoSeg;
}


public void setGano(boolean gano){
  this.gano = gano;
}

public void setTiempoSeg(long tiempoSeg){
  this.tiempoSeg = tiempoSeg;
}
}


  
