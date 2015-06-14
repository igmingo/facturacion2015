package app;

import java.util.Date;

public class Cliente  {


     private int id;
     private String nombre;
     private String apellidos;
     private String nif;
     private String dirCorreo;
     private String dirFactura;
     private String dirEnvio;
     private String contacto;
     private double porcRecargoEquivalencia;
     private double porcDescuento;
     private Date fechaAlta;
     private boolean baja;

    public Cliente() {
    }

	
    public Cliente(int id, String nombre, String apellidos, String nif, String dirCorreo, String dirFactura, String dirEnvio, String contacto, double porcRecargoEquivalencia, double porcDescuento, Date fechaAlta, boolean baja) {
        this.id = id;
    	this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.dirCorreo = dirCorreo;
        this.dirFactura = dirFactura;
        this.dirEnvio = dirEnvio;
        this.contacto = contacto;
        this.porcRecargoEquivalencia = porcRecargoEquivalencia;
        this.porcDescuento = porcDescuento;
        this.fechaAlta = fechaAlta;
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
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getNif() {
        return this.nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    public String getDirCorreo() {
        return this.dirCorreo;
    }
    
    public void setDirCorreo(String dirCorreo) {
        this.dirCorreo = dirCorreo;
    }
    public String getDirFactura() {
        return this.dirFactura;
    }
    
    public void setDirFactura(String dirFactura) {
        this.dirFactura = dirFactura;
    }
    public String getDirEnvio() {
        return this.dirEnvio;
    }
    
    public void setDirEnvio(String dirEnvio) {
        this.dirEnvio = dirEnvio;
    }
    public String getContacto() {
        return this.contacto;
    }
    
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public double getPorcRecargoEquivalencia() {
        return this.porcRecargoEquivalencia;
    }
    
    public void setPorcRecargoEquivalencia(double porcRecargoEquivalencia) {
        this.porcRecargoEquivalencia = porcRecargoEquivalencia;
    }
    public double getPorcDescuento() {
        return this.porcDescuento;
    }
    
    public void setPorcDescuento(double porcDescuento) {
        this.porcDescuento = porcDescuento;
    }
    public Date getFechaAlta() {
        return this.fechaAlta;
    }
    
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}


	@Override
	public String toString() {
		return apellidos + ", " + nombre;
	}
}


