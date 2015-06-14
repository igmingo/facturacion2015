package app;

import java.util.ArrayList;
import java.util.Date;

public class Factura {

	
//	id int(10) UNSIGNED No auto_increment
//	clienteId int(10) UNSIGNED No clientes -> id
//	numero int(11) No
//	fecha date No
//	porcDescuento double No
//	porcRecargoEq
//	uivalencia
//	double No
//	impTotal double No
//	impRecargo double No
//	impIva double No
//	dirCorreo mediumtext No
//	dirFactura mediumtext No
//	dirEnvio mediumtext No
//	cobrada tinyint(1) No
	
	
     private int id;
     private int clienteId;
     private Cliente cliente;
     private String nombreCliente;
     private int numero;
     private Date fecha;
     private double porcDescuento;
     private double porcRecargoEquivalencia;
     private double impTotal;
     private double impRecargo;
     private double impIva;
     private String dirCorreo;
     private String dirFactura;
     private String dirEnvio;
     private boolean cobrada;
     private ArrayList<FacturaDetalle> detalles;

    public Factura() {
    }
    
    public Factura(int id, Cliente cliente, String nombreCliente, int numero,
			Date fecha, double porcDescuento, double porcRecargoEquivalencia,
			double impTotal, double impRecargo, double impIva,
			String dirCorreo, String dirFactura, String dirEnvio,
			boolean cobrada, ArrayList<FacturaDetalle> detalles) {
		this.id = id;
		this.cliente = cliente;
		if (cliente!=null) {
			this.clienteId = cliente.getId();
		}
		this.nombreCliente = nombreCliente;
		this.numero = numero;
		this.fecha = fecha;
		this.porcDescuento = porcDescuento;
		this.porcRecargoEquivalencia = porcRecargoEquivalencia;
		this.impTotal = impTotal;
		this.impRecargo = impRecargo;
		this.impIva = impIva;
		this.dirCorreo = dirCorreo;
		this.dirFactura = dirFactura;
		this.dirEnvio = dirEnvio;
		this.cobrada = cobrada;
		this.detalles = detalles;
	}
	
    public Factura(int id, int clienteId, String nombreCliente, int numero,
			Date fecha, double porcDescuento, double porcRecargoEquivalencia,
			double impTotal, double impRecargo, double impIva,
			String dirCorreo, String dirFactura, String dirEnvio,
			boolean cobrada, ArrayList<FacturaDetalle> detalles) {
		this.id = id;
		this.clienteId = clienteId;
		this.nombreCliente = nombreCliente;
		this.numero = numero;
		this.fecha = fecha;
		this.porcDescuento = porcDescuento;
		this.porcRecargoEquivalencia = porcRecargoEquivalencia;
		this.impTotal = impTotal;
		this.impRecargo = impRecargo;
		this.impIva = impIva;
		this.dirCorreo = dirCorreo;
		this.dirFactura = dirFactura;
		this.dirEnvio = dirEnvio;
		this.cobrada = cobrada;
		this.detalles = detalles;
	}

//	public Factura(int id, int clienteId, int numero, Date fecha, double porcDescuento, double porcRecargoEquivalencia, double impTotal, double impRecargo, double impIva, String dirCorreo, String dirFactura, String dirEnvio, boolean cobrada) {
//        this.id = id;
//    	this.clienteId = clienteId;
//        this.numero = numero;
//        this.fecha = fecha;
//        this.porcDescuento = porcDescuento;
//        this.porcRecargoEquivalencia = porcRecargoEquivalencia;
//        this.impTotal = impTotal;
//        this.impRecargo = impRecargo;
//        this.impIva = impIva;
//        this.dirCorreo = dirCorreo;
//        this.dirFactura = dirFactura;
//        this.dirEnvio = dirEnvio;
//        this.cobrada = cobrada;
//    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getNumero() {
        return this.numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public double getPorcDescuento() {
        return this.porcDescuento;
    }
    
    public void setPorcDescuento(double porcDescuento) {
        this.porcDescuento = porcDescuento;
    }
    public double getPorcRecargoEquivalencia() {
        return this.porcRecargoEquivalencia;
    }
    
    public void setPorcRecargoEquivalencia(double porcRecargoEquivalencia) {
        this.porcRecargoEquivalencia = porcRecargoEquivalencia;
    }
    public double getImpTotal() {
        return this.impTotal;
    }
    
    public void setImpTotal(double impTotal) {
        this.impTotal = impTotal;
    }
    public double getImpRecargo() {
        return this.impRecargo;
    }
    
    public void setImpRecargo(double impRecargo) {
        this.impRecargo = impRecargo;
    }
    public double getImpIva() {
        return this.impIva;
    }
    
    public void setImpIva(double impIva) {
        this.impIva = impIva;
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
    public boolean isCobrada() {
        return this.cobrada;
    }
    
    public void setCobrada(boolean cobrada) {
        this.cobrada = cobrada;
    }
    
	public ArrayList<FacturaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(ArrayList<FacturaDetalle> detalles) {
		this.detalles = detalles;
	}
	
	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Nº " + numero + " (" + (fecha.getYear()+1900) + ")";
	}

}


