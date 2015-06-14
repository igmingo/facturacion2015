package app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class FacturasDetallesBDD {
	// TABLA facturasdetalle BASE DE DATOS
//	id int(10) UNSIGNED No auto_increment
//	facturaId int(10) UNSIGNED No facturas -> id
//	prodId int(10) UNSIGNED No productos -> id
//	prodNombre varchar(30) No
//	prodPrecio double No
//	prodIva double No
//	cantidad int(11) No
	
	//METODO PUBLICO
	
	public ArrayList<FacturaDetalle> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM facturasdetalle " + filtro
				+ " ORDER BY facturasdetalle.id";
		System.out.println(sql);
		ArrayList<FacturaDetalle> lista = new ArrayList<>();
		Connection c = new Conexion().getConection();
		if (c != null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				while (rs.next() == true) {
					int id = rs.getInt("id");
					int facturaId = rs.getInt("facturaId");
					int prodId = rs.getInt("prodId");
					String prodNombre = rs.getString("prodNombre");
					double prodPrecio = rs.getDouble("prodPrecio");
					double prodIva = rs.getDouble("prodIva");
					int cantidad = rs.getInt("cantidad");
					lista.add(new FacturaDetalle(id, facturaId, prodId, prodNombre, prodPrecio, prodIva, cantidad));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<FacturaDetalle> recuperaPorFacturaId(int facturaId) {
		String filtro = "WHERE facturasdetalle.facturaId = " + facturaId + " ";
		return recuperaPorFiltro(filtro);
	}
	
	public ArrayList<FacturaDetalle> recuperaTodos(){
		return recuperaPorFiltro("WHERE 1");
	}
	
	public FacturaDetalle recuperaPorId(int id){
		if (id != 0) {
			String filtro = "WHERE facturasdetalle.id = " + id;
			ArrayList<FacturaDetalle> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			FacturaDetalle p = new FacturaDetalle();
			p.setId(0);
			return p;
		}
	}
	
	public String generaSQL(FacturaDetalle fd) {
		String sql = "";
		if (fd.getId()==0) {		
			sql = "INSERT INTO facturasdetalle SET " +
					"facturasdetalle.facturaId = " + fd.getFacturaId() + ", " +
					"facturasdetalle.prodId = " + fd.getProdId() + ", " +
					"facturasdetalle.prodNombre = '" + fd.getProdNombre() + "', " +
					"facturasdetalle.prodPrecio = " + fd.getProdPrecio() + ", " +
					"facturasdetalle.prodIva = " + fd.getProdIva() + ", " +
					"facturasdetalle.cantidad = " + fd.getCantidad()
					;
		} else {
			sql = "UPDATE facturasdetalle SET " +
					"facturasdetalle.facturaId = " + fd.getFacturaId() + ", " +
					"facturasdetalle.prodId = " + fd.getProdId() + ", " +
					"facturasdetalle.prodNombre = '" + fd.getProdNombre() + "', " +
					"facturasdetalle.prodPrecio = " + fd.getProdPrecio() + ", " +
					"facturasdetalle.prodIva = " + fd.getProdIva() + ", " +
					"facturasdetalle.cantidad = " + fd.getCantidad() + " " +
					"WHERE facturasdetalle.id = " + fd.getId()
					;
		}
		return sql;
	}
	
	public int grabar(FacturaDetalle fd) {
		int respuesta = -1;
		String sql = "";
		if (fd.getId()==0) {		
//			TABLA facturasdetalle BASE DE DATOS
//			
//			id int(10) UNSIGNED No auto_increment
//			facturaId int(10) UNSIGNED No facturas -> id
//			prodId int(10) UNSIGNED No productos -> id
//			prodNombre varchar(30) No
//			prodPrecio double No
//			prodIva double No
//			cantidad int(11) No
			
			sql = "INSERT INTO facturasdetalle SET " +
					"facturasdetalle.facturaId = " + fd.getFacturaId() + ", " +
					"facturasdetalle.prodId = " + fd.getProdId() + ", " +
					"facturasdetalle.prodNombre = '" + fd.getProdNombre() + "', " +
					"facturasdetalle.prodPrecio = " + fd.getProdPrecio() + ", " +
					"facturasdetalle.prodIva = " + fd.getProdIva() + ", " +
					"facturasdetalle.cantidad = " + fd.getCantidad()
					;
		} else {
			sql = "UPDATE facturasdetalle SET " +
					"facturasdetalle.facturaId = " + fd.getFacturaId() + ", " +
					"facturasdetalle.prodId = " + fd.getProdId() + ", " +
					"facturasdetalle.prodNombre = '" + fd.getProdNombre() + "', " +
					"facturasdetalle.prodPrecio = " + fd.getProdPrecio() + ", " +
					"facturasdetalle.prodIva = " + fd.getProdIva() + ", " +
					"facturasdetalle.cantidad = " + fd.getCantidad() + " " +
					"WHERE facturasdetalle.id = " + fd.getId()
					;
		}
		System.out.println(sql);
		// CREO UNA CONEXION
		Connection c = new Conexion().getConection();
		// SI LA CONEXION ES VALIDA
		if (c!=null) {
			// INTENTA REALIZAR EL SQL
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				comando.execute(sql,Statement.RETURN_GENERATED_KEYS);
				// COMPRUEBA si estamos en un Insert o en un Update
				if (fd.getId() != 0){
					// ES UN UPDATE
					respuesta = comando.getUpdateCount()>0?0:-1;
				} else {
					// VAMOS A DEVOLVER EL ID GENERADO, pero el EXECUTE devuelve un RESULTSET
					ResultSet resultados = comando.getGeneratedKeys();
					// Si el conjunto de resultados no es nulo, y coge el proximo elemento (el primero)
					if (resultados!=null && resultados.next()) {
						respuesta = resultados.getInt(1);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//CERRAMOS LA CONEXION
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return respuesta;
	}
	
	public boolean eliminar (int id) {
		boolean respuesta = false;
		String 	sql = "DELETE FROM facturasdetalle " +
				"WHERE facturasdetalle.id = " + id;
		System.out.println(sql);
		// CREO UNA CONEXION
		Connection c = new Conexion().getConection();
		if (c!=null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				if (comando.execute(sql)==false){
					respuesta = comando.getUpdateCount()>0?true:false ;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(sql);
		return respuesta;
	}
	
	//RECUPERAR TABLAS ESPECIALES
	public ArrayList<Vector<Object>> recuperaTablaFacturaDetalles(String filtro) {
		//"Detalle", "Precio", "IVA", "Cantidad"
		ArrayList<Vector<Object>> tableData = null;
		ArrayList<FacturaDetalle> lista = recuperaPorFiltro(filtro);
		tableData = new ArrayList<>();
		for (FacturaDetalle fd : lista) {
			Vector<Object> filaData = new Vector<>();
			filaData.add(fd);
			filaData.add(fd.getProdPrecio());
			filaData.add(fd.getProdIva());
			filaData.add(fd.getCantidad());
			tableData.add(filaData);
		}
		return tableData;
	}
	
//	public ArrayList<Vector<Object>> recuperaTablaFacturaDetalles2(String f) {
//
////		id int(10) UNSIGNED No auto_increment
////		facturaId int(10) UNSIGNED No facturas -> id
////		prodId int(10) UNSIGNED No productos -> id
////		prodNombre varchar(30) No
////		prodPrecio double No
////		prodIva double No
////		cantidad int(11) No
//		
//			ArrayList<Vector<Object>> tableData = null;
//			String filtro = "WHERE facturasdetalle.facturaId = " + f + " ";
//			String sql = "SELECT * FROM facturasdetalle " + filtro + " ORDER BY facturasdetalle.id";
//			System.out.println(sql);
//			tableData = new ArrayList<>();
//			Connection c = new Conexion().getConection();
//			if (c!=null) {
//				try {
//					Statement comando = c.createStatement();
//					ResultSet rs = comando.executeQuery(sql);
//					while (rs.next() == true) {
//						//Los datos de la fila son un tipo VECTOR
//						Vector<Object> filaData = new Vector<>();
//						FacturaDetalle fd = null;
//						{
//							int id = rs.getInt("id");
//							int facturaId = rs.getInt("id");
//							int prodId = rs.getInt("id");
//							String prodNombre = rs.getString("prodNombre");
//							double prodPrecio = rs.getDouble("prodPrecio");
//							double prodIva = rs.getDouble("prodIva");
//							int cantidad = rs.getInt("cantidad");
//							fd = new FacturaDetalle(id, facturaId, prodId, prodNombre, prodPrecio, prodIva, cantidad);
//						}
//						filaData.add(fd);
//						filaData.add(fd.getProdPrecio());
//						filaData.add(fd.getProdIva());
//						filaData.add(fd.getCantidad());
//						tableData.add(filaData);
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			try {
//				c.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return tableData;
//	}
	
}
