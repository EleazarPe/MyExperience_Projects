package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;


import Logico.Conexion;

import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.Console;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class modificarPropiedad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdPropiedad;
	private JTextField txtidVendedor;
	private JTextField txtCalle;
	private JTextField txtCiudad;
	private JTextField txtCasa;
	private JSpinner spPrecio;
	private JComboBox cbxtipo;
	private JRadioButton rdbDisponible;
	private JRadioButton rdbRentado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			modificarPropiedad dialog = new modificarPropiedad(null,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public modificarPropiedad(String idCUser, String idSelPropiedad) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(modificarPropiedad.class.getResource("/Image/repair.png")));
		setTitle("Modificar propiedad");
		setBounds(100, 100, 621, 763);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel panel = new JPanel();
			panel.setToolTipText("Informacion");
			panel.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Informaci\u00F3n:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel.setBounds(5, 11, 595, 134);
			contentPanel.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Id Propiedad:");
			lblNewLabel.setBounds(10, 39, 115, 14);
			panel.add(lblNewLabel);
			
			txtIdPropiedad = new JTextField();
			txtIdPropiedad.setEditable(false);
			txtIdPropiedad.setBounds(88, 36, 184, 20);
			panel.add(txtIdPropiedad);
			txtIdPropiedad.setColumns(10);
			txtIdPropiedad.setText(idSelPropiedad);
			
			JLabel lblNewLabel_1 = new JLabel("Propietario:");
			lblNewLabel_1.setBounds(297, 39, 83, 14);
			panel.add(lblNewLabel_1);
			
			txtidVendedor = new JTextField();
			txtidVendedor.setEditable(false);
			txtidVendedor.setBounds(371, 36, 184, 20);
			panel.add(txtidVendedor);
			txtidVendedor.setColumns(10);
			txtidVendedor.setText(idCUser);
			
			JLabel lblNewLabel_2 = new JLabel("Tipo:");
			lblNewLabel_2.setBounds(10, 91, 46, 14);
			panel.add(lblNewLabel_2);
			
			cbxtipo = new JComboBox();
			cbxtipo.setModel(new DefaultComboBoxModel(new String[] {"Selecionar", "CASA", "APTO", "HABI"}));
			cbxtipo.setBounds(60, 88, 184, 20);
			panel.add(cbxtipo);
			
			JLabel lblNewLabel_3 = new JLabel("Precio: ");
			lblNewLabel_3.setBounds(297, 91, 83, 14);
			panel.add(lblNewLabel_3);
			
			spPrecio = new JSpinner();
			spPrecio.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spPrecio.setBounds(347, 88, 184, 20);
			panel.add(spPrecio);
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Direcci\u00F3n:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(5, 156, 595, 127);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Calle:");
		lblNewLabel_4.setBounds(10, 22, 71, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Ciudad:");
		lblNewLabel_5.setBounds(10, 69, 71, 14);
		panel.add(lblNewLabel_5);
		
		txtCalle = new JTextField();
		txtCalle.setBounds(60, 19, 214, 20);
		panel.add(txtCalle);
		txtCalle.setColumns(10);
		
		txtCiudad = new JTextField();
		txtCiudad.setColumns(10);
		txtCiudad.setBounds(60, 66, 214, 20);
		panel.add(txtCiudad);
		
		JLabel lblNewLabel_6 = new JLabel("Casa:");
		lblNewLabel_6.setBounds(296, 22, 71, 14);
		panel.add(lblNewLabel_6);
		
		txtCasa = new JTextField();
		txtCasa.setBounds(344, 19, 214, 20);
		txtCasa.setText(null);
		panel.add(txtCasa);
		txtCasa.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBounds(5, 294, 590, 307);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Elementos b\u00E1sicos:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(0, 0, 590, 74);
		panel_1.add(panel_2);
		
		JRadioButton rdbwifi = new JRadioButton("Wifi");
		rdbwifi.setBounds(49, 17, 124, 21);
		panel_2.add(rdbwifi);
		
		JRadioButton rdbCocina = new JRadioButton("Cocina\r\n");
		rdbCocina.setBounds(222, 17, 124, 21);
		panel_2.add(rdbCocina);
		
		JRadioButton rdbLavadora = new JRadioButton("Lavadora\r\n");
		rdbLavadora.setBounds(222, 40, 124, 21);
		panel_2.add(rdbLavadora);
		
		JRadioButton rdbAire = new JRadioButton("Aire Acondicionado\r\n");
		rdbAire.setBounds(395, 40, 143, 21);
		panel_2.add(rdbAire);
		
		JRadioButton rdbCalefac = new JRadioButton("Calefacci\u00F3n\r\n");
		rdbCalefac.setBounds(395, 17, 143, 21);
		panel_2.add(rdbCalefac);
		
		JRadioButton rdbTv = new JRadioButton("Televisi\u00F3n");
		rdbTv.setBounds(49, 40, 103, 21);
		panel_2.add(rdbTv);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Caracteristicas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(0, 85, 590, 74);
		panel_1.add(panel_3);
		
		JRadioButton rdbPiscina = new JRadioButton("Piscina");
		rdbPiscina.setBounds(49, 17, 124, 21);
		panel_3.add(rdbPiscina);
		
		JRadioButton rdbJacuzzi = new JRadioButton("Jacazzi\r\n");
		rdbJacuzzi.setBounds(49, 40, 124, 21);
		panel_3.add(rdbJacuzzi);
		
		JRadioButton rdbBarbacoa = new JRadioButton("Barbacoa");
		rdbBarbacoa.setBounds(222, 17, 124, 21);
		panel_3.add(rdbBarbacoa);
		
		JRadioButton rdbParqueo = new JRadioButton("Parqueo");
		rdbParqueo.setBounds(222, 40, 124, 21);
		panel_3.add(rdbParqueo);
		
		JRadioButton rdbGym = new JRadioButton("Gimnasio");
		rdbGym.setBounds(395, 17, 143, 21);
		panel_3.add(rdbGym);
		
		JRadioButton rdbPingpong = new JRadioButton("Pingpong");
		rdbPingpong.setBounds(395, 40, 143, 21);
		panel_3.add(rdbPingpong);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ubicaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(0, 170, 590, 50);
		panel_1.add(panel_4);
		
		JRadioButton rdbMar = new JRadioButton("Frente al Mar");
		rdbMar.setBounds(49, 17, 124, 21);
		panel_4.add(rdbMar);
		
		JRadioButton rdbMalecon = new JRadioButton("Malec\u00F3n\r\n");
		rdbMalecon.setBounds(222, 17, 124, 21);
		panel_4.add(rdbMalecon);
		
		JRadioButton rdbPenthouse = new JRadioButton("Penthouse");
		rdbPenthouse.setBounds(395, 17, 143, 21);
		panel_4.add(rdbPenthouse);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Seguridad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(0, 231, 590, 50);
		panel_1.add(panel_5);
		
		JRadioButton rdbAlarma = new JRadioButton("Alarma");
		rdbAlarma.setBounds(49, 17, 124, 21);
		panel_5.add(rdbAlarma);
		
		JRadioButton rdbCamaras = new JRadioButton("Camaras \r\n");
		rdbCamaras.setBounds(222, 17, 124, 21);
		panel_5.add(rdbCamaras);
		
		JRadioButton rdbHumo = new JRadioButton("Detector de humo\r\n");
		rdbHumo.setBounds(395, 17, 143, 21);
		panel_5.add(rdbHumo);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Disponibilidad:", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_6.setBounds(5, 612, 590, 63);
		contentPanel.add(panel_6);
		panel_6.setLayout(null);
		
		rdbDisponible = new JRadioButton("Disponible");
		rdbDisponible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbDisponible.setSelected(true);
				rdbRentado.setSelected(false);
				
			}
		});
		rdbDisponible.setBounds(119, 21, 109, 23);
		panel_6.add(rdbDisponible);
		
		rdbRentado = new JRadioButton("Rentado");
		rdbRentado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbRentado.setSelected(true);
				rdbDisponible.setSelected(false);
			}
		});
		rdbRentado.setBounds(317, 21, 109, 23);
		
		panel_6.add(rdbRentado);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton btnMod = new JButton("Modificar");
				btnMod.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int option = JOptionPane.showConfirmDialog(null, "Está seguro de modificar la propiedad: "+ idSelPropiedad, "Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if(option == JOptionPane.YES_OPTION){
							actualizarDatos(idSelPropiedad,idCUser,cbxtipo.getSelectedItem().toString(), String.valueOf(spPrecio.getValue()),txtCalle.getText(), txtCasa.getText(), txtCiudad.getText());
						}
						MisPropiedades MisPropiedadess = new MisPropiedades();
						MisPropiedadess.setVisible(true);
				        setVisible(false);
					}
					
				});
				btnMod.setActionCommand("OK");
				buttonPane.add(btnMod);
				getRootPane().setDefaultButton(btnMod);
			}
		}
		setLocationRelativeTo(null);
		System.out.println("idpropiedad"+ idSelPropiedad);
		llenarCampos(idSelPropiedad);
	}
	
	private void actualizarDatos(String idPropiedad, String idVen, String Tipo, String precio, String calle, String casa, String ciudad) {
		String estado = null;
		if(rdbDisponible.isSelected()) {
			estado = "D";
		}else if(rdbRentado.isSelected()) {
			estado = "R";
		}
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC sp_modificarPropiedad '"+ idPropiedad +"','"+idVen +"','"+Tipo+"',"+precio+",'"+estado+"','"+calle+"','"+casa+"','"+ciudad+"';";
			sqlStatement.executeQuery(consulta);
		} catch (SQLException ex) {
			if(ex.getErrorCode() == 0) {
				JOptionPane.showMessageDialog(null, "Propiedad modificada correctamente");
			}else {
			JOptionPane.showMessageDialog(null, ex.toString());
			}
		}
	}
	
	private void llenarCampos(String idPropiedad) {
	
			try {
				java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
				String consulta = "EXEC sp_CargarPropiedad '"+ idPropiedad +"';";
				ResultSet rs =sqlStatement.executeQuery(consulta);
				if (rs.next()) {

					spPrecio.setValue(Integer.parseInt(rs.getString("Precio")));
					txtCasa.setText(rs.getString("Casa"));
					txtCalle.setText(rs.getString("Calle"));
					txtCiudad.setText(rs.getString("Ciudad"));
					if(rs.getString("Estado").equalsIgnoreCase("D")) {
						rdbDisponible.setSelected(true);
						rdbRentado.setSelected(false);	
					}
					if(rs.getString("Estado").equalsIgnoreCase("R")) {
						rdbRentado.setSelected(true);
						rdbDisponible.setSelected(false);
					}
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.toString());
				
			}
	}
}
