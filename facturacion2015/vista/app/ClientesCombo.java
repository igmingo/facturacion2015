package app;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ClientesCombo extends JComboBox<Cliente> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClientesCombo() {
		super();
		setEditable(true);
		try {
			cargarCombo();
		} catch (Exception e) {
		}
	}
	
	public ClientesCombo(int idCliente) {
		super();
		setEditable(true);
		try {
			recargarCombo(idCliente);
		} catch (Exception e) {
		}
	}
	
	public Cliente obtenerClienteSeleccionado() {
		return getItemAt(getSelectedIndex());
	}
	
	public int obtenerClienteIdSeleccionado() {
		//Devuelve el ID del Genero selecionado en el Combo
		return getItemAt(getSelectedIndex()).getId();
	}
	
	public void ponerClienteIdSeleccionado(Integer idCliente) {
		//Devuelve el ID del Genero selecionado en el Combo
		recargarCombo(idCliente);
	}
	
	/**Carga todos los Generos del Repositorio ClientesBDD, y deja seleccionado el que tiene el idCliente
	 * @param idCliente
	 */
	public void recargarCombo(Integer idCliente) {
		if (idCliente!=null){
			int pos = 0;
			removeAllItems();
			ArrayList<Cliente> lista = new ClientesBDD().recuperaTodos();
			for (Cliente c : lista) {
				addItem(c);
				if (c.getId() == idCliente) {
					setSelectedIndex(pos);
				}
				pos++;
			}
		}
	}
	
	public void recargarCombo() {
		int clienteId = obtenerClienteIdSeleccionado();
		recargarCombo(-1);
		ponerClienteIdSeleccionado(clienteId);
	}
	
	public void cargarCombo() {
			removeAllItems();
			ArrayList<Cliente> lista = new ClientesBDD().recuperaTodos();
			for (Cliente c : lista) {
				addItem(c);
			}
	}
	
	public String obtenerNombreCliente() {
		return ((JTextField) getEditor().getEditorComponent()).getText();
	}

}
