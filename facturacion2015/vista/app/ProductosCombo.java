package app;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class ProductosCombo extends JComboBox<Producto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void recargarCombo() {
		recargarCombo(0);
	}
	
	public int getSelectedId() {
		//Devuelve el ID del Genero selecionado en el Combo
		return getItemAt(getSelectedIndex()).getId();
	}
	
	public void setSelectedId(Integer idProducto) {
		//Devuelve el ID del Genero selecionado en el Combo
		recargarCombo(idProducto);
	}
	
	/**Carga todos los Generos del Repositorio ProductosBDD, y deja seleccionado el que tiene el idProducto
	 * @param idProducto
	 */
	public void recargarCombo(Integer idProducto) {
		if (idProducto!=null){
			int idSel = idProducto;
			int pos = 0;
			removeAllItems();
			ArrayList<Producto> lista = new ProductosBDD().recuperaTodos();
			for (Producto p : lista) {
				addItem(p);
				if (p.getId() == idSel) {
					setSelectedIndex(pos);
				}
				pos++;
			}
		}
	}

}
