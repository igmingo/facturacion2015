package app;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.Font;

public class LoginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CorreoField txtEmail;
	private JPasswordField txtPassword;
	private Usuario usuarioPanel;
	public JButton btnLogin;

	public LoginPanel() {
		this.usuarioPanel = null;
		setBounds(0, 0, 474, 425);
		setLayout(null);
		
		JLabel lblUsuariocorreo = new JLabel("Correo electr\u00F3nico");
		lblUsuariocorreo.setBounds(112, 112, 250, 26);
		add(lblUsuariocorreo);
		lblUsuariocorreo.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		txtEmail = new CorreoField();
		txtEmail.setBounds(112, 150, 250, 30);
		add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblContrasea.setBounds(112, 206, 250, 26);
		add(lblContrasea);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(111, 244, 251, 30);
		add(txtPassword);
		txtPassword.setColumns(10);
		
		btnLogin = new JButton("Entrar");
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnLogin.setBounds(179, 311, 115, 43);
		add(btnLogin);
	}

	public Usuario getUsuarioPanel() {
		return usuarioPanel;
	}

	public Usuario login() {
		System.out.println(Encriptacion.getMensajeEncriptado(new String(txtPassword.getPassword()), Encriptacion.SHA256));
		Usuario user = new UsuariosBDD().login(txtEmail.getText(), new String(txtPassword.getPassword()));
		if (user!=null && user.getStatus()>0) {
			user.setLoged(true);
			return user;
		} else {
			return null;
		}
	}
}
