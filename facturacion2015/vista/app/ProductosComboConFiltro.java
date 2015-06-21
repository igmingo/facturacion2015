package app;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class ProductosComboConFiltro extends JComboBox<Producto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cbFiltro;
	
	public ProductosComboConFiltro() {
		cbFiltro = null;
		setEditable(true);
		setSelectedIndex(-1);
		
		addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if (cbFiltro!=null && cbFiltro.length()>0){
					setFiltro(((JTextField) getEditor().getEditorComponent()).getText());
					recargarComboFiltrado();
				} else {
					setFiltro(((JTextField) getEditor().getEditorComponent()).getText());
					recargarComboFiltrado();
				}
			}
		});
		
	}
	
	/**Recupera el Id del Producto seleccionado
	 * @return Devuelve un entero con el ProductoId selecionado en el Combo
	 */
	public int getSelectedProductoId() {
		return getItemAt(getSelectedIndex()).getId();
	}
	
	/**Pone como Seleccionado del Producto con el ProductoId
	 * @param productoId
	 */
	public void setSelectedProductoId(Integer productoId) {
		recargarCombo(productoId);
	}
	
	/**Carga todos los Generos del Repositorio ProductosBDD, y deja seleccionado el que tiene el idProducto
	 * @param idProducto
	 */
	public void recargarCombo(Integer idProducto) {
		if (idProducto!=null){
			int idSel = idProducto;
			int pos = 0;
			removeAllItems();
			ArrayList<Producto> lista = new ProductosBDD().recuperaPorFiltro("productos.id =" + idProducto);
			if (lista!=null) {
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
	
	public void recargarCombo() {
		removeAllItems();
		ArrayList<Producto> lista = new ProductosBDD().recuperaPorFiltro(null);
		if (lista!=null) {
			for (Producto p : lista) {
				addItem(p);
			}
			setSelectedIndex(-1);
		}
	}
	
	public void recargarComboFiltrado() {
		removeAllItems();
		ArrayList<String> filtros = new ArrayList<>();
		filtros.add("productos.nombre LIKE '%" + cbFiltro + "%'");
		String filtroString = Utilidades.creaFiltroOR(filtros);
		ArrayList<Producto> lista = new ProductosBDD().recuperaPorFiltro(filtroString);
		if (lista!=null) {
			for (Producto p : lista) {
				addItem(p);
			}
			setSelectedIndex(lista.size()-1);
		}
	}

	public String getFiltro() {
		return cbFiltro;
	}

	public void setFiltro(String f) {
		this.cbFiltro = f;
	}
	
//	public void recargarCombo() {
//		recargarCombo("");
//	}

}
