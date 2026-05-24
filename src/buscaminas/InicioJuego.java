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
    System.out.println("| 1. Nueva partida         |");
    System.out.println("| 2. Historial             |");
    System.out.println("| 3. Salir                 |");
    System.out.println("+--------------------------+");
    System.out.println("| Elige una opción         |");
  }

  private void elegirNivel(){
    System.out.println();
    System.out.println("+--------------------------+");
    System.out.println("|   Nivel de dificultad    |");
    System.out.println("+--------------------------+");
    System.out.println("| 1.Fácil:(9x9, 10 minas)  |");
    System.out.println("| 2.Medio:(16x16, 40 minas |");
    System.out.println("| 3.Difícil:(16x30, 99minas|");
    System.out.println("| 4.Personalizado          |");
    System.out.println("+--------------------------+");
    System.out.println(" Elige el nivel ");

    String linea = scanner.nextLine.trim();
    switch(linea){
      case "1": tablero = new Tablero(Tablero.FACIL);
        break;
      case "2": tablero = new Tablero(Tablero.MEDIO);
        break;
      case "3": tablero = new Tablero(Tablero.DIFICIL);
        break;
      case "4": tablero = crearPersonalizado();
        break;
      default:
        System.out.println(" Opción no válida. Se usará fácil");
        tablero = new Tablero(Tablero.FACIL);
    }
  }

  private void crearPersonalizado(){
    System.out.println();
    System.out.println("Advertencia: El tablero debe ser mínimo 2x2.");
    System.out.println();
    int filas = leerEnteroEnRango("Filas (mínimo 2):", 2 , 100);
    int columnas = leerEnteroEnRango("Columnas(mínimo2):", 2, 100);
    int maxMinas = filas * columnas-1;
    int minas = leerEnteroEnRango("Minas (max " + maxMinas + "): ", 1, maxMinas);
    tablero = new Tablero(filas, columnas, minas);
  }

  private void jugarPartida(){
    elegirNivel();

    long inicio = System.currentMillis();
    boolean termino = false;
    boolean gano = false;
    boolean salio = false;

    System.out.println();
    System.out.println(" Nivel: " + tablero.getNombreNivel() + " | Casillas del " + tablero.getNumeroBase() + " al " + tablero.getUltimoNumero());
    System.out.println()

    
    
    
    
          
          
      

  
