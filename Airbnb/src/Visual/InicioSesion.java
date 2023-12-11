package Visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Logico.Conexion;
import Logico.Propietario;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JPasswordField;

public class InicioSesion extends JFrame {

	private JPanel contentPane;
	public JTextField txtUsername;
	private JPasswordField txtContraseña;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioSesion frame = new InicioSesion();
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
	public InicioSesion() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InicioSesion.class.getResource("/Image/homepage_home_house_icon_153873.png")));
		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 463, 362);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(InicioSesion.class.getResource("/Image/avatardefault_92824.png")));
		lblNewLabel.setBounds(179, 33, 96, 110);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contraseña:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(188, 221, 116, 19);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Usuario:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(199, 154, 116, 19);
		panel.add(lblNewLabel_1_1);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(120, 181, 214, 19);
		panel.add(txtUsername);
		
		JButton btnCancelar = new JButton("Cancelar ");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal prin = new Principal();
				prin.setVisible(true);
		        setVisible(false);
			}
		});
		btnCancelar.setForeground(new Color(255, 0, 0));
		btnCancelar.setBounds(231, 305, 116, 21);
		panel.add(btnCancelar);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesi\u00F3n");
		
		//NUEVO MODIFIQUE AQUI 
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_nick = txtUsername.getText(); 
				String user_pass = txtContraseña.getText();
			
			    boolean resultado = validarInicioSesion(user_nick, user_pass);
			    if(!resultado) {
			        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto");
			        clean ();
			        
			    } else {
			        guardarCurrentUser( user_nick);
			        Principal prin = new Principal();
			        prin.setVisible(true);
			        setVisible(false);
			   
			        prin.btnIniciarSesion.setVisible(false);
			        prin.btnRegistrarse.setVisible(false);
			      
			    }
			}
			
		});
		btnIniciarSesion.setBounds(107, 305, 116, 21);
		panel.add(btnIniciarSesion);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(120, 250, 214, 19);
		panel.add(txtContraseña);
		
        setLocationRelativeTo(null);

	}
	
	//MODIFIQUE AQUI 
	//FUNCION PARA VALIDAR EL INICIO DE SESION 
	
	public boolean validarInicioSesion(String user_nick, String user_pass) {
	    boolean resultado = false;
	    try {
	        Connection con = Conexion.getConexion();
	        CallableStatement cs = con.prepareCall("{ call sp_validar_inicio_sesion(?,?,?) }");
	        cs.setString(1, user_nick);
	        cs.setString(2, user_pass);
	        cs.registerOutParameter(3, java.sql.Types.BIT);
	        cs.execute();
	        resultado = cs.getBoolean(3);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return resultado;
	}
	
	//MODIFIQUE AQUI 
	//FUNCION LIMPIAR LOS CAMPOS 
		
	private void clean() {
		txtUsername.setText("");
		txtContraseña.setText("");

	}
	
	//NUEVO MODIFIQUE AQUI 
	public String obtenerIdUsuario(String user_nick) {
	    String id_usuario = "No encontrado";
	    try {
	        Connection con = Conexion.getConexion();
	        CallableStatement cs = con.prepareCall("{ call sp_obtener_id(?) }");
	        cs.setString(1, user_nick);
	        ResultSet rs = cs.executeQuery();
	        if (rs.next()) {
	            id_usuario = rs.getString("IdUsuario");
	        }
	    } catch (SQLException e) {
	    	System.out.println("Aqui ta1");
	        e.printStackTrace();
	    }
	    return id_usuario;
	}

	
	//GUARDA EN UNA TABLA EL USUARIO QUE INICIE SESION 
	public void guardarCurrentUser(String user_nick) {
	    try {
	        Connection con = Conexion.getConexion();
	        CallableStatement cs = con.prepareCall("{ call sp_guardar_current_user1(?) }");
	        cs.setString(1, user_nick);
	        cs.executeUpdate();
	    } catch (SQLException e) {
	    	System.out.println("Aqui ta3");
	        e.printStackTrace();
	    }
	}







}
