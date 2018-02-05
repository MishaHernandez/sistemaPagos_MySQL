/*
 * Ventana generica (Tabla) que muestra y elimina los registros de las tablas:
 * - alumno, tasa, pago y detallepago.
 */

package version3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
/**
 * @author Mijail J. Hernandez
 */
public class VentanaDatos extends JDialog implements ActionListener, KeyListener{
    JLabel texto, lblnombreTabla;
    JTextField caja;
    JTable tabla;
    JScrollPane scroll;
    DefaultTableModel modelo;
    JButton btn1, btn2, btn3, btn4, btn5;
    
    public VentanaDatos(String nombreTabla) throws SQLException{
        configurarVentana(nombreTabla);
        configurarComponentes(nombreTabla);
    }
    
    private void configurarVentana(String nombreTabla){
        this.setTitle("Registros de "+nombreTabla);
        this.setSize(650, 400);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private void configurarComponentes(String nombreTabla) throws SQLException{
        crearComponentes(nombreTabla);
        //posicion
        texto.setBounds(100, 20, 150, 25);
        caja.setBounds(250, 20, 150, 25);
        scroll.setBounds(25, 70, 590, 250);
        btn1.setBounds(25, 335, 100, 25);
        btn2.setBounds(135, 335, 100, 25);
        btn3.setBounds(245, 335, 100, 25);
        btn4.setBounds(355, 335, 100, 25);
        btn5.setBounds(515, 335, 100, 25);
        //diseño de tabla
        tabla.setModel(modelo);
        //añadir a ventana
        this.add(texto);
        this.add(caja);
        this.add(scroll);
        this.add(btn1);
        this.add(btn2);
        this.add(btn3);
        this.add(btn4);
        this.add(btn5);
        //añadir accion
        caja.addKeyListener(this);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
    }
    
    private void crearComponentes(String nombreTabla) throws SQLException{
        texto = new JLabel("Buscar por Codigo: ");
        lblnombreTabla = new JLabel(nombreTabla); //control auxiliar
        caja = new JTextField();
        tabla = new JTable();
        scroll = new JScrollPane(tabla);
        modelo = new DefaultTableModel();
        //carga los datos de la tabla en el jtable
        getDatos(nombreTabla);
        
        btn1 = new JButton("Actualizar");
        btn2 = new JButton("Insertar");
        btn3 = new JButton("Editar");
        btn4 = new JButton("Eliminar");
        btn5 = new JButton("Salir");
    }
    
    public void getDatos(String nombreTabla) throws SQLException{
        String sql;
        ResultSet rs;
        PreparedStatement pst;
        
        Conector con = new Conector();
        con.conectar();
        
        try{
            sql = "select * from "+nombreTabla;
            pst = con.conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            ResultSetMetaData rsmdata = rs.getMetaData();
            //conseguimos el numero de columnas de la tabla
            int nrocolumnas = rsmdata.getColumnCount();
            //almacenan los nombres de campo y los registros
            Object[]etiquetas = new Object[nrocolumnas];
            Object[]datos  = new Object[nrocolumnas];
            //se asignan los nombres de las columnas
            for(int i=0; i<nrocolumnas; i++){
                etiquetas[i] = rsmdata.getColumnLabel(i+1);
            }
            //cargo los datos de la tabla en el jtable
            modelo.setColumnIdentifiers(etiquetas);
            //se asignan los registros
            while(rs.next()){
                for(int i=0; i<nrocolumnas; i++){
                    datos[i] = rs.getObject(i+1);
                }
                //añadimos los nombres de campo en el jtable
                modelo.addRow(datos);
            }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        con.cerrar();
    }
    
    public String [] getSeleccion(){
        String nombTabla = lblnombreTabla.getText();
        int n = 0;
        //nro de campos de la tabla solicitada
        if("alumno".equals(nombTabla)){
            n=6;
        }
        if("tasa".equals(nombTabla)){
            n=3;
        }
        if("pago".equals(nombTabla)){
            n=4;
        }
        if("detallepago".equals(nombTabla)){
            n=3;
        }
        //asignar numero de campos
        String datos [] = new String [n];
        //obtener fila seleccionada
        int fila = tabla.getSelectedRow();
        //recorrer los campos de la fila seleccionada
        if(fila != -1){
            for(int i=0;i<n;i++){
                //almacenar los campos que contiene fila
                datos [i] = tabla.getValueAt(fila, i).toString();
            }
        }else{
            System.out.println("No ha seleccionado datos");
            datos = null;
        }
        return datos;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        OperacionSQL opsql=new OperacionSQL();
        Aplicacion menu=new Aplicacion();
        String nombTabla = lblnombreTabla.getText(); //indica la tabla a insertar/editar
        //ACTUALIZAR
        if(e.getSource()==btn1){
            try {
                //obtener el nro de filas
                int tamaño = modelo.getRowCount();
                //limpiar tabla
                for(int i = 0; i < tamaño ; i ++){
                    modelo.removeRow(0);
                }
                //cargar datos actuales de la tabla
                getDatos(nombTabla);
                
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            
        }
        //INSERTAR
        if(e.getSource()==btn2){
            String datos [] = null;
            VentanaOperar v1=new VentanaOperar("Insertar", nombTabla, datos);
            v1.setVisible(true);
        }
        //EDITAR
        if(e.getSource()==btn3){
            String datos [];
            datos = getSeleccion();
            if(datos != null){
                VentanaOperar v2=new VentanaOperar("Editar", nombTabla, datos);
                v2.setVisible(true);
            }
        }
        //ELIMINAR
        if(e.getSource()==btn4){
            String datos [];
            datos = getSeleccion();
            if(datos != null){
                try {
                    if("alumno".equals(nombTabla)){
                        opsql.eliminar(datos, nombTabla);
                    }
                    if("tasa".equals(nombTabla)){
                        opsql.eliminar(datos, nombTabla);
                    }
                    if("pago".equals(nombTabla)){
                        opsql.eliminar(datos, nombTabla);
                    }
                    if("detallepago".equals(nombTabla)){
                        opsql.eliminar(datos, nombTabla);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        //SALIR
        if(e.getSource()==btn5){
            dispose();
            menu.menuPrincipal();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        //cada vez que se presiona cualquier tecla
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        //cada vez que se presiona una tecla especifica
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        //cada vez que se suelta una tecla presionada
        OperacionSQL opsql=new OperacionSQL();
        String nombTabla = lblnombreTabla.getText(); //indica la tabla a buscar
        try {
            opsql.buscar(caja, modelo, nombTabla);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
}