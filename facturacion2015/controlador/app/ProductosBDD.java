package app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class ProductosBDD {
	// TABLA productos BASE DE DATOS
//	id int(11)
//	nombre varchar(30)
//	precio double
//	iva double
//	stock int(11)
//	descripcion mediumtext
//	baja tinyint(1)
	
	//METODO PUBLICO
	
	public ArrayList<Producto> recuperaPorFiltro(String filtro) {
		String sql = "SELECT * FROM productos " + filtro
				+ " ORDER BY productos.id";
		System.out.println(sql);
		ArrayList<Producto> lista = new ArrayList<>();
		Connection c = new Conexion().getConection();
		if (c != null) {
			try {
				// Crea un ESTAMENTO (comando de ejecucion de un sql)
				Statement comando = c.createStatement();
				ResultSet rs = comando.executeQuery(sql);
				while (rs.next() == true) {
					int id = rs.getInt("id");
					String nombre = rs.getString("nombre");
					double precio = rs.getDouble("precio");
					double iva = rs.getDouble("iva");
					int stock = rs.getInt("stock");
					String descripcion = rs.getString("descripcion");
					boolean baja = rs.getBoolean("baja");
					lista.add(new Producto(id, nombre, precio, iva, stock, descripcion, baja));
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
	
	public ArrayList<Producto> recuperaPorNombre(String criterio) {
		String filtro = "WHERE productos.nombre LIKE '%" + criterio + "%' ";
		//filtro += "OR productos.descripcion LIKE '%" + criterio + "%'";
		return recuperaPorFiltro(filtro);
	}
	
	public ArrayList<Producto> recuperaTodos(){
		return recuperaPorFiltro("WHERE 1");
	}
	
	public Producto recuperaPorId(int id){
		if (id != 0) {
			String filtro = "WHERE productos.id = " + id;
			ArrayList<Producto> lista = recuperaPorFiltro(filtro);
			return lista.get(0);
		} else {
			Producto p = new Producto();
			p.setId(0);
			return p;
		}
	}
	
	public int grabar(Producto prod) {
		int respuesta = -1;
		String sql = "";
		if (prod.getId()==0) {
			
			// TABLA productos BASE DE DATOS
//			id int(11)
//			nombre varchar(30)
//			precio double
//			iva double
//			stock int(11)
//			descripcion mediumtext
//			baja tinyint(1)
			
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
				if (prod.getId() != 0){
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
		String 	sql = "DELETE FROM productos " +
				"WHERE productos.id = " + id;
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
	
	public ArrayList<Vector<Object>> recuperaTablaProductos(String filtro) {
		//"Producto", "Precio", "Stock"
		ArrayList<Vector<Object>> tableData = null;
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
	
//	//RECUPERAR TABLAS ESPECIALES
//	public ArrayList<Vector<Object>> recuperaTablaProductos2(String filtro) {
//			// Devuelve una tabla, o Vector de Vectores de objetos
//			// `id`, `nombre`, `apellidos`, `nif`
//			ArrayList<Vector<Object>> tableData = null;
//			filtro = "WHERE productos.nombre LIKE '%" + filtro + "%'";
//			String sql = "SELECT productos.id, productos.nombre FROM productos " + filtro + " ORDER BY productos.nombre";
//			System.out.println(sql);
//			tableData = new ArrayList<>();
//			Connection c = new Conexion().getConection();
//			if (c!=null) {
//				try {
//					Statement comando = c.createStatement();
//					ResultSet rs = comando.executeQuery(sql);
//					while (rs.next() == true) {
//						//Los datos de la fila son un tipo VECTOR
//						Vector<Object> filaData = new Vector<>();
//						filaData.add(rs.getInt("id"));
//						filaData.add(rs.getString("nombre"));
//						tableData.add(filaData);
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			try {
//				c.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return tableData;
//	}
	
}
