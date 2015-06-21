package app;

public class Detalle {

	// TABLA facturasdetalle BASE DE DATOS
//	id int(10) UNSIGNED No auto_increment
//	facturaId int(10) UNSIGNED No facturas -> id
//	prodId int(10) UNSIGNED No productos -> id
//	prodNombre varchar(30) No
//	prodPrecio double No
//	prodIva double No
//	cantidad int(11) No

	protected int id;
	protected int facturaId;
	protected int prodId;
    protected String prodNombre;
    protected double prodPrecio;
    protected double prodIva;
	protected int cantidad;

    public Detalle() {
    }

    public Detalle(int id, int facturaId, int prodId, String prodNombre, double prodPrecio, double prodIva, int cantidad) {
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
		String nota = "";
		if (id < 0) {
			nota = "- ";
		} else {
			if (id == 0) {
			nota = "+ ";
			}
		}
		return nota + prodNombre;
	}

	public PRUEBA_FacturaDetalleParaTabla paraTabla(int operacion) {
		return new PRUEBA_FacturaDetalleParaTabla(this, operacion);
	}

	public PRUEBA_FacturaDetalleParaTabla paraTabla() {
		return new PRUEBA_FacturaDetalleParaTabla(this);
	}
}


