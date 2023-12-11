package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Conexion;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MisPropiedades extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JTextField txtUsuario;
	private JTable tblMisPropiedades;
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private Object row[];
	private String actualUs = obtenerUsuarioActual();
	private String idPropiedad = null;
	private JButton btnDel;
	private JButton btnMod;
	private Boolean PropiedadSel = false;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		try {
			MisPropiedades dialog = new MisPropiedades();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MisPropiedades() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MisPropiedades.class.getResource("/Image/homepage_home_house_icon_153873.png")));
		setTitle("Mis Propiedades");
		setBounds(100, 100, 836, 499);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Listado de Propiedades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 172, 810, 278);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		tblMisPropiedades = new JTable();
		tblMisPropiedades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = 0;
				row = tblMisPropiedades.getSelectedRow();
				if(row > -1) {
					btnDel.setEnabled(true);
					btnMod.setEnabled(true);
					PropiedadSel = true;
					int column = 0;
					int rowr = tblMisPropiedades.getSelectedRow();
					String value = tblMisPropiedades.getModel().getValueAt(rowr, column).toString();
					idPropiedad = value;
				}
			}
		});
		scrollPane.setViewportView(tblMisPropiedades);
		String headers[] = {"Id","Tipo","Estado","Direccion","Precio"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(headers);
		
		tblMisPropiedades.setModel(model);
		
		txtUsuario = new JTextField();
		txtUsuario.setEditable(false);
		txtUsuario.setText(actualUs);
		//NUEVO MODIFIQUE AQUI 
		//txtUsuario.setText(obtenerUsuarioActual());
		txtUsuario.setBounds(686, 11, 114, 20);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JButton btnInicio = new JButton("Inicio\r\n");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal prin = new Principal();
				prin.setVisible(true);
		        setVisible(false);
			}
		});
		btnInicio.setBounds(10, 24, 89, 23);
		panel.add(btnInicio);
		
		JButton btnAñadir = new JButton("A\u00F1adir");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AñadirPropiedad AñadirP = new AñadirPropiedad();
				AñadirP.setVisible(true);
		        setVisible(false);
			}
		});
		btnAñadir.setBounds(10, 58, 89, 23);
		panel.add(btnAñadir);
		
		btnMod = new JButton("Modificar");
		btnMod.setEnabled(false);
		btnMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarPropiedad ModP = new modificarPropiedad(actualUs,idPropiedad);
				ModP.setVisible(true);
				ModP = null;
				loadTable();
			}
		});
		btnMod.setBounds(10, 92, 89, 23);
		panel.add(btnMod);
		
		btnDel = new JButton("Eliminar");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(PropiedadSel != false) {
					int option = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar la propiedad: "+ idPropiedad, "Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(option == JOptionPane.YES_OPTION){
						delPropiedad(idPropiedad);
						loadTable();
						btnDel.setEnabled(false);
						btnMod.setEnabled(false);
					}
				}
				//-----------------
			}
		});
		btnDel.setEnabled(false);
		btnDel.setBounds(10, 126, 89, 23);
		panel.add(btnDel);
		
		loadTable();
	}
	
	//NUEVO MODIFIQUE AQUI 
	
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
    
	
	private void delPropiedad(String idDel) {
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC sp_EliminarPropiedad '"+ idDel +"';";
			sqlStatement.executeQuery(consulta);
		} catch (SQLException ex) {
			if(ex.getErrorCode() == 0) {
				JOptionPane.showMessageDialog(null, "Propiedad eliminada correctamente");
			}else {
			JOptionPane.showMessageDialog(null, ex.toString());
			}
		}
	}
	
	private void loadTable() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC sp_buscarpropiedad_byId "+ actualUs +" ;";
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


