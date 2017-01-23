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
	
	/*
	 * Método constructor del árbol de sufijos.
	 */
	public ArbolSufijos(String texto){
		
		raiz = new NodoArbol('r');		// Se crea el nodo de la raíz.
		this.texto = texto+"$";		// Se añade el texto del árbol.
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
				NodoArbol introducir = new NodoArbol(cadena.charAt(i));
				if(i==cadena.length()-1){		// Si es hoja, se pone etiqueta.
					introducir.setEtiqueta(obtenerEtiqueta());
				}
				// Se introduce en el árbol.
				actual.setPrimogenito(introducir);
				actual = introducir;
			}
			
		} else{		// Si ya hay elementos en el árbol...
			NodoArbol actual = raiz;		// Se obtiene la raíz.
			
			for(int i=0; i<cadena.length(); i++){		// Se recorre el sufijo.
				NodoArbol hijo = actual.getPrimogenito();	// Se obtiene el primogénito.
				char caracter = cadena.charAt(i);	// Se obtiene el caracter.
				// Se crea el nodo a introducir.
				NodoArbol introducir = new NodoArbol(caracter);
				if(i==cadena.length()-1){		// Si es hoja, se pone etiqueta.
					introducir.setEtiqueta(obtenerEtiqueta());
				}
				
				// Se localiza la posición en el árbol.
				if(hijo != null && hijo.getElemento() != caracter){
					NodoArbol siguiente = hijo.getSigHermano();
					// Se recorren los hermanos hasta encontrarlo si está.
					while(siguiente != null
							&& siguiente.getElemento() != caracter){
						hijo = siguiente;
						siguiente = siguiente.getSigHermano();
					}
					if(siguiente == null){		// Si no está, se introduce.
						hijo.setSigHermano(introducir);
						actual = hijo.getSigHermano();
					} else{			// Si está, se pasa al siguiente.
						actual = hijo.getSigHermano();
					}
				} else if(hijo == null){		// Si no tenía hijos, se introduce.
					actual.setPrimogenito(introducir);
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
		
		// Lista para los elementos con dos hijos.
		ArrayList<NodoArbol> dosHijos = new ArrayList<NodoArbol>();
		// Lista para todos los elementos.
		ArrayList<NodoArbol> elementos = obtenerElementos();
		
		for(int i=0; i<elementos.size(); i++){		// Se recorren buscando dos hijos...
			NodoArbol actual = elementos.get(i);	// Se obtiene el elemento actual.
			NodoArbol hijo = actual.getPrimogenito();	// Se obtiene el primer hijo.
			int numHijos = 0;		// Número de hijos.
			while(hijo != null){		// Se recorren los hijos.
				numHijos++;
				hijo = hijo.getSigHermano();
			}
			if(numHijos == 2){		// Si tiene dos hijos se añade a la lista.
				dosHijos.add(actual);
			}
		}
		
		while(!dosHijos.isEmpty()){
			
		}
		
		/*for(int i=0; i<dosHijos.size(); i++){
			System.out.println(dosHijos.get(i).getElemento());
		}*/
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
			System.out.print(nodo.getElemento());
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
	 * Método que devuelve la etiqueta actual y prepara la siguiente.
	 */
	public int obtenerEtiqueta(){
		
		etiqueta++;
		return etiqueta-1;
	}
}
