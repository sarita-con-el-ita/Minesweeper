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
          System.out.println("Hasta luego.");

          sesionActiva = false;
          break;
        default:
          System.out.println(" Opción no válida. Elige 1,2 o 3.");
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

    String linea = scanner.nextLine().trim();
    switch(linea){
      case "1": tablero = new Tablero(Tablero.FACIL);
        break;
      case "2": tablero = new Tablero(Tablero.MEDIO);
        break;
      case "3": tablero = new Tablero(Tablero.DIFICIL);
        break;
      case "4": crearPersonalizado();
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

    long inicio = System.currentTimeMillis();
    boolean termino = false;
    boolean gano = false;
    boolean salio = false;

    System.out.println();
    System.out.println(" Nivel: " + tablero.getNombreNivel() + " | Casillas del " + tablero.getNumeroBase() + " al " + tablero.getUltimoNumero());
    System.out.println(" Escribe 'salir' para abandonar la partida");
    System.out.println();
    tablero.imprimir(false);

    while(!termino){
      System.out.println(" >> ");

      String linea = scanner.nextLine().trim().replaceAll("\\s+"," ");

      if(linea.equalsIgnoreCase("salir")){
        System.out.println("Has salido de la partida");
        salio = true;
        termino = true;
        continue;
      }

      if(linea.length() >= 3 && linea.charAt(1) == ' '){
        char accion = Character.toLowerCase(linea.charAt(0));
        String resto = linea.substring(2).trim();
        int numero;

        try{
          numero = Integer.parseInt(resto);
        }catch (NumberFormatException e) {
          System.out.println("Número no válido. Ejemplo d 10");
          continue;
        }

        if(!tablero.esNumeroValido(numero)){
          System.out.println("Número fuera de rango. Las casillas van del" + tablero.getNumeroBase() + " al " + tablero.getUltimoNumero() + ".");
          continue;
        }

        switch(accion){
          case 'd':
            int resultado = tablero.descubrir(numero);

            if(resultado == 2){
              System.out.println("Esta casilla ya fue descubierta.");
            } else if(resultado ==1){
              tablero.imprimir(true);
              termino = true;
              gano = false;
            } else{
              tablero.imprimir(false);
              if(tablero.juegoGanado()){
                termino = true;
                gano = true;
              }
            }
            break;
            
          case 'p':
            int resBandera = tablero.ponerBandera(numero);
            if(resBandera == 1){
              System.out.println("No te quedan banderas disponibles");
            } else if (resBandera == 2){
              System.out.println("Esa casilla ya tiene bandera. Usa x para quitarla");
            } else {
              tablero.imprimir(false);
            }
            break;

          case 'x':
            tablero.quitarBandera(numero);
            tablero.imprimir(false);
            break;

          default:
            System.out.println("Comando no reconocido. Usa d, p, o x");
        }

      } else{
        System.out.println("Comando no reconocido");
        System.out.println("Usa: d[num], p [num], x[num]");
        System.out.println("O escribe: salir");
      }
    }

    long tiempoSeg = (System.currentTimeMillis() - inicio) / 1000;

    if(!salio){
      Fin fin = new Fin(gano, tiempoSeg);
      fin.mostrar();
    }

    registro.agregarPartida(tablero.getNombreNivel(),tablero.getFilas(),tablero.getColumnas(),tablero.getNumMinas(),tiempoSeg, gano);
    tablero = null;
  }

  public Scanner getScanner(){
    return scanner;
  }
  public Registro getRegistro(){
    return registro;
  }
  public Tablero getTablero(){
    return tablero;
  }

  public void setTablero(Tablero tablero){
    this.tablero = tablero;
  }

  private int leerEnteroEnRango(String mensaje, int min, int max){
    while(true){
      System.out.println(mensaje);
      try{
        int val = Integer.parseInt(scanner.nextLine().trim());
        if(val>= min && val<=max) return val;
        System.out.println(" Debe estar entre " + min + " y " + max + ".");
        }catch (NumberFormatException e ){
        System.out.println("Ingresa un número entero");
      }
    }
  }

  public static void main(String []args){
    new InicioJuego().iniciar();
  }
}

        
  
      
        
    

    
    
    
    
          
          
      

  
