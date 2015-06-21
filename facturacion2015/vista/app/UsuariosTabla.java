package app;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class UsuariosTabla extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -599114548334641747L;
	private String filtro;

	// METODOS CONSTRUCTORES
	public UsuariosTabla(String filtro) {
		this.filtro = filtro;
		setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"Usuario", "Nombre", "Estado"
			}
		) {
			Class[] columnTypes = new Class[] {
				Usuario.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		getColumnModel().getColumn(0).setPreferredWidth(174);
		getColumnModel().getColumn(1).setPreferredWidth(173);

		getColumnModel().getColumn(0).setPreferredWidth(200);
		setAutoCreateRowSorter(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = getSelectedRow();
				if (row != -1) {
					Usuario usu = (Usuario) getValueAt(row, 0);
					UsuarioDialogo dialog = new UsuarioDialogo(usu);
					Usuario c = dialog.mostrar();
					if (c != null) {
						actualizarTabla(filtro);
					}
				}
			}
		});
	}

	public void actualizarTabla(String filtro) {
		this.setFiltro(filtro);
		ArrayList<Vector<Object>> tabla = new UsuariosBDD().recuperaTablaUsuarios(filtro);
		DefaultTableModel dtm = (DefaultTableModel) getModel();
		dtm.setRowCount(0);
		for (Vector<Object> fila : tabla) {
			dtm.addRow(fila);
		}
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

}
