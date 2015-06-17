package app;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

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

public class FacturaCompletaDialogo extends JDialog {
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
	
	private FacturaDetallesPanel pnProductos;
	
	private JSpinner numImpBase;
	private JSpinner numImprecargo;
	private JSpinner numImpiva;
	private JButton numImporteTotal;
	
	private JTextArea txtDirCorreo;
	private JTextArea txtDirFactura;
	private JTextArea txtDirEnvio;
	
	private JCheckBox chkbxCobrada;
	private JTextField txtNif;

	public FacturaCompletaDialogo(Factura fac) {
		//De la factura que paso, solo necesito el ID (en principio)
		
		this.factura = fac;
		
		setBounds(new Rectangle(0, 0, 710, 600));
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("max(50dlu;default):grow"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblFactura = new JLabel("Factura");
		panel.add(lblFactura, "2, 1, right, default");
		
		JPanel pnNumFactura = new JPanel();
		panel.add(pnNumFactura, "4, 1, fill, fill");
		pnNumFactura.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("86px:grow(2)"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("95px:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("20px"),}));
		
		JLabel lblNfactura = new JLabel("N\u00FAmero");
		pnNumFactura.add(lblNfactura, "1, 1, left, center");
		
		txtNumero = new JTextField();
		pnNumFactura.add(txtNumero, "3, 1, fill, fill");
		txtNumero.setText("numFactura");
		txtNumero.setColumns(10);
		
		JLabel lblFechaDeFactura = new JLabel("Fecha de Factura");
		pnNumFactura.add(lblFechaDeFactura, "5, 1, left, center");
		
		dateFecha = new JDateChooser();
		pnNumFactura.add(dateFecha, "7, 1, fill, fill");
		
		JLabel lblId = new JLabel("ID");
		pnNumFactura.add(lblId, "9, 1, left, center");
		
		txtId = new JTextField();
		txtId.setColumns(10);
		txtId.setHorizontalAlignment(SwingConstants.RIGHT);
		pnNumFactura.add(txtId, "11, 1, right, fill");
		txtId.setEditable(false);
		txtId.setText("id");
		
		JLabel lblCliente = new JLabel("Cliente");
		panel.add(lblCliente, "2, 3, right, default");
		
		JPanel pnCliente = new JPanel();
		panel.add(pnCliente, "4, 3, fill, fill");
		pnCliente.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel pnDatosCliente = new JPanel();
		pnCliente.add(pnDatosCliente);
		pnDatosCliente.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("123px:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("29px:grow"),},
			new RowSpec[] {
				RowSpec.decode("20px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNombreCliente = new JLabel("Nombre");
		pnDatosCliente.add(lblNombreCliente, "1, 1, left, default");
		
		cbCliente = new ClientesCombo();
		pnDatosCliente.add(cbCliente, "3, 1, fill, fill");
		
		JLabel lblDescuento = new JLabel("% Descuento");
		pnDatosCliente.add(lblDescuento, "5, 1, left, center");
		lblDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
		
		numPorcDescuento = new JSpinner();
		pnDatosCliente.add(numPorcDescuento, "7, 1, fill, fill");
		numPorcDescuento.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		
		JLabel lblNif = new JLabel("NIF");
		pnDatosCliente.add(lblNif, "1, 3, left, default");
		
		txtNif = new JTextField();
		txtNif.setText("NIF");
		pnDatosCliente.add(txtNif, "3, 3, fill, default");
		txtNif.setColumns(10);
		
		JLabel lblRecargoEquivalencia = new JLabel("% Recargo Equiv.");
		pnDatosCliente.add(lblRecargoEquivalencia, "5, 3, left, center");
		lblRecargoEquivalencia.setHorizontalAlignment(SwingConstants.RIGHT);
		
		numPorcRecargoequivalencia = new JSpinner();
		pnDatosCliente.add(numPorcRecargoequivalencia, "7, 3, fill, fill");
		numPorcRecargoequivalencia.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		
		cbCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cliente cli = cbCliente.obtenerClienteSeleccionado();
				if (cli!=null) {
					txtNif.setText(cli.getNif());
					numPorcDescuento.setValue(cli.getPorcDescuento());
					numPorcRecargoequivalencia.setValue(cli.getPorcRecargoEquivalencia());
				}
			}
		});
		
		JLabel lblProductos = new JLabel("Productos");
		panel.add(lblProductos, "2, 5, right, default");
		
		//JPanel pnProductos = new JPanel();

		//pnProductos.setLayout(new BorderLayout(0, 0));
		
		//JScrollPane pnFacturaDetalles = new JScrollPane();
		//pnFacturaDetalles.setLayout(new ScrollPaneLayout());
		//pnProductos.add(pnFacturaDetalles);
		//JButton btAgregarProd = new JButton("A\u00F1adir Producto");
		//pnProductos.add(btAgregarProd, BorderLayout.SOUTH);
		
		//tbFacturaDetalles = new FacturasDetalleTabla("");
		//pnFacturaDetalles.setViewportView(tbFacturaDetalles);
		
		JLabel lblImporte = new JLabel("Importe");
		panel.add(lblImporte, "2, 7, right, default");
		
		JPanel pnDescuentos = new JPanel();
		panel.add(pnDescuentos, "4, 7, fill, fill");
		pnDescuentos.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblImporteTotal = new JLabel("Importe Base con descuento");
		lblImporteTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblImporteTotal);
		
		numImpBase = new JSpinner();
		numImpBase.setEnabled(false);
		numImpBase.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numImpBase);
		
		JLabel lblImporteIva = new JLabel("Importe IVA");
		lblImporteIva.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblImporteIva);
		
		numImpiva = new JSpinner();
		numImpiva.setEnabled(false);
		numImpiva.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numImpiva);
		
		JLabel lblImporteRecago = new JLabel("Importe Recargo Equivalencia");
		lblImporteRecago.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblImporteRecago);
		
		numImprecargo = new JSpinner();
		numImprecargo.setEnabled(false);
		numImprecargo.setMinimumSize(new Dimension(50, 20));
		numImprecargo.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		pnDescuentos.add(numImprecargo);
		
		JLabel lblImporteTotal_1 = new JLabel("IMPORTE TOTAL");
		lblImporteTotal_1.setHorizontalAlignment(SwingConstants.RIGHT);
		pnDescuentos.add(lblImporteTotal_1);
		
		numImporteTotal = new JButton();
		numImporteTotal.setFont(UIManager.getFont("ToolTip.font"));
		numImporteTotal.setText("PULSE PARA CALCULAR");
		numImporteTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcular();
			}
		});
		pnDescuentos.add(numImporteTotal);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n");
		panel.add(lblDireccin, "2, 9, right, default");
		
		JPanel pnDirecciones = new JPanel();
		panel.add(pnDirecciones, "4, 9, fill, fill");
		pnDirecciones.setLayout(new GridLayout(0, 3, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "Facturaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDirecciones.add(scrollPane);
		
		txtDirFactura = new JTextArea();
		scrollPane.setViewportView(txtDirFactura);
		txtDirFactura.setText("dirFactura");
		txtDirFactura.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(null, "Env\u00EDo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDirecciones.add(scrollPane_1);
		
		txtDirEnvio = new JTextArea();
		scrollPane_1.setViewportView(txtDirEnvio);
		txtDirEnvio.setText("dirEnvio");
		txtDirEnvio.setColumns(10);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new TitledBorder(null, "Correo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDirecciones.add(scrollPane_2);
		
		txtDirCorreo = new JTextArea();
		scrollPane_2.setViewportView(txtDirCorreo);
		txtDirCorreo.setText("dirCorreo");
		txtDirCorreo.setColumns(10);
		
		JLabel lblCobrada = new JLabel("Cobro");
		panel.add(lblCobrada, "2, 11, right, default");
		
		JPanel pnCobro = new JPanel();
		panel.add(pnCobro, "4, 11, fill, fill");
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
		
//		btAgregarProd.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				FacturaDetalleDialogo dialogo = new FacturaDetalleDialogo(null);
//				FacturaDetalle fd = dialogo.mostrar();
//				if (fd!=null) {
//					tbFacturaDetalles.addDetalle(fd);
//				}
//			}
//		});
		
		if (factura!=null && factura.getId()>0) {
			if (this.factura.getCliente()==null) {
				try {
					this.factura.setCliente(new ClientesBDD().recuperaPorId(this.factura.getClienteId()));
				} catch (Exception e1) {
				}
			}
			factura = new FacturasBDD().recuperaFacturaCompletaPorId(factura.getId());
			pnProductos = new FacturaDetallesPanel(factura.getId());
			panel.add(pnProductos, "4, 5, fill, fill");
		} else {
			factura = null;
			pnProductos = new FacturaDetallesPanel(0);
			panel.add(pnProductos, "4, 5, fill, fill");
		}
		setForm();
	}
	
	private void calcular() {
		double porcInversoDescuento = 1.0 - ((double) numPorcDescuento.getValue())/100.0;
		numImpBase.setValue(porcInversoDescuento * pnProductos.obtenerImporteProductos());
		numImpiva.setValue(porcInversoDescuento * pnProductos.obtenerImporteIVA());
		numImprecargo.setValue((double) numImpBase.getValue() * ((double) numPorcRecargoequivalencia.getValue())/100.0 );
		double impTotal = (double) numImpBase.getValue() + (double) numImpiva.getValue() + (double) numImprecargo.getValue() ;
		numImporteTotal.setText("" + impTotal);
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
		if (factura!=null && factura.getId()>0) {
			setTitle("Factura Existente");
			txtId.setText(""+ factura.getId());
			txtNumero.setText(""+ factura.getNumero());
			dateFecha.setDate(factura.getFecha());
			cbCliente.ponerClienteIdSeleccionado(factura.getClienteId());
			try {
				txtNif.setText(factura.getCliente().getNif());
			} catch (Exception e) {
			}
			pnProductos.ponerListaDetalles(factura.getDetalles());
			numPorcDescuento.setValue(factura.getPorcDescuento());
			numPorcRecargoequivalencia.setValue(factura.getPorcRecargoEquivalencia());
			numImpBase.setValue(factura.getImpTotal());
			numImprecargo.setValue(factura.getImpRecargo());
			numImpiva.setValue(factura.getImpIva());
			txtDirCorreo.setText(factura.getDirCorreo());
			txtDirFactura.setText(factura.getDirFactura());
			txtDirEnvio.setText(factura.getDirEnvio());
			chkbxCobrada.setSelected(factura.isCobrada());
		} else {
			setTitle("Nueva Factura con Detalles");
			txtId.setText("0");
			txtNumero.setText("");
			dateFecha.setDate(new Date());
			cbCliente.setSelectedIndex(-1);
			txtNif.setText("");
			pnProductos.ponerListaDetalles(null);
			numPorcDescuento.setValue(0);
			numPorcRecargoequivalencia.setValue(0);
			numImpBase.setValue(0);
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
		String nombreCliente = cbCliente.obtenerNombreCliente();
		Integer clienteId = cbCliente.obtenerClienteIdSeleccionado();
		Integer numero = Utilidades.validarEntero(txtNumero.getText().trim());
		Date fecha = dateFecha.getDate();
		Double porcDescuento = Utilidades.validarDouble("" + numPorcDescuento.getValue());
		Double porcRecargoEquivalencia = Utilidades.validarDouble("" + numPorcRecargoequivalencia.getValue());
		Double impTotal = Utilidades.validarDouble("" + numImpBase.getValue());
		Double impRecargo = Utilidades.validarDouble(""	+ numImpBase.getValue());
		Double impIva = Utilidades.validarDouble("" + numImpBase.getValue());
		String dirCorreo = txtDirCorreo.getText().trim();
		String dirFactura = txtDirFactura.getText().trim();
		String dirEnvio = txtDirEnvio.getText().trim();
		Boolean cobrada = chkbxCobrada.isSelected();
		ArrayList<FacturaDetalle> detalles = pnProductos.recuperarListaDetalles();
		//ArrayList<FacturaDetalle> detalles = tbFacturaDetalles.getListaDetalles();
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
