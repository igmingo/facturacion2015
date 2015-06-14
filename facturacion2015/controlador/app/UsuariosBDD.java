package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class UsuariosBDD {

	//table DB usuarios
	//*id, nombre, *password, estado, *administrador, *email
	
	public ArrayList<Vector<Object>> recuperaTablaUsuarios(String criterio) {
		// Devuelve una tabla, o Vector de Vectores de objetos
		// Encabezados 'Email', 'Usuario', 'ID'
		ArrayList<Vector<Object>> tableData = null;
		criterio = "WHERE usuarios.email LIKE '%" + criterio + "%'";
		String sql = "SELECT usuarios.email, usuarios.nombre, usuarios.id FROM usuarios " + criterio + " ORDER BY usuarios.email";
		System.out.println(sql);
		tableData = new ArrayList<>();
		Connection c = new Conexion().getConection();
		if (c!=null) {
			try {
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				while (rs.next() == true) {
					//Los datos de la fila son un tipo VECTOR
					Vector<Object> filaData = new Vector<>();
					filaData.add(rs.getString("email"));
					filaData.add(rs.getString("nombre"));
					filaData.add(rs.getInt("id"));
					tableData.add(filaData);
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
		return tableData;
	}
	
	public ArrayList<Usuario> RecuperaTodos (){
		return Recupera("");
	}
	
	public ArrayList<Usuario> Recupera(String criterio){
		criterio = "WHERE usuarios.email LIKE '%" + criterio + "%'";
		String sql = "SELECT * FROM usuarios " + criterio + " ORDER BY usuarios.email";
		System.out.println(sql);
		ArrayList<Usuario> lista = new ArrayList<>(); 
		Connection c = new Conexion().getConection();
		if (c!=null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
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
		}
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Usuario RecuperaPorId (int id) {
		Usuario u = null;
		if (id!=0) {
			String 	sql = "SELECT * FROM usuarios " +
					"WHERE usuarios.id = " + id;
			System.out.println(sql);
			// CREO UNA CONEXION
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
		} else {
			u = new Usuario(0);
		}
		return u;
	}
	
	public int Grabar(Usuario u) {
		int respuesta = -1;
		String sql = "";
		if (u.getId()==0) {
			sql = "INSERT INTO usuarios SET " +
					"usuarios.nombre = '" + u.getName() + "' " + 
					"usuarios.estado = '" + u.getStatus() + "' " + 
					"usuarios.administrador = '" + u.isAdmin() + "' " + 
					"usuarios.email = '" + u.getEmail() + "'"
					;
		} else {
			sql = "UPDATE usuarios SET " +
					"usuarios.nombre = '" + u.getName() + "' " + 
					"usuarios.estado = '" + u.getStatus() + "' " + 
					"usuarios.administrador = '" + u.isAdmin() + "' " + 
					"usuarios.email = '" + u.getEmail() + "' " +
					"WHERE usuarios.id = " + u.getId();
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
				if (u.getId() != 0){
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
	
	public boolean Eliminar (int id) {
		boolean respuesta = false;
		String 	sql = "DELETE FROM usuarios " +
				"WHERE usuarios.id = " + id;
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

	public Usuario login(String text, String password) {
		// TODO Apéndice de método generado automáticamente
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
	
}
