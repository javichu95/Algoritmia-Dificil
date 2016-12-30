package Practica1;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

public class Amazon {

	private static final int NUMPRODS = 10;	// Número total de productos.
	// Tabla que dice si un cierto producto ha sido comprado con otro.
	private static boolean parProductos [][] = new boolean [NUMPRODS][NUMPRODS];
	// Datos de los productos.
	private static Hashtable<String,Producto> datosProductos = new Hashtable<String,Producto> ();
	// Empareja los índices de la tabla de pares con el producto original.
	private static Hashtable<String,String> indiceProd = new Hashtable<String,String> ();
	// Grafo con los vértices y aristas de los productos.
	private static Hashtable<String,Nodo> grafo = new Hashtable<String,Nodo> ();
	
	public static void main(String[] args) {
		
		generarProductos();		// Método que genera la lista de productos.
		emparejar();		// Método que empareja los productos comprados juntos.
		generarGrafo();		// Método que genera el grafo a partir de la lista de vértices.
		mostrarParejas();
		karger();
		mostrarKarger();
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
			// Correspondencia entre el producto y su índice en la tabla de pares.
			indiceProd.put(Integer.toString(i), nombre);
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
	 * Método que genera el grafo a partir de los productos emparejados.
	 */
	private static void generarGrafo(){
		for(int i=0; i<datosProductos.size(); i++){	// Recorre la matriz en diagonal superior.
			// Añade un vértice.
			Nodo nuevoVertice = new Nodo(Integer.toString(i));
			for(int j=0; j<datosProductos.size(); j++){
				if(i!=j && parProductos[i][j]){
					// Añadir arista al vértice i con vértice j.
					Nodo verticeUnido = new Nodo(Integer.toString(j));
					nuevoVertice.anadirArista(Integer.toString(j),verticeUnido);
				}
			}
			grafo.put(Integer.toString(i), nuevoVertice);
		}
	}
	
	/*
	 * Muestra parejas de productos.
	 */
	private static void mostrarParejas() {
		System.out.println("Parejas de productos:");
		for(int i = 0; i < NUMPRODS; i++) {
			System.out.print(indiceProd.get(Integer.toString(i))+ ": ");
			for(int j = 0; j < NUMPRODS; j++) {
				if(i!=j && parProductos[i][j]) {
					System.out.print(indiceProd.get(Integer.toString(j)) + " ");
				}
			}
			System.out.println();
		}
	}
	
	/*
	 * Muestra el grafo actual.
	 */
	private static void mostrarGrafo(){
		for(int i=0; i< NUMPRODS; i++){
			if(grafo.get(Integer.toString(i)) != null){
				Nodo nodo = grafo.get(Integer.toString(i));
				System.out.print(indiceProd.get(Integer.toString(i)) + ": ");
				for(int j=0; j < NUMPRODS; j++){
					if(nodo.getArista(Integer.toString(j)) != null){
						System.out.print(indiceProd.get(nodo.getArista(Integer.toString(j)).getClave()) + " ");
					}
				}
				System.out.println();
			}
		}
	}
	
	/*
	 * Método que implementa el algoritmo de Karger para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static void karger(){
		int nodos = NUMPRODS; 		//Numero inicial de nodos.
		Random random = new Random();	//Se crea el objeto random.
		while(nodos > 2) {				//Mientras haya mas de dos nodos.
			System.out.println("\n");
			mostrarGrafo();
			//Se obtienen los dos nodos que se van a unir.
			Object key [] = grafo.keySet().toArray();
			String claves [] = Arrays.copyOf(key,key.length,String[].class);
			int random1 = random.nextInt(claves.length);
			Nodo vertice1 = grafo.get(claves[random1]);
			System.out.println("vertice1: " + vertice1.getClave());
			if(vertice1.numAristas() == 0){
				// Unir sin más.
			} else{
				key = vertice1.getKeys().toArray();
				claves = Arrays.copyOf(key,key.length,String[].class);
				int random2 = random.nextInt(claves.length);
				Nodo vertice2 = grafo.get(claves[random2]);
				System.out.println("vertice2: " + vertice2.getClave());
				// Unir los dos vértices.
				unir(vertice1,vertice2);
				// Vértice1 tiene clave aleatorio1 y Vértice2 tiene clave aleatorio2.
			}
			//Se reduce el numero de nodos.
			nodos--;
		}
		//mostrarConjuntos(unidos);		//Se muestran los conjuntos.
	}
	
	/*
	 * Método que une dos ciertos vértices en uno sólo.
	 */
	private static void unir(Nodo vertice1, Nodo vertice2){
		Hashtable<String,Nodo> aristas1 = vertice1.getAristas();
		aristas1.putAll(vertice2.getAristas());
		vertice1.anadirProd(vertice2.getClave());
		Object key [] = grafo.keySet().toArray();
		String claves [] = Arrays.copyOf(key,key.length,String[].class);
		for(int i=0; i<claves.length; i++){
			if(grafo.get(claves[i]).getArista(vertice2.getClave()) != null){
				// Se modifica el nombre de la arista.
				Hashtable<String,Nodo> aristas = grafo.get(claves[i]).getAristas();
				aristas.remove(vertice2.getClave());
			}
		}
		// Se borra el nodo vertice2.
		grafo.remove(vertice2.getClave());
	}
	
	/*
	 * Método que muestra el resultado final del algoritmo de karger.
	 */
	private static void mostrarKarger(){
		Object key [] = grafo.keySet().toArray();
		String claves [] = Arrays.copyOf(key,key.length,String[].class);
		for(int i=0; i<grafo.size(); i++){
			System.out.print("Conjunto " + i + ": " + indiceProd.get(claves[i]) + " ");
			Nodo nodo = grafo.get(claves[i]);
			Object key2 [] = nodo.getAristas().keySet().toArray();
			String claves2 [] = Arrays.copyOf(key2,key2.length,String[].class);
			for(int j=0; j<nodo.numAristas(); j++){
				System.out.print(indiceProd.get(claves2[j]) + " ");
			}
			System.out.println();
		}
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
	private static boolean comprobarKarger(boolean[][] unidos,boolean[][] pares) {
		boolean terminado = false;
		for(int i = 0; i < NUMPRODS; i++) {
			for(int j = 0; j < NUMPRODS; j++) {
				//Si existen dos vertices conexos y que no estan unidos en un conjunto, se puede seguir.
				if(pares[i][j] && !unidos[i][j]) {
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
}
