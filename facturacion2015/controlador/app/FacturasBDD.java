package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class FacturasBDD {
	// TABLA facturas BASE DE DATOS
//	id int(10) UNSIGNED No auto_increment
//	clienteId int(10) UNSIGNED No clientes -> id
//	numero int(11) No
//	fecha date No
//	porcDescuento double No
//	porcRecargoEquivalencia	double No
//	impTotal double No
//	impRecargo double No
//	impIva double No
//	dirCorreo mediumtext No
//	dirFactura mediumtext No
//	dirEnvio mediumtext No
//	cobrada tinyint(1) No
	
	//METODO PUBLICO
	
	public ArrayList<Factura> recuperaFacturaCompletaPorFiltro(String filtro) {
		String sql = "";
		sql += "SELECT * FROM facturas WHERE ";
		sql += filtro;
		sql += " ORDER BY facturas.numero";
		System.out.println(sql);
		ArrayList<Factura> lista = null;
		Connection c = new Conexion().getConection();
		if (c != null) {
			lista = new ArrayList<>();
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				while (rs.next() == true) {
					int id = rs.getInt("id");
					int clienteId = rs.getInt("clienteId");
					String nombreCliente = rs.getString("nombreCliente");
					int numero = rs.getInt("numero");
					Date fecha = rs.getDate("fecha");
					double porcDescuento = rs.getDouble("porcDescuento");
					double porcRecargoEquivalencia = rs.getDouble("porcRecargoEquivalencia");
					double impTotal = rs.getDouble("impTotal");
					double impRecargo = rs.getDouble("impRecargo");
					double impIva = rs.getDouble("impIva");
					String dirCorreo = rs.getString("dirCorreo");
					String dirFactura = rs.getString("dirFactura");
					String dirEnvio = rs.getString("dirEnvio");
					boolean cobrada = rs.getBoolean("cobrada");
					ArrayList<FacturaDetalle> detalles = new FacturasDetallesBDD().recuperaPorFacturaId(id);
					lista.add(new Factura(id, clienteId, nombreCliente, numero, fecha, porcDescuento, porcRecargoEquivalencia, impTotal, impRecargo, impIva, dirCorreo, dirFactura, dirEnvio, cobrada, detalles));		
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}
	
	public ArrayList<Factura> recuperaFacturaSimplePorFiltro(String filtro) {
		String sql = "";
		sql += "SELECT * FROM facturas WHERE ";
		sql += filtro;
		sql += " ORDER BY facturas.numero";
		System.out.println(sql);
		ArrayList<Factura> lista = null;
		Connection c = new Conexion().getConection();
		if (c != null) {
			lista = new ArrayList<>();
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				while (rs.next() == true) {
					int id = rs.getInt("id");
					int clienteId = rs.getInt("clienteId");
					String nombreCliente = rs.getString("nombreCliente");
					int numero = rs.getInt("numero");
					Date fecha = rs.getDate("fecha");
					double porcDescuento = rs.getDouble("porcDescuento");
					double porcRecargoEquivalencia = rs.getDouble("porcRecargoEquivalencia");
					double impTotal = rs.getDouble("impTotal");
					double impRecargo = rs.getDouble("impRecargo");
					double impIva = rs.getDouble("impIva");
					String dirCorreo = rs.getString("dirCorreo");
					String dirFactura = rs.getString("dirFactura");
					String dirEnvio = rs.getString("dirEnvio");
					boolean cobrada = rs.getBoolean("cobrada");
					ArrayList<FacturaDetalle> detalles = null;
					lista.add(new Factura(id, clienteId, nombreCliente, numero, fecha, porcDescuento, porcRecargoEquivalencia, impTotal, impRecargo, impIva, dirCorreo, dirFactura, dirEnvio, cobrada, detalles));		
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}
	
	public ArrayList<Factura> recuperaPorNumeroFactura(int numeroFactura) {
		String filtro = "WHERE facturas.numero = " + numeroFactura;
		return recuperaFacturaSimplePorFiltro(filtro);
	}
	
	public ArrayList<Factura> recuperaTodas(){
		return recuperaFacturaSimplePorFiltro("WHERE 1");
	}
	
	public Factura recuperaFacturaCompletaPorId(int id){
		if (id != 0) {
			String filtro = "facturas.id = " + id;
			ArrayList<Factura> lista = recuperaFacturaCompletaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Factura p = new Factura();
			p.setId(0);
			return p;
		}
	}
	
	public Factura recuperaPorId(int id){
		if (id != 0) {
			String filtro = "WHERE facturas.id = " + id;
			ArrayList<Factura> lista = recuperaFacturaSimplePorFiltro(filtro);
			return lista.get(0);
		} else {
			Factura p = new Factura();
			p.setId(0);
			return p;
		}
	}
	
	public Integer maximoNumeroFactura () {
		Integer numeroMaximo = null;
		String sql = "SELECT MAX(facturas.numero) as numeroMaximo FROM facturas";
		System.out.println(sql);
		Connection c = new Conexion().getConection();
		if (c != null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				if (rs.first()){
					numeroMaximo = rs.getInt("numeroMaximo");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return numeroMaximo;	
	}
	
	public int grabarFacturaCompleta(Factura fac) {
		//Preparo la lista de SQLs
		ArrayList<String> listaSQLs = new ArrayList<>();
		int respuesta = -1;
		String sql = "";
		if (fac.getId()==0) {
			sql += "INSERT INTO facturas SET " +
					"facturas.clienteId = " + fac.getClienteId() + ", " +
					"facturas.nombreCliente = " + fac.getNombreCliente() + ", " +
					"facturas.numero = " + fac.getNumero() + ", " +
					"facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', " +
					"facturas.porcDescuento = " + fac.getPorcDescuento() + ", " +
					"facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia() + ", " +
					"facturas.impTotal = " + fac.getImpTotal() + ", " +
					"facturas.impRecargo = " + fac.getImpRecargo() + ", " +
					"facturas.impIva = " + fac.getImpIva() + ", " +
					"facturas.dirCorreo = '" + fac.getDirCorreo() + "', " +
					"facturas.dirFactura = '" + fac.getDirFactura() + "', " +
					"facturas.dirEnvio = '" + fac.getDirEnvio() + "', " +
					"facturas.cobrada = " + fac.isCobrada()
					;
		} else {
			sql = "UPDATE facturas SET " +
					"facturas.clienteId = " + fac.getClienteId() + ", " +
					"facturas.nombreCliente = " + fac.getNombreCliente() + ", " +
					"facturas.numero = " + fac.getNumero() + ", " +
					"facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', " +
					"facturas.porcDescuento = " + fac.getPorcDescuento() + ", " +
					"facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia() + ", " +
					"facturas.impTotal = " + fac.getImpTotal() + ", " +
					"facturas.impRecargo = " + fac.getImpRecargo() + ", " +
					"facturas.impIva = " + fac.getImpIva() + ", " +
					"facturas.dirCorreo = '" + fac.getDirCorreo() + "', " +
					"facturas.dirFactura = '" + fac.getDirFactura() + "', " +
					"facturas.dirEnvio = '" + fac.getDirEnvio() + "', " +
					"facturas.cobrada = " + fac.isCobrada() + " " +
					"WHERE facturas.id = " + fac.getId()
					;
		}
		//Preparo la lista de SLQ de los detalles
		for (FacturaDetalle fd : fac.getDetalles()) {
			listaSQLs.add(new FacturasDetallesBDD().generaSQL(fd));
		}
		System.out.println(listaSQLs);
		// CREO UNA CONEXION
		Connection c = new Conexion().getConection();
		// SI LA CONEXION ES VALIDA
		if (c!=null) {
			// INTENTA REALIZAR EL SQL
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				
				comando.execute(sql);
//				comando.execute(sql,Statement.RETURN_GENERATED_KEYS);
//				// COMPRUEBA si estamos en un Insert o en un Update
//				if (fac.getId() != 0){
//					// ES UN UPDATE
//					respuesta = comando.getUpdateCount()>0?0:-1;
//				} else {
//					// VAMOS A DEVOLVER EL ID GENERADO, pero el EXECUTE devuelve un RESULTSET
//					ResultSet resultados = comando.getGeneratedKeys();
//					// Si el conjunto de resultados no es nulo, y coge el proximo elemento (el primero)
//					if (resultados!=null && resultados.next()) {
//						respuesta = resultados.getInt(1);
//					}
//				}
				//Vamos a generar un execute para cada Detalle de la lista;
				for (String sqlDetalle : listaSQLs) {
					comando.execute(sqlDetalle);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//CERRAMOS LA CONEXION
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}
	
	public int grabarFacturaSimple(Factura fac) {
		int respuesta = -1;
		String sql = "";
		if (fac.getId()==0) {
			
//			id int(10) UNSIGNED No auto_increment
//			clienteId int(10) UNSIGNED No clientes -> id
//			nombreCliente
//			numero int(11) No
//			fecha date No
//			porcDescuento double No
//			porcRecargoEquivalencia	double No
//			impTotal double No
//			impRecargo double No
//			impIva double No
//			dirCorreo mediumtext No
//			dirFactura mediumtext No
//			dirEnvio mediumtext No
//			cobrada tinyint(1) No
			
			sql = "INSERT INTO facturas SET " +
					"facturas.clienteId = " + fac.getClienteId() + ", " +
					"facturas.nombreCliente = " + fac.getNombreCliente() + ", " +
					"facturas.numero = " + fac.getNumero() + ", " +
					"facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', " +
					"facturas.porcDescuento = " + fac.getPorcDescuento() + ", " +
					"facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia() + ", " +
					"facturas.impTotal = " + fac.getImpTotal() + ", " +
					"facturas.impRecargo = " + fac.getImpRecargo() + ", " +
					"facturas.impIva = " + fac.getImpIva() + ", " +
					"facturas.dirCorreo = '" + fac.getDirCorreo() + "', " +
					"facturas.dirFactura = '" + fac.getDirFactura() + "', " +
					"facturas.dirEnvio = '" + fac.getDirEnvio() + "', " +
					"facturas.cobrada = " + fac.isCobrada()
					;
		} else {
			sql = "UPDATE facturas SET " +
					"facturas.clienteId = " + fac.getClienteId() + ", " +
					"facturas.nombreCliente = " + fac.getNombreCliente() + ", " +
					"facturas.numero = " + fac.getNumero() + ", " +
					"facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', " +
					"facturas.porcDescuento = " + fac.getPorcDescuento() + ", " +
					"facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia() + ", " +
					"facturas.impTotal = " + fac.getImpTotal() + ", " +
					"facturas.impRecargo = " + fac.getImpRecargo() + ", " +
					"facturas.impIva = " + fac.getImpIva() + ", " +
					"facturas.dirCorreo = '" + fac.getDirCorreo() + "', " +
					"facturas.dirFactura = '" + fac.getDirFactura() + "', " +
					"facturas.dirEnvio = '" + fac.getDirEnvio() + "', " +
					"facturas.cobrada = " + fac.isCobrada() + " " +
					"WHERE facturas.id = " + fac.getId()
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
				if (fac.getId() != 0){
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
			//CERRAMOS LA CONEXION
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return respuesta;
	}
	
	public boolean eliminar (int id) {
		boolean respuesta = false;
		String 	sql = "DELETE FROM facturas " +
				"WHERE facturas.id = " + id;
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
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(sql);
		return respuesta;
	}
	
	//RECUPERAR TABLAS ESPECIALES
	
	
	//RECUPERAR TABLAS ESPECIALES
	
	
	/** Recoge los datos de la BBDD para una Jtable con Factura, Fecha, Importe Total, Cobrada
	 * @param filtro
	 * @return devuelve datos Jtabla, es decir una array de vectores, ArrayList<Vector<Object>>
	 */
	public ArrayList<Vector<Object>> recuperaTablaFacturas(String txtFiltro) {			
		ArrayList<String> filtros = new ArrayList<>();
		filtros.add("facturas.numero LIKE '%" + txtFiltro + "%'");
		filtros.add("facturas.nombreCliente LIKE '%" + txtFiltro + "%'");
		String filtro = creaFiltro(filtros);
		ArrayList<Factura> lista = recuperaFacturaSimplePorFiltro(filtro);		
				
		ArrayList<Vector<Object>> tableData = new ArrayList<>();
		for (Factura factura : lista) {
			Vector<Object> filaData = new Vector<>();
			filaData.add(factura);
			filaData.add(factura.getFecha());
			filaData.add(factura.getNombreCliente());
			filaData.add(factura.getImpTotal());
			filaData.add(factura.isCobrada());
			tableData.add(filaData);
		}
		return tableData;
	}
	
	private String creaFiltro(ArrayList<String> filtros) {
		String stringFiltro = "";
		for (int i = 0; i < filtros.size()-1 ; i++) {
			String filtro = filtros.get(i);
			stringFiltro += filtro;
			stringFiltro += " AND ";
		}
		String filtro = filtros.get(filtros.size()-1);
		stringFiltro += filtro;
		return stringFiltro;
	}
	
}
