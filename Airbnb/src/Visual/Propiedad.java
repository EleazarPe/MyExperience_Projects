package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Conexion;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JTable;

public class Propiedad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDireccion;
	private JTextField txtPropietario;
	private JTextField txtEstado;
	private JTextField txtPrecio;
	private JTable tblComentarios;
	private JTextField txtPropiedad;
    private DefaultTableModel model;
    private Object row[];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Propiedad dialog = new Propiedad(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Propiedad(String idSelPropiedad) {
		setBounds(100, 100, 787, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("DesktopIcon.border"));
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panel_1.setBounds(10, 42, 741, 108);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBounds(10, 42, 190, 20);
		panel_1.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		txtPropietario = new JTextField();
		txtPropietario.setEditable(false);
		txtPropietario.setBounds(593, 11, 138, 20);
		panel_1.add(txtPropietario);
		txtPropietario.setColumns(10);
		
		JLabel lblPropietario = new JLabel("Propietario:");
		lblPropietario.setBounds(521, 14, 73, 14);
		panel_1.add(lblPropietario);
		
		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setBounds(645, 42, 86, 20);
		panel_1.add(txtEstado);
		txtEstado.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(593, 45, 52, 14);
		panel_1.add(lblEstado);
		
		JLabel lblPrecioPorNoche = new JLabel("Precio por noche:");
		lblPrecioPorNoche.setBounds(10, 77, 101, 14);
		panel_1.add(lblPrecioPorNoche);
		
		txtPrecio = new JTextField();
		txtPrecio.setEditable(false);
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(114, 74, 86, 20);
		panel_1.add(txtPrecio);
		
		JButton btnVerFacilidades = new JButton("Ver Facilidades\r\n");
		btnVerFacilidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerFacilidades misFacilidades = new VerFacilidades(idSelPropiedad);
				misFacilidades.setModal(true);
				misFacilidades.setVisible(true);
			}
		});
		btnVerFacilidades.setBounds(593, 73, 138, 23);
		panel_1.add(btnVerFacilidades);
		
		txtPropiedad = new JTextField();
		txtPropiedad.setEditable(false);
		txtPropiedad.setColumns(10);
		txtPropiedad.setBounds(99, 11, 101, 20);
		panel_1.add(txtPropiedad);
		
		JLabel lblPropiedad = new JLabel("Propiedad:");
		lblPropiedad.setBounds(10, 14, 73, 14);
		panel_1.add(lblPropiedad);
		
		JButton btnVolver = new JButton("Volver\r\n");
		btnVolver.setBounds(22, 8, 89, 23);
		panel.add(btnVolver);
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReservar.setBounds(20, 161, 89, 23);
		panel.add(btnReservar);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panel_1_1.setBounds(10, 216, 741, 224);
		panel.add(panel_1_1);
		panel_1_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1_1.add(scrollPane, BorderLayout.CENTER);
		
		tblComentarios = new JTable();
		scrollPane.setViewportView(tblComentarios);
		String headers1[] = {"CLiente","Comentario"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(headers1);
		tblComentarios.setModel(model);
		
		JLabel lblComentario = new JLabel("Comentarios");
		lblComentario.setBounds(10, 191, 79, 14);
		panel.add(lblComentario);
		{
			setLocationRelativeTo(null);
		}
		llenarCampos(idSelPropiedad);
		loadTable(idSelPropiedad);
	}
	
	private void llenarCampos(String idPropiedad) {
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC sp_CargarPropiedadParaRent'"+ idPropiedad +"';";
			ResultSet rs =sqlStatement.executeQuery(consulta);
			if (rs.next()) {
				txtPropiedad.setText(idPropiedad);
				txtPrecio.setText(rs.getString("Precio"));
				String casa = rs.getString("Casa");
				String calle = rs.getString("Calle");
				String ciudad = rs.getString("Ciudad");
				txtDireccion.setText(String.format("%s %s, %s", calle, casa, ciudad));
				//String nombre = rs.getString("Nom1");
				//String apellido = rs.getString("Ape1");
				txtPropietario.setText(rs.getString("Nombre_Vendedor"));
				txtEstado.setText(rs.getString("Estado"));
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
			
		}
	}
	
	private void loadTable(String IdPropiedad) {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC ObtenerRetroalimentacion "+ IdPropiedad +" ;";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("Nombre del Cliente");
				row[1] = resultadoResultSet.getString("Comentario");			
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}
}
