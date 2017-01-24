package practica2;

/**
 * Clase que representa una abstracción de un nodo que va a ser
 * almacenado en un árbol N-ario.
 */
public class NodoArbol {

	private String elemento;		// Elemento del nodo.
	private NodoArbol primogenito;	// Primogénito del nodo.
	private NodoArbol sigHermano;	// Siguiente hermano del nodo.
	private NodoArbol antHermano;	// Anterior hermano del nodo.
	private NodoArbol padre;	// Padre del nodo.
	private int etiqueta;	// Etiqueta del nodo.
	private int enlace;
	private String camino;	//Camino de raiz a nodo.
	
	/*
	 * Método constructor del nodo de con el elemento.
	 */
	public NodoArbol(String elemento){
		
		this.elemento = elemento;
		primogenito = null;
		sigHermano = null;
		antHermano = null;
		padre = null;
		etiqueta = -1;
		enlace=-1;
	}
	
	/*
	 * Método constructor de un nodo con el elemento, primogénito y siguiente
	 * hermano.
	 */
	public NodoArbol(String elemento, NodoArbol prim, NodoArbol sigHer){
		
		this.elemento = elemento;
		primogenito = prim;
		sigHermano = sigHer;
		antHermano = null;
		padre = null;
		etiqueta = -1;
		enlace=-1;
	}
	
	/*
	 * Método que fija el elemento del nodo.
	 */
	public void setElemento(String elemento){
		
		this.elemento = elemento;
	}
	
	/*
	 * Método que devuelve el elemento del nodo.
	 */
	public String getElemento(){
		
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
	 * Método que fija el siguiente hermano del nodo.
	 */
	public void setAntHermano(NodoArbol hermano){
		
		antHermano = hermano;
	}
	
	/*
	 * Método que devuelve el siguiente hermano del nodo.
	 */
	public NodoArbol getAntHermano(){
		
		return antHermano;
	}
	
	/*
	 * Método que fija el padre del nodo.
	 */
	public void setPadre(NodoArbol padre){
		
		this.padre = padre;
	}
	
	/*
	 * Método que devuelve el padre del nodo.
	 */
	public NodoArbol getPadre(){
		
		return padre;
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
	
	/*
	 * Método que fija el enlace del nodo.
	 */
	public void setEnlace(int enlace){
		
		this.enlace = enlace;
	}
	
	/*
	 * Método que devuelve el enlace del nodo.
	 */
	public int getEnlace(){
		
		return enlace;
	}
	
	/*
	 * Método que fija el camino de la raiz al nodo.
	 */
	public void setCamino(String camino){
		
		this.camino = camino;
	}
	
	/*
	 * Método que devuelve el camino de la raiz al nodo.
	 */
	public String getCamino(){
		
		return camino;
	}
}
