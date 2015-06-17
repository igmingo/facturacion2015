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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FacturaDetallesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable prodTabla;
	private ProductosCombo cbProducto;
	private JSpinner numCantidad;
	private JSpinner txtIvas;
	private int facturaId;
	private JSpinner txtBases;
	
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

		form.add(btnEliminar, "7, 1, fill, center");
		
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
		pnTable.setViewportView(prodTabla);
		
		JPanel pnButtons = new JPanel();
		add(pnButtons, BorderLayout.SOUTH);
		pnButtons.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("pref:grow"),},
			new RowSpec[] {
				RowSpec.decode("26px"),}));
		
		JButton btnBorrarTabla = new JButton("Borrar Tabla");
		pnButtons.add(btnBorrarTabla, "1, 1, fill, fill");
		
		JButton btnTotalizar = new JButton("Totalizar");
		pnButtons.add(btnTotalizar, "2, 1, fill, fill");
		
		JLabel lblBase = new JLabel("Productos");
		pnButtons.add(lblBase, "4, 1, right, fill");
		
		txtBases = new JSpinner();
		txtBases.setEnabled(false);
		txtBases.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnButtons.add(txtBases, "6, 1, fill, fill");
		JLabel lblTotal = new JLabel("IVA");
		pnButtons.add(lblTotal, "8, 1, fill, center");
		
		txtIvas = new JSpinner();
		txtIvas.setEnabled(false);
		txtIvas.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		txtIvas.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pnButtons.add(txtIvas, "10, 1, fill, fill");
		
		/*
		 * ESCUCHADORES
		 */
		
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
		
		prodTabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				System.out.println(me);
				if (me.getClickCount()>=2) {
					int row = prodTabla.getSelectedRow();
					if (row!=-1) {
						FacturaDetalle fd = obtenerFacturaDetalleEn(row);
						System.out.println(fd);
							FacturaDetalleDialogo dialog = new FacturaDetalleDialogo (fd);
							FacturaDetalle c = dialog.mostrar();
							if (c!=null) {
								ponerFacturaDetalle(row, c);
								prodTabla.validate();
								prodTabla.repaint();
							}
					}
				}
			}
		});
		
		/*
		 * BOTONES
		 */
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarFilaSeleccionada(prodTabla);
			}
		});
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Producto producto = (Producto) cbProducto.getSelectedItem();
				if (producto!=null) {
					FacturaDetalle fd = new FacturaDetalle(0, facturaId, producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getIva(), (int) numCantidad.getValue());
					agregarFacturaDetalle(fd);
					cbProducto.setSelectedIndex(-1);
				}				

				//SpinnerNumberModel a = (SpinnerNumberModel) numCantidad.getModel();
				//numCantidad.setValue(a.getMinimum());
			}
		});
		
		btnTotalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculaImportesProductos();
			}
		});
		
		btnBorrarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vaciar();
			}
		});
		
		/*
		 * ACCIONES A REALIZAR AL INICIAR
		 * */
		vaciar();
		cbProducto.recargarCombo();
		
	}

	/*
	 * 
	 * METODOS PRIVADOS
	 *
	 *
	*/
	
	private void calculaImportesProductos() {
		DefaultTableModel dtm = (DefaultTableModel) prodTabla.getModel();
		double sumaBases = 0;
		double sumaIvas = 0;
		for (int i = 0; i < dtm.getRowCount(); i++) {
			FacturaDetalle fd = obtenerFacturaDetalleEn(i);
			sumaIvas += (fd.getCantidad() * fd.getProdIva());
			sumaBases += (fd.getCantidad() * fd.getProdPrecio());
		}
		txtIvas.setValue(sumaIvas);
		txtBases.setValue(sumaBases);
	}
	
	protected FacturaDetalle obtenerFacturaDetalleEn(int row) {
		FacturaDetalle facDet = (FacturaDetalle) prodTabla.getValueAt(row, 0);
		facDet.setProdPrecio((double) prodTabla.getValueAt(row, 1));
		facDet.setProdIva((double) prodTabla.getValueAt(row, 2));
		facDet.setCantidad((int) prodTabla.getValueAt(row, 3));
		return facDet;
	}
	
	private void ponerFacturaDetalle(int row, FacturaDetalle fd) {
		//"Detalle", "Precio", "IVA", "Cantidad"
		Vector<Object> filaData = new Vector<>();
		filaData.add(fd);
		filaData.add(fd.getProdPrecio());
		filaData.add(fd.getProdIva());
		filaData.add(fd.getCantidad());
		DefaultTableModel datos = (DefaultTableModel) prodTabla.getModel();
		datos.removeRow(row);
		datos.insertRow(row, filaData);
	}
	
	private void agregarFacturaDetalle(FacturaDetalle fd) {
		//"Detalle", "Precio", "IVA", "Cantidad"
		Vector<Object> filaData = new Vector<>();
		filaData.add(fd);
		filaData.add(fd.getProdPrecio());
		filaData.add(fd.getProdIva());
		filaData.add(fd.getCantidad());
		DefaultTableModel datos = (DefaultTableModel) prodTabla.getModel();
		datos.addRow(filaData);
	}
	
	/*
	 * 
	 * METODOS PUBLICOS
	 *
	 *
	*/
	
	public double obtenerImporteProductos() {
		calculaImportesProductos();
		return (double) txtBases.getValue();
	}
	
	public double obtenerImporteIVA() {
		calculaImportesProductos();
		return (double) txtIvas.getValue();
	}
	
	public void ponerListaDetalles(ArrayList<FacturaDetalle> listaDetalles) {
		vaciar();
		if (listaDetalles!=null) {
			for (FacturaDetalle fd : listaDetalles) {
				agregarFacturaDetalle(fd);
			}
		}
	}
	
	public ArrayList<FacturaDetalle> recuperarListaDetalles() {
		DefaultTableModel datos = (DefaultTableModel) prodTabla.getModel();
		ArrayList<FacturaDetalle> listaDetalles = new ArrayList<>();
		for (int i = 0; i < datos.getRowCount(); i++) {
			FacturaDetalle fd = (FacturaDetalle) datos.getValueAt(i, 0);
			listaDetalles.add(fd);
		}
		return listaDetalles;
	}
	
	protected void eliminarFilaSeleccionada(JTable t) {
		int row = t.getSelectedRow();
		if (row>=0) {
			System.out.println(row);
			DefaultTableModel dtm = (DefaultTableModel) t.getModel();
			dtm.removeRow(row);
		}
	}

	private void vaciar() {
		DefaultTableModel datos = (DefaultTableModel) prodTabla.getModel();
		datos.setRowCount(0);
	}

}
