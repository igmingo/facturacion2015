package app;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CorreoField extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean isValidEmail() {
        String emailreg = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		//String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		return super.getText().matches(emailreg);
	}
	
	public String getEmail() {
		if(isValidEmail() == true){
			return super.getText().toLowerCase();
		} else {
			return null;
		}
	}
	
	private boolean isValidChar(char c) {
       //String emailreg = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		boolean retorno = false;
		if (c>='a' && c<='z' || c>='A' && c<='Z' || c>='0' && c<='9') {
			return true;
		}
        String validChars = "!#$%&'*+/=?^_`{|}~-@.";
        for (int i = 0; i < validChars.length(); i++) {
        	char temp = validChars.charAt(i);
        	if (c==temp) {
        		return true;
        	}
		}
        return retorno;
	}

	public CorreoField() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (isValidChar(c)) {
					e.setKeyChar(c);
				} else {
					e.setKeyChar('\u0000');
				}
			}
		});
	}
	
}
