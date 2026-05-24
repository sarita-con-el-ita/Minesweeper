package buscaminas;

import java.util.Scanner;

public class InicioJuego{
  
  private Scanner scanner;
  private Registro registro;
  private Tablero tablero;

  public InicioJuego(){
    this.scanner = new Scanner(System.in);
    this.registro = new Registro();
    this.tablero=null;
  }

  public void iniciar(){
    
    boolean sesionActiva = true;
    while(sesionActiva){
      mostrarMenu();
      String opcion = scanner.nextLine().trim();
      switch(opcion){
        case "1":
          jugarPartida();
          break;
        case "2":
        case "Historial":
          registro.mostrar(scanner);
          break;
        case "3":
          System.out.println();
          System.out.println("Opción no válida. Elige 1, 2, 3.");
      }
    }
    scanner.close();
  }

  private void mostrarMenu(){
    System.out.println("+--------------------------+");
    System.out.println("|      Menú principal      |");
    System.out.println("+--------------------------+");
    
    
          
          
      

  
