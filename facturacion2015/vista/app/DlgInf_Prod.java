package app;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class DlgInf_Prod extends JDialog {
	/*	TABLA productos BASE DE DATOS
	id int(11)
	nombre varchar(30)
	precio double
	iva double
	stock int(11)
	descripcion mediumtext
	baja tinyint(1)*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> retorno = new HashMap<String, Object>();
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public DlgInf_Prod() {
		setResizable(false);
		setBounds(new Rectangle(0, 0, 350, 180));
		setModal(true);
		setTitle("Informe de Productos");
		
		JPanel pnCentral = new JPanel();
		getContentPane().add(pnCentral, BorderLayout.CENTER);
		pnCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 60));
		pnCentral.add(panel);
		panel.setBorder(new TitledBorder(null, "Precio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblDesde = new JLabel("Desde");
		panel.add(lblDesde);
		
		JSpinner desdeprecio = new JSpinner();
		desdeprecio.setPreferredSize(new Dimension(70, 20));
		desdeprecio.setMinimumSize(new Dimension(50, 20));
		desdeprecio.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		panel.add(desdeprecio);
		
		JLabel lblHasta = new JLabel("hasta");
		panel.add(lblHasta);
		
		JSpinner hastaprecio = new JSpinner();
		hastaprecio.setPreferredSize(new Dimension(70, 20));
		hastaprecio.setMinimumSize(new Dimension(50, 20));
		hastaprecio.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		panel.add(hastaprecio);
		
		JPanel ordenadopor = new JPanel();
		ordenadopor.setPreferredSize(new Dimension(300, 50));
		pnCentral.add(ordenadopor);
		ordenadopor.setBorder(new TitledBorder(null, "Ordenado por", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ordenadopor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JRadioButton rdbtnNombre = new JRadioButton("Nombre");
		buttonGroup.add(rdbtnNombre);
		rdbtnNombre.setSelected(true);
		ordenadopor.add(rdbtnNombre);
		
		JRadioButton rdbtnPrecio = new JRadioButton("Precio");
		buttonGroup.add(rdbtnPrecio);
		ordenadopor.add(rdbtnPrecio);
		
		JRadioButton rdbtnIva = new JRadioButton("IVA");
		buttonGroup.add(rdbtnIva);
		ordenadopor.add(rdbtnIva);
		
		JPanel botones = new JPanel();
		getContentPane().add(botones, BorderLayout.SOUTH);
		botones.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnReturnParams = new JButton("Informe");
		botones.add(btnReturnParams);
		btnReturnParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Map<String, Object> retorno = new HashMap<String, Object>();
				retorno.put("desdeprecio", desdeprecio.getValue());
				retorno.put("hastaprecio", hastaprecio.getValue());
				String orden = "nombre";
				orden = rdbtnNombre.isSelected()?"nombre":orden;
				orden = rdbtnPrecio.isSelected()?"precio":orden;
				orden = rdbtnIva.isSelected()?"iva":orden;
				retorno.put("orden", orden);
				setVisible(false);
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		botones.add(btnCancelar);

	}
	
	public Map<String, Object> mostrar() {
		setVisible(true);
		dispose();
		return retorno;
	}

}
