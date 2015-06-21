package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class BDD {
	
	/**Ejecuta una Consulta SQL, CREANDO una conexión y llamando a consultaSQL
	 * Cierra la conexion y el ResultSet lo devuelve como un CachedRowSet
	 * @param sql: String con el sql
	 * @return CachedRowSet
	 */
	protected CachedRowSet consultaSQL(String sql) {
		Connection cnx = new Conexion().getConection();
		CachedRowSet crs = consultaSQL(cnx,sql);
		try {
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return crs;
	}

	/**Ejecuta una Consulta SQL, de una conexión creada ya ejecutando una executeQuery
	 * Devuelve el ResultSet que devuelve.
	 * @param cnx: conexión
	 * @param sql: String con la consulta sql
	 * @return ResultSet con el resultado de la executeQuery
	 */
	protected CachedRowSet consultaSQL(Connection cnx, String sql) {
		System.out.println(sql);
		ResultSet rs = null;
		CachedRowSet crs = null;
		try {
			Statement sentencia = cnx.createStatement();
			sentencia.executeQuery(sql);
			//EL RS DE executeQuery NUNCA ES NULO
			rs = sentencia.getResultSet();
			crs = new CachedRowSetImpl();
			crs.populate(rs);
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return crs;
	}
	
//	protected ResultSet consultaSQL_rs(Connection cnx, String sql) {
//		System.out.println(sql);
//		ResultSet rs = null;
//		try {
//			Statement sentencia = cnx.createStatement();
//			sentencia.executeQuery(sql);
//			//EL RS DE executeQuery NUNCA ES NULO
//			rs = sentencia.getResultSet();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		return rs;
//	}
	
	/**Ejecuta una SQL, CREANDO antes una conexión y ejecutando el SQL
	 * @param sql es el String con la SQL (INSERT, UPDATE o DELETE)
	 * @return Devuelve un Integer
	 * null, si no hay conexion
	 * >0 Es el id del SQL con INSERT
	 * 0  Se ha actualizado la tabla con UPDATE, o DELETE
	 * -1 Si no se realizado la SQL Correctamente, pero hay conexion
	 */
	protected Integer ejecutaSQL(String sql) {
		Connection cnx = new Conexion().getConection();
		Object resp = ejecutaSQL(cnx, sql);
		Integer r = null;
		try {
			cnx.close();
			try {
				r = (Integer) resp;
			} catch (Exception e) {
				r = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * Ejecuta una SQL en una Connection ya existente.
	 * No cierra la conexión ni la crea.
	 * @param cnx
	 * @param sql
	 * @return Devuelte un Object que puede ser un Integer o un ResultSet
	 * null si la conexion no existe
	 * SELECT
	 * devuelte un ResultSet con el resultado de la select
	 * INSERT
	 * >0 Se ha insertado la factura con el ID devuelto
	 * -1 La insercion no se ha podido llevar a cabo
	 * UPDATE //0 Se ha actualizado la tabla
	 * -1 El Update no ha afectado a ningun elemento
	 */
	protected Object ejecutaSQL(Connection cnx, String sql) {
		System.out.println(sql);
		Object respuesta = null;
		if (cnx!=null) {
			ResultSet rs = null;
			CachedRowSet crs = null;
			try {
				Statement sentencia = cnx.createStatement();
				sentencia.execute(sql,Statement.RETURN_GENERATED_KEYS);
				ResultSet keysGen = sentencia.getGeneratedKeys();
				rs = sentencia.getResultSet();
				if (keysGen!=null) {
					if(keysGen.next()) {
						respuesta = keysGen.getInt(1);
					} else {
						respuesta = sentencia.getUpdateCount()>0?0:-1;
					}
				}
				if (rs!=null) {
					crs = new CachedRowSetImpl();
					crs.populate(rs);
					rs.close();
					respuesta = rs;	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}
	
//	/**Ejecuta una Consulta SQL, CREANDO antes una conexión y ejecutando el SQL
//	 * @param sql
//	 * @return Devuelve un ResultSet
//	 */
//	protected ArrayList<Object> consultaSQL2(Connection cnx, String sql) {
//		System.out.println(sql);
//		//ArrayList<Object> lista = null;
//		ArrayList<Object> vLista = null;
//		try {
//			Statement sentencia = cnx.createStatement();
//			sentencia.execute(sql,Statement.RETURN_GENERATED_KEYS);
//			ResultSet indicesGenerados = sentencia.getGeneratedKeys();
//			ResultSet rs = sentencia.getResultSet();
//			vLista = new ArrayList();
//	        ResultSetMetaData rsmd = rs.getMetaData();
//	        int numCol = rsmd.getColumnCount(); 
//	        while(rs.next())
//	        {
//	        	//ArrayList<Object> vFila = new ArrayList();
//	        	Object[] vFila = new Object[numCol] ;
//		        for(int i=1;i <= numCol; i++) {
//		        	//vFila.add(rs.getObject(i));
//		        	vFila[i-1] = rs.getObject(i);
//		        }
//		        vLista.add(vFila);
//	        }
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		return vLista;
//	}
//	/**Ejecuta una Consulta SQL, CREANDO antes una conexión y ejecutando el SQL
//	 * @param sql
//	 * @return Devuelve un Array de dos dimensiones
//	 */
//	protected Object[][] consultaSQL_Array(String sql) {
//		Connection cnx = new Conexion().getConection();
//		Object[][] arrayRs = consultaSQL_Array(cnx,sql);
//		try {
//			cnx.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return arrayRs;
//	}
//	
//	protected Object[][] consultaSQL_Array(Connection cnx, String sql) {
//		System.out.println(sql);
//		Object[][] arrayRs = null;
//		try {
//			Statement sentencia = cnx.createStatement();
//			sentencia.executeQuery(sql);
//			//EL RS DE executeQuery NUNCA ES NULO
//			ResultSet rs = sentencia.getResultSet();
//			arrayRs = DbUtils.resultSetToArray(rs);
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		return arrayRs;
//	}
	
}
