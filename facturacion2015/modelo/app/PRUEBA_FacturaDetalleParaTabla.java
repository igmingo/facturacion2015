package app;

public class PRUEBA_FacturaDetalleParaTabla extends Detalle {

	/** Operación es un entero que vale:
	 * 0 nuevo detalle (un INSERT)
	 * 1 detalle a modificar (un UPDATE)
	 * 2 detalle a eliminar (un delete)
	 */
	private int operacion = 0;
	
	public PRUEBA_FacturaDetalleParaTabla(int operacion) {
		super();
		this.operacion = operacion;
	}

	protected PRUEBA_FacturaDetalleParaTabla(Detalle fd, int operacion) {
		super(fd.id, fd.facturaId, fd.prodId, fd.prodNombre, fd.prodPrecio, fd.prodIva, fd.cantidad);
		this.operacion = operacion;
	}
	
	protected PRUEBA_FacturaDetalleParaTabla(Detalle fd) {
		super(fd.id, fd.facturaId, fd.prodId, fd.prodNombre, fd.prodPrecio, fd.prodIva, fd.cantidad);
	}

	public PRUEBA_FacturaDetalleParaTabla ponerParaEliminar() {
		this.operacion = -1;
		return this;
	}
	
	public PRUEBA_FacturaDetalleParaTabla ponerParaInsertar() {
		this.operacion = 0;
		return this;
	}
	
	public PRUEBA_FacturaDetalleParaTabla ponerParaActualizar() {
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
