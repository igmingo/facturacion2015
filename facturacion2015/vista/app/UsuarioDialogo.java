package app;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class UsuarioDialogo extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private JTextField txtId;
	private JTextField txtUsername;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JCheckBox chkIsAdmin;
	private EstadosCombo cbStatus;

	//private Usuario userLogin;
	
	public UsuarioDialogo(Usuario user) {
		this.usuario = user;
		setResizable(false);
		setModal(true);
		setBounds(new Rectangle(0, 0, 380, 250));
		getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 11, 77, 14);
		getContentPane().add(lblId);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 39, 77, 14);
		getContentPane().add(lblNombre);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("");
		txtId.setBounds(97, 8, 114, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setText("");
		txtUsername.setBounds(97, 39, 267, 20);
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JPanel pnBotones = new JPanel();
		pnBotones.setBounds(0, 162, 374, 60);
		getContentPane().add(pnBotones);
		pnBotones.setOpaque(false);
		pnBotones.setLayout(null);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(138, 17, 98, 26);
		pnBotones.add(btnEliminar);
		
		JButton btnGrabar = new JButton("Grabar");
		btnGrabar.setBounds(20, 17, 98, 26);
		pnBotones.add(btnGrabar);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(256, 17, 98, 26);
		pnBotones.add(btnCancelar);
		
		txtPassword = new JPasswordField();
		txtPassword.setText("");
		txtPassword.setColumns(10);
		txtPassword.setBounds(97, 70, 267, 20);
		getContentPane().add(txtPassword);
		
		txtEmail = new JTextField();
		txtEmail.setText("");
		txtEmail.setColumns(10);
		txtEmail.setBounds(97, 101, 267, 20);
		getContentPane().add(txtEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 73, 77, 14);
		getContentPane().add(lblPassword);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 104, 77, 14);
		getContentPane().add(lblEmail);
		
		chkIsAdmin = new JCheckBox("Adminstrador");
		chkIsAdmin.setHorizontalAlignment(SwingConstants.RIGHT);
		chkIsAdmin.setBounds(241, 128, 123, 23);
		getContentPane().add(chkIsAdmin);
		
		cbStatus = new EstadosCombo();
		cbStatus.setBounds(97, 132, 134, 20);
		cbStatus.recargarCombo();
		getContentPane().add(cbStatus);
		
		JLabel lblNewLabel = new JLabel("Estado");
		lblNewLabel.setBounds(10, 132, 46, 14);
		getContentPane().add(lblNewLabel);
		
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
				if (usuario!=null) {
					setVisible(false);
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
				setVisible(false);
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuario = null;
				setVisible(false);
			}
		});
		
		if (this.usuario!=null && this.usuario.getId()>0) {
			this.usuario = new UsuariosBDD().recuperaPorId(this.usuario.getId());
		} else {
			this.usuario = null;
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
					usuario = null;
					mostrarMensaje("Cliente Eliminado.");
				} else {
					mostrarMensaje("No se ha podido eliminar.");
				}
			}
		}
	}
	
	private void guardar() {
		usuario = getForm();
		if (usuario != null && usuario.getEmail()!= null
				&& usuario.getName()!= null) {
			int newId = new UsuariosBDD().grabar(usuario);
			if (newId >= 0) {
				usuario.setId(newId);
				ponerPassword(new String(txtPassword.getPassword()));
				setForm();
				mostrarMensaje("Usuario guardado correctamente.");
			} else {
				mostrarMensaje("Error al guardar.");
			}
		} else {
			mostrarMensaje("El formulario no es correcto.");
			usuario = null;
		}
	}
	
	private boolean ponerPassword(String password) {
		boolean resultado = false;
		if (password!=null && usuario!=null && usuario.isLoged()) {
			UsuariosBDD db = new UsuariosBDD();
			resultado = db.ponerPassword(usuario.getId(), password);
		}
		return resultado;
	}
	
	private void setForm() {
		if (usuario!=null) {
			txtId.setText("" + usuario.getId());
			txtUsername.setText(usuario.getName());
			txtEmail.setText(usuario.getEmail());
			txtPassword.setText("");
			chkIsAdmin.setSelected(usuario.isAdmin());
			cbStatus.setEstado(usuario.getStatus());
		} else {
			txtId.setText("0");
			txtUsername.setText("");
			txtEmail.setText("");
			txtPassword.setText("");
			chkIsAdmin.setSelected(false);
			cbStatus.setEstado(0);
		}
	}
	
	private Usuario getForm() {
		Usuario usu = null;
		int id = Utilidades.validarEntero(txtId.getText());
		String username = Utilidades.validarString(txtUsername.getText());
		String email = Utilidades.validarString(txtEmail.getText());
		//String password = Utilidades.validarString(txtPassword.getText());
		int status = cbStatus.getEstado();
		boolean isAdmin = chkIsAdmin.isSelected();
		try {
			usu = new Usuario(id, username, email, isAdmin, status);
		} catch (Exception e) {
		}
		return usu;
	}
	
	private void mostrarMensaje(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	
	// METODOS PUBLICOS
	public Usuario mostrar() {
		setVisible(true);
		dispose();
		return usuario;
	}
}
