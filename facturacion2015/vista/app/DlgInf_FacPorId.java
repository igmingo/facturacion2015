package app;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DlgInf_FacPorId extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacturasTabla table;
	private JTextField txtFiltro;
	
	public DlgInf_FacPorId() {
		setBounds(new Rectangle(0, 0, 400, 300));
		setModal(true);
		setTitle("Informe de Facturas");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new FacturasTabla(null);
		scrollPane.setViewportView(table);
		table.actualizarTabla(null);
		
		txtFiltro = new JTextField();
		txtFiltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.actualizarTabla(txtFiltro.getText());
			}
		});
		panel.add(txtFiltro, BorderLayout.NORTH);
		txtFiltro.setColumns(10);
		
		JButton btnReturnParams = new JButton("Crear factura para imprimir");
		btnReturnParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		getContentPane().add(btnReturnParams, BorderLayout.SOUTH);

	}
	
	public Map<String, Object> mostrar() {
		setVisible(true);
		Map<String, Object> retorno = new HashMap<String, Object>();
		retorno.put("facturaid", table.obtenerFacturaId());
		dispose();
		return retorno;
	}

}
