package practica1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Amazon {

	private static final int NUMPRODS = 20;
	private static boolean parProductos [][] = new boolean [NUMPRODS][NUMPRODS];
	private static Hashtable<String,Producto> datosProductos = new Hashtable<String,Producto> ();
	
	public static void main(String[] args) {
		
		generarProductos();		// Método que genera la lista de productos.
		emparejar();		// Método que empareja los productos comprados juntos.
		//mostrarParejas();
		karger();
	}

	/*
	 * Método que genera productos con valores aleatorios.
	 */
	private static void generarProductos(){
		
		Random random = new Random();	// Generador de números aleatorios.
		for(int i=0; i<parProductos.length; i++){	// Bucle que genera los productos.
			String nombre = "producto" + i;	// Nombre del producto.
			int unidades = random.nextInt(100);	// Unidades del producto.
			double precio = random.nextDouble()*100.0;	// Precio del producto.
			double descuento = random.nextDouble()%0.5;	// Descuento del producto.
			String marca = "marca" + i%5;		// Marca del producto.
			// Introduce el producto en la tabla hash.
			datosProductos.put(nombre,new Producto(nombre,unidades,precio,descuento,marca));
		}
	}
	
	/*
	 * Método que empareja los productos según hayan sido comprados juntos
	 * alguna vez o no.
	 */
	private static void emparejar(){
		
		Random random = new Random();		// Generador de números aleatorios.
		for(int i=0; i<datosProductos.size(); i++){	// Recorre la matriz en diagonal superior.
			for(int j=i; j<datosProductos.size(); j++){
				if(i==j){		// Si son el mismo producto, se marca a true.
					parProductos[i][j] = true;
				} else{		// Se genera número aleatorio para indicar si se han comprado juntos.
					int aleatorio = random.nextInt();
					if(aleatorio%2 == 0){
						parProductos[i][j] = true;
						parProductos[j][i] = true;
					}
				}
			}
		}
	}
	
	/*
	 * Muestra parejas de productos.
	 */
	private static void mostrarParejas() {
		System.out.println("Parejas de productos:");
		for(int i = 0; i < NUMPRODS; i++) {
			for(int j = i; j < NUMPRODS; j++) {
				if(parProductos[i][j]) {
					System.out.println("producto" + i + " producto" + j);
				}
			}
		}
	}
	/*
	 * Método que implementa el algoritmo de Karger para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static void karger(){
		int nodos = NUMPRODS; 		//Numero inicial de nodos.
		//tabal que representa los vertice que se encuentran unidos formando un nodo.
		boolean[][] unidos = new boolean[parProductos.length][parProductos[0].length];
		for(int i = 0; i < NUMPRODS; i++) {		//Un producto ya esta unid consigo mismo.
			unidos[i][i] = true;
		}
		Random random = new Random();	//Se crea el objeto random.
		while(nodos > 2) {				//Mientras haya mas de dos nodos.
			if(comprobarKarger(unidos)) {		//Se comprueba que s epuede seguir usando el algoritmo.
				//Se obtienen los dos nodos que se van a unir.
				int aleatorio1 = random.nextInt(NUMPRODS);
				int aleatorio2 = random.nextInt(NUMPRODS);
				//Se comprueba que no esten unidos y que se puedan unir, si no se vuelven a generar.
				while(!parProductos[aleatorio1][aleatorio2] || unidos[aleatorio1][aleatorio2]) {
					aleatorio1 = random.nextInt(NUMPRODS);
					aleatorio2 = random.nextInt(NUMPRODS);
				}
				//Se unen los conjuntos de ambos números.
				unidos = unirConjuntos(unidos, aleatorio1, aleatorio2);
				//Se reduce el numero de nodos.
				nodos--;
			} else {			//Si no se puede continuar se fuerza la finalización.
				nodos = 2;
			}
		}
		mostrarConjuntos(unidos);		//Se muestran los conjuntos.
	}
	
	/*
	 * Método que implementa el algoritmo de Karger-Stein para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static void karger_stein(){
		
	}
	
	
	/*
	 * Comprueba que s epueda seguir aplicando el algoritmo de karger.
	 */
	private static boolean comprobarKarger(boolean[][] unidos) {
		boolean terminado = false;
		for(int i = 0; i < NUMPRODS; i++) {
			for(int j = 0; j < NUMPRODS; j++) {
				//Si existen dos vertices conexos y que no estan unidos en un conjunto, se puede seguir.
				if(parProductos[i][j] && !unidos[i][j]) {
					terminado = true;		//Si se puede seguir se fuerza la salida del bucle.
					break;
				}
			}
			if(terminado) {
				break;
			}
		}
		return terminado;
	}
	
	/*
	 * Une los conjuntos disjuntos en los que se encuentran dos números.
	 */
	private static boolean[][] unirConjuntos(boolean[][] unidos, int aleatorio1, int aleatorio2) {
		//Se unen ambos conjuntos.
		for(int i = 0; i < NUMPRODS; i++) {
			if(unidos[aleatorio1][i]) {
				unidos[aleatorio2][i] = true;
				unidos[i][aleatorio2] = true;
			}
		}
		for(int i = 0; i < NUMPRODS; i++) {
			if(unidos[aleatorio2][i]) {
				unidos[aleatorio1][i] = true;
				unidos[i][aleatorio1] = true;
			}
		}
		return unidos;
	}
	
	/*
	 * Metodo que muestra los conjuntos creados.
	 */
	private static void mostrarConjuntos(boolean[][] unidos) {
		//Se crea una array que contendra el conjunto1.
		ArrayList<Integer> conjunto1 = new ArrayList<Integer>();
		//Se muestra el conjunto1.
		System.out.print("Conjunto 1: ");
		for(int i = 0; i < NUMPRODS; i++) {
			if(unidos[0][i]) {
				System.out.print("producto" + i + " ");
				conjunto1.add(i);		//Se añade al array.
			}
		}
		System.out.println();
		//Se muestra el conjunto2.
		System.out.print("Conjunto 2: ");
		for(int i = 0; i < NUMPRODS; i++) {
			if(!conjunto1.contains(i)) {		//Si no esta en el array del cojunto1, se muestra.
				System.out.print("producto" + i + " ");
			}
		}
	}
}
