package practica1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

public class Amazon {

	private static final int NUMPRODS = 200;	// Número total de productos.
	// Tabla que dice si un cierto producto ha sido comprado con otro.
	private static boolean parProductos [][] = new boolean[NUMPRODS][NUMPRODS];/*{{true, true, false, false, false, false},
												{true, true, true, false, false, false},
												{false, true, true, true, true, true},
												{false, false, true, true, true, true},
												{false, false, true, true, true, true},
												{false, false, true, true, true, true}
												};*/

	private static int pesosProductos [][] = new int [NUMPRODS][NUMPRODS];/*]{{0, 3, 0, 0, 0, 0},
																			{3, 0, 1, 0, 0, 0},
																			{0, 1, 0, 3, 2, 2},
																			{0, 0, 3, 0, 1, 4},
																			{0, 0, 2, 1, 0, 2},
																			{0, 0, 2, 4, 2, 0}
																			};*/
	// Datos de los productos.
	private static Hashtable<String,Producto> datosProductos = new Hashtable<String,Producto> ();
	// Empareja los índices de la tabla de pares con el producto original.
	private static Hashtable<String,String> indiceProd = new Hashtable<String,String> ();
	// Grafo con los vértices y aristas de los productos.
	private static Hashtable<String,Nodo> grafoKarger = new Hashtable<String,Nodo> ();
	private static Hashtable<String,Nodo> grafoKargerStein = new Hashtable<String,Nodo> ();
	
	public static void main(String[] args) {
		
		generarProductos();		// Método que genera la lista de productos.
		emparejarPesos();		// Método que empareja los productos comprados juntos.
		generarGrafoPesos(grafoKarger);		// Método que genera el grafo a partir de la lista de vértices.
		generarGrafoPesos(grafoKargerStein);		// Método que genera el grafo a partir de la lista de vértices.
		int[][] aux = new int[NUMPRODS][NUMPRODS];
		//Se hace una copia de la matriz de pesos.
		for(int i = 0; i<NUMPRODS;i++) {
			for(int j = i; j< NUMPRODS; j++) {
				aux[i][j] = pesosProductos[i][j];
				aux[j][i] = pesosProductos[j][i];
			}
		}
		//mostrarParejas();
		grafoKarger = kargerPesos(grafoKarger,2);
		System.out.println("Karger:");
		mostrarKarger(grafoKarger);
		//Se restaura la tabla a su estado anterior para realizar el algoritmo de Karger-Stein.
		for(int i = 0; i<NUMPRODS;i++) {
			for(int j = i; j< NUMPRODS; j++) {
				pesosProductos[i][j] = aux[i][j];
				pesosProductos[j][i] = aux[j][i];
			}
		}
		grafoKargerStein = karger_steinPesos(grafoKargerStein);		// Método que resuelve el algoritmo de karger_Stein.
		System.out.println("Karger Stein:");
		mostrarKarger(grafoKargerStein);
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
	 * Método que empareja los productos según hayan sido comprados juntos y cuantas veces lo han sido.
	 */
	private static void emparejarPesos(){
		
		Random random = new Random();		// Generador de números aleatorios.
		for(int i=0; i<datosProductos.size(); i++){	// Recorre la matriz en diagonal superior.
			for(int j=i; j<datosProductos.size(); j++){
				if(i==j){		// Si son el mismo producto, se marca a true.
					pesosProductos[i][j] = 0;
					pesosProductos[j][i] = 0;
				} else{		// Se genera número aleatorio para indicar si se han comprado juntos.
					int aleatorio = random.nextInt(101);
					pesosProductos[i][j] = aleatorio;
					pesosProductos[j][i] = aleatorio;
				}
			}
		}
	}
	
	/*
	 * Método que genera el grafo a partir de los productos emparejados.
	 */
	private static Hashtable<String,Nodo> generarGrafo(Hashtable<String,Nodo> grafo){
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
			// Introduce el vértice en el grafo.
			grafo.put(Integer.toString(i), nuevoVertice);
		}
		return grafo;
	}
	
	/*
	 * Método que genera el grafo a partir de los productos emparejados con pesos.
	 */
	private static Hashtable<String,Nodo> generarGrafoPesos(Hashtable<String,Nodo> grafo){
		for(int i=0; i<datosProductos.size(); i++){	// Recorre la matriz en diagonal superior.
			// Añade un vértice.
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
		return grafo;
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
	 * Método que muestra el grafo actual.
	 */
	/*private static void mostrarGrafo(){
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
	}*/
	
	/*
	 * Método que implementa el algoritmo de Karger para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static Hashtable<String,Nodo> karger(Hashtable<String,Nodo> grafo, int numNodos){
		int nodos = grafo.size(); 		// Número inicial de nodos.
		Random random = new Random();	// Se crea el objeto random.
		while(nodos > numNodos) {				// Mientras haya más de dos nodos.
			//Se obtienen los dos nodos que se van a unir.
			Object key [] = grafo.keySet().toArray();
			String claves [] = Arrays.copyOf(key,key.length,String[].class);
			// Primer vértice aleatorio.
			int random1 = random.nextInt(claves.length);
			Nodo vertice1 = grafo.get(claves[random1]);	// Claves de los vértices adyacentes.
			if(vertice1.numAristas() == 0){
				// El grafo tiene que ser conexo y se muestra error.
				System.err.println("El grafo tiene que ser conexo.");
				System.exit(1);
			} else{
				// Si tiene vértices adyacentes, se selecciona uno aleatorio.
				key = vertice1.getKeys().toArray();
				claves = Arrays.copyOf(key,key.length,String[].class);
				int random2 = random.nextInt(claves.length);
				// Se obtiene el vértice.
				Nodo vertice2 = grafo.get(claves[random2]);
				unir(vertice1,vertice2,grafo);		// Método que une los dos vértices en uno.
			}
			//Se reduce el numero de nodos.
			nodos--;
		}
		return grafo;
	}
	
	

	/*
	 * Método que implementa el algoritmo de Karger para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static Hashtable<String,Nodo> kargerPesos(Hashtable<String,Nodo> grafo, int numNodos){
		int nodos = grafo.size(); 		// Número inicial de nodos.
		Random random = new Random();	// Se crea el objeto random.
		while(nodos > numNodos) {				// Mientras haya más de dos nodos.
			//Se obtienen los dos nodos que se van a unir.
			Object key [] = grafo.keySet().toArray();
			String claves [] = Arrays.copyOf(key,key.length,String[].class);
			// Primer vértice aleatorio.
			int random1 = random.nextInt(claves.length);
			Nodo vertice1 = grafo.get(claves[random1]);	// Claves de los vértices adyacentes.
			if(vertice1.numAristas() == 0){
				// El grafo tiene que ser conexo y se muestra error.
				System.err.println("El grafo tiene que ser conexo.");
				System.exit(1);
			} else{
				// Si tiene vértices adyacentes, se selecciona uno aleatorio.
				key = vertice1.getKeys().toArray();
				claves = Arrays.copyOf(key,key.length,String[].class);
				int random2 = random.nextInt(claves.length);
				// Se obtiene el vértice.
				Nodo vertice2 = grafo.get(claves[random2].substring(0,claves[random2].lastIndexOf('_')));;
				unirPesos(vertice1,vertice2,grafo);		// Método que une los dos vértices en uno.
			}
			//Se reduce el numero de nodos.
			nodos--;
		}
		return grafo;
	}
	
	
	/*
	 * Método que une dos ciertos vértices en uno sólo, combinando sus aristas.
	 */
	private static void unir(Nodo vertice1, Nodo vertice2, Hashtable<String,Nodo> grafo){
		// Obtiene las aristas del primer vértice.
		Hashtable<String,Nodo> aristas1 = vertice1.getAristas();
		// Combina en el primer vértice las aristas del segundo.
		aristas1.putAll(vertice2.getAristas());
		// Elimina los propios vértices de los adyacentes (no generar aristas a si mismo).
		aristas1.remove(vertice1.getClave());
		aristas1.remove(vertice2.getClave());
		// Se borra el vértice 2 de la lista de vértices.
		grafo.remove(vertice2.getClave());
		// Se añade el vértice a la lista de combinados para saber los conjuntos.
		vertice1.anadirProd(vertice2.getClave(),vertice2.getProdCombinados());
		Object key [] = grafo.keySet().toArray();
		String claves [] = Arrays.copyOf(key,key.length,String[].class);
		for(int i=0; i<claves.length; i++){		
			// Se recorren el resto de vértices para actualizar con la nueva unión.
			Hashtable<String,Nodo> aristas = grafo.get(claves[i]).getAristas();
			// Si tiene el segundo vértice en su lista...
			if(grafo.get(claves[i]).getArista(vertice2.getClave()) != null){
				// Si tiene el primer y el segundo vértice...
				if(grafo.get(claves[i]).getArista(vertice1.getClave()) != null){
					// Se borra el segundo vértice.
					aristas.remove(vertice2.getClave());
				} else{		// Si tiene sólo el segundo vértice...
					// Se añade un vértice con el nombre del primero.
					aristas.put(vertice1.getClave(), new Nodo(vertice1.getClave()));
					// Se elimina el segundo vértice.
					aristas.remove(vertice2.getClave());
				}
			}
		}
	}
	
	/*
	 * Método que une dos ciertos vértices en uno sólo, combinando sus aristas.
	 */
	private static void unirPesos(Nodo vertice1, Nodo vertice2, Hashtable<String,Nodo> grafo){
		// Obtiene las aristas del primer vértice.
		Hashtable<String,Nodo> aristas1 = vertice1.getAristas();
		// Combina en el primer vértice las aristas del segundo.
		aristas1.putAll(vertice2.getAristas());
		// Elimina los propios vértices de los adyacentes (no generar aristas a si mismo).
		for(int i = 0; i < pesosProductos[Integer.parseInt(vertice1.getClave())][Integer.parseInt(vertice2.getClave())]; i++) {
			aristas1.remove(vertice1.getClave()+"_"+i);
			aristas1.remove(vertice2.getClave()+"_"+i);
		}
		// Se borra el vértice 2 de la lista de vértices.
		grafo.remove(vertice2.getClave());
		// Se añade el vértice a la lista de combinados para saber los conjuntos.
		vertice1.anadirProd(vertice2.getClave(),vertice2.getProdCombinados());
		Object key [] = grafo.keySet().toArray();
		String claves [] = Arrays.copyOf(key,key.length,String[].class);
		for(int i=0; i<claves.length; i++){		
			// Se recorren el resto de vértices para actualizar con la nueva unión.
			Hashtable<String,Nodo> aristas = grafo.get(claves[i]).getAristas();
			// Si tiene el segundo vértice en su lista...
			if(grafo.get(claves[i]).getArista(vertice2.getClave()+"_0") != null){
					//Se hace para todas las aristas entre los vertices.
					for(int j = 0; j < pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice2.getClave())]; j++) {
						// Se añade un vértice con el nombre del primero.
						aristas.put(vertice1.getClave()+ "_" +
								(pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice1.getClave())]+j), 
									new Nodo(vertice1.getClave()));
						// Se elimina el segundo vértice.
						aristas.remove(vertice2.getClave()+"_"+j);
					}
					//Se actuealiza la matriz de pesos con las aristas añadidas.
					int nuevoPeso = 
							pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice1.getClave())] +
							pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice2.getClave())];
					pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice1.getClave())] = nuevoPeso;
					pesosProductos[Integer.parseInt(vertice1.getClave())][Integer.parseInt(claves[i])] = nuevoPeso;
			}
		}
	}
	
	/*
	 * Método que muestra el resultado final del algoritmo de karger.
	 */
	private static void mostrarKarger(Hashtable<String,Nodo> grafo){
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
			System.out.println("Tamaño: " + claves2.size());	// Muestra el tamaño del conjunto.
		}
	}
	
	/*
	 * Método que implementa el algoritmo de Karger-Stein para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static Hashtable<String,Nodo> karger_stein(Hashtable<String,Nodo> grafo){
		if(grafo.size() <= 6) {
			//Si el número de nodos es pequeño se llama una sola vez a Karger.
			return karger(grafo,2);
		} else {
			int numNodos = (int)(1 + grafo.size() / Math.sqrt(2.0));	//Se calcula el numero de nodos a los que se reducira.
			//Se llama dos veces a Karger.
			Hashtable<String,Nodo> grafo1 = karger(grafo,numNodos);
			Hashtable<String,Nodo> grafo2 = karger(grafo,numNodos);
			//Se devuelve el mejor de los dos.
			return min(karger_stein(grafo1),karger_stein(grafo2));
		}
	}
	
	/*
	 * Método que implementa el algoritmo de Karger-Stein para realizar una partición
	 * de los productos cercana a la óptima (con pesos).
	 */
	private static Hashtable<String,Nodo> karger_steinPesos(Hashtable<String,Nodo> grafo){
		if(grafo.size() <= 6) {
			//Si el número de nodos es pequeño se llama una sola vez a Karger.
			return kargerPesos(grafo,2);
		} else {
			int numNodos = (int)(1 + grafo.size() / Math.sqrt(2.0));	//Se calcula el numero de nodos a los que se reducira.
			int[][] aux = new int[NUMPRODS][NUMPRODS];
			//Se guarda una copia de la tabla actual.
			for(int i = 0; i<NUMPRODS;i++) {
				for(int j = i; j< NUMPRODS; j++) {
					aux[i][j] = pesosProductos[i][j];
					aux[j][i] = pesosProductos[j][i];
				}
			}
			Hashtable<String,Nodo> grafo1 = kargerPesos(grafo,numNodos);	//Se llama a Karger.
			grafo1 = karger_steinPesos(grafo1);			//Se llama recursivamente.
			//Se restaura la tabla de pesos a su estado anterior para la segunda llamada a Karger.
			for(int i = 0; i<NUMPRODS;i++) {
				for(int j = i; j< NUMPRODS; j++) {
					pesosProductos[i][j] = aux[i][j];
					pesosProductos[j][i] = aux[j][i];
				}
			}
			Hashtable<String,Nodo> grafo2 = kargerPesos(grafo,numNodos);	//Se llama a Karger.
			grafo2 = karger_steinPesos(grafo2);		//Se llama recursivamente.
			//Se devuelve el mejor de los dos.
			return min(grafo1,grafo2);
		}
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
	
	/*
	 * Devuelve el grafo que esta mas proximo a un corte minimo.
	 */
	private static Hashtable<String,Nodo> min(Hashtable<String,Nodo> grafo1, Hashtable<String,Nodo> grafo2) {
		Object key [] = grafo1.keySet().toArray();
		String claves [] = Arrays.copyOf(key,key.length,String[].class);
		int numAristas1 = 0;
		for(int i = 0; i < claves.length; i++) {
			numAristas1 += grafo1.get(claves[i]).numAristas();
		}
		key = grafo2.keySet().toArray();
		claves = Arrays.copyOf(key,key.length,String[].class);
		int numAristas2 = 0;
		for(int i = 0; i < claves.length; i++) {
			numAristas2 += grafo2.get(claves[i]).numAristas();
		}
		if(numAristas2 < numAristas1) {
			return grafo2;
		} else {
			return grafo1;
		}
	}
}
