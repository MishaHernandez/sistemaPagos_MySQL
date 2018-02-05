/*
 * Ventana generica (Formulario) que inserta o edita los registros de las tablas:
 * - alumno, tasa, pago y detallepago.
 */

package version2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 * @author Mijail J. Hernandez
 */
public final class VentanaOperar extends JDialog implements ActionListener{
    JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
    JTextField txt1, txt2, txt3, txt4, txt5, txt6, txtNombreTabla;
    JButton btn1, btn2;
    
    public VentanaOperar(String operacion, String nombTabla, String [] datos){
        //se le pasa los parametros como titulo para la ventana
        configurarVentana(operacion, nombTabla);
        //se le pasa un array de datos para mostrar en cajas de texto (Editar)
        configurarComponentes(operacion, nombTabla, datos);
    }
    
    public void configurarVentana(String operacion, String nombTabla){
        this.setTitle(operacion+" "+nombTabla);
        this.setSize(350, 400);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    public void configurarComponentes(String operacion, String nombTabla, String [] datos){
        //creacion
        if("alumno".equals(nombTabla)){
            lbl1 = new JLabel("Codigo: ");
            lbl2 = new JLabel("Nombre");
            lbl3 = new JLabel("Apellido");
            lbl4 = new JLabel("Direccion");
            lbl5 = new JLabel("Telefono");
            lbl6 = new JLabel("Correo");
            txt1 = new JTextField();
            txt2 = new JTextField();
            txt3 = new JTextField();
            txt4 = new JTextField();
            txt5 = new JTextField();
            txt6 = new JTextField();
            //contenido de las cajas
            if("Editar".equals(operacion)){
                txt1.setText(datos[0]);
                txt2.setText(datos[1]);
                txt3.setText(datos[2]);
                txt4.setText(datos[3]);
                txt5.setText(datos[4]);
                txt6.setText(datos[5]);
            }
            //posicion
            lbl1.setBounds(40, 20, 100, 25);
            lbl2.setBounds(40, 60, 100, 25);
            lbl3.setBounds(40, 100, 100, 25);
            lbl4.setBounds(40, 140, 100, 25);
            lbl5.setBounds(40, 180, 100, 25);
            lbl6.setBounds(40, 220, 100, 25);
            txt1.setBounds(150, 20, 100, 25);
            txt2.setBounds(150, 60, 100, 25);
            txt3.setBounds(150, 100, 100, 25);
            txt4.setBounds(150, 140, 100, 25);
            txt5.setBounds(150, 180, 100, 25);
            txt6.setBounds(150, 220, 100, 25);
            //añadir a la ventana
            this.add(lbl1);
            this.add(lbl2);
            this.add(lbl3);
            this.add(lbl4);
            this.add(lbl5);
            this.add(lbl6);
            this.add(txt1);
            this.add(txt2);
            this.add(txt3);
            this.add(txt4);
            this.add(txt5);
            this.add(txt6);
        }
        if("tasa".equals(nombTabla)){
            lbl1 = new JLabel("Codigo: ");
            lbl2 = new JLabel("Descripcion: ");
            lbl3 = new JLabel("Monto: ");
            txt1 = new JTextField();
            txt2 = new JTextField();
            txt3 = new JTextField();
            //contenido de las cajas
            if("Editar".equals(operacion)){
                txt1.setText(datos[0]);
                txt2.setText(datos[1]);
                txt3.setText(datos[2]);
            }
            //posicion
            lbl1.setBounds(40, 20, 100, 25);
            lbl2.setBounds(40, 60, 100, 25);
            lbl3.setBounds(40, 100, 100, 25);
            txt1.setBounds(150, 20, 100, 25);
            txt2.setBounds(150, 60, 100, 25);
            txt3.setBounds(150, 100, 100, 25);
            //añadir a la ventana
            this.add(lbl1);
            this.add(lbl2);
            this.add(lbl3);
            this.add(txt1);
            this.add(txt2);
            this.add(txt3);
        }
        if("pago".equals(nombTabla)){
            lbl1 = new JLabel("Codigo: ");
            lbl2 = new JLabel("Fecha: ");
            lbl3 = new JLabel("Observacion: ");
            lbl4 = new JLabel("Codigo Alumno: ");
            txt1 = new JTextField();
            txt2 = new JTextField();
            txt3 = new JTextField();
            txt4 = new JTextField();
            //contenido de las cajas
            if("Editar".equals(operacion)){
                txt1.setText(datos[0]);
                txt2.setText(datos[1]);
                txt3.setText(datos[2]);
                txt4.setText(datos[3]);
            }else{
                txt1.setText(String.valueOf(getIDPago()));
                txt2.setText(getFecha());
            }
            //posicion
            lbl1.setBounds(40, 20, 100, 25);
            lbl2.setBounds(40, 60, 100, 25);
            lbl3.setBounds(40, 100, 100, 25);
            lbl4.setBounds(40, 140, 100, 25);
            txt1.setBounds(150, 20, 100, 25);
            txt2.setBounds(150, 60, 100, 25);
            txt3.setBounds(150, 100, 100, 25);
            txt4.setBounds(150, 140, 100, 25);
            //añadir a la ventana
            this.add(lbl1);
            this.add(lbl2);
            this.add(lbl3);
            this.add(lbl4);
            this.add(txt1);
            this.add(txt2);
            this.add(txt3);
            this.add(txt4);
        }
        if("detallepago".equals(nombTabla)){
            lbl1 = new JLabel("Codigo de Tasa:");
            lbl2 = new JLabel("Codigo Pago:");
            lbl3 = new JLabel("Monto:");
            txt1 = new JTextField();
            txt2 = new JTextField();
            txt3 = new JTextField();
            //contenido de las cajas
            if("Editar".equals(operacion)){
                txt1.setText(datos[0]);
                txt2.setText(datos[1]);
                txt3.setText(datos[2]);
            }
            //posicion
            lbl1.setBounds(40, 20, 100, 25);
            lbl2.setBounds(40, 60, 100, 25);
            lbl3.setBounds(40, 100, 100, 25);
            txt1.setBounds(150, 20, 100, 25);
            txt2.setBounds(150, 60, 100, 25);
            txt3.setBounds(150, 100, 100, 25);
            
            //añadir a la ventana
            this.add(lbl1);
            this.add(lbl2);
            this.add(lbl3);
            this.add(txt1);
            this.add(txt2);
            this.add(txt3);
        }
        //controles genericos
        txtNombreTabla = new JTextField(nombTabla); //control auxiliar
        btn1 = new JButton(operacion);
        btn2 = new JButton("Salir");
        btn1.setBounds(50, 300, 100, 25);
        btn2.setBounds(200, 300, 100, 25);
        this.add(btn1);
        this.add(btn2);
        //añadir evento
        btn1.addActionListener(this);
        btn2.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //INSERTAR
        String nombreTabla = txtNombreTabla.getText(); //indica la tabla a insertar/editar
        if(e.getSource()==btn1){
            OperacionSQL opsql=new OperacionSQL();
            if(btn1.getText().equals("Insertar")){
                try {
                    if("alumno".equals(nombreTabla)){
                        opsql.insertarAlumno(txt1, txt2, txt3, txt4, txt5, txt6);
                    }
                    if("tasa".equals(nombreTabla)){
                        opsql.insertarTasa(txt1, txt2, txt3);
                    }
                    if("pago".equals(nombreTabla)){
                        opsql.insertarPago(txt1, txt2, txt3, txt4);
                    }
                    if("detallepago".equals(nombreTabla)){
                        opsql.insertarDetalleP(txt1, txt2, txt3);
                    }
                    
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            else{
                try {
                    if("alumno".equals(nombreTabla)){
                        opsql.editarAlumno(txt1, txt2, txt3, txt4, txt5, txt6);
                    }
                    if("tasa".equals(nombreTabla)){
                        opsql.editarTasa(txt1, txt2, txt3);
                    }
                    if("pago".equals(nombreTabla)){
                        opsql.editarPago(txt1, txt2, txt3, txt4);
                    }
                    if("detallepago".equals(nombreTabla)){
                        opsql.editarDetalleP(txt1, txt2, txt3);
                    }
                    
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        //SALIR
        if(e.getSource()==btn2){
            dispose();
        }
    }
    //conseguir codigo de pago generado aleatoriamente
    public int getIDPago(){
        int clave = (int) (Math.random() * 2000 + 1000); //numero aleatorio de 4 cifras    
        return clave;
    }
    //conseguir fecha actual del sistema
    public String getFecha(){
        Calendar fecha = new GregorianCalendar();
        
        String formato;
        
        int año = fecha.get(Calendar.YEAR); //año
        int mes = fecha.get(Calendar.MONTH); //mes
        int dia = fecha.get(Calendar.DAY_OF_MONTH); //dia
        //formato DD/MM/AAAA
        formato = año + "-" + (mes+1) + "-" + dia;
        
        return formato;
    }
    
}
