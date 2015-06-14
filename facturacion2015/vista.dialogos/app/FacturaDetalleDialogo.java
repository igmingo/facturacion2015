package app;

import javax.swing.JDialog;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import javax.swing.JSpinner;
import javax.swing.JComboBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.BorderLayout;

// TABLA facturasdetalle BASE DE DATOS
//id int(10) UNSIGNED No auto_increment
//facturaId int(10) UNSIGNED No facturas -> id
//prodId int(10) UNSIGNED No productos -> id
//prodNombre varchar(30) No
//prodPrecio double No
//prodIva double No
//cantidad int(11) No

public class FacturaDetalleDialogo extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FacturaDetalle fd;
	
	private JTextField txtId;
	private JTextField txtFacturaId;
	private ProductosCombo cbProd;
	private JTextField txtProdNombre;
	private JSpinner numProdPrecio;
	private JSpinner numProdIva;
	private JSpinner numCantidad;

	public FacturaDetalleDialogo(FacturaDetalle facDet) {
		this.fd = facDet;
		
		setResizable(false);
		setBounds(new Rectangle(0, 0, 450, 280));
		setTitle("Detalle de Producto en la Factura");
		setModal(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel form = new JPanel();
		getContentPane().add(form);
		FormLayout fl_form = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("4dlu:grow"),});
		fl_form.setHonorsVisibility(false);
		form.setLayout(fl_form);
		
		JLabel lblId = new JLabel("ID");
		form.add(lblId, "2, 2, right, default");
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("id");
		form.add(txtId, "4, 2, fill, default");
		txtId.setColumns(10);
		
		JLabel lblFacturaid = new JLabel("Factura ID");
		form.add(lblFacturaid, "2, 4, right, default");
		
		txtFacturaId = new JTextField();
		txtFacturaId.setEditable(false);
		txtFacturaId.setText("facturaId");
		form.add(txtFacturaId, "4, 4, fill, default");
		txtFacturaId.setColumns(10);
		
		JLabel lblProductoId = new JLabel("Producto");
		form.add(lblProductoId, "2, 6, right, default");
		
		cbProd = new ProductosCombo();
		cbProd.setEditable(true);
		form.add(cbProd, "4, 6, fill, default");
		
		JLabel lblNombreDeProducto = new JLabel("Nombre de Producto");
		form.add(lblNombreDeProducto, "2, 8, right, default");
		
		txtProdNombre = new JTextField();
		txtProdNombre.setText("prodNombre");
		form.add(txtProdNombre, "4, 8, fill, default");
		txtProdNombre.setColumns(10);
		
		JLabel lblPrecioDelProducto = new JLabel("Precio del Producto");
		form.add(lblPrecioDelProducto, "2, 10, right, default");
		
		numProdPrecio = new JSpinner();
		form.add(numProdPrecio, "4, 10, fill, default");
		
		JLabel lblIvaDelProducto = new JLabel("IVA del Producto");
		form.add(lblIvaDelProducto, "2, 12, right, default");
		
		numProdIva = new JSpinner();
		form.add(numProdIva, "4, 12, fill, default");
		
		JLabel lblCantidad = new JLabel("Cantidad");
		form.add(lblCantidad, "2, 14, right, default");
		
		numCantidad = new JSpinner();
		form.add(numCantidad, "4, 14, fill, default");
		
		cbProd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				Producto prod = (Producto) cbProd.getSelectedItem();
				txtProdNombre.setText(prod.getNombre());
				numProdPrecio.setValue(prod.getPrecio());
				numProdIva.setValue(prod.getIva());
			}
		});
		
		JPanel pnButtons = new JPanel();
		getContentPane().add(pnButtons, BorderLayout.SOUTH);
		
		JButton btnGrabar = new JButton("Guardar");
		pnButtons.add(btnGrabar);
		JButton btnEliminar = new JButton("Eliminar");
		pnButtons.add(btnEliminar);
		JButton btnCancelar = new JButton("Cancelar");
		pnButtons.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fd = null;
				setVisible(false);
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
				setVisible(false);
			}
		});
		
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
				setVisible(false);
			}
		});
		
		
		if (fd!=null && fd.getId()>0) {
			fd = new FacturasDetallesBDD().recuperaPorId(fd.getId());
		}
		setForm();
		
	}
	
	private void eliminar() {
		Integer id = Utilidades.validarEntero(txtId.getText());
		if (id!=null) {
			int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Detalle de Producto?\n", "Eliminar Detalle de Producto", JOptionPane.OK_CANCEL_OPTION);
			if (pregunta==JOptionPane.OK_OPTION) {
				boolean eliminado = new FacturasDetallesBDD().eliminar(id);
				if (eliminado) {
					fd = null;
					mostrarMensaje("Detalle de Factura Eliminado.");
				} else {
					mostrarMensaje("No se ha podido eliminar.");
				}
			}
		}
	}
	
	private void guardar() {
		fd = getForm();
		if (fd!=null) {
			int newId = new FacturasDetallesBDD().grabar(fd);
			if (newId>=0) {
				fd.setId(newId);
				setForm();
				mostrarMensaje("Detalle de Factura guardada correctamente.");
			} else {
				mostrarMensaje("Error al guardar.");
			}
		} else {
			mostrarMensaje("El formulario no es correcto.");
		}
	}

	/**
	 * Rellena los datos del Detalle de Producto en el Formulario
	 * @param fd es la instancia del Detalle de Producto con el que vamos a rellenar el forumulario. Si es null, se rellena un formulario con id = 0;
	 */
	private void setForm() {
		if (fd!=null && fd.getId()>0) {
			txtId.setText("" + fd.getId());
			txtFacturaId.setText("" + fd.getFacturaId());
			cbProd.setSelectedId(fd.getProdId());
			txtProdNombre.setText(fd.getProdNombre());
			numProdPrecio.setValue(fd.getProdPrecio());
			numProdIva.setValue(fd.getProdIva());
			numCantidad.setValue(fd.getCantidad());
		} else {
			txtId.setText("0");
			txtFacturaId.setText("");
			cbProd.setSelectedId(null);
			txtProdNombre.setText("");
			numProdPrecio.setValue(0);
			numProdIva.setValue(0);
			numCantidad.setValue(1);
		}
	}
	/**
	 * Recoge el formulario y crea una instancia de Cliente
	 * @return Retorna una instacia de Cliente. Null si el formulario esta incorrecto.
	 */
	private FacturaDetalle getForm() {
		FacturaDetalle facDet = null;
		// TABLA facturasdetalle BASE DE DATOS
		// id int(10) UNSIGNED No auto_increment
		// facturaId int(10) UNSIGNED No facturas -> id
		// prodId int(10) UNSIGNED No productos -> id
		// prodNombre varchar(30) No
		// prodPrecio double No
		// prodIva double No
		// cantidad int(11) No

		int id = Utilidades.validarEntero(txtId.getText());
		int facturaId = Utilidades.validarEntero(txtFacturaId.getText());
		int prodId = cbProd.getSelectedId();
		String prodNombre = Utilidades.validarString(txtProdNombre.getText());
		Double prodPrecio = Utilidades.validarDouble(""	+ numProdPrecio.getValue());
		Double prodIva = Utilidades.validarDouble("" + numProdIva.getValue());
		Integer cantidad = Utilidades.validarEntero("" + numCantidad.getValue());
		try {
			facDet = new FacturaDetalle(id, facturaId, prodId, prodNombre, prodPrecio, prodIva, cantidad);
		} catch (Exception e) {
			mostrarMensaje("Error de formulario");
		}
		return facDet;
	}
	
	private void mostrarMensaje(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	
	/**
	 * Hace visible el diálogo Modal para editar un Detalle de Producto. Si se hace invisible el Dialogo, se cierra y elimina de la memoria.
	 * @return Retorna la instacia del Detalle de Producto que se ha guardado
	 */
	public FacturaDetalle mostrar() {
		setVisible(true);
		dispose();
		return fd;
	}
}
