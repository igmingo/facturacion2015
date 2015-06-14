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
import javax.swing.ScrollPaneLayout;

import java.awt.Rectangle;

import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import javax.swing.JSpinner;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.SpinnerNumberModel;

import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

// TABLA facturas BASE DE DATOS
//id int(10) UNSIGNED No auto_increment
//clienteId int(10) UNSIGNED No clientes -> id
//numero int(11) No
//fecha date No
//porcDescuento double No
//porcRecargoEquivalencia	double No
//impTotal double No
//impRecargo double No
//impIva double No
//dirCorreo mediumtext No
//dirFactura mediumtext No
//dirEnvio mediumtext No
//cobrada tinyint(1) No

public class FacturaDialogo extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Factura factura;
	
	private JTextField txtId;
	private JTextField txtNumero;
	private JDateChooser dateFecha;
	private ClientesCombo cbCliente;
	private JSpinner numPorcDescuento;
	private JSpinner numPorcRecargoequivalencia;
	private JSpinner numImptotal;
	private JSpinner numImprecargo;
	private JSpinner numImpiva;
	private JTextField txtDirCorreo;
	private JTextField txtDirFactura;
	private JTextField txtDirEnvio;
	private JCheckBox chkbxCobrada;
	private FacturasDetalleTabla tbFacturaDetalles;
	private JTextField txtNombreCliente;

	public FacturaDialogo(Factura fac) {
		//De la factura que paso, solo necesito el ID (en principio)
		
		this.factura = fac;
		
		setResizable(false);
		setBounds(new Rectangle(0, 0, 700, 600));
		setTitle("Factura");
		setModal(true);
		getContentPane().setLayout(null);
		
		JPanel pnButtons = new JPanel();
		pnButtons.setBounds(10, 529, 674, 31);
		getContentPane().add(pnButtons);
		
		JButton btnGrabar = new JButton("Guardar");
		pnButtons.add(btnGrabar);
		JButton btnEliminar = new JButton("Eliminar");
		pnButtons.add(btnEliminar);
		JButton btnCancelar = new JButton("Cancelar");
		pnButtons.add(btnCancelar);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 674, 507);
		getContentPane().add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
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
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblFactura = new JLabel("Factura");
		panel.add(lblFactura, "2, 2, right, default");
		
		JPanel pnNumFactura = new JPanel();
		panel.add(pnNumFactura, "4, 2, fill, fill");
		pnNumFactura.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNfactura = new JLabel("N\u00BA");
		pnNumFactura.add(lblNfactura);
		
		txtNumero = new JTextField();
		pnNumFactura.add(txtNumero);
		txtNumero.setText("numFactura");
		txtNumero.setColumns(10);
		
		JLabel lblFechaDeFactura = new JLabel("Fecha de Factura");
		pnNumFactura.add(lblFechaDeFactura);
		
		dateFecha = new JDateChooser();
		pnNumFactura.add(dateFecha);
		
		JLabel lblId = new JLabel("ID");
		pnNumFactura.add(lblId);
		
		txtId = new JTextField();
		txtId.setColumns(10);
		txtId.setHorizontalAlignment(SwingConstants.RIGHT);
		pnNumFactura.add(txtId);
		txtId.setEditable(false);
		txtId.setText("id");
		
		JLabel lblCliente = new JLabel("Cliente");
		panel.add(lblCliente, "2, 4, right, default");
		
		JPanel pnCliente = new JPanel();
		panel.add(pnCliente, "4, 4, fill, fill");
		pnCliente.setLayout(new GridLayout(0, 2, 0, 0));
		
		txtNombreCliente = new JTextField();
		pnCliente.add(txtNombreCliente);
		txtNombreCliente.setColumns(10);
		
		cbCliente = new ClientesCombo();
		pnCliente.add(cbCliente);
		
		JLabel lblProductos = new JLabel("Productos");
		panel.add(lblProductos, "2, 6, right, default");
		
		JPanel pnProductos = new JPanel();
		panel.add(pnProductos, "4, 6, fill, fill");
		pnProductos.setLayout(new BorderLayout(0, 0));
		
		JScrollPane pnFacturaDetalles = new JScrollPane();
		pnFacturaDetalles.setLayout(new ScrollPaneLayout());
		pnProductos.add(pnFacturaDetalles);
		JButton btAgregarProd = new JButton("A\u00F1adir Producto");
		pnProductos.add(btAgregarProd, BorderLayout.SOUTH);
		
		tbFacturaDetalles = new FacturasDetalleTabla("");
		pnFacturaDetalles.setViewportView(tbFacturaDetalles);
		
		JLabel lblImporte = new JLabel("Importe");
		panel.add(lblImporte, "2, 8, right, default");
		
		JPanel pnDescuentos = new JPanel();
		panel.add(pnDescuentos, "4, 8, fill, fill");
		pnDescuentos.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblImporteRecago = new JLabel("Recargo");
		lblImporteRecago.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblImporteRecago);
		
		numImprecargo = new JSpinner();
		numImprecargo.setMinimumSize(new Dimension(50, 20));
		numImprecargo.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numImprecargo);
		
		JLabel lblDescuento = new JLabel("% Descuento");
		lblDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblDescuento);
		
		numPorcDescuento = new JSpinner();
		numPorcDescuento.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numPorcDescuento);
		
		JLabel lblRecargoEquivalencia = new JLabel("% Recargo Equivalencia");
		lblRecargoEquivalencia.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblRecargoEquivalencia);
		
		numPorcRecargoequivalencia = new JSpinner();
		numPorcRecargoequivalencia.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numPorcRecargoequivalencia);
		
		JLabel lblImporteIva = new JLabel("IVA");
		lblImporteIva.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblImporteIva);
		
		numImpiva = new JSpinner();
		numImpiva.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numImpiva);
		
		JLabel lblImporteTotal = new JLabel("TOTAL");
		lblImporteTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblImporteTotal);
		
		numImptotal = new JSpinner();
		numImptotal.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numImptotal);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n");
		panel.add(lblDireccin, "2, 10, right, default");
		
		JPanel pnDirecciones = new JPanel();
		panel.add(pnDirecciones, "4, 10, fill, fill");
		pnDirecciones.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblDireccinDeFacturacin = new JLabel("Facturaci\u00F3n");
		lblDireccinDeFacturacin.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDirecciones.add(lblDireccinDeFacturacin);
		
		txtDirFactura = new JTextField();
		pnDirecciones.add(txtDirFactura);
		txtDirFactura.setText("dirFactura");
		txtDirFactura.setColumns(10);
		
		JLabel lblDireccinDeEnvo = new JLabel("Env\u00EDo");
		lblDireccinDeEnvo.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDirecciones.add(lblDireccinDeEnvo);
		
		txtDirEnvio = new JTextField();
		pnDirecciones.add(txtDirEnvio);
		txtDirEnvio.setText("dirEnvio");
		txtDirEnvio.setColumns(10);
		
		JLabel lblDireccinDeCorreo = new JLabel("Correo");
		lblDireccinDeCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDirecciones.add(lblDireccinDeCorreo);
		
		txtDirCorreo = new JTextField();
		pnDirecciones.add(txtDirCorreo);
		txtDirCorreo.setText("dirCorreo");
		txtDirCorreo.setColumns(10);
		
		JLabel lblCobrada = new JLabel("Cobro");
		panel.add(lblCobrada, "2, 12, right, default");
		
		JPanel pnCobro = new JPanel();
		panel.add(pnCobro, "4, 12, fill, fill");
		pnCobro.setLayout(new GridLayout(0, 1, 0, 0));
		
		chkbxCobrada = new JCheckBox();
		pnCobro.add(chkbxCobrada);
		chkbxCobrada.setText("\u00BFLa factura est\u00E1 cobrada?");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				factura = null;
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
		
		btAgregarProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FacturaDetalleDialogo dialogo = new FacturaDetalleDialogo(null);
				FacturaDetalle fd = dialogo.mostrar();
				if (fd!=null) {
					tbFacturaDetalles.addDetalle(fd);
				}
			}
		});
		
		if (factura!=null && factura.getId()>0) {
			factura = new FacturasBDD().recuperaFacturaCompletaPorId(factura.getId());
		} else {
			factura = null;
		}
		setForm();
		
	}
	
	private void eliminar() {
		Integer id = Utilidades.validarEntero(txtId.getText());
		if (id!=null) {
			int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la factura?\n", "Eliminar Factura", JOptionPane.OK_CANCEL_OPTION);
			if (pregunta==JOptionPane.OK_OPTION) {
				boolean eliminado = new ProductosBDD().eliminar(id);
				if (eliminado) {
					factura = null;
					mostrarMensaje("Factura Eliminada.");
				} else {
					mostrarMensaje("No se ha podido eliminar.");
				}
			}
		}
	}
	
	private void guardar() {
		factura = getForm();
		if (factura!=null) {
			int newId = new FacturasBDD().grabarFacturaCompleta(factura);
			if (newId>=0) {
				factura.setId(newId);
				setForm();
				mostrarMensaje("Factura guardada correctamente.");
			} else {
				mostrarMensaje("Error al guardar.");
			}
		} else {
			mostrarMensaje("El formulario no es correcto.");
		}
	}

	/**
	 * Rellena los datos de la Factura en el Formulario
	 * @param factura es la instancia del la Factura con el que vamos a rellenar el forumulario. Si es null, se rellena un formulario con id = 0;
	 */
	private void setForm() {
		if (factura!=null) {
			txtId.setText(""+ factura.getId());
			txtNumero.setText(""+ factura.getNumero());
			dateFecha.setDate(factura.getFecha());
			txtNombreCliente.setText(factura.getNombreCliente());
			cbCliente.setSelectedId(factura.getClienteId());
			tbFacturaDetalles.putDetalles(factura.getDetalles());
			numPorcDescuento.setValue(factura.getPorcDescuento());
			numPorcRecargoequivalencia.setValue(factura.getPorcRecargoEquivalencia());
			numImptotal.setValue(factura.getImpTotal());
			numImprecargo.setValue(factura.getImpRecargo());
			numImpiva.setValue(factura.getImpIva());
			txtDirCorreo.setText(factura.getDirCorreo());
			txtDirFactura.setText(factura.getDirFactura());
			txtDirEnvio.setText(factura.getDirEnvio());
			chkbxCobrada.setSelected(factura.isCobrada());
		} else {
			txtId.setText("0");
			txtNumero.setText("");
			dateFecha.setDate(new Date());
			txtNombreCliente.setText("");
			cbCliente.setSelectedId(null);
			tbFacturaDetalles.putDetalles(null);
			numPorcDescuento.setValue(0);
			numPorcRecargoequivalencia.setValue(0);
			numImptotal.setValue(0);
			numImprecargo.setValue(0);
			numImpiva.setValue(0);
			txtDirCorreo.setText("");
			txtDirFactura.setText("");
			txtDirEnvio.setText("");
			chkbxCobrada.setSelected(false);
		}
	}
	/**
	 * Recoge el formulario y crea una instancia de Factura
	 * @return Retorna una instacia de Factura. Null si el formulario esta incorrecto.
	 */
	private Factura getForm() {
		Factura fac = null;
		
		Integer id = Utilidades.validarEntero(txtId.getText().trim());
		String nombreCliente = txtNombreCliente.getText().trim();
		Integer clienteId = cbCliente.getSelectedId();
		Integer numero = Utilidades.validarEntero(txtNumero.getText().trim());
		Date fecha = dateFecha.getDate();
		Double porcDescuento = Utilidades.validarDouble("" + numPorcDescuento.getValue());
		Double porcRecargoEquivalencia = Utilidades.validarDouble("" + numPorcRecargoequivalencia.getValue());
		Double impTotal = Utilidades.validarDouble("" + numImptotal.getValue());
		Double impRecargo = Utilidades.validarDouble(""	+ numImptotal.getValue());
		Double impIva = Utilidades.validarDouble("" + numImptotal.getValue());
		String dirCorreo = txtDirCorreo.getText().trim();
		String dirFactura = txtDirFactura.getText().trim();
		String dirEnvio = txtDirEnvio.getText().trim();
		Boolean cobrada = chkbxCobrada.isSelected();
		ArrayList<FacturaDetalle> detalles = tbFacturaDetalles.getListaDetalles();
		try {
			fac = new Factura(id, clienteId, nombreCliente, numero, fecha, porcDescuento, porcRecargoEquivalencia, impTotal, impRecargo, impIva, dirCorreo, dirFactura, dirEnvio, cobrada, detalles);
		} catch (Exception e) {
			mostrarMensaje("Error de formulario");
		}
		return fac;
	}
	
	private void mostrarMensaje(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	
	/**
	 * Hace visible el diálogo Modal para editar un Cliente. Si se hace invisible el Dialogo, se cierra y elimina de la memoria.
	 * @return Retorna la instacia del Cliente que se ha guardado
	 */
	public Factura mostrar() {
		setVisible(true);
		dispose();
		return factura;
	}
}
