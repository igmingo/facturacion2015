package app;


import javax.swing.JComboBox;

public class EstadosCombo extends JComboBox<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Es estado es el indice de ComboBox
	//Las opciones son (String nombreEstado)
	// ("Desactivado")
	// ("Activado")
	
	public void recargarCombo() {
		recargarCombo(0);
	}
	
	public int getEstado() {
		return getSelectedIndex();
	}

	public void setEstado(int indice) {
		setSelectedIndex(indice);
	}

	public void recargarCombo(int indice) {
		addItem("Desactivado");
		addItem("Activado");
		setSelectedIndex(indice>=0 && indice<=1?indice:0);
	}

}
