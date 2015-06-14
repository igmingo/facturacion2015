package app;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	
	//LOGIN
	private LoginPanel pnLogin;
	private Usuario userPpal = null;
	
	public Main() {
		
		setResizable(false);
		setBounds(0, 0, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		ImageIcon fondo = new ImageIcon("./images/fondo.jpg");
		
		/* PANEL DE LOGIN
		 *
		 **/
		pnLogin = new LoginPanel();
		pnLogin.setVisible(true);
		pnLogin.setBounds(0, 0, getWidth(), getHeight());
		getContentPane().add(pnLogin);
		pnLogin.setLayout(null);
		pnLogin.setVisible(false);
		
		pnPrincipal = new JPanel();
		pnPrincipal.setOpaque(false);
		pnPrincipal.setBounds(10, 11, 774, 528);
		getContentPane().add(pnPrincipal);
		pnPrincipal.setLayout(null);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 800, 600);
		lblFondo.setIcon(fondo);
		getContentPane().add(lblFondo);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnProductos = new JMenu("Productos");
		menuBar.add(mnProductos);
		JMenuItem mntmNuevoProducto = new JMenuItem("Nuevo Producto");
		mntmNuevoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProductoDialogo dialogo = new ProductoDialogo(null);
				dialogo.mostrar();
			}
		});
		mnProductos.add(mntmNuevoProducto);
		JMenuItem mntmListadoDeProductos = new JMenuItem("Listado de productos");
		mntmListadoDeProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel("Productos");
			}
		});
		mnProductos.add(mntmListadoDeProductos);
		
		JMenu mnNewMenu = new JMenu("Clientes");
		menuBar.add(mnNewMenu);
		JMenuItem mntmNuevoCliente = new JMenuItem("Nuevo Cliente");
		mntmNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClienteDialogo dialogo = new ClienteDialogo(null);
				dialogo.mostrar();
			}
		});
		mnNewMenu.add(mntmNuevoCliente);
		JMenuItem mntmListadoDeClientes = new JMenuItem("Listado de Clientes");
		mntmListadoDeClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel("Clientes");
			}
		});
		mnNewMenu.add(mntmListadoDeClientes);
		
		JMenu mnFacturas = new JMenu("Facturas");
		menuBar.add(mnFacturas);
		JMenuItem mntmNuevaFactura = new JMenuItem("Nueva Factura");
		mnFacturas.add(mntmNuevaFactura);
		JMenuItem mntmListadoDeFacturas = new JMenuItem("Listado de Facturas");
		mnFacturas.add(mntmListadoDeFacturas);
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		JMenuItem mntmPruebas = new JMenuItem("Pruebas");
		mntmPruebas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Prueba p = new Prueba ();
				p.setVisible(true);
			}
		});
		mnConsultas.add(mntmPruebas);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmAadirUsuario = new JMenuItem("A\u00F1adir Usuario");
		mnUsuarios.add(mntmAadirUsuario);
		
		JMenuItem mntmAdminstracinDeUsuarios = new JMenuItem("Administraci\u00F3n de Usuarios");
		mnUsuarios.add(mntmAdminstracinDeUsuarios);
		mntmListadoDeFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarPanel("Facturas");
			}
		});
	}

	protected void mostrarPanel(String string) {
		pnPrincipal.removeAll();
		pnPrincipal.add(new Paneles(userPpal, string));
		pnPrincipal.repaint();
		pnPrincipal.validate();
		
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.mostrar();
	}
	
	private void mostrar() {
		setVisible(true);
	}
}
