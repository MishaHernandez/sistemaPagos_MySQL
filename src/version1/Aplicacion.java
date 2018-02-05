package version1;

import javax.swing.JOptionPane;

public class Aplicacion{
    
    public static void main(String[] args) {
        Aplicacion ap=new Aplicacion();
        ap.menuPrincipal();
    }
    
    public void menuPrincipal(){
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, "MENU\n"
            + "1: Insertar Registro\n"
            + "2: Editar Registro\n"
            + "3: Eliminar Registro\n"
            + "3: Buscar Registro\n"
            + "4: Salir\n"));
        
        opciones(opc); //llamo al metodo opciones y le paso un numero
    }
    
    public void opciones(int opc){ //llama a los metodos principales
        OperacionesSQL op = new OperacionesSQL(); //instancio la clase operaciones
        
        try {
            switch(opc){
                case 1:
                    op.insertar();
                    break;
                    
                case 2:
                    op.menuEditar();
                    break;
                    
                case 3:
                    //op.eliminar();
                    break;
                
                case 4:
                    //op.buscar();
                    break;
                    
                case 5:
                    System.exit(0); //salir el programa
            }
        }catch (Exception e) {
            System.out.println("Elegir una opción"+e);
        }
    }
    
}