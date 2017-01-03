package practica1;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

/**
 * Clase que contiene todos los métodos necesarios para implementar
 * los algoritmos que resuelven el problema de Amazon.
 */
public class Amazon {
	
	/*
	 * Método principal que lanza la ejecución.
	 */
	public static void main(String[] args) {
		
		comprobarArgumentos(args);		// Comprueba los argumentos de la llamada.
		
		if(args[0].equals("-alg")){		// Si es un ejemplo aleatorio...
			switch(args[1]){
			case "karger":
				System.out.println("Ejemplo aleatorio con Karger:");
				Grafo.emparejar(5,args[1]);		// Método que empareja los productos comprados juntos.
				Grafo.mostrarGrafo();			// Se muestra el grafo.
				Grafo.grafo = karger(Grafo.grafo,2);		// Método que resuelve el algoritmo de karger.
				Grafo.mostrar();		// Se muestra el resultado final.
				break;
			case "karger_stein":
				System.out.println("Ejemplo aleatorio con Karger-Stein:");
				Grafo.emparejar(5,args[1]);		// Método que empareja los productos comprados juntos.
				Grafo.mostrarGrafo();		// Se muestra el grafo.
				Grafo.grafo = karger_stein(Grafo.grafo);		// Método que resuelve el algoritmo de karger_stein.
				Grafo.mostrar();		// Método que muestra el resultado final.
				break;
			case "karger_steinPesos":
				System.out.println("Ejemplo aleatorio con Karger-Stein con pesos:");
				Grafo.emparejar(5,args[1]);		// Método que empareja los productos comprados juntos.
				Grafo.mostrarGrafo();			// Se muestra el grafo.
				Grafo.grafo = karger_steinPesos(Grafo.grafo);		// Método que resuelve el algoritmo de karger_stein con pesos.
				Grafo.mostrar();		// Método que muestra el resultado final.
				break;
			case "kargerPesos":
				System.out.println("Ejemplo aleatorio con Karger con pesos:");
				Grafo.emparejar(5,args[1]);		// Método que empareja los productos comprados juntos.
				Grafo.mostrarGrafo(); 		// Se muestra el grafo.
				Grafo.grafo = kargerPesos(Grafo.grafo,2);		// Método que resuelve el algoritmo de karger con pesos.
				Grafo.mostrar();		// Método que muestra el resultado final.
				break;
			default:			// Error con los argumentos
				System.err.println("Usar para ejemplo random:");
				System.err.println("java Amazon -alg [karger|karger_stein|"
						+ "kargerPesos|karger_steinPesos]");
				System.err.println("Usar para ejemplo:");
				System.err.println("java Amazon -ejemplo [1|2|3|4]");
				System.exit(0);
			}
		} else if(args[0].equals("-ejemplo")){		// Si es un ejemplo dado...
			switch(args[1]){
			case "1":
				System.out.println("Ejemplo con Karger:");
				Grafo.emparejar(1,null);		// Método que crea el ejemplo con los datos.
				Grafo.mostrarGrafo(); 		// Se muestra el grafo.
				Grafo.grafo = karger(Grafo.grafo,2);		// Método que resuelve el algoritmo de karger.
				Grafo.mostrar();		// Método que muestra el resultado final.
				break;
			case "2":
				System.out.println("Ejemplo con Karger-Stein:");
				Grafo.emparejar(2,null);		// Método que crea el ejemplo con los datos.
				Grafo.mostrarGrafo(); 		// Se muestra el grafo.
				Grafo.grafo = karger_stein(Grafo.grafo);		// Método que resuelve el algoritmo de karger_stein.
				Grafo.mostrar();		// Método que muestra el resultado final.
				break;
			case "3":
				System.out.println("Ejemplo con Karger con pesos:");
				Grafo.emparejar(3,null);		// Método que crea el ejemplo con los datos.
				Grafo.mostrarGrafo(); 		// Se muestra el grafo.
				Grafo.grafo = kargerPesos(Grafo.grafo,2);		// Método que resuelve el algoritmo de karger con pesos.
				Grafo.mostrar();		// Método que muestra el resultado final.
				break;
			case "4":
				System.out.println("Ejemplo con Karger-Stein con pesos");
				Grafo.emparejar(4,null);		// Método que crea el ejemplo con los datos.
				Grafo.mostrarGrafo(); 		// Se muestra el grafo.
				Grafo.grafo = karger_steinPesos(Grafo.grafo);		// Método que resuelve el algoritmo de karger_stein con pesos.
				Grafo.mostrar();		// Método que muestra el resultado final.
				break;
			default:		// Si no existe el caso de prueba...
				System.err.println("No existe el caso de prueba " + args[1]);
				System.exit(0);
			}
		}
	}
	
	/*
	 * Método que comprueba el número de argumentos y si son correctos.
	 */
	private static void comprobarArgumentos(String [] args){
		
		if(args.length != 2 || (!args[0].equals("-alg") && !args[0].equals("-ejemplo"))){	
			// Error con los argumentos.
			System.err.println("Usar para ejemplo random:");
			System.err.println("java Amazon -alg [karger|karger_stein|"
					+ "kargerPesos|karger_steinPesos]");
			System.err.println("Usar para ejemplo:");
			System.err.println("java Amazon -ejemplo [1|2|3|4]");
			System.exit(0);
		} 
	}
	
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
				// Se obtiene la clave del vértice.
				String clave = claves[random2].substring(0,claves[random2].lastIndexOf('_'));
				// Se obtiene el vértice.
				Nodo vertice2 = grafo.get(clave);
				
				unir(vertice1,vertice2,grafo);		// Método que une los dos vértices en uno.
			}
			
			//Se reduce el numero de nodos.
			nodos--;
		}
		
		return grafo;		// Se devuelve el grafo.
	}
	
	/*
	 * Método que une dos ciertos vértices en uno sólo, combinando sus aristas.
	 */
	private static void unir(Nodo vertice1, Nodo vertice2, Hashtable<String,Nodo> grafo){
				
		// Obtiene las aristas del primer vértice.
		Hashtable<String,Nodo> aristas1 = vertice1.getAristas();
				
		// Elimina los propios vértices de los adyacentes (no generar aristas a si mismo).
		for(int i = 0; i < Grafo.pesosProductos[Integer.parseInt(vertice1.getClave())]
				[Integer.parseInt(vertice2.getClave())]; i++) {
			aristas1.remove(vertice2.getClave()+"_"+i);
		}
				
		// Se añade el vértice a la lista de combinados para saber los conjuntos.
		vertice1.anadirProd(vertice2.getClave(),vertice2.getProdCombinados());
		// Se obtienen las claves de todos los vértices.
		Object key [] = grafo.keySet().toArray();
		String claves [] = Arrays.copyOf(key,key.length,String[].class);
				
		for(int i=0; i<claves.length; i++){		
			// Se recorren el resto de vértices para actualizar con la nueva unión.
			Hashtable<String,Nodo> aristas = grafo.get(claves[i]).getAristas();
			
			// Si tiene el segundo vértice en su lista...
			if(grafo.get(claves[i]).getArista(vertice2.getClave()+"_0") != null){
				//Se hace para todas las aristas entre los vertices.
				for(int j = 0; j < Grafo.pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice2.getClave())]; j++) {
					// Se añade un vértice con el nombre del primero.
					aristas.put(vertice1.getClave()+ "_" +
							(Grafo.pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice1.getClave())]+j), 
								new Nodo(vertice1.getClave()));
					// Se añade la misma arista en vertice1 hacia el nodo i.
					aristas1.put(claves[i]+ "_" +
							(Grafo.pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice1.getClave())]+j), 
								new Nodo(claves[i]));
					// Se elimina el segundo vértice.
					aristas.remove(vertice2.getClave()+"_"+j);
				}
				//Se actualiza la matriz de pesos con las aristas añadidas.
				int nuevoPeso = 
						Grafo.pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice1.getClave())] +
						Grafo.pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice2.getClave())];
				Grafo.pesosProductos[Integer.parseInt(claves[i])][Integer.parseInt(vertice1.getClave())] = nuevoPeso;
				Grafo.pesosProductos[Integer.parseInt(vertice1.getClave())][Integer.parseInt(claves[i])] = nuevoPeso;
			}
		}
		
		Grafo.grafo.remove(vertice2.getClave());		// Se borra el segundo vértice.
		
	}
	
	/*
	 * Método que implementa el algoritmo de Karger para realizar una partición
	 * de los productos cercana a la óptima en un grafo con pesos.
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
				Nodo vertice2 = grafo.get(claves[random2].substring(0,claves[random2].lastIndexOf('_')));
				unir(vertice1,vertice2,grafo);		// Método que une los dos vértices en uno.
			}
			
			//Se reduce el numero de nodos.
			nodos--;
		}
		
		return grafo;		// Se devuelve el grafo.
	}
	
	/*
	 * Método que implementa el algoritmo de Karger-Stein para realizar una partición
	 * de los productos cercana a la óptima en un grafo sin pesos.
	 */
	private static Hashtable<String,Nodo> karger_stein(Hashtable<String,Nodo> grafo){
		
		if(grafo.size() <= 6) {
			//Si el número de nodos es pequeño se llama una sola vez a Karger.
			return karger(grafo,2);
		} else {
			//Se calcula el numero de nodos a los que se reducira.
			int numNodos = (int)(1 + grafo.size() / Math.sqrt(2.0));	
			//Se llama dos veces a Karger.
			Hashtable<String,Nodo> grafo1 = karger(grafo,numNodos);
			Hashtable<String,Nodo> grafo2 = karger(grafo,numNodos);
			//Se devuelve el mejor de los dos.
			return min(karger_stein(grafo1),karger_stein(grafo2));
		}
	}
	
	/*
	 * Método que implementa el algoritmo de Karger-Stein para realizar una partición
	 * de los productos cercana a la óptima con pesos.
	 */
	private static Hashtable<String,Nodo> karger_steinPesos(Hashtable<String,Nodo> grafo){
		
		if(grafo.size() <= 6) {
			//Si el número de nodos es pequeño se llama una sola vez a Karger.
			return kargerPesos(grafo,2);
		} else {
			//Se calcula el numero de nodos a los que se reducira.
			int numNodos = (int)(1 + grafo.size() / Math.sqrt(2.0));	
			int[][] aux = new int[Grafo.NUMPRODS][Grafo.NUMPRODS];
			
			//Se guarda una copia de la tabla actual.
			for(int i = 0; i<Grafo.NUMPRODS;i++) {
				for(int j = i; j< Grafo.NUMPRODS; j++) {
					aux[i][j] = Grafo.pesosProductos[i][j];
					aux[j][i] = Grafo.pesosProductos[j][i];
				}
			}
			
			Hashtable<String,Nodo> grafo1 = kargerPesos(grafo,numNodos);	//Se llama a Karger.
			grafo1 = karger_steinPesos(grafo1);			// Se llama recursivamente.
			
			//Se restaura la tabla de pesos a su estado anterior para la segunda llamada a Karger.
			for(int i = 0; i<Grafo.NUMPRODS;i++) {
				for(int j = i; j< Grafo.NUMPRODS; j++) {
					Grafo.pesosProductos[i][j] = aux[i][j];
					Grafo.pesosProductos[j][i] = aux[j][i];
				}
			}
			
			Hashtable<String,Nodo> grafo2 = kargerPesos(grafo,numNodos);	//Se llama a Karger.
			grafo2 = karger_steinPesos(grafo2);		// Se llama recursivamente.
			
			//Se devuelve el mejor de los dos.
			return min(grafo1,grafo2);
		}
	}
	
	/*
	 * Devuelve el grafo que está más próximo a un corte mínimo.
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
