package practica2;

/**
 * Clase que representa una abstracción de un nodo que va a ser
 * almacenado en un árbol N-ario.
 */
public class NodoArbol {

	private char elemento;		// Elemento del nodo.
	private NodoArbol primogenito;	// Primogénito del nodo.
	private NodoArbol sigHermano;	// Siguiente hermano del nodo.
	private int etiqueta;	// Etiqueta del nodo.
	
	/*
	 * Método constructor del nodo de con el elemento.
	 */
	public NodoArbol(char elemento){
		
		this.elemento = elemento;
		primogenito = null;
		sigHermano = null;
		etiqueta = -1;
	}
	
	/*
	 * Método constructor de un nodo con el elemento, primogénito y siguiente
	 * hermano.
	 */
	public NodoArbol(char elemento, NodoArbol prim, NodoArbol sigHer){
		
		this.elemento = elemento;
		primogenito = prim;
		sigHermano = sigHer;
		etiqueta = -1;
	}
	
	/*
	 * Método que fija el elemento del nodo.
	 */
	public void setElemento(char elemento){
		
		this.elemento = elemento;
	}
	
	/*
	 * Método que devuelve el elemento del nodo.
	 */
	public char getElemento(){
		
		return elemento;
	}
	
	/*
	 * Método que devuelve el primogénito del nodo.
	 */
	public void setPrimogenito(NodoArbol prim){
		
		primogenito = prim;
	}
	
	/*
	 * Método que devuelve el primogénito del nodo.
	 */
	public NodoArbol getPrimogenito(){
		
		return primogenito;
	}
	
	/*
	 * Método que fija el siguiente hermano del nodo.
	 */
	public void setSigHermano(NodoArbol hermano){
		
		sigHermano = hermano;
	}
	
	/*
	 * Método que devuelve el siguiente hermano del nodo.
	 */
	public NodoArbol getSigHermano(){
		
		return sigHermano;
	}
	
	/*
	 * Método que fija la etiqueta del nodo.
	 */
	public void setEtiqueta(int etiqueta){
		
		this.etiqueta = etiqueta;
	}
	
	/*
	 * Método que devuelve la etiqueta del nodo.
	 */
	public int getEtiqueta(){
		
		return etiqueta;
	}
}