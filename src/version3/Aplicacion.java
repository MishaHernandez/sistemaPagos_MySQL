/*
 * Ventana generica (Menú) que invoca el matenimiento de las tablas:
 * - alumno, tasa, pago y detallepago.
 */

package version3;

import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 * @author Mijail J. Hernandez
 */
public class Aplicacion{
    
    public static void main(String[] args) {
        Aplicacion ap=new Aplicacion();
        System.out.println("Historial de Eventos:");
        System.out.println("=====================");
        ap.menuPrincipal();
    }
    
    public void menuPrincipal(){
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, "Elija una Tabla para operar:\n"
            + "========================\n"
            + " (1) Alumno\n"
            + " (2) Tasa\n"
            + " (3) Pagos\n"
            + " (4) Detalle de pagos\n"
            + " (5) Salir\n"
            + "========================\n"));
        
        opciones(opc); //llamo al metodo opciones y le paso un numero
    }
    
    private void opciones(int opc){ //llama a la clase que muestra los registros
        String nombreTabla;
        //se le pasa como parametro el nombre de la tabla seleccionada
        try {
            switch(opc){
                case 1:
                    nombreTabla = "alumno";
                    System.out.println("Accediendo a los registros de Alumnos");
                    VentanaDatos alumno = new VentanaDatos(nombreTabla);
                    alumno.setVisible(true);
                    break;
                    
                case 2:
                    nombreTabla = "tasa";
                    System.out.println("Accediendo a los registros de Tasa");
                    VentanaDatos tasa = new VentanaDatos(nombreTabla);
                    tasa.setVisible(true);
                    break;
                    
                case 3:
                    nombreTabla = "pago";
                    System.out.println("Accediendo a los registros de Pagos");
                    VentanaDatos pagos = new VentanaDatos(nombreTabla);
                    pagos.setVisible(true);
                    break;
                    
                case 4:
                    nombreTabla = "detallepago";
                    System.out.println("Accediendo a los registros de Detalles de pago");
                    VentanaDatos detallep = new VentanaDatos(nombreTabla);
                    detallep.setVisible(true);
                    break;
                    
                case 5:
                    System.exit(0); //salir el programa
            }
        }catch (SQLException e) {
            System.out.println("Elegir una opción"+e);
        }
    }
    
}