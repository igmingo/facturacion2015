package app;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DbUtils {
	
	/**
	 * Convierte un resultSet en un Array de Objets de 2 dimensiones.
	 * En la fila 0 se ha insertado el texto de la cabezera
	 * El numero de filas, es el numero de resultados mas uno por la cabezera.
	 * Por lo que cada registro empieza en el indice i = 1, hasta el indice Object.length
	 * Las comunas (o campos) de cada registro, van desde 0 hasta el (Object[0]).length
	 * 
	 * @param rs
	 * @return Object[][] de tipo Object[ cabezera + i filas ][ j columnas ]
	 */
	public static Object[][] resultSetToArray(ResultSet rs) {
		Object arrayRs[][] = null;
		try {
			rs.last();
			ResultSetMetaData rsmd = rs.getMetaData();
			int numCols = rsmd.getColumnCount();
			int numFils = rs.getRow();
			arrayRs = new Object[numFils+1][numCols];
			int j = 0;
			for (int i=0; i<numCols; i++) {
				arrayRs[j][i] = rsmd.getColumnName(i + 1);
	        }
			j++;
			rs.beforeFirst();
			while (rs.next()) {
				for (int i = 0; i < numCols; i++) {
					arrayRs[j][i] = rs.getObject(i + 1);
				}
				j++;
			}
		} catch (Exception e) {
		}
		return arrayRs;
	}
	
	public static TableModel resultSetToTableModel(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numCols = rsmd.getColumnCount();
			Vector columnNames = new Vector();
			// Get the column names
			for (int column = 0; column < numCols; column++) {
				columnNames.addElement(rsmd.getColumnLabel(column + 1));
			}
			// Get all rows.
			Vector rows = new Vector();
			while (rs.next()) {
				Vector newRow = new Vector();
				for (int i = 1; i <= numCols; i++) {
					newRow.addElement(rs.getObject(i));
				}
				rows.addElement(newRow);
			}
			return new DefaultTableModel(rows, columnNames);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Object> resultSetToArrayList(ResultSet rs) {
		ArrayList<Object> lista = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numCols = rsmd.getColumnCount();
			lista = new ArrayList();
			ArrayList<String> cabeceras = new ArrayList();
			for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
				cabeceras.add(rsmd.getColumnName(i));
			}
			lista.add(cabeceras);
			while (rs.next()) {
				ArrayList<Object> fila = new ArrayList();
				for (int i = 1; i <= numCols; i++) {
					fila.add(rs.getObject(i));
				}
				lista.add(fila);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
}
