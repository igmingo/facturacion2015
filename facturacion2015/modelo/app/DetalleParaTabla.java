package app;

public class DetalleParaTabla {

	private Detalle facturaDetalle;
	private int operacion;
	
	public DetalleParaTabla() {
	}

	public DetalleParaTabla(Detalle fd, int operacion) {
		this.facturaDetalle = fd;
		this.operacion = operacion;
	}
	
	public DetalleParaTabla(Detalle fd) {
		this.facturaDetalle = fd;
	}

	public Detalle getFacturaDetalle() {
		return facturaDetalle;
	}

	public void setFacturaDetalle(Detalle facturaDetalle) {
		this.facturaDetalle = facturaDetalle;
	}

	public DetalleParaTabla ponerParaEliminar() {
		this.operacion = -1;
		return this;
	}
	
	public DetalleParaTabla ponerParaInsertar() {
		this.operacion = 0;
		return this;
	}
	
	public DetalleParaTabla ponerParaActualizar() {
		this.operacion = 1;
		return this;
	}

	public boolean esParaEliminar() {
		return this.operacion==-1;
	}
	
	public boolean esParaInsertar() {
		return this.operacion==0;
	}
	
	public boolean esParaActualizar() {
		return this.operacion==1;
	}

}
