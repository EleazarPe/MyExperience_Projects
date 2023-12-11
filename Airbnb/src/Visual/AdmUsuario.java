package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.directory.InvalidSearchControlsException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Conexion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdmUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private Object row[];
	private String userSelect = null;
	private JButton btnMod;
	private JButton btnDel;
	private boolean usuarioSel = false;
	private String idUString = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AdmUsuario dialog = new AdmUsuario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AdmUsuario() {
		setTitle("Administrar Usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdmUsuario.class.getResource("/Image/repair.png")));
		setBounds(100, 100, 850, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						int row = 0;
						row = table.getSelectedRow();
						if(row > -1) {
							btnDel.setEnabled(true);
							btnMod.setEnabled(true);
							usuarioSel = true;
							int column = 0;
							int rowr = table.getSelectedRow();
							String value = table.getModel().getValueAt(rowr, column).toString();
							idUString = value;
							//medicoselect = (Medico) Clinica.getInstance().buscarUsuarioByCedula(table.getValueAt(row, 0).toString());

						}
					}
				});
				String headers[] = {"Id","Nombre","Correo","Telefono","Cedula","Nick","Password","Clase"};
				model = new DefaultTableModel();
				model.setColumnIdentifiers(headers);
				table.setModel(model);
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnDel = new JButton("Eliminar");
				btnDel.setEnabled(false);
				btnDel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//--------------------------------> aqui voy
						if(usuarioSel != false) {
							int option = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar el usuario: "+ idUString, "Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
							if(option == JOptionPane.YES_OPTION){
								delUser(idUString);
								loadTable();
							}
						}
					}
				});
				{
					btnMod = new JButton("Modificar");
					btnMod.setEnabled(false);
					buttonPane.add(btnMod);
				}
				buttonPane.add(btnDel);
			}
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		loadTable();
	}
	
	private void delUser(String idDel) {
		try {
			java.sql.Statement sqlStatement = Conexion.getConexion().createStatement();
			String consulta = "EXEC sp_eliminar_usuarioF '"+ idDel +"';";
			sqlStatement.executeQuery(consulta);
		} catch (SQLException ex) {
			if(ex.getErrorCode() == 0) {
				JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
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
			String consulta = "SELECT * FROM Usuario";
			ResultSet resultadoResultSet = sqlStatement.executeQuery(consulta);
			while(resultadoResultSet.next()) {
				row[0] = resultadoResultSet.getString("ID_User");
				row[1] = resultadoResultSet.getString("Nom1")+" " + resultadoResultSet.getString("Ape1");
				row[2] =resultadoResultSet.getString("Correo");
				row[3] = resultadoResultSet.getString("Telefono");
				row[4] = resultadoResultSet.getString("cedula");
				row[5] = resultadoResultSet.getString("user_nick");
				row[6] =resultadoResultSet.getString("user_pass");
				row[7] = resultadoResultSet.getString("Clase");
				model.addRow(row);
			}
			
		} catch (SQLException  ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}

	}

}
