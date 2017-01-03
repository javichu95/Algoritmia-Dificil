package practica1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * Clase que representa una abstracción de un nodo/vértice a almacenar en
 * una tabla hash.
 */
public class Nodo {

	private String clave;		// Clave del nodo en la tabla hash.
	private ArrayList<String> prodCombinados;		// Lista de los vértices combinados.
	private Hashtable<String,Nodo> aristas;		// Aristas que conectan con otros vértices.
	
	/*
	 * Método constructor de un objeto Nodo.
	 */
	public Nodo(String clave){
		this.clave = clave;
		prodCombinados = new ArrayList<String>();
		aristas = new Hashtable<String,Nodo>();
	}
	
	/*
	 * Método que devuelve la lista de productos combinados por sus vértices.
	 */
	public ArrayList<String> getProdCombinados() {
		return prodCombinados;
	}
	
	/*
	 * Método que añade un cierto producto al combinarlos.
	 */
	public void anadirProd(String nombre, ArrayList<String> unidos){
		prodCombinados.add(nombre);
		prodCombinados.addAll(unidos);
	}
	
	/*
	 * Método que añade una nueva arista al grafo.
	 */
	public void anadirArista(String key, Nodo p){
		aristas.put(key, p);
	}
	
	/*
	 * Método que devuelve el número de aristas del vértice.
	 */
	public int numAristas(){
		return aristas.size();
	}
	
	/*
	 * Método que devuelve una cierta arista.
	 */
	public Nodo getArista(String nombre){
		return aristas.get(nombre);
	}
	
	/*
	 * Método que devuelve las claves de la tabla hash.
	 */
	public Set<String> getKeys(){
		return aristas.keySet();
	}
	
	/*
	 * Método que devuelve la clave del nodo.
	 */
	public String getClave(){
		return clave;
	}
	
	/*
	 * Método que devuelve la tabla hash con la lista de aristas.
	 */
	public Hashtable<String,Nodo> getAristas(){
		return aristas;
	}
}
