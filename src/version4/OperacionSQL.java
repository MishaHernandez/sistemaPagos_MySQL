/*
 * Clase generica que invoca los procedimientos almacenados en mysql:
 * - Busqueda, Insercion, Edición, Eliminación.
 * - Se pasa como parametro el nombre de la tabla a operar: Buscar y Eliminar
 */

package version4;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 * @author Mijail J. Hernandez
 */
class OperacionSQL {
    Conector con;
    public OperacionSQL(){
        con = new Conector();
    }
    
    public int totalPagos(JTextField txtid) throws SQLException{
        String id;
        int num;
        id = txtid.getText();
        
        con.conectar();
        
        CallableStatement cst=con.conexion.prepareCall("{? = call func_totalPagos(?)}");
        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2, id);
        cst.executeUpdate();
        
        num=cst.getInt(1);
        
        con.cerrar();
        
        return num;
    }
    
    public void validarID(JTextField txtid, String tabla) throws SQLException{
        String id;
        int num;
        id = txtid.getText();
        
        con.conectar();
        
        CallableStatement cst=con.conexion.prepareCall("{? = call func_validar(?,?)}");
        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2, id);
        cst.setString(3, tabla);
        cst.executeUpdate();
        
        num=cst.getInt(1);
        
        if(num == 1){
            System.out.println("El codigo ya existe:"); //mensaje test
            JOptionPane.showMessageDialog(null, "Ya existe un registro con ese codigo"); //mensaje test
        }
        
        con.cerrar();
    }
    
    public void buscar(JTextField txtletra, DefaultTableModel modelo, String vtabla) throws SQLException{
        ResultSet rs;
        String id, nombreTabla = null;
        //conseguir el texto de la caja
        id = txtletra.getText();
        //se verifica la tabla a operar
        if("alumno".equals(vtabla)){
            nombreTabla = "Alumno";
        }
        if("tasa".equals(vtabla)){
            nombreTabla = "Tasa";
        }
        if("pago".equals(vtabla)){
            nombreTabla = "Pago";
        }
        if("detallepago".equals(vtabla)){
            nombreTabla = "DetalleP";
        }
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_buscar"+nombreTabla+" (?)}");
        // Parametro de entrada del procedimiento almacenado
        cst.setString(1, id);
        // Ejecuta el procedimiento almacenado
        cst.execute();
        //obtener resultado de la consulta
        rs = cst.getResultSet();
        //obtener metadata del resultado de la consulta
        ResultSetMetaData rsmdata = rs.getMetaData();
        //obtener nro de columnas de la tabla
        int nrocolumnas = rsmdata.getColumnCount();
        //array de datos que guarda los datos de cada columna
        Object[]datos  = new Object[nrocolumnas];
        //obtener el nro de filas
        int tamaño = modelo.getRowCount();
        //limpiar tabla
        for(int i = 0; i < tamaño ; i ++){
            modelo.removeRow(0);
        }
        //cargar registros encontrados
        while(rs.next()){
            for(int i=0; i<nrocolumnas; i++){
                datos[i] = rs.getObject(i+1);
            }
            modelo.addRow(datos);
        }
        
        con.cerrar();
    }
    
    public void insertarAlumno(JTextField txtid, JTextField txtnom, JTextField txtapell, JTextField txtdirec, JTextField txttelef, JTextField txtemail) throws SQLException{
        String id, nombre, apell, direc, email;
        int telef;
        //obtener texto del formulario llenado
        id = txtid.getText();
        nombre = txtnom.getText();
        apell = txtapell.getText();
        direc = txtdirec.getText();
        telef = Integer.parseInt(txttelef.getText());
        email = txtemail.getText();
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_insertarAlumno (?,?,?,?,?,?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, nombre);
        cst.setString(3, apell);
        cst.setString(4, direc);
        cst.setInt(5, telef);
        cst.setString(6, email);
        // Ejecuta el procedimiento almacenado
        cst.execute();
        
        System.out.println("Los datos se han registrado con éxito"); //mensaje test
        
        con.cerrar();
    }
    
    public void insertarTasa(JTextField txtid, JTextField txtdesc, JTextField txtmonto) throws SQLException{
        String id, desc, monto;
        //obtener texto del formulario llenado
        id = txtid.getText();
        desc = txtdesc.getText();
        monto = txtmonto.getText();
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_insertarTasa (?,?,?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, desc);
        cst.setString(3, monto);
        // Ejecuta el procedimiento almacenado
        cst.execute();
        
        System.out.println("Los datos se han registrado con éxito"); //mensaje test
        
        con.cerrar();
    }
    
    public void insertarPago(JTextField txtid, JTextField txtfecha, JTextField txtobserv, JComboBox txtidalumno) throws SQLException{
        String id, fecha, observ, idalumno;
        //obtener texto del formulario llenado
        id = txtid.getText();
        fecha = txtfecha.getText();
        observ = txtobserv.getText();
        idalumno = String.valueOf(txtidalumno.getSelectedItem());
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_insertarPago (?,?,?,?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, fecha);
        cst.setString(3, observ);
        cst.setString(4, idalumno);
        // Ejecuta el procedimiento almacenado
        cst.execute();
        
        System.out.println("Los datos se han registrado con éxito"); //mensaje test
        
        con.cerrar();
    }
    
    public void insertarDetalleP(JComboBox txtid, JComboBox txtidpago, JTextField txtmonto) throws SQLException{
        String id, idpago, monto;
        //obtener texto del formulario llenado
        id = String.valueOf(txtid.getSelectedItem());
        idpago = String.valueOf(txtidpago.getSelectedItem());
        monto = txtmonto.getText();
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_insertarDetalleP (?,?,?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, idpago);
        cst.setString(3, monto);
        // Ejecuta el procedimiento almacenado
        cst.execute();
        
        System.out.println("Los datos se han registrado con éxito"); //mensaje test
        
        con.cerrar();
    }
    
    public void editarAlumno(JTextField txtid, JTextField txtnom, JTextField txtapell, JTextField txtdirec, JTextField txttelef, JTextField txtemail) throws SQLException{
        String id, nombre, apell, direc, email;
        int telef;
        //obtener texto del formulario
        id = txtid.getText();
        nombre = txtnom.getText();
        apell = txtapell.getText();
        direc = txtdirec.getText();
        telef = Integer.parseInt(txttelef.getText());
        email = txtemail.getText();
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_actualizarAlumno (?, ?, ?, ?, ?, ?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, nombre);
        cst.setString(3, apell);
        cst.setString(4, direc);
        cst.setInt(5, telef);
        cst.setString(6, email);
        // Ejecuta el procedimiento almacenado
        cst.execute();
        
        System.out.println("Los datos se han actualizado con éxito"); //mensaje test
        
        con.cerrar();
        
    }
    
    public void editarTasa(JTextField txtid, JTextField txtdesc, JTextField txtmonto) throws SQLException{
        String id, desc, monto;
        //obtener texto del formulario
        id = txtid.getText();
        desc = txtdesc.getText();
        monto = txtmonto.getText();
        
        con.conectar();
            
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_actualizarTasa (?, ?, ?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, desc);
        cst.setString(3, monto);
        // Ejecuta el procedimiento almacenado
        cst.execute();
            
        System.out.println("Los datos se han actualizado con éxito"); //mensaje test
            
        con.cerrar();
        
    }
    
    public void editarPago(JTextField txtid, JTextField txtfecha, JTextField txtobserv, JComboBox txtidalumno) throws SQLException{
        String id, fecha, observ, idalumno;
        //obtener texto del formulario
        id = txtid.getText();
        fecha = txtfecha.getText();
        observ = txtobserv.getText();
        idalumno = String.valueOf(txtidalumno.getSelectedItem());
        
        con.conectar();
            
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_actualizarPago (?, ?, ?, ?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, fecha);
        cst.setString(3, observ);
        cst.setString(4, idalumno);
        // Ejecuta el procedimiento almacenado
        cst.execute();
            
        System.out.println("Los datos se han actualizado con éxito"); //mensaje test
            
        con.cerrar();
        
    }
    
    public void editarDetalleP(JComboBox txtid, JComboBox txtidpago, JTextField txtmonto) throws SQLException{
        String id, idpago, monto;
        //obtener texto del formulario
        id = String.valueOf(txtid.getSelectedItem());
        idpago = String.valueOf(txtidpago.getSelectedItem());
        monto = txtmonto.getText();
        
        con.conectar();
            
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_actualizarDetalleP (?, ?, ?)}");
        // Parametros del procedimiento almacenado
        cst.setString(1, id);
        cst.setString(2, idpago);
        cst.setString(3, monto);
        // Ejecuta el procedimiento almacenado
        cst.execute();
            
        System.out.println("Los datos se han actualizado con éxito"); //mensaje test
            
        con.cerrar();
        
    }
    
    public void eliminar(String [] datos, String nombreTabla) throws SQLException{
        String id = datos[0];
        String nomTabla = null;
        //se verifica la tabla a operar
        if("alumno".equals(nombreTabla)){
            nomTabla = "Alumno";
        }
        if("tasa".equals(nombreTabla)){
            nomTabla = "Tasa";
        }
        if("pago".equals(nombreTabla)){
            nomTabla = "Pago";
        }
        if("detallepago".equals(nombreTabla)){
            nomTabla = "DetalleP";
        }
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_eliminar"+nomTabla +" (?)}");
        // Parametro del procedimiento almacenado
        cst.setString(1, id);
        // Ejecuta el procedimiento almacenado
        cst.execute();
            
        System.out.println("Los datos se han se ha eliminado con éxito"); //mensaje test
            
        con.cerrar();
    }
    
    public void salir(){
        System.exit(0);
    }
}
