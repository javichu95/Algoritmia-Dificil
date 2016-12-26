package Practica1;

public class Producto {

	private String nombre;
	private int numUnidades;
	private double precio;
	private double descuento;
	private String marca;
	
	public Producto(String nombre, int numUnidades, double precio,
			double descuento, String marca) {
		super();
		this.nombre = nombre;
		this.numUnidades = numUnidades;
		this.precio = precio;
		this.descuento = descuento;
		this.marca = marca;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getNumUnidades() {
		return numUnidades;
	}
	
	public void setNumUnidades(int numUnidades) {
		this.numUnidades = numUnidades;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public double getDescuento() {
		return descuento;
	}
	
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	
}
