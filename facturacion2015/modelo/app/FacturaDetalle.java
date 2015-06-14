package app;

public class FacturaDetalle {

	// TABLA facturasdetalle BASE DE DATOS
//	id int(10) UNSIGNED No auto_increment
//	facturaId int(10) UNSIGNED No facturas -> id
//	prodId int(10) UNSIGNED No productos -> id
//	prodNombre varchar(30) No
//	prodPrecio double No
//	prodIva double No
//	cantidad int(11) No

     private int id;
     private int facturaId;
     private int prodId;
     private String prodNombre;
     private double prodPrecio;
     private double prodIva;
     private int cantidad;

    public FacturaDetalle() {
    }

    public FacturaDetalle(int id, int facturaId, int prodId, String prodNombre, double prodPrecio, double prodIva, int cantidad) {
       this.id = id;
       this.facturaId = facturaId;
       this.prodId = prodId;
       this.prodNombre = prodNombre;
       this.prodPrecio = prodPrecio;
       this.prodIva = prodIva;
       this.cantidad = cantidad;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(int facturaId) {
		this.facturaId = facturaId;
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public String getProdNombre() {
        return this.prodNombre;
    }
    
    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }
    public double getProdPrecio() {
        return this.prodPrecio;
    }
    
    public void setProdPrecio(double prodPrecio) {
        this.prodPrecio = prodPrecio;
    }
    public double getProdIva() {
        return this.prodIva;
    }
    
    public void setProdIva(double prodIva) {
        this.prodIva = prodIva;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

	@Override
	public String toString() {
		return prodNombre + "" + prodId + "";
	}
    
    

}


