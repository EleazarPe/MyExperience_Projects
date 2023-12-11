package Visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Conexion;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Principal extends JFrame {

    private JPanel contentPane;
    private JTextField txtBuscar;
    private JComboBox cmbFiltro;
    public JButton btnIniciarSesion;
    public JButton btnRegistrarse;
    private JButton btnMisPropiedades;
    private JTable tblPropiedadesRent;
    private DefaultTableModel model;
    private Object row[];
    private String idPropiedad = null;
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal frame = new Principal();
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
    public Principal() {
    	setTitle("YouJevaRent");
    	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
					String consulta = "EXEC sp_eliminar_CurrentUser;";
					sqlStatement.executeQuery(consulta);
				} catch (SQLException ex) {
					if(ex.getErrorCode() == 0) {
						JOptionPane.showMessageDialog(null, "Secion cerrada");
					}else {
					JOptionPane.showMessageDialog(null, ex.toString());
					}
				}
			}
		});

    	
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/Image/homepage_home_house_icon_153873.png")));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_1.setBounds(10, 286, 1374, 434);
        panel.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        panel_1.add(scrollPane, BorderLayout.CENTER);
        
        tblPropiedadesRent = new JTable();
        tblPropiedadesRent.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int column = 0;
				int rowr = tblPropiedadesRent.getSelectedRow();
        		String value = tblPropiedadesRent.getModel().getValueAt(rowr, column).toString();
				idPropiedad = value;
        		  Propiedad selecPropiedad = new Propiedad(idPropiedad) ;
        		  selecPropiedad.setModal(true);
        		  selecPropiedad.setVisible(true);
        	}
        });
        scrollPane.setViewportView(tblPropiedadesRent);
        String headers1[] = {"Id","Tipo","Estado","Direccion","Precio"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(headers1);
		
		tblPropiedadesRent.setModel(model);
        
        txtBuscar = new JTextField();
        txtBuscar.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		txtBuscar.setText(null);
        	}
        });
        txtBuscar.setText("Buscar Ciudad...");
        txtBuscar.setBounds(450, 32, 504, 29);
        panel.add(txtBuscar);
        txtBuscar.setColumns(10);
        
       btnMisPropiedades = new JButton("Mis Propiedades");

        btnMisPropiedades.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MisPropiedades MisPropie = new MisPropiedades();
        		MisPropie.setVisible(true);
		        //setVisible(false);
        	}
        });
        btnMisPropiedades.setBounds(0, 0, 151, 23);
        panel.add(btnMisPropiedades);
        mostrarbtn(obtenerUsuarioActual());
        cmbFiltro = new JComboBox();
        cmbFiltro.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String opcionSeleccionada = cmbFiltro.getSelectedItem().toString();
        		
        		if (opcionSeleccionada.equals("Todos")) {
        			loadTableTodos();
                    
                } else if (opcionSeleccionada.equals("Disponibles")) {
                	loadTableDisponibles();
                	
                } else if (opcionSeleccionada.equals("Precio Desendente")) {
                	loadTablePreciodAscendente(); 
                	             	
                } else if (opcionSeleccionada.equals("Precio Ascendente")) {
                	loadTablePreciodDescendente();

                } else if (opcionSeleccionada.equals("Casas")) {
                	loadTableCasas();
                	
                } else if (opcionSeleccionada.equals("Apartamento")) {
                	loadTablePreciodApartamento();
                	
                } else if (opcionSeleccionada.equals("Habitacion")) {
                	loadTableLote();
                }
        		
        	}
        });
        cmbFiltro.setModel(new DefaultComboBoxModel(new String[] {"Filtrar...", "Todos", "Disponibles", "Precio Ascendente", "Precio Desendente", "Casas", "Apartamento", "Habitacion"}));
        cmbFiltro.setToolTipText("");
        cmbFiltro.setBounds(10, 253, 141, 22);
        panel.add(cmbFiltro);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String buscarString = txtBuscar.getText();
        		loadTableBuscar(buscarString);
        	}
        });
        btnNewButton.setIcon(new ImageIcon(Principal.class.getResource("/Image/search_look_for_seek_magnifying_glass_lens_icon_141967 (1).png")));
        btnNewButton.setBounds(952, 32, 41, 29);
        panel.add(btnNewButton);
        
        btnIniciarSesion = new JButton("Iniciar Sesion");
        btnIniciarSesion.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		InicioSesion ini = new InicioSesion();
				ini.setVisible(true);
		        setVisible(false);
        	}
        });
        btnIniciarSesion.setBounds(1274, 1, 120, 21);
        panel.add(btnIniciarSesion);
        
       // Component btnRegistrarse;
		btnRegistrarse = new JButton("Registrarse ");
        btnRegistrarse.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Registrarse reg = new Registrarse();
				reg.setVisible(true);
		        //setVisible(false);

        	}
        });
        btnRegistrarse.setBounds(1155, 1, 120, 21);
        panel.add(btnRegistrarse);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/Image/house_rent_home_building_icon_231026 (2).png")));
        lblNewLabel.setBounds(574, 72, 256, 206);
        panel.add(lblNewLabel);

        // Agrega el código para establecer el tamaño y la posición de la ventana
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height - 300;
        int width = screenSize.width - 500;
        setSize(width, height);
        setLocationRelativeTo(null);

        setVisible(true);
        loadTableTodos();
    }
    private void mostrarbtn(String Id) {
    	
    	try {
	        Connection con = Conexion.getConexion();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("EXEC sp_obtener_tipo_usuarioVendedor '"+ Id +"';");
	        if (rs.next()) {
	             if( rs.getString("TipoUsuario").equals("1")) {
	            	 System.out.println("Funciona ID " + Id);
	            	 btnMisPropiedades.setVisible(true);
	             }else {
	            	 btnMisPropiedades.setVisible(false);
	             }
	        }
	    } catch (SQLException e) {
	    	System.out.println("NO Funciona ID " + Id);
	        e.printStackTrace();
	    }
    	
    }
	public String obtenerUsuarioActual() {
	    String usuarioActual = "No encontrado";
	    try {
	        Connection con = Conexion.getConexion();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT ID_User FROM CurrentUser");
	        if (rs.next()) {
	            usuarioActual = rs.getString("ID_User");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return usuarioActual;
	}

	private void loadTableTodos() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "SELECT p.Id_Propiedad, p.Tipo, p.Estado, CONCAT(d.Calle, ' ', d.Casa, ', ', d.Ciudad) AS Direccion, p.Precio\r\n"
					+ "FROM Propiedad p\r\n"
					+ "JOIN Propiedad_Dir d ON p.Id_Propiedad = d.Id_Propiedad\r\n"
					+ "ORDER BY p.Id_Propiedad DESC;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
	private void loadTableDisponibles() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "SELECT p.Id_Propiedad, p.Tipo, p.Estado, CONCAT(d.Calle, ' ', d.Casa, ', ', d.Ciudad) AS Direccion, p.Precio\r\n"
					+ "FROM Propiedad p\r\n"
					+ "JOIN Propiedad_Dir d ON p.Id_Propiedad = d.Id_Propiedad\r\n"
					+ "WHERE p.Estado = 'D'\r\n"
					+ "ORDER BY p.Id_Propiedad DESC;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
	
	private void loadTablePreciodDescendente() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "SELECT p.Id_Propiedad, p.Tipo, p.Estado, CONCAT(d.Calle, ' ', d.Casa, ', ', d.Ciudad) AS Direccion, p.Precio\r\n"
					+ "FROM Propiedad p\r\n"
					+ "JOIN Propiedad_Dir d ON p.Id_Propiedad = d.Id_Propiedad\r\n"
					+ "ORDER BY p.Precio ASC;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
	private void loadTablePreciodAscendente() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "SELECT p.Id_Propiedad, p.Tipo, p.Estado, CONCAT(d.Calle, ' ', d.Casa, ', ', d.Ciudad) AS Direccion, p.Precio\r\n"
					+ "FROM Propiedad p\r\n"
					+ "JOIN Propiedad_Dir d ON p.Id_Propiedad = d.Id_Propiedad\r\n"
					+ "ORDER BY p.Precio DESC;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
	private void loadTablePreciodApartamento() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "SELECT p.Id_Propiedad, p.Tipo, p.Estado, CONCAT(d.Calle, ' ', d.Casa, ', ', d.Ciudad) AS Direccion, p.Precio\r\n"
					+ "FROM Propiedad p\r\n"
					+ "JOIN Propiedad_Dir d ON p.Id_Propiedad = d.Id_Propiedad\r\n"
					+ "WHERE p.Tipo = 'APTO'\r\n"
					+ "ORDER BY p.Id_Propiedad DESC;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}	
	private void loadTableCasas() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "SELECT p.Id_Propiedad, p.Tipo, p.Estado, CONCAT(d.Calle, ' ', d.Casa, ', ', d.Ciudad) AS Direccion, p.Precio\r\n"
					+ "FROM Propiedad p\r\n"
					+ "JOIN Propiedad_Dir d ON p.Id_Propiedad = d.Id_Propiedad\r\n"
					+ "WHERE p.Tipo = 'CASA'\r\n"
					+ "ORDER BY p.Id_Propiedad DESC;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
	private void loadTableLote() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "SELECT p.Id_Propiedad, p.Tipo, p.Estado, CONCAT(d.Calle, ' ', d.Casa, ', ', d.Ciudad) AS Direccion, p.Precio\r\n"
					+ "FROM Propiedad p\r\n"
					+ "JOIN Propiedad_Dir d ON p.Id_Propiedad = d.Id_Propiedad\r\n"
					+ "WHERE p.Tipo = 'HABI'\r\n"
					+ "ORDER BY p.Id_Propiedad DESC;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
	
	private void loadTableBuscar(String IdPropiedad) {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC sp_obtener_propiedad_con_direccion '" + IdPropiedad + "';";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Id_Propiedad");
				row[1] = resultadoResultSet.getString("Tipo");
				row[2] =resultadoResultSet.getString("Estado");
				row[3] = resultadoResultSet.getString("Direccion");
				row[4] =  " $ "+ resultadoResultSet.getString("Precio");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
}



    




