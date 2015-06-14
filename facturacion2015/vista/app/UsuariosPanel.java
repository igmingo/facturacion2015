package app;
import javax.swing.JPanel;

import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class UsuariosPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFiltro;
	private UsuariosTabla tbUsuarios;
	private Usuario usuario;

	public UsuariosPanel(Usuario user) {
		this.usuario = user;
		setBounds(new Rectangle(0, 0, 469, 397));
		setLayout(null);
		
		JLabel lblFiltro = new JLabel("Usuarios");
		lblFiltro.setBounds(10, 11, 80, 16);
		add(lblFiltro);
		
		textFiltro = new JTextField();
		textFiltro.setBounds(100, 8, 260, 20);
		add(textFiltro);
		textFiltro.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 449, 316);
		add(scrollPane);
		
		tbUsuarios = new UsuariosTabla(usuario, textFiltro.getText());
		scrollPane.setViewportView(tbUsuarios);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(370, 7, 89, 23);
		add(btnFiltrar);
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(370, 363, 89, 23);
		add(btnNuevo);
		
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UsuarioDialogo dialogo = new UsuarioDialogo(usuario, 0);
				Usuario newUsuario = dialogo.mostrar();
				if (newUsuario!=null) {
					tbUsuarios.actualizarTabla(textFiltro.getText());
				}
			}
		});
		
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tbUsuarios.actualizarTabla(textFiltro.getText());
			}
		});
	}
}
