package app;

import javax.swing.JDialog;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Prueba extends JDialog {
	private JTextField txtNif;
	private JTextField txtNombre;
	private JTextField txtDescuento;
	private JTextField txtRecargo;
	private ClientesCombo cbClientes;	
	public Prueba() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(new Rectangle(0, 0, 450, 300));
		setModal(true);
		setTitle("Consultas");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblCliente = new JLabel("Cliente");
		panel.add(lblCliente, "1, 1, right, default");
		
		cbClientes = new ClientesCombo();

		cbClientes.recargarCombo(0);
		panel.add(cbClientes, "3, 1, fill, default");
		
		JLabel lblNid = new JLabel("NIF");
		panel.add(lblNid, "1, 3, right, default");
		
		txtNif = new JTextField();
		panel.add(txtNif, "3, 3, fill, default");
		txtNif.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		panel.add(lblNombre, "1, 5, right, default");
		
		txtNombre = new JTextField();
		panel.add(txtNombre, "3, 5, fill, default");
		txtNombre.setColumns(10);
		
		JLabel lblDescuento = new JLabel("Descuento");
		panel.add(lblDescuento, "1, 7, right, default");
		
		txtDescuento = new JTextField();
		panel.add(txtDescuento, "3, 7, fill, default");
		txtDescuento.setColumns(10);
		
		JLabel lblRecargo = new JLabel("Recargo");
		panel.add(lblRecargo, "1, 9, right, default");
		
		txtRecargo = new JTextField();
		panel.add(txtRecargo, "3, 9, fill, default");
		txtRecargo.setColumns(10);
		
		JButton btnCerrar = new JButton("Cerrar");
		getContentPane().add(btnCerrar, BorderLayout.SOUTH);
		
		cbClientes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent selected) {
				System.out.println(selected.getItem());
				
				Cliente cli = (Cliente) selected.getItem();
				System.out.println(cli);
				System.out.println(cli.getNombre());
				//Cliente cli = cbClientes.getSelectedCliente();
				//Cliente cli = cbClientes.getItemAt(cbClientes.getSelectedIndex());
				//txtNif.setText(cli.getNif());
				txtNombre.setText(cli.getNombre());
//				txtDescuento.setText("" + cli.getPorcDescuento());
//				txtRecargo.setText(""+cli.getPorcRecargoEquivalencia());
			}
		});
	}
}
