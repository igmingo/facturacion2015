package app;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

public class Paneles extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFiltro;
	private JTable tabla;

	public Paneles(Usuario user, String nombrePanel) {
		setBounds(0, 0, 774, 528);
		setLayout(null);
		
		JPanel pnTipo = new JPanel();
		pnTipo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), nombrePanel, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnTipo.setBounds(10, 8, 754, 509);
		add(pnTipo);
		pnTipo.setLayout(null);
		
		textFiltro = new JTextField();
		textFiltro.setBounds(10, 20, 655, 20);
		pnTipo.add(textFiltro);
		textFiltro.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 734, 413);
		pnTipo.add(scrollPane);
		
		switch (nombrePanel) {
		case "Usuarios":
			tabla = new UsuariosTabla(user, textFiltro.getText());
			break;
		case "Productos":
			tabla = new ProductosTabla(textFiltro.getText());
			break;
		case "Clientes":
			tabla = new ClientesTabla(textFiltro.getText());
			break;
		case "Facturas":
			tabla = new FacturasTabla(textFiltro.getText());
			break;
		default:
			break;
		}
		scrollPane.setViewportView(tabla);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(675, 20, 69, 23);
		pnTipo.add(btnFiltrar);
		
		JButton btnNuevo = new JButton(" + " + nombrePanel );
		btnNuevo.setBounds(292, 475, 170, 23);
		pnTipo.add(btnNuevo);
		
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (nombrePanel) {
				case "Usuarios":
				{
					UsuarioDialogo dialogo = new UsuarioDialogo(user, 0);
					Usuario u = dialogo.mostrar();
					if (u!=null) {
						((UsuariosTabla) tabla).actualizarTabla(textFiltro.getText());
					}
				}
					break;
				case "Productos":
				{
					ProductoDialogo dialogo = new ProductoDialogo(null);
					Producto prod = dialogo.mostrar();
					if (prod!=null) {
						((ProductosTabla) tabla).actualizarTabla(textFiltro.getText());
					}
				}
					break;
				case "Clientes":
				{
					ClienteDialogo dialogo = new ClienteDialogo(null);
					Cliente cli = dialogo.mostrar();
					if (cli!=null) {
						((ClientesTabla) tabla).actualizarTabla(textFiltro.getText());
					}
				}
					break;
				case "Facturas":
				{
					FacturaDialogo dialogo = new FacturaDialogo(null);
					Factura fac = dialogo.mostrar();
					if (fac!=null) {
						((FacturasTabla) tabla).actualizarTabla(textFiltro.getText());
					}
				}
					break;
				default:
					break;
				}
			}
		});
		
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (nombrePanel) {
				case "Productos":
					((ProductosTabla) tabla).actualizarTabla(textFiltro.getText());
					break;
				case "Clientes":
					((ClientesTabla) tabla).actualizarTabla(textFiltro.getText());
					break;
				case "Facturas":
					((FacturasTabla) tabla).actualizarTabla(textFiltro.getText());
					break;
				default:
					break;
				}
			}
		});
		

	}
}
