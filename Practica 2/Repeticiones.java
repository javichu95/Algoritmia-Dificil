package practica2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
				cadena = cadena +"$";
				arbol = new ArbolSufijos(cadena);
				arbol.crearArbol();
				arbol.compactar();
				System.out.println("Mayor repeticion: "+arbol.mayorRepeticion());
				ArrayList<String> maximales = arbol.maximales();
				System.out.println("Maximales: ");
				for(int i = 0; i < maximales.size(); i++) {
					System.out.println(maximales.get(i));
				}
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
