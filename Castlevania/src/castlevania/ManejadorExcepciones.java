package castlevania;

import javax.swing.JOptionPane;

//Swing usa esta clase automáticamente (por la propiedad "sun.awt.exception.handler"
//que se configura en Main) para atrapar cualquier error que truene mientras la
//ventana está funcionando, en vez de que el programa se cierre de golpe.
public class ManejadorExcepciones {

    public void handle(Throwable error) {
        error.printStackTrace();

        JOptionPane.showMessageDialog(
                null,
                "Ocurrió un error inesperado:\n" + error.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
