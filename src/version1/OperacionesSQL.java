package version1;

import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Aqui van los metodos que operan directamente con la base de datos (MySQL)
 * 
 */

/**
 * @author Mijail J. Hernandez
 */
public class OperacionesSQL {
    Conector con;
    
    public OperacionesSQL(){
        con = new Conector();
    }
    
    public void insertar() throws SQLException{
        String id, nombre, apell, direc, email;
        int telef;
        
        System.out.println("Ingrese los datos del alumno..."); //mensaje test
        
        id = JOptionPane.showInputDialog(null, "ingrese ID: ");
        nombre = JOptionPane.showInputDialog(null, "ingrese Nombre: ");
        apell = JOptionPane.showInputDialog(null, "ingrese Apellido: ");
        direc = JOptionPane.showInputDialog(null, "ingrese Direccion: ");
        telef = Integer.parseInt(JOptionPane.showInputDialog(null, "ingrese Telefono: "));
        email = JOptionPane.showInputDialog(null, "ingrese Correo: ");
        
        con.conectar();
        
        //llamado al procedimiento
        CallableStatement cst = con.conexion.prepareCall("{call sp_insertarAlumno (?,?,?,?,?,?)}");
        // Parametro 1 del procedimiento almacenado
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
    
    public void editar(String dato, int n) throws SQLException{
        int telef;
        if(n==4){
            telef = Integer.parseInt(dato);
            con.conectar();
            
            //llamado al procedimiento
            CallableStatement cst = con.conexion.prepareCall("{call sp_actualizarAlumno (?, ?)}");
            // Parametro 1 del procedimiento almacenado
            cst.setInt(1, telef);
            //cst.setString(2, id);
            // Ejecuta el procedimiento almacenado
            cst.execute();
            
            System.out.println("Los datos se han actualizado con éxito"); //mensaje test
            
            con.cerrar();
        }
        else{
            con.conectar();
            
            //llamado al procedimiento
            CallableStatement cst = con.conexion.prepareCall("{call sp_actualizarAlumno2 (?)}");
            // Parametro 1 del procedimiento almacenado
            cst.setString(1, dato);
            // Ejecuta el procedimiento almacenado
            cst.execute();
            
            System.out.println("Los datos se han actualizado con éxito"); //mensaje test
            
            con.cerrar();
        }
    }
    
    public void menuEditar(){
        System.out.println("Editando los datos del alumno..."); //mensaje test
        
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, "Elegir dato a editar:\n"
            + "1: Nombre\n"
            + "2: Apellido\n"
            + "3: Direccion\n"
            + "4: Telefono\n"
            + "5: Correo\n"
            + "6: Salir\n"));
            
        opciones(opc); //llamo al metodo opciones y le paso un numero
    }
    
    public void opciones(int opc){
        OperacionesSQL op = new OperacionesSQL(); //instancio la clase operaciones
        int n;
        try {
            switch(opc){
                case 1:
                    String nombre = JOptionPane.showInputDialog(null, "Ingrese Nuevo Mombre");
                    n=1;
                    editar(nombre, n);
                    break;
                    
                case 2:
                    String apell = JOptionPane.showInputDialog(null, "Ingrese Nuevo Apellido: ");
                    n=2;
                    editar(apell, n);
                    break;
                    
                case 3:
                    String direc = JOptionPane.showInputDialog(null, "Ingrese Nueva Direccion: ");
                    n=3;
                    editar(direc, n);
                    break;
                    
                case 4:
                    String telef = JOptionPane.showInputDialog(null, "Ingrese Nuevo Telefono: ");
                    n=4;
                    editar(telef, n);
                    break;
                    
                case 5:
                    String email = JOptionPane.showInputDialog(null, "Ingrese Nuevo Correo: ");
                    n=5;
                    editar(email, n);
                    break;
                    
                case 6:
                    System.exit(0); //salir el programa
            }
        }catch (Exception e) {
            System.out.println("Elegir una opción"+e);
        }
    }
    
}