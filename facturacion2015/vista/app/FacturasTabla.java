package app;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// TABLA facturas BASE DE DATOS
//id int(10) UNSIGNED No auto_increment
//numero int(11) No
//fecha date No
//impTotal double No
//cobrada tinyint(1) No

public class FacturasTabla extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -371381706871909469L;
	private String filtro;
	
	public FacturasTabla(String fil) {
		this.filtro = fil;
		setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"Factura", "Fecha", "Cliente", "Importe Total", "Cobrada"
			}
		) {
			Class[] columnTypes = new Class[] {
				Factura.class, Date.class, String.class, Double.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		getColumnModel().getColumn(2).setPreferredWidth(300);
		getColumnModel().getColumn(2).setMinWidth(50);
		getColumnModel().getColumn(4).setPreferredWidth(60);
		getColumnModel().getColumn(4).setMinWidth(60);
		getColumnModel().getColumn(4).setMaxWidth(60);
		setAutoCreateRowSorter(true);
		actualizarTabla(this.filtro);
	}
	
	public void actualizarTabla(String filtro) {
		this.setFiltro(filtro);
		ArrayList<Vector<Object>> tabla = new FacturasBDD().recuperaTablaFacturas(filtro);
		if (tabla!=null) {
			DefaultTableModel dtm = (DefaultTableModel) getModel();
			dtm.setRowCount(0);
			for (Vector<Object> fila : tabla) {
				dtm.addRow(fila);
			}
		}
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	public int obtenerFacturaId(){
		DefaultTableModel dtm = (DefaultTableModel) getModel();
		Factura fac = (Factura) dtm.getValueAt(getSelectedRow(),0);
		return fac.getId();
	}

}
