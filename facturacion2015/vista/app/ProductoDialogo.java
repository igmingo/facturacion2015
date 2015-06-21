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
import javax.swing.JSpinner;

public class ProductoDialogo extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtNombre;
	private JSpinner numPrecio;
	private JSpinner numIva;
	private JSpinner numStock;
	private JTextArea taDescripcion;
	private JCheckBox chckbxBaja;
	private JPanel form;
	
	private Producto producto;

	public ProductoDialogo(Producto pro) {
		this.producto = pro;
		setBounds(new Rectangle(0, 0, 450, 370));
		setTitle("Producto");
		setModal(true);
		getContentPane().setLayout(null);
		
		form = new JPanel();
		form.setBounds(10, 11, 414, 269);
		getContentPane().add(form);
		FormLayout fl_form = new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
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
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		form.setLayout(fl_form);
		
		JLabel lblId = new JLabel("id");
		form.add(lblId, "1, 1, right, default");
		txtId = new JTextField();
		txtId.setEditable(false);
		form.add(txtId, "3, 1, fill, default");
		txtId.setText("id");
		txtId.setColumns(10);
		JLabel lblNombre = new JLabel("Nombre");
		form.add(lblNombre, "1, 3, right, default");
		txtNombre = new JTextField();
		form.add(txtNombre, "3, 3, fill, default");
		txtNombre.setText("Nombre");
		txtNombre.setColumns(10);
		JLabel lblPrecio = new JLabel("Precio");
		form.add(lblPrecio, "1, 5, right, default");
		numPrecio = new JSpinner();
		form.add(numPrecio, "3, 5, fill, default");
		JLabel lblIva = new JLabel("IVA");
		form.add(lblIva, "1, 7, right, default");
		numIva = new JSpinner();
		form.add(numIva, "3, 7, fill, default");
		JLabel lblStock = new JLabel("STOCK");
		form.add(lblStock, "1, 9, right, default");
		numStock = new JSpinner();
		form.add(numStock, "3, 9, fill, default");
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		form.add(lblDescripcin, "1, 11, right, default");
		JScrollPane scrollPane = new JScrollPane();
		form.add(scrollPane, "3, 11, fill, fill");
		taDescripcion = new JTextArea();
		taDescripcion.setWrapStyleWord(true);
		taDescripcion.setLineWrap(true);
		scrollPane.setViewportView(taDescripcion);
		JLabel lblBaja = new JLabel("Baja");
		form.add(lblBaja, "1, 13, right, default");
		chckbxBaja = new JCheckBox("\u00BFProducto dado de baja?");
		form.add(chckbxBaja, "3, 13");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 291, 414, 31);
		getContentPane().add(panel_1);
		
		JButton btnGrabar = new JButton("Guardar");
		panel_1.add(btnGrabar);
		JButton btnEliminar = new JButton("Eliminar");
		panel_1.add(btnEliminar);
		JButton btnCancelar = new JButton("Cancelar");
		panel_1.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				producto = null;
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
		if (producto!=null && producto.getId()>0) {
			producto = new ProductosBDD().recuperaPorId(producto.getId());
		} else {
			producto = null;
		}
		setForm();
		
	}
	
	private void eliminar() {
		Integer id = Utilidades.validarEntero(txtId.getText());
		if (id!=null) {
			int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el producto?\n", "Eliminar Producto", JOptionPane.OK_CANCEL_OPTION);
			if (pregunta==JOptionPane.OK_OPTION) {
				boolean eliminado = new ProductosBDD().eliminar(id);
				if (eliminado) {
					producto = null;
					mostrarMensaje("Producto Eliminado.");
				} else {
					mostrarMensaje("No se ha podido eliminar.");
				}
			}
		}
	}
	
	private void guardar() {
		producto = getForm();
		if (producto!=null) {
			int newId = new ProductosBDD().grabar(producto);
			if (newId>=0) {
				producto.setId(newId);
				setForm();
				mostrarMensaje("Producto guardado correctamento.");
			} else {
				mostrarMensaje("Error al guardar.");
			}
		} else {
			mostrarMensaje("El formulario no es correcto.");
		}
	}

	/**
	 * Rellena los datos del Producto en el Formulario
	 * @param producto es la instancia del Producto con el que vamos a rellenar el forumulario. Si es null, se rellena un formulario con id = 0;
	 */
	private void setForm() {
		if (producto!=null) {
			txtId.setText("" + producto.getId());
			txtNombre.setText(producto.getNombre());
			numPrecio.setValue(producto.getPrecio());
			numIva.setValue(producto.getIva());
			numStock.setValue(producto.getStock());
			taDescripcion.setText(producto.getDescripcion());
			chckbxBaja.setSelected(producto.isBaja());
		} else {
			txtId.setText("0");
			txtNombre.setText("");
			numPrecio.setValue(0);
			numIva.setValue(0);
			numStock.setValue(0);
			taDescripcion.setText("");
			chckbxBaja.setSelected(false);
		}
	}
	/**
	 * Recoge el formulario y crea una instancia de Producto
	 * @return Retorna una instacia de Producto. Null si el formulario esta incorrecto.
	 */
	private Producto getForm() {

		Producto p = null;
		Integer id = Utilidades.validarEntero(txtId.getText());
		String nombre = Utilidades.validarString(txtNombre.getText());
		Double precio = Utilidades.validarDouble("" + numPrecio.getValue());
		Double iva = Utilidades.validarDouble("" + numIva.getValue());
		Integer stock = Utilidades.validarEntero("" + numStock.getValue());
		String descripcion = Utilidades.validarString(taDescripcion.getText());
		boolean baja = chckbxBaja.isSelected();
		if (id!=null && nombre!=null && precio!=null && iva!=null && stock!=null && descripcion!=null) {
			p = new Producto(id, nombre, precio, iva, stock, descripcion, baja);
		}
		return p;
	}
	
	private void mostrarMensaje(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	
	/**
	 * Hace visible el diálogo Modal para editar un Producto. Si se hace invisible el Dialogo, se cierra y elimina de la memoria.
	 * @return Retorna la instacia del Producto que se ha guardado
	 */
	public Producto mostrar() {
		setVisible(true);
		dispose();
		return producto;
	}
}
