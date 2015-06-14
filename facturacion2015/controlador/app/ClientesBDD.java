package app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class ClientesBDD {
	// TABLA CLIENTES BASE DE DATOS
//	id int(10) UNSIGNED
//	nombre varchar(30)
//	apellidos varchar(30)
//	nif varchar(9)
//	dirCorreo mediumtext
//	dirFactura mediumtext
//	dirEnvio mediumtext
//	contacto mediumtext
//	porcRecargoEquivalencia double
//	porcDescuento double
//	fechaAlta date
//	baja tinyint(1)
	
	//METODO PUBLICO
	
	public ArrayList<Cliente> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM clientes " + filtro
				+ " ORDER BY clientes.id";
		System.out.println(sql);
		ArrayList<Cliente> lista = new ArrayList<>();
		Connection c = new Conexion().getConection();
		if (c != null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				while (rs.next() == true) {
					int id = rs.getInt("id");
					String nombre = rs.getString("nombre");
					String apellidos= rs.getString("apellidos");
					String nif = rs.getString("nif");
					String dirCorreo = rs.getString("dirCorreo");
					String dirFactura = rs.getString("dirFactura");
					String dirEnvio = rs.getString("dirEnvio");
					String contacto = rs.getString("contacto");
					double porcRecargoEquivalencia = rs.getDouble("porcRecargoEquivalencia");
					double porcDescuento = rs.getDouble("porcDescuento");
					Date fechaAlta = rs.getDate("fechaAlta");
					boolean baja = rs.getBoolean("baja");
					lista.add(new Cliente(id, nombre, apellidos, nif, dirCorreo, dirFactura, dirEnvio, contacto, porcRecargoEquivalencia, porcDescuento, fechaAlta, baja));
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
	
	public ArrayList<Cliente> recuperaPorNombre(String criterio) {
		String filtro = "WHERE clientes.nombre LIKE '%" + criterio + "%' ";
		filtro += "OR clientes.apellidos LIKE '%" + criterio + "%'";
		return recuperaPorFiltro(filtro);
	}
	
	public ArrayList<Cliente> recuperaTodos(){
		return recuperaPorFiltro("WHERE 1");
	}
	
	public Cliente recuperaPorId(int id){
		if (id != 0) {
			String filtro = "WHERE clientes.id = " + id;
			ArrayList<Cliente> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Cliente c = new Cliente();
			c.setId(0);
			return c;
		}
	}
	
	public int grabar(Cliente cli) {
		int respuesta = -1;
		String sql = "";
		if (cli.getId()==0) {
			
//			id int(10) UNSIGNED
//			nombre varchar(30)
//			apellidos varchar(30)
//			nif varchar(9)
//			dirCorreo mediumtext
//			dirFactura mediumtext
//			dirEnvio mediumtext
//			contacto mediumtext
//			porcRecargoEquivalencia double
//			porcDescuento double
//			fechaAlta date
//			baja tinyint(1)
			
			
			sql = "INSERT INTO clientes SET " +
					"clientes.nombre = '" + cli.getNombre() + "', " +
					"clientes.apellidos = '" + cli.getApellidos() + "', " +
					"clientes.nif = '" + cli.getNif() + "', " +
					"clientes.dirCorreo = '" + cli.getDirCorreo() + "', " +
					"clientes.dirFactura = '" + cli.getDirFactura() + "', " +
					"clientes.dirEnvio = '" + cli.getDirEnvio() + "', " +
					"clientes.contacto = '" + cli.getContacto() + "', " +
					"clientes.porcRecargoEquivalencia = " + cli.getPorcRecargoEquivalencia() + ", " +
					"clientes.porcDescuento = " + cli.getPorcDescuento() + ", " +
					"clientes.fechaAlta = '" + Utilidades.fechaToSQL(cli.getFechaAlta()) + "', " +
					"clientes.baja = " + cli.isBaja()
					;
		} else {
			sql = "UPDATE clientes SET " +
					"clientes.nombre = '" + cli.getNombre() + "', " +
					"clientes.apellidos = '" + cli.getApellidos() + "', " +
					"clientes.nif = '" + cli.getNif() + "', " +
					"clientes.dirCorreo = '" + cli.getDirCorreo() + "', " +
					"clientes.dirFactura = '" + cli.getDirFactura() + "', " +
					"clientes.dirEnvio = '" + cli.getDirEnvio() + "', " +
					"clientes.contacto = '" + cli.getContacto() + "', " +
					"clientes.porcRecargoEquivalencia = " + cli.getPorcRecargoEquivalencia() + ", " +
					"clientes.porcDescuento = " + cli.getPorcDescuento() + ", " +
					"clientes.fechaAlta = '" + Utilidades.fechaToSQL(cli.getFechaAlta()) + "', " +
					"clientes.baja = " + cli.isBaja() + " " +
					"WHERE clientes.id = " + cli.getId()
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
				if (cli.getId() != 0){
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
		String 	sql = "DELETE FROM clientes " +
				"WHERE clientes.id = " + id;
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
		return respuesta;
	}
	
	//RECUPERAR TABLAS ESPECIALES
	public ArrayList<Vector<Object>> recuperaTablaClientes(String filtro) {
		ArrayList<Vector<Object>> tableData = null;
		ArrayList<Cliente> lista = recuperaPorFiltro(filtro);
		tableData = new ArrayList<>();
		for (Cliente cliente : lista) {
			Vector<Object> filaData = new Vector<>();
			filaData.add(cliente);
			filaData.add(cliente.getNombre());
			filaData.add(cliente.getApellidos());
			filaData.add(cliente.getNif());
			tableData.add(filaData);
		}
		return tableData;
	}
	
}
