package Visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import Logico.Conexion;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JPasswordField;

public class Registrarse extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomb1;
	private JTextField txtApe1;
	private JTextField txtNomb2;
	private JTextField txtApe2;
	private JLabel lblCorreoElectronico;
	private JTextField txtCorreo;
	private JLabel lblTelfono;
	private JTextField txtTelefono;
	private JLabel lblCdula;
	private JTextField txtCedula;
	private JLabel lblUsuario;
	private JTextField txtUsername;
	private JLabel lblContrasea;
	private JRadioButton rdbtnCliente;
	private JRadioButton rdbtnPropietario;
	private JPanel PanelTipoP;
	private JRadioButton rdbtnPersona;
	private JRadioButton rdbtnEmpresa;
	private JPasswordField txtContraseña;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registrarse frame = new Registrarse();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Registrarse() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Registrarse.class.getResource("/Image/homepage_home_house_icon_153873.png")));
		setTitle("Registrarse");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 626, 280);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPNombre = new JLabel("Primer Nombre:");
		lblPNombre.setBounds(10, 36, 123, 13);
		panel.add(lblPNombre);
		
		JLabel lblSegundoNombre = new JLabel("Segundo Nombre:");
		lblSegundoNombre.setBounds(325, 36, 123, 13);
		panel.add(lblSegundoNombre);
		
		JLabel lblPrimerApellido = new JLabel("Primer Apellido:");
		lblPrimerApellido.setBounds(10, 92, 123, 13);
		panel.add(lblPrimerApellido);
		
		JLabel lblSegundoApellido = new JLabel("Segundo Apellido:");
		lblSegundoApellido.setBounds(325, 92, 123, 13);
		panel.add(lblSegundoApellido);
		
		txtNomb1 = new JTextField();
		txtNomb1.setBounds(136, 33, 165, 19);
		panel.add(txtNomb1);
		txtNomb1.setColumns(10);
		
		txtApe1 = new JTextField();
		txtApe1.setColumns(10);
		txtApe1.setBounds(136, 89, 165, 19);
		panel.add(txtApe1);
		
		txtNomb2 = new JTextField();
		txtNomb2.setColumns(10);
		txtNomb2.setBounds(439, 33, 165, 19);
		panel.add(txtNomb2);
		
		txtApe2 = new JTextField();
		txtApe2.setColumns(10);
		txtApe2.setBounds(439, 89, 165, 19);
		panel.add(txtApe2);
		
		lblCorreoElectronico = new JLabel("Correo Electrónico:");
		lblCorreoElectronico.setBounds(10, 143, 123, 13);
		panel.add(lblCorreoElectronico);
		
		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(136, 140, 468, 19);
		panel.add(txtCorreo);
		
		lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(50, 190, 123, 13);
		panel.add(lblTelfono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(136, 187, 165, 19);
		panel.add(txtTelefono);
		
		lblCdula = new JLabel("Cédula:");
		lblCdula.setBounds(366, 190, 123, 13);
		panel.add(lblCdula);
		
		txtCedula = new JTextField();
		txtCedula.setColumns(10);
		txtCedula.setBounds(439, 187, 165, 19);
		panel.add(txtCedula);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(50, 238, 123, 13);
		panel.add(lblUsuario);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(136, 235, 165, 19);
		panel.add(txtUsername);
		
		lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(346, 238, 123, 13);
		panel.add(lblContrasea);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(439, 235, 165, 19);
		panel.add(txtContraseña);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Tipo de usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 300, 626, 76);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		rdbtnCliente = new JRadioButton("Cliente");
		
		//MODIFIQUE AQUI
		rdbtnPropietario = new JRadioButton("Propietario ");
		rdbtnPropietario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnCliente.setSelected(false);
				rdbtnPropietario.setSelected(true);
				PanelTipoP.setVisible(true);
			}
		});
		rdbtnPropietario.setBounds(306, 29, 103, 21);
		panel_1.add(rdbtnPropietario);
		
		//MODIFIQUE AQUI 
		rdbtnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnCliente.setSelected(true);
				rdbtnPropietario.setSelected(false);
				PanelTipoP.setVisible(false);
			}
		});
		rdbtnCliente.setBounds(181, 29, 103, 21);
		panel_1.add(rdbtnCliente);
		
		//MODIFIQUE AQUI
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String user_nick = txtUsername.getText();
			    String nombre = txtNomb1.getText();
			    String nombre2 = txtNomb2.getText();
			    String apellido = txtApe1.getText(); 
			    String apellido2 = txtApe2.getText();
			    String correo = txtCorreo.getText();
			    String tel = txtTelefono.getText();
			    String ced = txtCedula.getText();
			    String contrasena = txtContraseña.getText();
			    String tipo;
			    String tipoPropietario = null;

			    
			    if(rdbtnCliente.isSelected() == true){
			    	tipo = "C";
			    }else {
			    	tipo = "B";
			    	if(rdbtnPersona.isSelected() == true) {
			    		tipoPropietario = "P";
			    	}else if (rdbtnEmpresa.isSelected() == true){
			    		tipoPropietario = "E";
			    	}
			    }
			    
				if(validarUserNick(user_nick)) {
			        JOptionPane.showMessageDialog(null, "El user_nick ya existe. Por favor, elige otro.");
			    }else {
			    	insertarUsuario(nombre, nombre2, apellido, apellido2, correo, tel, ced, user_nick,contrasena,tipo,tipoPropietario);
			        JOptionPane.showMessageDialog(null, "Su usuario se ha registrado exitosamente");
			        //ME FALTA QUE DESPUES DE REGISTRARSE MANDARLO A LA PRINCIPAL 
			    }
				
			}
		});
		btnRegistrar.setBounds(440, 441, 97, 21);
		contentPane.add(btnRegistrar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal prin = new Principal();
				/*reg.setModal(true);*/
				prin.setVisible(true);
		        setVisible(false);
			}
		});
		btnCancelar.setForeground(new Color(255, 0, 0));
		btnCancelar.setBounds(551, 441, 85, 21);
		contentPane.add(btnCancelar);
		
		//MODIFIQUE AQUI 
		//AGREGUE UN PANEL PARA EL TIPO DE PROPIETARIO 
		PanelTipoP = new JPanel();
		PanelTipoP.setVisible(false);
		PanelTipoP.setBorder(new TitledBorder(null, "Tipo de Propietario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PanelTipoP.setBounds(10, 386, 626, 49);
		contentPane.add(PanelTipoP);
		PanelTipoP.setLayout(null);
		
		
		rdbtnPersona = new JRadioButton("Persona");
		rdbtnPersona.setBounds(179, 22, 103, 21);
		PanelTipoP.add(rdbtnPersona);
		
		rdbtnEmpresa = new JRadioButton("Empresa");
		rdbtnEmpresa.setBounds(314, 22, 103, 21);
		PanelTipoP.add(rdbtnEmpresa);
		
        setLocationRelativeTo(null);
           

	}
	
   //AGREGUE ESTA FUNCION QUE VALIDA EL INICIO DE SESION
	public static boolean validarUserNick(String user_nick) {
        boolean tipo_usuario = false;
        try {
            Connection con = Conexion.getConexion();
            CallableStatement cs = con.prepareCall("{call sp_validar_usernick(?)}");
            cs.setString(1, user_nick);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                tipo_usuario = rs.getBoolean("TipoUsuario");
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el user_nick: " + e.getMessage());
        }
        return tipo_usuario;
    }
	
	//FUNCION PARA REGISTRAR AL USUARIO (YA SEA ADM, CLIENTE O VENDEDOR) 
	public void insertarUsuario(String nombre,String nombre2, String apellido,String apellido2,String correo, String tel, String ced, String user_nick, String contrasena, String tipo, String tipoPropietario) {
	   String nuevo_id = null;
	    try {
	        Connection con = Conexion.getConexion();
	        CallableStatement cs = con.prepareCall("{call sp_insertar_usuario(?,?,?,?,?,?,?,?,?,?,?)}");
	        cs.setString(1, nombre);
	        cs.setString(2, nombre2);
	        cs.setString(3, apellido);
	        cs.setString(4, apellido2);
	        cs.setString(5, correo);
	        cs.setString(6, tel);
	        cs.setString(7, ced);
	        cs.setString(8, user_nick);
	        cs.setString(9, contrasena);
	        cs.setString(10, tipo);
	        cs.setString(11, tipoPropietario);

	        ResultSet rs = cs.executeQuery();
	       if (rs.next()) {
	        	nuevo_id = rs.getString("nuevo_id");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al insertar el usuario: " + e.getMessage());
	    }
	}


}
