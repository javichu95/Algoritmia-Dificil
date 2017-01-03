package Practica1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

/**
 * Clase que contiene todos los métodos necesarios para generar el grafo
 * y trabajar con él.
 */
public class Grafo {

	public static int NUMPRODS;	// Número total de productos.
	// Tabla que dice si un cierto producto ha sido comprado con otro.
	public static boolean parProductos [][];
	// Tabla con las veces que un producto se ha comprado con otro.
	public static int pesosProductos [][];
	// Datos de los productos.
	public static Hashtable<String,Producto> datosProductos = new Hashtable<String,Producto> ();
	// Empareja los índices de la tabla de pares con el producto original.
	public static Hashtable<String,String> indiceProd = new Hashtable<String,String> ();
	// Grafo con los vértices y aristas de los productos.
	public static Hashtable<String,Nodo> grafo = new Hashtable<String,Nodo> ();
	
	/*
	 * Método que genera productos con valores aleatorios.
	 */
	public static void generarProductos(){
		
		Random random = new Random();	// Generador de números aleatorios.
		for(int i=0; i<NUMPRODS; i++){	// Bucle que genera los productos.
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
	 * Método que empareja los productos según el caso de prueba.
	 */
	public static void emparejar(int caso, String algoritmo){
		if(caso<=4){
			NUMPRODS = 6;
			generarProductos();
			parProductos = new boolean[][]{{true, true, false, false, false, false},
					{true, true, true, false, false, false},
					{false, true, true, true, true, true},
					{false, false, true, true, true, true},
					{false, false, true, true, true, true},
					{false, false, true, true, true, true}
					};
			if(caso ==  1 || caso == 2){
				pesosProductos = new int[][]{{1, 1, 0, 0, 0, 0},
						{1, 1, 1, 0, 0, 0},
						{0, 1, 1, 1, 1, 1},
						{0, 0, 1, 1, 1, 1},
						{0, 0, 1, 1, 1, 1},
						{0, 0, 1, 1, 1, 1}
						};
				generarGrafo();
			} else{
				pesosProductos =  new int[][]{{0, 3, 0, 0, 0, 0},
						{3, 0, 1, 0, 0, 0},
						{0, 1, 0, 3, 2, 2},
						{0, 0, 3, 0, 1, 4},
						{0, 0, 2, 1, 0, 2},
						{0, 0, 2, 4, 2, 0}
						};
				generarGrafoPesos();
			}
		} else{
			NUMPRODS = 10;
			generarProductos();
			if(algoritmo.equals("karger") || algoritmo.equals("karger_stein")){
				emparejarRandom();
				generarGrafo();
			} else{
				emparejarPesosRandom();
				generarGrafoPesos();
			}
		}
	}
	
	/*
	 * Método que empareja los productos según hayan sido comprados juntos
	 * alguna vez o no de manera aleatoria.
	 */
	public static void emparejarRandom(){
		
		parProductos = new boolean [NUMPRODS][NUMPRODS];
		pesosProductos = new int [NUMPRODS][NUMPRODS];
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
						// Se indica que hay una arista entre esos elementos.
						pesosProductos[i][j] = 1;
						pesosProductos[j][i] = 1;
					}
				}
			}
		}
	}
	
	/*
	 * Método que empareja los productos según hayan sido comprados juntos y 
	 * cuantas veces lo han sido de manera aleatoria.
	 */
	public static void emparejarPesosRandom(){
		
		pesosProductos = new int [NUMPRODS][NUMPRODS];
		Random random = new Random();		// Generador de números aleatorios.
		for(int i=0; i<datosProductos.size(); i++){	// Recorre la matriz en diagonal superior.
			for(int j=i; j<datosProductos.size(); j++){
				if(i==j){		// Si son el mismo producto, se marca a true.
					pesosProductos[i][j] = 0;
					pesosProductos[j][i] = 0;
				} else{		// Se genera número aleatorio para indicar si se han comprado juntos.
					int aleatorio = random.nextInt(5);
					pesosProductos[i][j] = aleatorio;
					pesosProductos[j][i] = aleatorio;
				}
			}
		}
	}
	
	/*
	 * Método que genera el grafo a partir de los productos emparejados.
	 */
	public static Hashtable<String,Nodo> generarGrafo(){
		
		for(int i=0; i<datosProductos.size(); i++){	// Recorre la matriz .
			// Crea un vértice.
			Nodo nuevoVertice = new Nodo(Integer.toString(i));
			for(int j=0; j<datosProductos.size(); j++){
				if(i!=j && parProductos[i][j]){
					// Añadir arista al vértice i con vértice j.
					Nodo verticeUnido = new Nodo(Integer.toString(j));
					String clave = Integer.toString(j)+"_"+0;	// Código de la arista.
					nuevoVertice.anadirArista(clave,verticeUnido);
				}
			}
			// Introduce el vértice en el grafo.
			grafo.put(Integer.toString(i), nuevoVertice);
		}
		
		return grafo;		// Devuelve el grafo.
	}
	
	/*
	 * Método que genera el grafo a partir de los productos emparejados con pesos.
	 */
	public static Hashtable<String,Nodo> generarGrafoPesos(){
		
		for(int i=0; i<datosProductos.size(); i++){	// Recorre la matriz.
			// Crea el nuevo vértice.
			Nodo nuevoVertice = new Nodo(Integer.toString(i));
			for(int j=0; j<datosProductos.size(); j++){
				if(i!=j){
					// Añadir arista al vértice i con vértice j.
					Nodo verticeUnido = new Nodo(Integer.toString(j));
					for(int k = 0; k < pesosProductos[i][j]; k++) {
						nuevoVertice.anadirArista(Integer.toString(j)+"_"+k,verticeUnido);
					}
				}
			}
			// Introduce el vértice en el grafo.
			grafo.put(Integer.toString(i), nuevoVertice);
		}
		
		return grafo;		// Devuelve el grafo.
	}
	
	/*
	 * Método que muestra el grafo actual.
	 */
	public static void mostrarGrafo(){
		
		for(int i=0; i< NUMPRODS; i++){		// Recorre los vértices obteniendo las aristas.
			if(grafo.get(Integer.toString(i)) != null){
				Nodo nodo = grafo.get(Integer.toString(i));
				System.out.print(indiceProd.get(Integer.toString(i)) + ": ");
				Object key [] = nodo.getAristas().keySet().toArray();
				String claves [] = Arrays.copyOf(key,key.length,String[].class);
				// Recorre las aristas del vértice.
				for(int k=0; k < claves.length; k++){
					// Muestra las aristas por pantalla.
					System.out.print(indiceProd.get(claves[k].substring(0,claves[k].indexOf("_"))) + " ");
				}
				System.out.println();
			}
		}
	}
	
	/*
	 * Método que muestra el resultado final del grafo.
	 */
	public static void mostrar(){
		
		System.out.println();
		Object key [] = grafo.keySet().toArray();
		String claves [] = Arrays.copyOf(key,key.length,String[].class);
		
		for(int i=0; i<grafo.size(); i++){		// Recorre la tabla obteniendo los conjuntos.
			System.out.print("Conjunto " + i + ": " + indiceProd.get(claves[i]) + " ");
			Nodo nodo = grafo.get(claves[i]);
			ArrayList<String> claves2 = nodo.getProdCombinados();
			for(int j=0; j<claves2.size(); j++){		// Muestra los elementos de un conjunto.
				System.out.print(indiceProd.get(claves2.get(j)) + " ");
			}
			System.out.println();
			System.out.println("Tamaño: " + (claves2.size()+1));	// Muestra el tamaño del conjunto.
		}
	}
}
