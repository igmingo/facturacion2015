package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;

public class DetallesBDD  extends BDD {
	/*	TABLA facturasdetalle BASE DE DATOS
		id int(10) UNSIGNED No auto_increment
		facturaId int(10) UNSIGNED No facturas -> id
		prodId int(10) UNSIGNED No productos -> id
		prodNombre varchar(30) No
		prodPrecio double No
		prodIva double No
		cantidad int(11) No*/
	
	/*
	 * 	METODOS PUBLICOS
	 */
	public ArrayList<Detalle> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM facturas WHERE ";
		sql += filtro == null || filtro.length() == 0 ? "1" : filtro;
		sql += " ORDER BY facturas.numero";
		ArrayList<Detalle> lista = null;
		CachedRowSet rs = consultaSQL(sql);
		try {
			lista = new ArrayList<>();
			while (rs.next() == true) {
				lista.add(new Detalle(rs.getInt("id"), rs.getInt("facturaId"),
						rs.getInt("prodId"), rs.getString("prodNombre"), rs
								.getDouble("prodPrecio"), rs
								.getDouble("prodIva"), rs.getInt("cantidad")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Detalle> recuperaPorFacturaId(int facturaId) {
		String filtro = "facturasdetalle.facturaId = " + facturaId + " ";
		return recuperaPorFiltro(filtro);
	}
	
	public ArrayList<Detalle> recuperaTodos(){
		return recuperaPorFiltro(null);
	}
	
	public Detalle recuperaPorId(int id){
		if (id != 0) {
			String filtro = "facturasdetalle.id = " + id;
			ArrayList<Detalle> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Detalle p = new Detalle();
			p.setId(0);
			return p;
		}
	}
	
	public String generaSQL(Detalle fd) {
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
			if (fd.getId()>0) {
				sql = "UPDATE facturasdetalle SET " +
						"facturasdetalle.facturaId = " + fd.getFacturaId() + ", " +
						"facturasdetalle.prodId = " + fd.getProdId() + ", " +
						"facturasdetalle.prodNombre = '" + fd.getProdNombre() + "', " +
						"facturasdetalle.prodPrecio = " + fd.getProdPrecio() + ", " +
						"facturasdetalle.prodIva = " + fd.getProdIva() + ", " +
						"facturasdetalle.cantidad = " + fd.getCantidad() + " " +
						"WHERE facturasdetalle.id = " + fd.getId()
						;
			} else {
				sql = "DELETE FROM facturasdetalle WHERE facturasdetalle.id = " + (fd.getId()*-1);
			}
		}
		return sql;
	}
	
	public int grabar(Connection cnx, Detalle fd) {
		String sql = generaSQL(fd);
		Object resp = ejecutaSQL(cnx, sql);
		int r = -1;
		try {
			r = (int) resp;
		} catch (Exception e) {
		}
		return r;
	}
	
	public int grabar(Detalle fd) {
		String sql = generaSQL(fd);
		return ejecutaSQL(sql);
	}
	
	public boolean eliminarDirecto(int id) {
		return ejecutaSQL("DELETE FROM facturasdetalle WHERE facturasdetalle.id = " + id)>0?true:false;
	}
	
	//RECUPERAR TABLAS ESPECIALES
	public ArrayList<Vector<Object>> recuperaTablaFacturaDetalles(String filtro) {
		//"Detalle", "Precio", "IVA", "Cantidad"
		ArrayList<Vector<Object>> tableData = null;
		ArrayList<Detalle> lista = recuperaPorFiltro(filtro);
		tableData = new ArrayList<>();
		for (Detalle fd : lista) {
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
