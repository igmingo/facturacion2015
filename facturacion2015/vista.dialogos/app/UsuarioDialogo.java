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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtUsername;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JCheckBox chkIsAdmin;
	private EstadosCombo cbStatus;
	private Usuario usuario;
	
	public UsuarioDialogo(Usuario user, int id) {
		this.usuario = user;
		setTitle(usuario.getName());
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
		
		JButton btnRemove = new JButton("Eliminar");
		btnRemove.setBounds(138, 17, 98, 26);
		pnBotones.add(btnRemove);
		btnRemove.setVisible(id==0?false:true);
		
		JButton btnSave = new JButton("Grabar");
		btnSave.setBounds(20, 17, 98, 26);
		pnBotones.add(btnSave);
		
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
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setForm(salvar(getForm()));
				setVisible(false);
			}
		});
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar(getForm().getId());
				setForm(null);
				setVisible(false);
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setForm(null);
				setVisible(false);
			}
		});
		
		setForm(new UsuariosBDD().RecuperaPorId(id));
	}

	private void eliminar(int id) {
		int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Usuario?", "Eliminar Usuario", JOptionPane.OK_CANCEL_OPTION);
		if (pregunta==JOptionPane.OK_OPTION) {
			boolean eliminado = new UsuariosBDD().Eliminar(id);
			mostrarMensaje(eliminado?"Usuario Eliminado.":"No se ha podido eliminar.");
		}
	}
	
	private Usuario salvar(Usuario u) {
		if (u!=null) {
			UsuariosBDD db = new UsuariosBDD();
			int newId = db.Grabar(u);
			if (newId>=0) {
				u.setId(newId);
				ponerPassword(u, new String(txtPassword.getPassword()) );
				mostrarMensaje("Usuario añadido correctamento.");
			} else {
				mostrarMensaje("Error al Añadir.");
			}
		}
		return u;
	}
	
	private boolean ponerPassword(Usuario u, String password) {
		boolean resultado = false;
		if (password!=null && u!=null && u.isLoged()) {
			UsuariosBDD db = new UsuariosBDD();
			resultado = db.ponerPassword(u.getId(), password);
		}
		return resultado;
	}
	
	private void setForm(Usuario g) {
		if (g!=null) {
			txtId.setText("" + g.getId());
			txtUsername.setText(g.getName());
			txtEmail.setText(g.getEmail());
			txtPassword.setText("");
			chkIsAdmin.setSelected(g.isAdmin());
			cbStatus.setEstado(g.getStatus());
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
		Usuario g = null;
		Integer id = Utilidades.validarEntero(txtId.getText());
		if (id!=null) {
			String username = Utilidades.validarString(txtUsername.getText());
			String email = Utilidades.validarString(txtEmail.getText());
			//String password = Utilidades.validarString(txtPassword.getText());
			int status = cbStatus.getEstado();
			boolean isAdmin = chkIsAdmin.isSelected();
			g = new Usuario(id, username, email, isAdmin, status );
		}
		return g;
	}
	
	private void mostrarMensaje(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	
	// METODOS PUBLICOS
	public Usuario mostrar() {
		setVisible(true);
		Usuario retorno = getForm();
		System.out.println("Retorno del dialogo: " + retorno);
		dispose();
		return retorno;
	}
}
