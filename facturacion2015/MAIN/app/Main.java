package app;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;

import javax.swing.JDesktopPane;

import java.awt.Color;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	
	//LOGIN
	private LoginPanel pnLogin;
	private Usuario user = null;
	
	public Main() {
		user = new Usuario();
		user.setLoged(true);
		
		setResizable(false);
		setBounds(0, 0, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		//ImageIcon fondo = new ImageIcon("./images/fondo.jpg");
		ImageIcon fondo = null;
		fondo = new ImageIcon(new ImageIcon("./images/fondo.jpg").getImage().getScaledInstance(811,611, Image.SCALE_SMOOTH));
		
		//setIcon(new ImageIcon (new ImageIcon("./images/fondo.jpg").getImage().getScaledInstance(811,611, Image.SCALE_SMOOTH)));
		
		/* 
		 * PANEL DE LOGIN
		 */
		pnLogin = new LoginPanel(user);
		pnLogin.setVisible(true);
		pnLogin.setBounds(0, 0, getWidth(), getHeight());
		getContentPane().add(pnLogin);
		pnLogin.setVisible(!user.isLoged());
		
		pnPrincipal = new JPanel();
		pnPrincipal.setOpaque(false);
		pnPrincipal.setBounds(10, 11, 774, 528);
		getContentPane().add(pnPrincipal);
		pnPrincipal.setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.ORANGE);
		desktopPane.setBounds(0, 0, 1, 1);
		getContentPane().add(desktopPane);
		
		JLabel lblFondo = new JLabel();
		lblFondo.setBounds(0, 0, 800, 600);
		lblFondo.setIcon(fondo);
		getContentPane().add(lblFondo);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnProductos = new JMenu("Productos");
		menuBar.add(mnProductos);
		JMenuItem mntmNuevoProducto = new JMenuItem("Nuevo Producto");
		mnProductos.add(mntmNuevoProducto);
		JMenuItem mntmListadoDeProductos = new JMenuItem("Listado de productos");
		mnProductos.add(mntmListadoDeProductos);
		
		JMenu mnNewMenu = new JMenu("Clientes");
		menuBar.add(mnNewMenu);
		JMenuItem mntmNuevoCliente = new JMenuItem("Nuevo Cliente");
		mnNewMenu.add(mntmNuevoCliente);
		JMenuItem mntmListadoDeClientes = new JMenuItem("Listado de Clientes");
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
		mnConsultas.add(mntmPruebas);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		menuBar.add(mnUsuarios);
		JMenuItem mntmAadirUsuario = new JMenuItem("A\u00F1adir Usuario");
		mnUsuarios.add(mntmAadirUsuario);
		JMenuItem mntmAdminstracinDeUsuarios = new JMenuItem("Administraci\u00F3n de Usuarios");
		mnUsuarios.add(mntmAdminstracinDeUsuarios);
		menuBar.setVisible(user.isLoged());
		
		// LISTENERS
		
		mntmListadoDeProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel("Productos");
			}
		});
		
		mntmNuevoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProductoDialogo dialogo = new ProductoDialogo(null);
				dialogo.mostrar();
			}
		});
		
		mntmNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClienteDialogo dialogo = new ClienteDialogo(null);
				dialogo.mostrar();
			}
		});
		
		mntmListadoDeClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel("Clientes");
			}
		});
		
		mntmNuevaFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FacturaCompletaDialogo dialogo = new FacturaCompletaDialogo(null);
				Factura fac = dialogo.mostrar();
			}
		});
		
		mntmListadoDeFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarPanel("Facturas");
			}
		});
		
		mntmPruebas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Prueba p = new Prueba ();
				p.setVisible(true);
			}
		});
		getContentPane().addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
			@Override
			public void ancestorResized(HierarchyEvent e) {
				Dimension dim = getContentPane().getSize();
				pnLogin.setSize(dim);
//				if (dim.getWidth()>100 && dim.getWidth()>100) {
//					lblFondo.setIcon(new ImageIcon(new ImageIcon(
//							"./images/fondo.jpg").getImage()
//							.getScaledInstance(
//									(int) dim.getWidth(),
//									(int) dim.getHeight(),
//									Image.SCALE_SMOOTH)));
//				}
				pnLogin.repaint();
				//System.out.println(getContentPane().getSize());
			}
		});
		
		pnLogin.btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user = pnLogin.login();
				if (user==null) {
					System.out.println("Error de Login");
				} else {
					System.out.println("Login correcto");
					setTitle(user.getName());
					pnLogin.setVisible(false);
					menuBar.setVisible(true);
				}
			}
		});
		
	}

	protected void mostrarPanel(String string) {
		pnPrincipal.removeAll();
		pnPrincipal.add(new Paneles(user, string));
		pnPrincipal.repaint();
		pnPrincipal.validate();
	}

	public static void main(String[] args) {
		
		//Toolkit.getDefaultToolkit().beep();
		
//		Clip sonido;
//		try {
//			sonido = AudioSystem.getClip();
//			File file = new File("C:/Windows/Media/tada.wav");
//			try {
//				sonido.open(AudioSystem.getAudioInputStream(file));
//			} catch (IOException e) {
//				// TODO Bloque catch generado automáticamente
//				e.printStackTrace();
//			} catch (UnsupportedAudioFileException e) {
//				// TODO Bloque catch generado automáticamente
//				e.printStackTrace();
//			}
//			sonido.loop(20);
//			sonido.start();
//			sonido.stop();
//		} catch (LineUnavailableException e) {
//			// TODO Bloque catch generado automáticamente
//			e.printStackTrace();
//		}
		
		ArrayList<Factura> lista = new FacturasBDD().recuperaFacturaPorFiltro_NEW(null);
		
		//Main main = new Main();
		//main.mostrar();
	}
	
	private void mostrar() {
		setVisible(true);
	}
}
