package app;

public class Producto {


     private int id;
     private String nombre;
     private double precio;
     private double iva;
     private int stock;
     private String descripcion;
     private boolean baja;

    public Producto() {
    }

	
    public Producto(int id, String nombre, double precio, double iva, int stock, String descripcion, boolean baja) {
        this.id = id;
    	this.nombre = nombre;
        this.precio = precio;
        this.iva = iva;
        this.stock = stock;
        this.descripcion = descripcion;
        this.baja = baja;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getIva() {
        return this.iva;
    }
    
    public void setIva(double iva) {
        this.iva = iva;
    }
    public int getStock() {
        return this.stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}


	@Override
	public String toString() {
		return nombre;
	}
	
	


}


