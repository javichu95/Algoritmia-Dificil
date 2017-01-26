package practica2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal que lee los argumentos y lanza el programa
 * para crear el árbol de sufijos y calcular las cadenas según
 * convenga.
 */
public class Repeticiones {

	private static ArbolSufijos arbol;		// Árbol de sufijos.
	private static String cadena;		// Cadena para crear el árbol.
	
	/*
	 * Método principal que lanza el programa.
	 */
	public static void main(String [] args){
		
		args = new String[2];
		args[0] = "-c";
		args[1] = "bcbabaca";
		
		comprobarArgumentos(args);
		
		System.out.println("Cadena: " + cadena);
		
		System.out.println("Creando árbol...\n");
		
		// Se crea el árbol y compacta.
		arbol = new ArbolSufijos(cadena);
		arbol.crearArbol();
		arbol.compactar();
		
		// Muestra la mayor repetición exacta.
		System.out.println("Mayor repeticion: " + arbol.mayorRepeticion() + "\n");
		
		// Se obtienen maximales y se muestran.
		ArrayList<String> maximales = arbol.maximales();
		System.out.println("Maximales: ");
		for(int i = 0; i < maximales.size(); i++) {
			System.out.println(maximales.get(i));
		}
		
		System.out.println("Fin de ejecución.");
		
	}
	
	/*
	 * Método que comprueba los argumentos.
	 */
	private static void comprobarArgumentos(String [] args){
		
		if(args.length != 2){	// Se mira el número de argumentos.
			System.err.println("Usar: java [-c|-f] [cadena|fichero]");
			System.exit(1);
		}
		// Se comprueba argumentos correctos.
		if(!args[0].equals("-c") && !args[0].equals("-f")){
			System.err.println("Usar: java [-c|-f] [cadena|fichero]");
			System.exit(1);
		} else{
			if(args[0].equals("-c")){
				cadena = args[1];
			} else{
				try{
					leerFichero(new Scanner(new File(args[1])));
				} catch(FileNotFoundException e){
					System.err.println("Fichero " + args[1] + " no encontrado.");
					System.exit(1);
				}	
			}
		}
	}
	
	/*
	 * Método que lee la cadena de un fichero.
	 */
	private static void leerFichero(Scanner scan) {
		
		System.out.println("Leyendo cadena de fichero...");
		String cadenaLeida = "";		// Cadena total leída.
		
		while(scan.hasNextLine()) {		// Se leen todas las líneas...
			String aux = scan.nextLine();
			if(aux.charAt(0) != '>') {		// Si no es cabecera se lee...
				cadenaLeida = cadenaLeida + aux;
			}
		}
		
		cadena = cadenaLeida;	
		scan.close();		// Se cierra la entrada.
	}
}
