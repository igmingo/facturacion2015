package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;

//SQL para obtener el nombre: CONCAT(cli.apellidos, CONCAT(',', cli.nombre))

public class FacturasBDD extends BDD {
	// TABLA facturas BASE DE DATOS
	// id int(10) UNSIGNED No auto_increment
	// clienteId int(10) UNSIGNED No clientes -> id
	// nombreCliente
	// nifCliente
	// numero int(11) No
	// fecha date No
	// porcDescuento double No
	// porcRecargoEquivalencia double No
	// impTotal double No
	// impRecargo double No
	// impIva double No
	// dirCorreo mediumtext No
	// dirFactura mediumtext No
	// dirEnvio mediumtext No
	// cobrada tinyint(1) No

	/** recupera una lista de Facturas de la base de datos, según un filtro
	 * @param filtro: es la sentencia SQL de consulta sin el WHERE
	 * @return ArrayList<Factura>
	 */
	public ArrayList<Factura> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM facturas WHERE ";
		sql += filtro == null || filtro.length() == 0 ? "1" : filtro;
		sql += " ORDER BY facturas.numero";
		ArrayList<Factura> lista = null;
		CachedRowSet rs = consultaSQL(sql);
		try {
			lista = new ArrayList<>();
			while (rs.next() == true) {
				lista.add(new Factura(rs.getInt("id"), rs.getInt("clienteId"), rs.getString("nombreCliente"), rs.getString("nifCliente"), rs.getInt("numero"), rs.getDate("fecha"),
						rs.getDouble("porcDescuento"), rs.getDouble("porcRecargoEquivalencia"),
						rs.getDouble("impTotal"), rs.getDouble("impRecargo"), rs.getDouble("impIva"),
						rs.getString("dirCorreo"), rs.getString("dirFactura"), rs.getString("dirEnvio"),
						rs.getBoolean("cobrada")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<Factura> recuperaPorNumeroFactura(int numeroFactura) {
		String filtro = "facturas.numero = " + numeroFactura;
		return recuperaPorFiltro(filtro);
	}

	public ArrayList<Factura> recuperaTodas() {
		return recuperaPorFiltro(null);
	}

	public Factura recuperaFacturaCompletaPorId(int id) {
		if (id != 0) {
			String filtro = "facturas.id = " + id;
			ArrayList<Factura> lista = recuperaPorFiltro(filtro);
			Factura fd = lista.get(0);
			// RECUPERAMOS EL CLIENTE Y LOS DETALLES
			fd.setDetalles(new DetallesBDD().recuperaPorFacturaId(fd.getId()));
			fd.setCliente(new ClientesBDD().recuperaPorId(fd.getClienteId()));
			return lista.get(0);
		} else {
			Factura p = new Factura();
			p.setId(0);
			//p.setDetalles(new ArrayList<FacturaDetalle>());
			//p.setCliente(new Cliente());
			return p;
		}
	}

	public Factura recuperaPorId(int id) {
		if (id != 0) {
			String filtro = "facturas.id = " + id;
			ArrayList<Factura> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Factura p = new Factura();
			p.setId(0);
			return p;
		}
	}

	public Integer maximoNumeroFactura(Connection cnx) {
		Integer numeroMaximo = null;
		String sql = "SELECT MAX(facturas.numero) as numeroMaximo FROM facturacion2015.facturas";
		CachedRowSet rs = consultaSQL(cnx, sql);
		try {
			if (rs.first()) {
				numeroMaximo = rs.getInt("numeroMaximo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numeroMaximo;
	}
	
//	private Integer maximoNumeroFactura3() {
//		Integer numeroMaximo = null;
//		String sql = "SELECT MAX(facturas.numero) as numeroMaximo FROM facturacion2015.facturas";
//		System.out.println(sql);
//		Connection c2 = new Conexion().getConection();
//		if (c2 != null) {
//			try {
//				// Crea un ESTAMENTO (comando de ejecucion de un sql)
//				Statement comando = c2.createStatement();
//				ResultSet rs = comando.executeQuery(sql);
//				if (rs.first()) {
//					numeroMaximo = rs.getInt("numeroMaximo");
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				c2.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return numeroMaximo;
//	}

	// public int actualizarFacturaCompleta(Factura fac) {
	// //Preparo la lista de SQLs
	// ArrayList<String> listaSQLs = new ArrayList<>();
	// int respuesta = -1;
	// if (fac.getId()==0) {
	// String sqlFactura = "INSERT INTO facturas SET " +
	// "facturas.clienteId = " + fac.getClienteId() + ", " +
	// "facturas.nombreCliente = " + fac.getNombreCliente() + ", " +
	// "facturas.numero = " + fac.getNumero() + ", " +
	// "facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', " +
	// "facturas.porcDescuento = " + fac.getPorcDescuento() + ", " +
	// "facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia()
	// + ", " +
	// "facturas.impTotal = " + fac.getImpTotal() + ", " +
	// "facturas.impRecargo = " + fac.getImpRecargo() + ", " +
	// "facturas.impIva = " + fac.getImpIva() + ", " +
	// "facturas.dirCorreo = '" + fac.getDirCorreo() + "', " +
	// "facturas.dirFactura = '" + fac.getDirFactura() + "', " +
	// "facturas.dirEnvio = '" + fac.getDirEnvio() + "', " +
	// "facturas.cobrada = " + fac.isCobrada()
	// ;
	// } else {
	// String sqlFactura = "UPDATE facturas SET " +
	// "facturas.clienteId = " + fac.getClienteId() + ", " +
	// "facturas.nombreCliente = " + fac.getNombreCliente() + ", " +
	// "facturas.numero = " + fac.getNumero() + ", " +
	// "facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', " +
	// "facturas.porcDescuento = " + fac.getPorcDescuento() + ", " +
	// "facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia()
	// + ", " +
	// "facturas.impTotal = " + fac.getImpTotal() + ", " +
	// "facturas.impRecargo = " + fac.getImpRecargo() + ", " +
	// "facturas.impIva = " + fac.getImpIva() + ", " +
	// "facturas.dirCorreo = '" + fac.getDirCorreo() + "', " +
	// "facturas.dirFactura = '" + fac.getDirFactura() + "', " +
	// "facturas.dirEnvio = '" + fac.getDirEnvio() + "', " +
	// "facturas.cobrada = " + fac.isCobrada() + " " +
	// "WHERE facturas.id = " + fac.getId()
	// ;
	// }
	// //Preparo la lista de SLQ de los detalles
	// for (FacturaDetalle fd : fac.getDetalles()) {
	// int newId = 0;
	// fd.setFacturaId(newId);
	// listaSQLs.add(new FacturasDetallesBDD().generaSQL(fd));
	// }
	//
	// //MOSTRAMOS lista de SQL a realizar con COMMIT
	// for (String MuestraSQL : listaSQLs) {
	// System.out.println(MuestraSQL);
	// }
	//
	// /*
	// // CREO UNA CONEXION
	// Connection c = new Conexion().getConection();
	// // SI LA CONEXION ES VALIDA
	// if (c!=null) {
	// try {
	// c.commit();
	// // INTENTA REALIZAR EL SQL
	// try {
	// // Crea un ESTAMENTO (comando de ejecucion de un sql)
	// Statement comando = c.createStatement();
	// comando.execute(sql);
	// for (String sqlDetalle : listaSQLs) {
	// comando.execute(sqlDetalle);
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// //CERRAMOS LA CONEXION
	// try {
	// c.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// } catch (SQLException e1) {
	// // TODO Bloque catch generado automáticamente
	// try {
	// c.rollback();
	// } catch (SQLException e) {
	// // TODO Bloque catch generado automáticamente
	// e.printStackTrace();
	// }
	// }
	// }
	// */
	//
	// return respuesta;
	// }

	public int grabarFacturaCompleta(Factura fac) {
		//RESPUESTAS POSIBLES
		//INSERT
		//>0 Se ha insertado la factura con el ID devuelto
		//-1 La insercion no se ha podido llevar a cabo
		//UPDATE
		//0	Se ha actualizado la tabla
		//-1 El Update no ha afectado a ningun elemento
		//PROCESO
		//-2 Se ha realizado un rollback
		
		ArrayList<String> listaSQLs = new ArrayList<>();
		int respuesta = -1;
		// CREO UNA CONEXION
		Connection cnx = new Conexion().getConection();
		// SI LA CONEXION ES VALIDA
		if (cnx != null) {
			try {
				cnx.setAutoCommit(false);
				// INTENTA REALIZAR EL SQL
				try {
					// Crea un ESTAMENTO (comando de ejecucion de un sql)
					Statement sql = cnx.createStatement();
					if (fac.getId() == 0) {
						//ESTAMOS EN UN INSERT DE FACTURA
						fac.setNumero(maximoNumeroFactura(cnx) + 1);
						System.out.println(fac.getNumero());
						listaSQLs.add(
								"INSERT INTO facturas SET "
								+ "facturas.clienteId = " + fac.getClienteId() + ", "
								+ "facturas.nombreCliente = '" + fac.getNombreCliente() + "', "
								+ "facturas.nifCliente = '" + fac.getNifCliente() + "', "
								+ "facturas.numero = " + fac.getNumero() + ", "
								+ "facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', "
								+ "facturas.porcDescuento = " + fac.getPorcDescuento() + ", "
								+ "facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia() + ", "
								+ "facturas.impTotal = " + fac.getImpTotal() + ", "
								+ "facturas.impRecargo = " + fac.getImpRecargo() + ", "
								+ "facturas.impIva = " + fac.getImpIva() + ", "
								+ "facturas.dirCorreo = '" + fac.getDirCorreo() + "', "
								+ "facturas.dirFactura = '" + fac.getDirFactura() + "', "
								+ "facturas.dirEnvio = '" + fac.getDirEnvio() + "', "
								+ "facturas.cobrada = " + fac.isCobrada()
								);
						System.out.println(listaSQLs.get(0));
						sql.execute(listaSQLs.get(0), Statement.RETURN_GENERATED_KEYS);
						ResultSet keysGeneradas = sql.getGeneratedKeys();
						// Si el conjunto de resultados no es nulo, y coge el
						// proximo elemento (el primero)
						if (keysGeneradas != null && keysGeneradas.next()) {
							fac.setId(keysGeneradas.getInt(1));
							respuesta = fac.getId();
						}
					} else {
						listaSQLs.add(
								"UPDATE facturas SET "
								+ "facturas.clienteId = " + fac.getClienteId() + ", "
								+ "facturas.nombreCliente = '" + fac.getNombreCliente() + "', "
								+ "facturas.nifCliente = '" + fac.getNifCliente() + "', "
								+ "facturas.numero = " + fac.getNumero() + ", "
								+ "facturas.fecha = '" + Utilidades.fechaToSQL(fac.getFecha()) + "', "
								+ "facturas.porcDescuento = " + fac.getPorcDescuento() + ", "
								+ "facturas.porcRecargoEquivalencia = " + fac.getPorcRecargoEquivalencia() + ", "
								+ "facturas.impTotal = " + fac.getImpTotal() + ", "
								+ "facturas.impRecargo = " + fac.getImpRecargo() + ", "
								+ "facturas.impIva = " + fac.getImpIva() + ", "
								+ "facturas.dirCorreo = '" + fac.getDirCorreo() + "', "
								+ "facturas.dirFactura = '" + fac.getDirFactura() + "', "
								+ "facturas.dirEnvio = '" + fac.getDirEnvio() + "', "
								+ "facturas.cobrada = " + fac.isCobrada() + " "
								+ "WHERE facturas.id = " + fac.getId()
								);
						System.out.println(listaSQLs.get(0));
						sql.execute(listaSQLs.get(0), Statement.RETURN_GENERATED_KEYS);
						respuesta = sql.getUpdateCount() > 0 ? 0 : -1;
					}
					// YA TENEMOS la FACTURA, insertada o actualizada
					if(fac!=null && fac.getId()>0) {
						// Preparo la lista de SLQ de los detalles
						for (Detalle fd : fac.getDetalles()) {
							fd.setFacturaId(fac.getId());
							listaSQLs.add(new DetallesBDD().generaSQL(fd));
						}
						ArrayList<Detalle> listaDetalles = fac.getDetalles();
						// VAMOS A REALIZAR LAS SQL de los DETALLES, empezando en la 1
						for (int i = 1; i < listaDetalles.size(); i++) {
							Detalle facDet = listaDetalles.get(i);
							facDet.setFacturaId(fac.getId());
							String detalleSQL = new DetallesBDD().generaSQL(facDet);
							System.out.println(detalleSQL);
							sql.execute(detalleSQL);
							new ProductosBDD().restarStock(cnx, facDet.getProdId(), facDet.getCantidad());
						}
					}
					cnx.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// CERRAMOS LA CONEXION
				try {
					cnx.setAutoCommit(false);
					cnx.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				// SI EL COMMIT DA ERROR
				try {
					cnx.rollback();
					cnx.setAutoCommit(false);
					cnx.close();
					respuesta = -2;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return respuesta;
	}

//	public int grabarFacturaSimple(Factura fac) {
//		int respuesta = -1;
//		String sql = "";
//		if (fac.getId() == 0) {
//
//			// id int(10) UNSIGNED No auto_increment
//			// clienteId int(10) UNSIGNED No clientes -> id
//			// nombreCliente
//			// numero int(11) No
//			// fecha date No
//			// porcDescuento double No
//			// porcRecargoEquivalencia double No
//			// impTotal double No
//			// impRecargo double No
//			// impIva double No
//			// dirCorreo mediumtext No
//			// dirFactura mediumtext No
//			// dirEnvio mediumtext No
//			// cobrada tinyint(1) No
//
//			sql = "INSERT INTO facturas SET " + "facturas.clienteId = "
//					+ fac.getClienteId() + ", " + "facturas.nombreCliente = "
//					+ fac.getNombreCliente() + ", " + "facturas.nifCliente = "
//					+ fac.getNifCliente() + ", " + "facturas.numero = "
//					+ fac.getNumero() + ", " + "facturas.fecha = '"
//					+ Utilidades.fechaToSQL(fac.getFecha()) + "', "
//					+ "facturas.porcDescuento = " + fac.getPorcDescuento()
//					+ ", " + "facturas.porcRecargoEquivalencia = "
//					+ fac.getPorcRecargoEquivalencia() + ", "
//					+ "facturas.impTotal = " + fac.getImpTotal() + ", "
//					+ "facturas.impRecargo = " + fac.getImpRecargo() + ", "
//					+ "facturas.impIva = " + fac.getImpIva() + ", "
//					+ "facturas.dirCorreo = '" + fac.getDirCorreo() + "', "
//					+ "facturas.dirFactura = '" + fac.getDirFactura() + "', "
//					+ "facturas.dirEnvio = '" + fac.getDirEnvio() + "', "
//					+ "facturas.cobrada = " + fac.isCobrada();
//		} else {
//			sql = "UPDATE facturas SET " + "facturas.clienteId = "
//					+ fac.getClienteId() + ", " + "facturas.nombreCliente = "
//					+ fac.getNombreCliente() + ", " + "facturas.nifCliente = "
//					+ fac.getNifCliente() + ", " + "facturas.numero = "
//					+ fac.getNumero() + ", " + "facturas.fecha = '"
//					+ Utilidades.fechaToSQL(fac.getFecha()) + "', "
//					+ "facturas.porcDescuento = " + fac.getPorcDescuento()
//					+ ", " + "facturas.porcRecargoEquivalencia = "
//					+ fac.getPorcRecargoEquivalencia() + ", "
//					+ "facturas.impTotal = " + fac.getImpTotal() + ", "
//					+ "facturas.impRecargo = " + fac.getImpRecargo() + ", "
//					+ "facturas.impIva = " + fac.getImpIva() + ", "
//					+ "facturas.dirCorreo = '" + fac.getDirCorreo() + "', "
//					+ "facturas.dirFactura = '" + fac.getDirFactura() + "', "
//					+ "facturas.dirEnvio = '" + fac.getDirEnvio() + "', "
//					+ "facturas.cobrada = " + fac.isCobrada() + " "
//					+ "WHERE facturas.id = " + fac.getId();
//		}
//		System.out.println(sql);
//		// CREO UNA CONEXION
//		Connection c = new Conexion().getConection();
//		// SI LA CONEXION ES VALIDA
//		if (c != null) {
//			// INTENTA REALIZAR EL SQL
//			try {
//				// Crea un ESTAMENTO (comando de ejecucion de un sql)
//				Statement comando = c.createStatement();
//				comando.execute(sql, Statement.RETURN_GENERATED_KEYS);
//				// COMPRUEBA si estamos en un Insert o en un Update
//				if (fac.getId() != 0) {
//					// ES UN UPDATE
//					respuesta = comando.getUpdateCount() > 0 ? 0 : -1;
//				} else {
//					// VAMOS A DEVOLVER EL ID GENERADO, pero el EXECUTE devuelve
//					// un RESULTSET
//					ResultSet resultados = comando.getGeneratedKeys();
//					// Si el conjunto de resultados no es nulo, y coge el
//					// proximo elemento (el primero)
//					if (resultados != null && resultados.next()) {
//						respuesta = resultados.getInt(1);
//					}
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			// CERRAMOS LA CONEXION
//			try {
//				c.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return respuesta;
//	}

	public boolean eliminar(int id) {
		boolean respuesta = false;
		String sql = "DELETE FROM facturas " + "WHERE facturas.id = " + id;
		System.out.println(sql);
		// CREO UNA CONEXION
		Connection c = new Conexion().getConection();
		if (c != null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				if (comando.execute(sql) == false) {
					respuesta = comando.getUpdateCount() > 0 ? true : false;
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

	// RECUPERAR TABLAS ESPECIALES

	/**
	 * Recoge los datos de la BBDD para una Jtable con Factura, Fecha, Importe
	 * Total, Cobrada
	 * 
	 * @param filtro
	 * @return devuelve datos Jtabla, es decir una array de vectores,
	 *         ArrayList<Vector<Object>>
	 */
	public ArrayList<Vector<Object>> recuperaTablaFacturas(String txtFiltro) {
		ArrayList<Vector<Object>> tableData = null;
		
		String filtro = null;
		if (txtFiltro!=null) {
			ArrayList<String> filtros = new ArrayList<>();
			filtros.add("facturas.numero LIKE '%" + txtFiltro + "%'");
			filtros.add("facturas.nombreCliente LIKE '%" + txtFiltro + "%'");
			filtro = Utilidades.creaFiltroOR(filtros);
		}
		ArrayList<Factura> lista = recuperaPorFiltro(filtro);
		tableData = new ArrayList<>();
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
}
