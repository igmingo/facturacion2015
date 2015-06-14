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

public class ClienteDialogo extends JDialog {
	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtNif;
	private JTextField txtDirCorreo;
	private JTextField txtDirFactura;
	private JTextField txtDirEnvio;
	private JTextArea taContacto;
	private JSpinner numRecargoEquivalencia;
	private JSpinner numDescuento;
	private JDateChooser dateFechaAlta;
	private JCheckBox chckbxBaja;

	public ClienteDialogo(Cliente cli) {
		this.cliente = cli;
		setBounds(new Rectangle(0, 0, 450, 500));
		setTitle("Cliente");
		setModal(true);
		getContentPane().setLayout(null);
		
		JPanel pnButtons = new JPanel();
		pnButtons.setBounds(10, 419, 414, 31);
		getContentPane().add(pnButtons);
		
		JButton btnGrabar = new JButton("Guardar");
		pnButtons.add(btnGrabar);
		JButton btnEliminar = new JButton("Eliminar");
		pnButtons.add(btnEliminar);
		JButton btnCancelar = new JButton("Cancelar");
		pnButtons.add(btnCancelar);
		
		JPanel form = new JPanel();
		form.setBounds(10, 11, 414, 397);
		getContentPane().add(form);
		form.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
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
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblId_1 = new JLabel("id");
		form.add(lblId_1, "2, 2, right, default");
		
		txtId = new JTextField();
		txtId.setEditable(false);
		form.add(txtId, "4, 2, fill, default");
		txtId.setColumns(10);
		JLabel lblNombre = new JLabel("Nombre");
		form.add(lblNombre, "2, 4, right, default");
		
		txtNombre = new JTextField();
		form.add(txtNombre, "4, 4, fill, default");
		txtNombre.setColumns(10);
		JLabel lblApellidos = new JLabel("Apellidos");
		form.add(lblApellidos, "2, 6, right, default");
		
		txtApellidos = new JTextField();
		form.add(txtApellidos, "4, 6, fill, default");
		txtApellidos.setColumns(10);
		JLabel lblNif = new JLabel("NIF");
		form.add(lblNif, "2, 8, right, default");
		
		txtNif = new JTextField();
		form.add(txtNif, "4, 8, fill, default");
		txtNif.setColumns(10);
		JLabel lblDirCorreo = new JLabel("Direcci\u00F3n de Correo");
		form.add(lblDirCorreo, "2, 10, right, default");
		
		txtDirCorreo = new JTextField();
		form.add(txtDirCorreo, "4, 10, fill, default");
		txtDirCorreo.setColumns(10);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n de Facturaci\u00F3n");
		form.add(lblDireccin, "2, 12, right, default");
		
		txtDirFactura = new JTextField();
		form.add(txtDirFactura, "4, 12, fill, default");
		txtDirFactura.setColumns(10);
		
		JLabel lblDireccinDe = new JLabel("Direcci\u00F3n de Env\u00EDo");
		form.add(lblDireccinDe, "2, 14, right, default");
		
		txtDirEnvio = new JTextField();
		form.add(txtDirEnvio, "4, 14, fill, default");
		txtDirEnvio.setColumns(10);
		
		JLabel lblContacto_1 = new JLabel("Contacto");
		form.add(lblContacto_1, "2, 16, right, default");
		JScrollPane scrollPane = new JScrollPane();
		form.add(scrollPane, "4, 16, fill, fill");
		taContacto = new JTextArea();
		taContacto.setWrapStyleWord(true);
		taContacto.setLineWrap(true);
		scrollPane.setViewportView(taContacto);
		
		JLabel lblRecargo = new JLabel("% Recargo Equivalencia");
		form.add(lblRecargo, "2, 18, right, default");
		
		numRecargoEquivalencia = new JSpinner();
		form.add(numRecargoEquivalencia, "4, 18, fill, default");
		
		JLabel lblDescuento = new JLabel("% Descuento");
		form.add(lblDescuento, "2, 20, right, default");
		
		numDescuento = new JSpinner();
		form.add(numDescuento, "4, 20, fill, default");
		
		JLabel lblFechaDeAlta = new JLabel("Fecha de Alta");
		form.add(lblFechaDeAlta, "2, 22, right, default");
		
		dateFechaAlta = new JDateChooser();
		form.add(dateFechaAlta, "4, 22, fill, default");
		
		JLabel lblBaja_1 = new JLabel("Baja");
		form.add(lblBaja_1, "2, 24, right, default");
		chckbxBaja = new JCheckBox("\u00BFCliente dado de baja?");
		form.add(chckbxBaja, "4, 24");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cliente = null;
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
				if (cliente!=null) {
					setVisible(false);
				}
			}
		});
		
		if (cliente!=null && cliente.getId()>0) {
			cliente = new ClientesBDD().recuperaPorId(cliente.getId());
		} else {
			cliente = null;
		}
		setForm();
		
	}
	
	private void eliminar() {
		Integer id = Utilidades.validarEntero(txtId.getText());
		if (id!=null && id>0) {
			int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el cliente?\n", "Eliminar Cliente", JOptionPane.OK_CANCEL_OPTION);
			if (pregunta==JOptionPane.OK_OPTION) {
				boolean eliminado = new ClientesBDD().eliminar(id);
				if (eliminado) {
					cliente = null;
					mostrarMensaje("Cliente Eliminado.");
				} else {
					mostrarMensaje("No se ha podido eliminar.");
				}
			}
		}
	}
	
	private void guardar() {
		cliente = getForm();
		if (cliente != null && cliente.getNombre() != null
				&& cliente.getApellidos() != null && cliente.getNif() != null
				&& cliente.getFechaAlta() != null) {
			int newId = new ClientesBDD().grabar(cliente);
			if (newId >= 0) {
				cliente.setId(newId);
				setForm();
				mostrarMensaje("Cliente guardado correctamente.");
			} else {
				mostrarMensaje("Error al guardar.");
			}
		} else {
			mostrarMensaje("El formulario no es correcto.");
			cliente = null;
		}
	}
	/**
	 * Rellena los datos del Producto en el Formulario
	 * @param cliente es la instancia del Producto con el que vamos a rellenar el forumulario. Si es null, se rellena un formulario con id = 0;
	 */
	private void setForm() {
		if (cliente!=null) {
			txtId.setText(""+ cliente.getId());
			txtNombre.setText(cliente.getNombre().trim());
			txtApellidos.setText(cliente.getApellidos().trim());
			txtNif.setText(cliente.getNif().trim());
			txtDirCorreo.setText(cliente.getDirCorreo().trim());
			txtDirFactura.setText(cliente.getDirFactura().trim());
			txtDirEnvio.setText(cliente.getDirEnvio().trim());
			taContacto.setText(cliente.getContacto().trim());
			numRecargoEquivalencia.setValue(cliente.getPorcRecargoEquivalencia());
			numDescuento.setValue(cliente.getPorcDescuento());
			dateFechaAlta.setDate(cliente.getFechaAlta());
			chckbxBaja.setSelected(cliente.isBaja());
		} else {
			txtId.setText("0");
			txtNombre.setText("");
			txtApellidos.setText("");
			txtNif.setText("");
			txtDirCorreo.setText("");
			txtDirFactura.setText("");
			txtDirEnvio.setText("");
			taContacto.setText("");
			numRecargoEquivalencia.setValue(0);
			numDescuento.setValue(0);
			dateFechaAlta.setDate(new Date());
			chckbxBaja.setSelected(false);
		}
	}
	/**
	 * Recoge el formulario y crea una instancia de Cliente
	 * @return Retorna una instacia de Cliente. Null si el formulario esta incorrecto.
	 */
	private Cliente getForm() {
		Cliente cli = null;
		int id = Utilidades.validarEntero(txtId.getText());
		String nombre = Utilidades.validarString(txtNombre.getText());
		String apellidos = Utilidades.validarString(txtApellidos.getText());
		String nif = Utilidades.validarString(txtNif.getText());
		String dirCorreo = Utilidades.validarStringNoNull(txtDirCorreo.getText());
		String dirFactura = Utilidades.validarStringNoNull(txtDirFactura.getText());
		String dirEnvio = Utilidades.validarStringNoNull(txtDirEnvio.getText());
		String contacto = Utilidades.validarStringNoNull(taContacto.getText());
		double porcRecargoEquivalencia = Utilidades.validarDouble("" + numRecargoEquivalencia.getValue());
		double porcDescuento = Utilidades.validarDouble("" + numDescuento.getValue());
		Date fechaAlta = dateFechaAlta.getDate();
		boolean baja = chckbxBaja.isSelected();
		try {
			cli = new Cliente(id, nombre, apellidos, nif, dirCorreo, dirFactura, dirEnvio, contacto, porcRecargoEquivalencia, porcDescuento, fechaAlta, baja);
		} catch (Exception e) {
		}
		return cli;
	}
	
	private void mostrarMensaje(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	
	/**
	 * Hace visible el diálogo Modal para editar un Cliente.
	 * Si se hace invisible el Dialogo, se cierra y elimina de la memoria.
	 * @return Retorna la instacia del Cliente que se ha guardado
	 */
	public Cliente mostrar() {
		setVisible(true);
		dispose();
		return cliente;
	}
}
