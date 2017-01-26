package practica2;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Clase que representa una abstracción de un árbol de sufijos.
 */
public class ArbolSufijos {

	private NodoArbol raiz = null;		// Nodo raíz del árbol.
	private String texto = null;		// Texto del árbol.
	private int etiqueta = 1;		// Etiqueta de inicial para las hojas.
	private ArrayList<NodoArbol> hojas;	// Lista de las hojas del árbol.
	private final int K = 3;		// Tamaño máximo del camino a reducir.
	
	/*
	 * Método constructor del árbol de sufijos.
	 */
	public ArbolSufijos(String texto){
		
		raiz = new NodoArbol("r");		// Se crea el nodo de la raíz.
		this.texto = texto;		// Se añade el texto del árbol.
		hojas = new ArrayList<NodoArbol>();		// Crea la lista de hojas.
	}
	
	/*
	 * Método que crea el árbol a partir del texto.
	 */
	public void crearArbol(){
		
		int longitud = texto.length();		// Se obtiene longitud del texto.
		for(int i=0; i<longitud; i++){	// Se recorre cortando el texto en sufijos.
			// Se inserta el sufijo en el árbol.
			insertarSubCadena(texto.substring(i,texto.length()));
		}
	}
	
	/*
	 * Método que inserta un cierto sufijo en el árbol.
	 */
	private void insertarSubCadena(String cadena){

		if(raiz.getPrimogenito() == null){		// Si el árbol está vacío...
			NodoArbol actual = raiz;		// Se obtiene la raíz.
			
			for(int i=0; i<cadena.length(); i++){	// Se recorre el sufijo.
				// Se crea el nodo a introducir.
				NodoArbol introducir = new NodoArbol(cadena.substring(i,i+1));
				if(i==cadena.length()-1){		// Si es hoja, se pone etiqueta.
					introducir.setEtiqueta(obtenerEtiqueta());
					introducir.setCamino(cadena);
					hojas.add(introducir);
				}
				// Se introduce en el árbol.
				actual.setPrimogenito(introducir);
				introducir.setPadre(actual);
				actual = introducir;
			}
			
		} else{		// Si ya hay elementos en el árbol...
			NodoArbol actual = raiz;		// Se obtiene la raíz.
			
			for(int i=0; i<cadena.length(); i++){		// Se recorre el sufijo.
				NodoArbol hijo = actual.getPrimogenito();	// Se obtiene el primogénito.
				String caracter = cadena.substring(i,i+1);	// Se obtiene el caracter.
				// Se crea el nodo a introducir.
				NodoArbol introducir = new NodoArbol(caracter);
				if(i==cadena.length()-1){		// Si es hoja, se pone etiqueta.
					introducir.setEtiqueta(obtenerEtiqueta());
					introducir.setCamino(cadena);
					hojas.add(introducir);
				}
				
				// Se localiza la posición en el árbol.
				if(hijo != null && !hijo.getElemento().equals(caracter)){
					NodoArbol siguiente = hijo.getSigHermano();
					// Se recorren los hermanos hasta encontrarlo si está.
					while(siguiente != null
							&& !siguiente.getElemento().equals(caracter)){
						hijo = siguiente;
						siguiente = siguiente.getSigHermano();
					}
					if(siguiente == null){		// Si no está, se introduce.
						hijo.setSigHermano(introducir);
						introducir.setPadre(actual);
						introducir.setAntHermano(hijo);
						actual = hijo.getSigHermano();
					} else{			// Si está, se pasa al siguiente.
						actual = hijo.getSigHermano();
					}
				} else if(hijo == null){		// Si no tenía hijos, se introduce.
					actual.setPrimogenito(introducir);
					introducir.setPadre(actual);
					actual = actual.getPrimogenito();
				} else{		// Si el hijo es el elemento, se pasa al siguiente.
					actual = actual.getPrimogenito();
				}
				
			}
			
		}
	}
	
	/*
	 * Método que compacta el árbol utilizando las etiquetas.
	 */
	public void compactar(){
		
		// Lista para los elementos con un hijo.
		ArrayList<NodoArbol> unHijo = new ArrayList<NodoArbol>();
		// Lista para todos los elementos.
		ArrayList<NodoArbol> elementos = obtenerElementos();
		
		for(int i=0; i<elementos.size(); i++){		// Se recorren buscando un hijo...
			NodoArbol actual = elementos.get(i);	// Se obtiene el elemento actual.
			NodoArbol hijo = actual.getPrimogenito();	// Se obtiene el primer hijo.
			int numHijos = 0;		// Número de hijos.
			while(hijo != null){		// Se recorren los hijos.
				numHijos++;
				hijo = hijo.getSigHermano();
			}
			if(numHijos == 1){		// Si tiene un hijo se añade a la lista.
				unHijo.add(actual);
			}
		}
		
		for(int i = 0; i < unHijo.size(); i++){
			NodoArbol borrar = unHijo.get(i);			// Nodo a borrar.
			NodoArbol hijo = borrar.getPrimogenito();	// Hijo del nodo.
			hijo.setElemento(borrar.getElemento()+hijo.getElemento()); 	// Se juntan ambas etiquetas.
			NodoArbol padre = borrar.getPadre();			// Padre del nodo a borrar.
			// Se borra el nodo.
			hijo.setPadre(padre);
			
			// Se comprueban los hermanos del nodo a borrar.
			NodoArbol hermano = borrar.getAntHermano();
			if(hermano!=null) {		// Si tiene hermano anterior, se ajustan.
				hermano.setSigHermano(hijo);
				hijo.setAntHermano(hermano);
			} else {		// Sino se fija como primogénito.
				padre.setPrimogenito(hijo);
			}
			hermano = borrar.getSigHermano();
			if(hermano!=null) {		// Se fija el siguiente hermano.
				hermano.setAntHermano(hijo);
				hijo.setSigHermano(hermano);
			}
		}
		
		/*mostrarArbol();
		System.out.println();
		System.out.println();*/

		for(int i=0; i<hojas.size(); i++){		// Se recorren todas las hojas.
			NodoArbol actual = hojas.get(i);	// Se obtiene la hoja actual.
			if(actual.getElemento().length() > K) {		// Si el nodo tiene un texto de tamaño superior a K...
				for(int j = 0; j < hojas.size(); j++) {		// Se recorren las hojas para encontrar el camino.
					NodoArbol hojaActual = hojas.get(j);	// Se obtiene la hoja actual.
					// Si los dos nodos a comparar son distintos...
					if(hojaActual.getEtiqueta() != actual.getEtiqueta()) {
						// Si el texto de la hoja es igual se enlazan.
						if(actual.getElemento().equals(hojaActual.getCamino())) {		
							actual.setEnlace(j);
							actual.setElemento("");
							break;
						}
					}
				}
			}
		}
	}
	
	/*
	 * Método que obtiene los elementos del árbol.
	 */
	private ArrayList<NodoArbol> obtenerElementos(){
		
		// Crea la cola.
		LinkedList<NodoArbol> cola = new LinkedList<NodoArbol>();
		// Crea la lista para los elementos.
		ArrayList<NodoArbol> elementos = new ArrayList<NodoArbol>();
		cola.addFirst(raiz);		// Añade la raíz.
		while(!cola.isEmpty()){		// Mientras la cola tenga elementos...
			// Se obtiene el primer elemento y se guarda en la lista.
			NodoArbol nodo = cola.removeLast();
			elementos.add(nodo);
			nodo = nodo.getPrimogenito();
			// Se añaden a la cola todos sus hijos.
			while(nodo != null){
				cola.addFirst(nodo);
				nodo = nodo.getSigHermano();
			}
		}
		return elementos;
	}
	
	/*
	 * Método que muestra el árbol actual. Utiliza una cola auxiliar para realizar
	 * el recorrido por niveles.
	 */
	public void mostrarArbol(){
		
		// Crea la cola.
		LinkedList<NodoArbol> cola = new LinkedList<NodoArbol>();
		cola.addFirst(raiz);		// Añade la raíz.
		while(!cola.isEmpty()){		// Mientras la cola tenga elementos...
			// Se obtiene el primer elemento y se muestra.
			NodoArbol nodo = cola.removeLast();
			if(nodo.getEnlace() > -1) {
				System.out.print(nodo.getEnlace()+1);
			} else {
				System.out.print(nodo.getElemento());
			}
			if(nodo.getEtiqueta() != -1){		// Si es una hoja, se muestra etiqueta.
				System.out.print(" " + nodo.getEtiqueta());
			}
			System.out.println();
			nodo = nodo.getPrimogenito();
			// Se añaden a la cola todos sus hijos.
			while(nodo != null){
				cola.addFirst(nodo);
				nodo = nodo.getSigHermano();
			}
		}
	}
	
	/*
	 * Método que muestra el árbol con las etiquetas.
	 */
	public void mostrarEtiqHojas(){
		
		for(int i = 0; i<hojas.size(); i++) {		// Recorre las hojas y las muestra.
			System.out.println(getTexto(hojas.get(i))+" "+(hojas.get(i).getEtiqueta()));
		}
	}
	
	/*
	 * Método que devuelve la etiqueta actual y prepara la siguiente.
	 */
	public int obtenerEtiqueta(){
		
		etiqueta++;
		return etiqueta-1;
	}
	
	
	/*
	 * Se obtiene el texto contenido desde el nodo hasta la raíz.
	 */
	private String getTexto(NodoArbol nodo) {
		if(nodo.getPadre() != null) {		// Si no es la raiz.
			String texto = "";
			// Se obtiene el texto contenido en el nodo.
			if(nodo.getEnlace() > -1) {
				texto = texto+" "+(nodo.getEnlace()+1);
			} else {
				texto = nodo.getElemento();
			}
			// Se devuelve el texto obtenido concatenado con el del padre.
			return getTexto(nodo.getPadre())+texto;
		} else {
			return "";		// Si es la raíz, se devuelve cadena vacía.
		}
	}
	
	/*
	 * Obtiene la mayor repetición dentro de la cadena de texto.
	 */
	public String mayorRepeticion() {
		
		String repetido = "";
		for(int k = 0; k < hojas.size();k++) {	// Bucle para cada rama del arbol.
			for(int i = 0; i < hojas.get(k).getCamino().length() 
					&& hojas.get(k).getCamino().length() > repetido.length(); i++) {
				// Cada vez se comprueba una parte del texto.
				String subTexto = hojas.get(k).getCamino().substring(0,i+1);
				for(int j = 0; j < hojas.size(); j++) {
					if(j!=k) {
						// Para las otras ramas se comprueba si el texto coincide.
						if(subTexto.length() <= hojas.get(j).getCamino().length()) {
							String cadena = hojas.get(j).getCamino().substring(0,subTexto.length());
							if(cadena.equals(subTexto) && cadena.length() > repetido.length()) {
								repetido = cadena;
							}
						}
					}
				}
			}
		}
		
		return repetido;		
	}
	
	/*
	 * Obtiene las repeticiones maximales del texto.
	 */
	public ArrayList<String> maximalRepeticion() {
		
		ArrayList <String> repetidos = new ArrayList<String>();
		String repetido = "";
		for(int k = 0; k < hojas.size();k++) {	// Bucle para cada rama del arbol.
			for(int i = 0; i < hojas.get(k).getCamino().length() 
					&& hojas.get(k).getCamino().length() > repetido.length(); i++) {
				// Cada vez se comprueba una parte del texto.
				String subTexto = hojas.get(k).getCamino().substring(0,i+1);
				for(int j = 0; j < hojas.size(); j++) {
					if(j!=k) {
						// Para las otras ramas se comprueba si el texto coincide.
						if(subTexto.length() <= hojas.get(j).getCamino().length()) {
							String cadena = hojas.get(j).getCamino().substring(0,subTexto.length());
							if(cadena.equals(subTexto) && cadena.length() > repetido.length()) {
								repetidos.add(cadena);
								repetido = cadena;
							}
						}
					}
				}
			}
		}
		
		return repetidos;		
	}
}
