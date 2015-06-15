package app;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.SpinnerNumberModel;

import java.awt.Component;

import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class FacturaDetallesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable prodTabla;
	private ProductosCombo cbProducto;
	private JSpinner numCantidad;
	private JTextField txtTotal;
	private int facturaId;
	
//	private DefaultTableModel dtm;
	
	public FacturaDetallesPanel(int facId) {
		this.facturaId = facId;
		//setBounds(new Rectangle(0, 0, 550, 300));
		setLayout(new BorderLayout(0, 0));
		
		JPanel form = new JPanel();
		add(form, BorderLayout.NORTH);
		form.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow(3)"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		cbProducto = new ProductosCombo();
		form.add(cbProducto, "1, 1, fill, default");
		
		JLabel lblCantidad = new JLabel("Cantidad");
		form.add(lblCantidad, "3, 1, right, default");
		
		numCantidad = new JSpinner();
		numCantidad.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		form.add(numCantidad, "4, 1, fill, default");
		
		JButton btnAgregar = new JButton("Agregar");
		form.add(btnAgregar, "6, 1, fill, center");
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarFilaSeleccionada(prodTabla);
			}
		});
		form.add(btnEliminar, "7, 1, fill, center");
		
		//BOTONES
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Producto producto = (Producto) cbProducto.getSelectedItem();
				FacturaDetalle fd = new FacturaDetalle(0, facturaId, producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getIva(), (int) numCantidad.getValue());
				insertarFacturaDetalle(fd);
				cbProducto.setSelectedIndex(-1);
				//SpinnerNumberModel a = (SpinnerNumberModel) numCantidad.getModel();
				//numCantidad.setValue(a.getMinimum());
			}
		});
		
		JScrollPane pnTable = new JScrollPane();
		add(pnTable, BorderLayout.CENTER);
		
		prodTabla = new JTable();
		prodTabla.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Producto", "Precio", "IVA", "Cantidad"
			}
		) {
			Class[] columnTypes = new Class[] {
					FacturaDetalle.class, Double.class, Double.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		prodTabla.getColumnModel().getColumn(0).setMinWidth(120);
		prodTabla.getColumnModel().getColumn(1).setPreferredWidth(70);
		prodTabla.getColumnModel().getColumn(1).setMinWidth(70);
		prodTabla.getColumnModel().getColumn(1).setMaxWidth(100);
		prodTabla.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println(e);
				if (e.getKeyChar()==KeyEvent.VK_DELETE || e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
					JTable t = (JTable) e.getSource();
					eliminarFilaSeleccionada(t);
				}
			}
		});
		pnTable.setViewportView(prodTabla);
		
		JPanel pnButtons = new JPanel();
		add(pnButtons, BorderLayout.SOUTH);
		pnButtons.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("26px"),}));
		
		JButton btnBorrarTabla = new JButton("Borrar Tabla");
		btnBorrarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vaciar(prodTabla);
			}
		});
		pnButtons.add(btnBorrarTabla, "1, 1");
		
		JButton btnTotalizar = new JButton("Totalizar");
		pnButtons.add(btnTotalizar, "2, 1, left, fill");
		btnTotalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int suma = 0;
				DefaultTableModel dtm = (DefaultTableModel) prodTabla.getModel();
				for (int i = 0; i < dtm.getRowCount(); i++) {
					suma += (int) dtm.getValueAt(i, 1);
				}
				txtTotal.setText(""+suma);
			}
		});
		
		JLabel lblTotal = new JLabel("Total");
		pnButtons.add(lblTotal, "3, 1, right, center");
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pnButtons.add(txtTotal, "5, 1, right, fill");
		txtTotal.setColumns(10);
		
		//ACCIONES A REALIZAR AL INICIAR
		vaciar(prodTabla);
		cbProducto.recargarCombo();
	}

	protected void eliminarFilaSeleccionada(JTable t) {
		int row = t.getSelectedRow();
		if (row>=0) {
			System.out.println(row);
			DefaultTableModel dtm = (DefaultTableModel) t.getModel();
			dtm.removeRow(row);
		}
	}

	private void vaciar(JTable t) {
		DefaultTableModel datos = (DefaultTableModel) t.getModel();
		datos.setRowCount(0);
	}
	
	private void insertarFacturaDetalle(FacturaDetalle fd) {
		//"Detalle", "Precio", "IVA", "Cantidad"
		Vector<Object> filaData = new Vector<>();
		filaData.add(fd);
		filaData.add(fd.getProdPrecio());
		filaData.add(fd.getProdIva());
		filaData.add(fd.getCantidad());
		DefaultTableModel datos = (DefaultTableModel) prodTabla.getModel();
		datos.addRow(filaData);
	}
	
	public void putListaDetalles(ArrayList<FacturaDetalle> listaDetalles) {
		vaciar(prodTabla);
		for (FacturaDetalle fd : listaDetalles) {
			insertarFacturaDetalle(fd);
		}
	}
	
	public ArrayList<FacturaDetalle> getListaDetalles() {
		DefaultTableModel datos = (DefaultTableModel) prodTabla.getModel();
		ArrayList<FacturaDetalle> listaDetalles = new ArrayList<>();
		for (int i = 0; i < datos.getRowCount(); i++) {
			FacturaDetalle fd = (FacturaDetalle) datos.getValueAt(i, 0);
			listaDetalles.add(fd);
		}
		return listaDetalles;
	}

}
