package Practica1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Nodo {

	private String clave;
	private ArrayList<String> prodCombinados;
	private Hashtable<String,Nodo> aristas;
	
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
	 * Método que fija la lista de productos combinados en un único vértice.
	 */
	public void setProdCombinados(ArrayList<String> prodCombinados) {
		this.prodCombinados = prodCombinados;
	}
	
	/*
	 * Método que añade un cierto producto al combinarlos.
	 */
	public void anadirProd(String nombre){
		prodCombinados.add(nombre);
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
