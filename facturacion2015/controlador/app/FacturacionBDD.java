package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.jws.Oneway;

public class FacturacionBDD {
	
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
		Object retorno = ejecutaSQL(cnx,sql);
		try {
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Integer) retorno;
	}
	
	protected ArrayList<Object> consultaSQL(String sql) {
		Connection cnx = new Conexion().getConection();
		Object retorno = consultaSQL(cnx,sql);
		try {
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (ArrayList<Object>) retorno;
	}
	
	/**Ejecuta una Consulta SQL, CREANDO antes una conexión y ejecutando el SQL
	 * @param sql
	 * @return Devuelve un ResultSet
	 */
	protected ArrayList<Object> consultaSQL(Connection cnx, String sql) {
		System.out.println(sql);
		ArrayList<Object> lista = null;
		try {
			Statement sentencia = cnx.createStatement();
			sentencia.execute(sql,Statement.RETURN_GENERATED_KEYS);
			ResultSet indicesGenerados = sentencia.getGeneratedKeys();
			ResultSet rs = sentencia.getResultSet();
			int respuesta;
			if (indicesGenerados!=null) {
				if(indicesGenerados.next()) {
					respuesta = indicesGenerados.getInt(1);
				} else {
					respuesta = sentencia.getUpdateCount()>0?0:-1;
				}
			}
			Vector coleccion = new Vector();
	        coleccion.add(",");
	        Vector vHeader = new Vector();
	        Vector vTable = new Vector();
	        int index = 0;
	        vHeader.add("Index");
	        //Get table headers
	        ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			for (int i=1; i<rsmd.getColumnCount()+1; i++) 
	        {                                          
	            vHeader.add(rsmd.getColumnName(i));
	        }               
	        //Get table data
	        vHeader = new Vector();
	        vTable = new Vector();
	        while(!rs.isLast()) {
	        	rs.next(); 
	            lista = new ArrayList();
	            Vector vRow = new Vector();
	            vRow.add(index++);
	            lista.add(index++);
	            for (int i = 1; i < rsmd.getColumnCount()+1; i++) {
	                vRow.add(rs.getString(i)); 
	                lista.add(rs.getString(i));
	                vTable.add(vRow);
	                vRow.removeAll(coleccion);
				}
	            vHeader.removeAll(coleccion);
	        }
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Ejecuta una SQL en una Connection activa.
	 * No cierra la conexión ni la crea.
	 * 
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
			try {
				Statement sentencia = cnx.createStatement();
				sentencia.execute(sql,Statement.RETURN_GENERATED_KEYS);
				ResultSet indicesGenerados = sentencia.getGeneratedKeys();
				ResultSet resultados = sentencia.getResultSet();
				if (indicesGenerados!=null) {
					if(indicesGenerados.next()) {
						respuesta = indicesGenerados.getInt(1);
					} else {
						respuesta = sentencia.getUpdateCount()>0?0:-1;
					}
				}
				if (resultados!=null) {
					respuesta = resultados;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}
	
}
