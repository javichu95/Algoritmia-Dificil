package practica2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Repeticiones {

	private static ArbolSufijos arbol;
	
	public static void main(String [] args){
		
		String cadena = "ATATCGTTTTATCGTT";
		/*try {
			Scanner scan = new Scanner(new File(args[0]));
			scan.nextLine();
			while(scan.hasNextLine()) {
				cadena = leerFichero(scan);*/
				arbol = new ArbolSufijos(cadena);
				arbol.crearArbol();
				arbol.compactar();
				System.out.println(arbol.mayorRepeticion());
			/*}
			scan.close();
		} catch(FileNotFoundException e) {
			System.err.println("Fichero no encontrado.");
		}*/
	}
	
	private static String leerFichero(Scanner scan) {
		String cadena = "";
		while(scan.hasNextLine()) {
			String aux = scan.nextLine();
			if(aux.charAt(0) == '>') {
				return cadena;
			} else {
				cadena = cadena + aux;
			}
		}
		return cadena;
	}
}
