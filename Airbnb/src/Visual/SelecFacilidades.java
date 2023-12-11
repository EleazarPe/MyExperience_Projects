package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.border.TitledBorder;

import Logico.Conexion;

import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class SelecFacilidades extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnWifi;
	private JRadioButton rdbtnTelevision;
	private JRadioButton rdbtnCocina;
	private JRadioButton rdbtnLavadora;
	private JRadioButton rdbtnCalefaccin;
	private JRadioButton rdbtnAireAcondicionado;
	private JRadioButton rdbtnPiscina;
	private JRadioButton rdbtnJacuzzi;
	private JRadioButton rdbtnBarbacoa;
	private JRadioButton rdbtnParqueo;
	private JRadioButton rdbtnGimnasio;
	private JRadioButton rdbtnPingpong;
	private JRadioButton rdbtnFrenteAlMar;
	private JRadioButton rdbtnMalecn;
	private JRadioButton rdbtnPenthouse;
	private JRadioButton rdbtnAlarma;
	private JRadioButton rdbtnCamaras;
	private JRadioButton rdbtnDetectorDeHumo;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			SelecFacilidades dialog = new SelecFacilidades();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public SelecFacilidades(String id_Propiedad) {
		setTitle("Seleccionar Facilidades");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SelecFacilidades.class.getResource("/Image/homepage_home_house_icon_153873.png")));
		setBounds(100, 100, 472, 395);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		{
			
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel pnlElementosBasicos = new JPanel();
			pnlElementosBasicos.setBorder(new TitledBorder(null, "Elementos b\u00E1sicos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnlElementosBasicos.setBounds(10, 22, 428, 74);
			panel.add(pnlElementosBasicos);
			pnlElementosBasicos.setLayout(null);
			
			rdbtnWifi = new JRadioButton("Wifi");
			rdbtnWifi.setBounds(6, 17, 124, 21);
			pnlElementosBasicos.add(rdbtnWifi);
			
			rdbtnCocina = new JRadioButton("Cocina\r\n");
			rdbtnCocina.setBounds(149, 17, 124, 21);
			pnlElementosBasicos.add(rdbtnCocina);
			
			rdbtnLavadora = new JRadioButton("Lavadora\r\n");
			rdbtnLavadora.setBounds(149, 40, 124, 21);
			pnlElementosBasicos.add(rdbtnLavadora);
			
			rdbtnAireAcondicionado = new JRadioButton("Aire Acondicionado\r\n");
			rdbtnAireAcondicionado.setBounds(279, 40, 143, 21);
			pnlElementosBasicos.add(rdbtnAireAcondicionado);
			
			rdbtnCalefaccin = new JRadioButton("Calefacci\u00F3n\r\n");
			rdbtnCalefaccin.setBounds(279, 17, 143, 21);
			pnlElementosBasicos.add(rdbtnCalefaccin);
			
			rdbtnTelevision = new JRadioButton("Televisi\u00F3n");
			rdbtnTelevision.setBounds(6, 40, 103, 21);
			pnlElementosBasicos.add(rdbtnTelevision);
			
			JPanel pnlCaracteriticas = new JPanel();
			pnlCaracteriticas.setLayout(null);
			pnlCaracteriticas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Caracteristicas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnlCaracteriticas.setBounds(10, 106, 428, 74);
			panel.add(pnlCaracteriticas);
			
			rdbtnPiscina = new JRadioButton("Piscina");
			rdbtnPiscina.setBounds(6, 17, 124, 21);
			pnlCaracteriticas.add(rdbtnPiscina);
			
			rdbtnJacuzzi = new JRadioButton("Jacazzi\r\n");
			rdbtnJacuzzi.setBounds(6, 40, 124, 21);
			pnlCaracteriticas.add(rdbtnJacuzzi);
			
			rdbtnBarbacoa = new JRadioButton("Barbacoa");
			rdbtnBarbacoa.setBounds(149, 17, 124, 21);
			pnlCaracteriticas.add(rdbtnBarbacoa);
			
			rdbtnParqueo = new JRadioButton("Parqueo");
			rdbtnParqueo.setBounds(149, 40, 124, 21);
			pnlCaracteriticas.add(rdbtnParqueo);
			
			rdbtnGimnasio = new JRadioButton("Gimnasio");
			rdbtnGimnasio.setBounds(279, 17, 143, 21);
			pnlCaracteriticas.add(rdbtnGimnasio);
			
			rdbtnPingpong = new JRadioButton("Pingpong");
			rdbtnPingpong.setBounds(279, 40, 143, 21);
			pnlCaracteriticas.add(rdbtnPingpong);
			
			JPanel pnlSeguridad = new JPanel();
			pnlSeguridad.setLayout(null);
			pnlSeguridad.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Seguridad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnlSeguridad.setBounds(10, 250, 428, 50);
			panel.add(pnlSeguridad);
			
			rdbtnAlarma = new JRadioButton("Alarma");
			rdbtnAlarma.setBounds(6, 17, 124, 21);
			pnlSeguridad.add(rdbtnAlarma);
			
			rdbtnCamaras = new JRadioButton("Camaras \r\n");
			rdbtnCamaras.setBounds(149, 17, 124, 21);
			pnlSeguridad.add(rdbtnCamaras);
			
			rdbtnDetectorDeHumo = new JRadioButton("Detector de humo\r\n");
			rdbtnDetectorDeHumo.setBounds(279, 17, 143, 21);
			pnlSeguridad.add(rdbtnDetectorDeHumo);
			
			JPanel pnlUbiacion = new JPanel();
			pnlUbiacion.setLayout(null);
			pnlUbiacion.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ubicaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnlUbiacion.setBounds(10, 190, 428, 50);
			panel.add(pnlUbiacion);
			
			rdbtnFrenteAlMar = new JRadioButton("Frente al Mar");
			rdbtnFrenteAlMar.setBounds(6, 17, 124, 21);
			pnlUbiacion.add(rdbtnFrenteAlMar);
			
			rdbtnMalecn = new JRadioButton("Malec\u00F3n\r\n");
			rdbtnMalecn.setBounds(149, 17, 124, 21);
			pnlUbiacion.add(rdbtnMalecn);
			
			rdbtnPenthouse = new JRadioButton("Penthouse");
			rdbtnPenthouse.setBounds(279, 17, 143, 21);
			pnlUbiacion.add(rdbtnPenthouse);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGuardar = new JButton("Guardar");
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					if(rdbtnWifi.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"1");
			        }
			        if(rdbtnTelevision.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"2");
			        }
			        if(rdbtnCocina.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"3");
			        }
			        if(rdbtnLavadora.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"4");
			        }
			        if(rdbtnCalefaccin.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"5");
			        }
			        if(rdbtnAireAcondicionado.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"6");
			        }
			        if(rdbtnPiscina.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"7");
			        }
			        if(rdbtnJacuzzi.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"8");
			        }
			        if(rdbtnBarbacoa.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"9");
			        }
			        if(rdbtnParqueo.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"10");
			        }
			        if(rdbtnGimnasio.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"11");
			        }
			        if(rdbtnPingpong.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"12");
			        }
			        if(rdbtnFrenteAlMar.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"13");
			        }
			        if(rdbtnMalecn.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"14");
			        }
			        if(rdbtnPenthouse.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"15");
			        }
			        if(rdbtnAlarma.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"16");
			        }
			        if(rdbtnCamaras.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"17");
			        }
			        if(rdbtnDetectorDeHumo.isSelected() == true ) {
			        	registrarFacilidadPropiedad(id_Propiedad,"18");
			        }
			        MisPropiedades MisP = new MisPropiedades();
			        MisP.setVisible(true);
			        setVisible(false);
				       
					}
				});
				btnGuardar.setActionCommand("OK");
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AñadirPropiedad AñadirPropie = new AñadirPropiedad();
						AñadirPropie.setVisible(true);
				        setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	//NUEVO MODIFIQUE AQUI 
	public boolean registrarFacilidadPropiedad(String id_propiedad, String id_facilidad) {
	    boolean registrado = false;
	    try {
	        Connection conn = Conexion.getConexion();
	        CallableStatement cs = conn.prepareCall("{call sp_registrar_facilidad_propiedad(?, ?)}");
	        cs.setString(1, id_propiedad);
	        cs.setString(2, id_facilidad);
	        int resultado = cs.executeUpdate();
	        if (resultado > 0) {
	            registrado = true;
	        }
	        conn.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return registrado;
	}
	
}

