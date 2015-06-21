package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;

public class ProductosBDD extends BDD {
/*	TABLA productos BASE DE DATOS
	id int(11)
	nombre varchar(30)
	precio double
	iva double
	stock int(11)
	descripcion mediumtext
	baja tinyint(1)*/
	
	/*
	 * 	METODOS PUBLICOS
	 */
	/** Resta el Stock la cantidad indicada
	 * @param cnx es la Conexion activa
	 * @param prodId es el ID del Producto que vamos a bajar el stock
	 * @param cantidad es la cantidad que va a ser restada (debe ser positiva)
	 * @return
	 */
	public Integer restarStock (Connection cnx, int prodId, int cantidad) {
		String sql = "UPDATE productos SET productos.stock = productos.stock - " + cantidad + " WHERE productos.id = " + prodId;
		System.out.println(sql);
		ejecutaSQL(cnx, sql);
		return cantidad;
	}
	
	public ArrayList<Producto> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM productos WHERE ";
		sql += filtro == null || filtro.length() == 0 ? "1" : filtro;
		sql += " ORDER BY productos.id";
		ArrayList<Producto> lista = null;
		CachedRowSet rs = consultaSQL(sql);
		try {
			lista = new ArrayList<>();
			while (rs.next() == true) {
				lista.add(new Producto(rs.getInt("id"), rs.getString("nombre"),
						rs.getDouble("precio"), rs.getDouble("iva"), rs
								.getInt("stock"), rs.getString("descripcion"),
						rs.getBoolean("baja")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Producto> recuperaPorNombre(String criterio) {
		String filtro = "productos.nombre LIKE '%" + criterio + "%' ";
		return recuperaPorFiltro(filtro);
	}
	
	public ArrayList<Producto> recuperaTodos(){
		return recuperaPorFiltro(null);
	}
	
	public Producto recuperaPorId(int id){
		if (id != 0) {
			String filtro = "productos.id = " + id;
			ArrayList<Producto> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Producto p = new Producto();
			p.setId(0);
			return p;
		}
	}
	
	public int grabar(Producto prod) {
		String sql = null;
		if (prod.getId()==0) {
			sql = "INSERT INTO productos SET " +
					"productos.nombre = '" + prod.getNombre() + "', " +
					"productos.precio = " + prod.getPrecio() + ", " +
					"productos.iva = " + prod.getIva() + ", " +
					"productos.stock = " + prod.getStock() + ", " +
					"productos.descripcion = '" + prod.getDescripcion() + "', " +
					"productos.baja = " + prod.isBaja() + ""
					;
		} else {
			sql = "UPDATE productos SET " +
					"productos.nombre = '" + prod.getNombre() + "', " +
					"productos.precio = " + prod.getPrecio() + ", " +
					"productos.iva = " + prod.getIva() + ", " +
					"productos.stock = " + prod.getStock() + ", " +
					"productos.descripcion = '" + prod.getDescripcion() + "', " +
					"productos.baja = " + prod.isBaja() + " " +
					"WHERE productos.id = " + prod.getId()
					;
		}
		return ejecutaSQL(sql);
	}
	
	public boolean eliminar(int id) {
		return ejecutaSQL("DELETE FROM productos WHERE productos.id = " + id)>0?true:false;
	}
	
	/*
	 * RECUPERAR TABLAS ESPECIALES
	 */
	public ArrayList<Vector<Object>> recuperaTablaProductos(String txtFiltro) {
		//"Producto", "Precio", "Stock"
		ArrayList<Vector<Object>> tableData = null;
		ArrayList<String> filtros = new ArrayList<>();
		filtros.add("productos.nombre LIKE '%" + txtFiltro + "%'");
		filtros.add("productos.descripcion LIKE '%" + txtFiltro + "%'");
		String filtro = Utilidades.creaFiltroOR(filtros);
		ArrayList<Producto> lista = recuperaPorFiltro(filtro);
		tableData = new ArrayList<>();
		for (Producto pro : lista) {
			Vector<Object> filaData = new Vector<>();
			filaData.add(pro);
			filaData.add(pro.getPrecio());
			filaData.add(pro.getStock());
			tableData.add(filaData);
		}
		return tableData;
	}
	
}
