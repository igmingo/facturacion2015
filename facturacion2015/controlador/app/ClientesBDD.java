package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;

public class ClientesBDD extends BDD {
/*  TABLA CLIENTES BASE DE DATOS
	id int(10) UNSIGNED
	nombre varchar(30)
	apellidos varchar(30)
	nif varchar(9)
	dirCorreo mediumtext
	dirFactura mediumtext
	dirEnvio mediumtext
	contacto mediumtext
	porcRecargoEquivalencia double
	porcDescuento double
	fechaAlta date
	baja tinyint(1)*/
	
	/*
	 * 	METODOS PUBLICOS
	 */
	public ArrayList<Cliente> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM clientes WHERE ";
		sql += filtro == null || filtro.length() == 0 ? "1" : filtro;
		sql += " ORDER BY clientes.id";
		ArrayList<Cliente> lista = null;
		CachedRowSet rs = consultaSQL(sql);
		try {
			lista = new ArrayList<>();
			while (rs.next() == true) {
				lista.add(new Cliente(rs.getInt("id"), rs.getString("nombre"),
						rs.getString("apellidos"), rs.getString("nif"), rs
								.getString("dirCorreo"), rs
								.getString("dirFactura"), rs
								.getString("dirEnvio"), rs
								.getString("contacto"), rs
								.getDouble("porcRecargoEquivalencia"), rs
								.getDouble("porcDescuento"), rs
								.getDate("fechaAlta"), rs.getBoolean("baja")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Cliente> recuperaPorNombre(String criterio) {
		String filtro = "clientes.nombre LIKE '%" + criterio + "%' ";
		filtro += "OR clientes.apellidos LIKE '%" + criterio + "%'";
		return recuperaPorFiltro(filtro);
	}
	
	public ArrayList<Cliente> recuperaTodos(){
		return recuperaPorFiltro(null);
	}
	
	public Cliente recuperaPorId(int id){
		if (id != 0) {
			String filtro = "clientes.id = " + id;
			ArrayList<Cliente> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Cliente c = new Cliente();
			c.setId(0);
			return c;
		}
	}
	
	public int grabar(Cliente cli) {
		String sql = null;
		if (cli.getId()==0) {
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
		return ejecutaSQL(sql);
	}
	
	public boolean eliminar(int id) {
		return ejecutaSQL("DELETE FROM clientes WHERE clientes.id = " + id)>0?true:false;
	}
	
	/*
	 * RECUPERAR TABLAS ESPECIALES
	 * */
	public ArrayList<Vector<Object>> recuperaTablaClientes(String txtFiltro) {
		ArrayList<Vector<Object>> tableData = null;
		ArrayList<String> filtros = new ArrayList<>();
		filtros.add("clientes.nombre LIKE '%" + txtFiltro + "%'");
		filtros.add("clientes.apellidos LIKE '%" + txtFiltro + "%'");
		filtros.add("clientes.nif LIKE '%" + txtFiltro + "%'");
		String filtro = Utilidades.creaFiltroOR(filtros);
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
