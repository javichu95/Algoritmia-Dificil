package Practica1;

/**
 * Clase que representa una abstracción de un producto de la tienda
 * de Amazon.
 */
public class Producto {

	private String nombre;	// Nombre del producto.
	private int numUnidades;	// Número de unidades del producto.
	private double precio;	// Precio del producto.
	private double descuento;		// Descuento del producto.
	private String marca;		// Marca del producto.
	
	/*
	 * Método constructor de un objeto Producto.
	 */
	public Producto(String nombre, int numUnidades, double precio,
			double descuento, String marca) {
		super();
		this.nombre = nombre;
		this.numUnidades = numUnidades;
		this.precio = precio;
		this.descuento = descuento;
		this.marca = marca;
	}
	
	/*
	 * Método que devuelve el nombre del producto.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/*
	 * Método que fija el nombre del producto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/*
	 * Método que devuelve el número de unidades del producto.
	 */
	public int getNumUnidades() {
		return numUnidades;
	}
	
	/*
	 * Método que fija el número de unidades del producto.
	 */
	public void setNumUnidades(int numUnidades) {
		this.numUnidades = numUnidades;
	}
	
	/*
	 * Método que devuelve el precio del producto.
	 */
	public double getPrecio() {
		return precio;
	}
	
	/*
	 * Método que fija el precio del producto.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	/*
	 * Método que devuelve el descuento del producto.
	 */
	public double getDescuento() {
		return descuento;
	}
	
	/*
	 * Método que fija el descuento del producto.
	 */
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	
	/*
	 * Método que devuelve la marca del producto.
	 */
	public String getMarca() {
		return marca;
	}
	
	/*
	 * Método que fija la marca del producto.
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
}
