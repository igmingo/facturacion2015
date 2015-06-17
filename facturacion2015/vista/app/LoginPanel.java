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
	private JPanel panel;

	public LoginPanel() {
		this.usuarioPanel = null;
		setBounds(0, 0, 474, 425);
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(111, 112, 251, 242);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblUsuariocorreo = new JLabel("Correo electr\u00F3nico");
		lblUsuariocorreo.setBounds(1, 0, 250, 26);
		panel.add(lblUsuariocorreo);
		lblUsuariocorreo.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		txtEmail = new CorreoField();
		txtEmail.setBounds(1, 38, 250, 30);
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(1, 94, 250, 26);
		panel.add(lblContrasea);
		lblContrasea.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(0, 132, 251, 30);
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		btnLogin = new JButton("Entrar");
		btnLogin.setBounds(68, 199, 115, 43);
		panel.add(btnLogin);
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 20));
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
