package castlevania;

import javax.swing.JOptionPane;

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
