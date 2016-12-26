package Practica1;

import java.util.Hashtable;
import java.util.Random;

public class Amazon {

	private static boolean parProductos [][] = new boolean [20][20];
	private static Hashtable<String,Producto> datosProductos = new Hashtable<String,Producto> ();
	
	public static void main(String[] args) {
		
		generarProductos();		// Método que genera la lista de productos.
		emparejar();		// Método que empareja los productos comprados juntos.
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
	 * Método que implementa el algoritmo de Karger para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static void karger(){
		
		for(int i=0; i<parProductos.length; i++){
			
		}
	}
	
	/*
	 * Método que implementa el algoritmo de Karger-Stein para realizar una partición
	 * de los productos cercana a la óptima.
	 */
	private static void karger_stein(){
		
	}
}
