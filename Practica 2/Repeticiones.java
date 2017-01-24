package practica2;

public class Repeticiones {

	public static void main(String [] args){
		
		String cadena = "aaabbbc";
		ArbolSufijos arbol = new ArbolSufijos(cadena);
		arbol.crearArbol();
		arbol.mostrarArbol();
		System.out.println();
		System.out.println();
		arbol.compactar();
		arbol.mostrarEtiqHojas();
	}
	
}
