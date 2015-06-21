package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;

public class UsuariosBDD extends BDD {

	//table DB usuarios
	//*id, nombre, *password, estado, *administrador, *email
	
	/*
	 * 	METODOS PUBLICOS
	 */
	public ArrayList<Usuario> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM usuarios WHERE ";
		sql += filtro == null || filtro.length() == 0 ? "1" : filtro;
		sql += " ORDER BY usuarios.email";
		ArrayList<Usuario> lista = null;
		CachedRowSet rs = consultaSQL(sql);
		try {
			lista = new ArrayList<>();
			while (rs.next() == true) {
				lista.add(new Usuario(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("email"),
						rs.getBoolean("administrador"),
						rs.getInt("estado")
						)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Usuario> recuperaTodos (){
		return recuperaPorFiltro(null);
	}
	
//	public ArrayList<Usuario> recuperaPorFiltro4(String criterio){
//		criterio = "WHERE usuarios.email LIKE '%" + criterio + "%'";
//		String sql = "SELECT * FROM usuarios " + criterio + " ORDER BY usuarios.email";
//		System.out.println(sql);
//		ArrayList<Usuario> lista = new ArrayList<>(); 
//		Connection c = new Conexion().getConection();
//		if (c!=null) {
//			try {
//				// Crea un ESTAMENTO (comando de ejecucion de un sql)
//				Statement comando = c.createStatement();
//				ResultSet rs = comando.executeQuery(sql);
//				while (rs.next() == true) {				
//					lista.add(new Usuario(
//							rs.getInt("id"),
//							rs.getString("nombre"),
//							rs.getString("email"),
//							rs.getBoolean("administrador"),
//							rs.getInt("estado")
//							)
//					);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		try {
//			c.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return lista;
//	}
	
	public Usuario recuperaPorId(int id){
		if (id != 0) {
			String filtro = "usuarios.id = " + id;
			ArrayList<Usuario> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Usuario c = new Usuario();
			c.setId(0);
			return c;
		}
	}
	
	public int grabar(Usuario u) {
		String sql = null;
		if (u.getId()==0) {
			sql = "INSERT INTO usuarios SET " +
			"usuarios.nombre = '" + u.getName() + "', " + 
			"usuarios.estado = " + u.getStatus() + ", " + 
			"usuarios.administrador = " + u.isAdmin() + ", " + 
			"usuarios.email = '" + u.getEmail() + "'"
			;
		} else {
			sql = "UPDATE usuarios SET " +
			"usuarios.nombre = '" + u.getName() + "', " + 
			"usuarios.estado = " + u.getStatus() + ", " + 
			"usuarios.administrador = " + u.isAdmin() + ", " + 
			"usuarios.email = '" + u.getEmail() + "' " +
			"WHERE usuarios.id = " + u.getId();
			;
		}
		return ejecutaSQL(sql);
	}
	
	public boolean eliminar(int id) {
		return ejecutaSQL("DELETE FROM usuarios WHERE usuarios.id = " + id)>0?true:false;
	}

	public Usuario login(String text, String password) {
		password = Encriptacion.getMensajeEncriptado(password, Encriptacion.SHA256);
		Usuario u = null;
		String criterio = "WHERE usuarios.email='" + text + "' AND usuarios.password='" + password + "'";
		String sql = "SELECT * FROM usuarios " + criterio + " ORDER BY usuarios.id";
		System.out.println(sql);
		Connection c = new Conexion().getConection();
		if (c!=null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				if ( rs.first() ) {
					u = new Usuario(
							rs.getInt("id"),
							rs.getString("nombre"),
							rs.getString("email"),
							rs.getBoolean("administrador"),
							rs.getInt("estado")
							);
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
		return u;
	}

	public boolean ponerPassword(int id, String password) {
		boolean respuesta = false;
		password = Encriptacion.getMensajeEncriptado(password, Encriptacion.SHA256);
		String sql = "";
		sql = "UPDATE usuarios SET " +
				"usuarios.password = '" + password + "' " +
				"WHERE usuarios.id = " + id;
				;
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
				//
				respuesta = comando.getUpdateCount()==1?true:false;
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
	
	/*
	 * RECUPERAR TABLAS ESPECIALES
	 * */
	public ArrayList<Vector<Object>> recuperaTablaUsuarios(String txtFiltro) {
		// Encabezados "Email", "Nombre", "Estado"
		ArrayList<Vector<Object>> tableData = null;
		ArrayList<String> filtros = new ArrayList<>();
		filtros.add("usuarios.email LIKE '%" + txtFiltro + "%'");
		filtros.add("usuarios.nombre LIKE '%" + txtFiltro + "%'");
		String filtro = Utilidades.creaFiltroOR(filtros);
		ArrayList<Usuario> lista = recuperaPorFiltro(filtro);
		tableData = new ArrayList<>();
		for (Usuario usuario : lista) {
			Vector<Object> filaData = new Vector<>();
			filaData.add(usuario);
			filaData.add(usuario.getName());
			filaData.add(usuario.getStatus());
			tableData.add(filaData);
		}
		return tableData;
	}
	
//	public ArrayList<Vector<Object>> recuperaTablaUsuarios(String txtFiltro) {
//		// Devuelve una tabla, o Vector de Vectores de objetos
//		// Encabezados "Email", "Nombre", "Estado"
//		ArrayList<Vector<Object>> tableData = null;
//		criterio = "WHERE usuarios.email LIKE '%" + criterio + "%'";
//		String sql = "SELECT usuarios.email, usuarios.nombre, usuarios.id FROM usuarios " + criterio + " ORDER BY usuarios.email";
//		System.out.println(sql);
//		tableData = new ArrayList<>();
//		Connection c = new Conexion().getConection();
//		if (c!=null) {
//			try {
//				Statement comando = c.createStatement();
//				ResultSet rs = comando.executeQuery(sql);
//				while (rs.next() == true) {
//					//Los datos de la fila son un tipo VECTOR
//					Vector<Object> filaData = new Vector<>();
//					filaData.add(rs.getString("email"));
//					filaData.add(rs.getString("nombre"));
//					filaData.add(rs.getInt("id"));
//					tableData.add(filaData);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		try {
//			c.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return tableData;
//	}
	
}
