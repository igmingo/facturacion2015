package app;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class ClientesCombo extends JComboBox<Cliente> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void recargarCombo() {
		recargarCombo(0);
	}
	
	public Cliente getSelectedCliente() {
		return getItemAt(getSelectedIndex());
	}
	
	public int getSelectedId() {
		//Devuelve el ID del Genero selecionado en el Combo
		return getItemAt(getSelectedIndex()).getId();
	}
	
	public void setSelectedId(Integer id) {
		//Devuelve el ID del Genero selecionado en el Combo
		recargarCombo(id);
	}
	
	/**Carga todos los Generos del Repositorio ClientesBDD, y deja seleccionado el que tiene el idCliente
	 * @param idCliente
	 */
	public void recargarCombo(Integer idCliente) {
		if (idCliente!=null){
			int idSel = idCliente;
			int pos = 0;
			removeAllItems();
			ArrayList<Cliente> lista = new ClientesBDD().recuperaTodos();
			for (Cliente c : lista) {
				addItem(c);
				if (c.getId() == idSel) {
					setSelectedIndex(pos);
				}
				pos++;
			}
		}
	}

}
