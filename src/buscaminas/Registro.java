import java.util.ArrayList;
import java.util.Scanner;

public class Registro{

    //ATRIBUTOS

    private ArrayList<String[]>partidas;

    //CONSTRUCTOR

    public Registro(){
        this.partidas=new ArrayList<>();
    }

    //MÉTODOS

    public void agregarPartida(String nivel, int filas, int columnas, int numMinas, long tiempoSeg, boolean gano){
        String[] datos = new String[5];
        datos[0] = nivel;
        datos[1] = filas + "x" + columnas;
        datos[2] = String.valueOf(numMinas);
        datos[3]=String.valueOf(tiempoSeg);
        if(gano == true){
            datos[4] = "VICTORIA";
        }else{
            datos[4] = "DERROTA";
        }
        partidas.add(datos);
    }

    public boolean hayPartida(){
        return !partidas.isEmpty();
    }

    //BUBBLE SORT
    public void organizar(){
        int n = partidas.size();

        for(int i = 0; i < n - 1; i++){
            for(int j = 0; j< n - 1 - i;j++){
                long tiempoJ = Long.parseLong(partidas.get(j)[3]);
                long tiempoJSig= Long.parseLong(partidas.get(j + 1)[3]);

                if (tiempoJ > tiempoJSig){
                    String[] temp = partidas.get(j);
                    partidas.set(j, partidas.get(j + 1));
                    partidas.set(j + 1, temp);
                }
            }
        }
    }

    //BINARY SEARCH
    public int buscarPorTiempo(long tiempoSeg){
        int izq=0;
        int der=partidas.size() - 1;

        while(izq <= der){
            int mid= (izq+der) / 2;
            long t = Long.parseLong(partidas.get(mid)[3]);

            if(t == tiempoSeg){
                return mid;
            }else if(t < tiempoSeg){
                izq = mid + 1;
            }else{
                der=mid - 1;
            }
        }
        return -1;
    }

    //BÚSQUEDA POR NÚMERO DE PARTIDA
    public String[] buscarPorNumero(int numero){
        int indice = numero - 1;

        if(indice >= 0 && indice < partidas.size()){
            return partidas.get(indice);
        }
        return null;
    }

    //BÚSQUEDA POR RESULTADO DE VICTORIA O DERROTA
    public void buscarPorResultado(String resultado){
        boolean encontro = false;

        System.out.println();
        System.out.println(" Partidas con resultado:" + resultado);
        System.out.println("  +----------------------------------------------------+");

        for(int i=0; i < partidas.size();i++){
            String[] p = partidas.get(i);

            if(p[4].equalsIgnoreCase(resultado)){
                System.out.printf("  | %-3d %-14s %-7s %-7s %-4s %-9s |%n", i + 1, p[0], p[1], p[2], p[3], p[4]);
                encontro = true;
            }
        }

        if(!encontro){
            System.out.println("  No hay partidas con ese resultado.");
        }
        System.out.println("  +----------------------------------------------------+");
        System.out.println();
    }

    //BÚSQUEDA POR NIVEL
    public void buscarPorNivel(String nivel){
        boolean encontro = false;

        System.out.println();
        System.out.println("  Partida del nivel: " + nivel);
        System.out.println("  +----------------------------------------------------+");

        for(int i = 0; i < partidas.size(); i++){
            String[] p = partidas.get(i);

            if(p[0].equalsIgnoreCase(nivel)){
                System.out.printf("  | %-3d %-14s %-7s %-7s %-4s %-9s |%n", i + 1, p[0], p[1], p[2], p[3], p[4]);
                encontro = true;
            }
        }
        if(!encontro){
            System.out.println("  No hay partidas en ese nivel.");
        }
        System.out.println("  +----------------------------------------------------+");
        System.out.println();
    }

    //HISTORIAL + MENÚ DE BÚSQUEDA
    public void mostrar(Scanner scanner){
        if(partidas.isEmpty()){
            System.out.println("  No hay partidas registradas aun.");
            return;
        }

        organizar();

        System.out.println();
        System.out.println("  +====================================================+");
        System.out.println("  |           HISTORIAL DE PARTIDAS                    |");
        System.out.println("  +====================================================+");
        System.out.printf("  | %-3s %-14s %-7s %-7s %-4s %-9s |%n", "#", "Nivel", "Tablero", "Minas", "Seg", "Resultado");
        System.out.println("  +----------------------------------------------------+");

        for(int i = 0;i < partidas.size(); i++){
            String[] p = partidas.get(i);
            System.out.printf("  | %-3d %-14s %-7s %-7s %-4s %-9s |%n", i + 1, p[0], p[1], p[2], p[3], p[4]);   
        }

        System.out.println("  +----------------------------------------------------+");
        System.out.println();

        //MENÚ DE BÚSQUEDA
        boolean buscando = true;
        while(buscando){
            System.out.println("  +------------------------------------------+");
            System.out.println("  |           BUSCAR PARTIDA                 |");
            System.out.println("  +------------------------------------------+");
            System.out.println("  |  1. Buscar por numero de partida         |");
            System.out.println("  |  2. Buscar por resultado (victoria/      |");
            System.out.println("  |     derrota)                             |");
            System.out.println("  |  3. Buscar por nivel                     |");
            System.out.println("  |  4. Buscar por tiempo (segundos)         |");
            System.out.println("  |  5. Volver al menu principal             |");
            System.out.println("  +------------------------------------------+");
            System.out.print("  Elige una opcion: ");

            String opcion=scanner.nextLine().trim();

            switch(opcion){
                case "1": //BUSCAR POR NÚMERO DE PARTIDA
                    System.out.print("  Numero de partida: ");
                    try {
                        int num = Integer.parseInt(scanner.nextLine().trim());
                        String[] encontrada = buscarPorNumero(num);
                        if(encontrada != null){
                           System.out.println();
                           System.out.println("  Partida encontrada:");
                           System.out.printf("  | %-3d %-14s %-7s %-4s %-9s |%n", num, encontrada[0], encontrada[1], encontrada[2], encontrada[3], encontrada[4]);
                           System.out.println();
                        }else{
                          System.out.println("  No existe una partida con ese numero.");
                        }   
                    } catch (NumberFormatException e) {
                        System.out.println("  Ingresa un numero entero valido.");
                    }
                    break;

                case "2": //BUSCAR POR RESULTADO
                    System.out.println("  Opciones: VICTORIA/DERROTA");
                    System.out.print("  Resultado a buscar: ");
                    String resultado = scanner.nextLine().trim();
                    buscarPorResultado(resultado);
                    break;

                case "3": //BUSCAR POR NIVEL
                    System.out.println("  Opciones: Facil / Medio / Dificil / Personalizado");
                    System.out.print("  Nivel a buscar: ");
                    String nivel = scanner.nextLine().trim();
                    buscarPorNivel(nivel);
                    break;

                case "4": //BUSCAR POR TIEMPO
                    System.out.print("  Tiempo en segundos a buscar: ");
                    try {
                        long tiempo = Long.parseLong(scanner.nextLine().trim());
                        int indice = buscarPorTiempo(tiempo);
                        if(indice != -1){
                            String[] p = partidas.get(indice);
                            System.out.println();
                            System.out.println("  Partida encontrada (BinarySearch):");
                            System.out.printf("  #%-3d %-14s %-7s %-7s %-4s %-9s%n", indice + 1, p[0], p[1], p[2], p[3], p[4]);
                            System.out.println();
                        }else{
                            System.out.println("  No hay ninguna partida con ese tiempo.");
                        }      
                    } catch (NumberFormatException e) {
                        System.out.println("  Ingresa un numero entero valido.");
                    }
                    break;

                case "5":
                   buscando= false;
                   break;

                default:
                System.out.println("  Opcion no valida. Elige entre 1 y 5.");  
            }

        }

    }

}
