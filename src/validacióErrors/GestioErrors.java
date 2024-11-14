package validacióErrors;

import java.sql.SQLException;

public class GestioErrors {
    public static void mostrarError(SQLException e) {
        System.err.println("\u001B[31m" + "Codi d'error: " + e.getErrorCode() + "\u001B[0m");
        System.err.println("\u001B[31m" + "SQL State: " + e.getSQLState() + "\u001B[0m");
        System.err.println("\u001B[31m" + "Missatge: " + e.getMessage() + "\u001B[0m");
    }
}
