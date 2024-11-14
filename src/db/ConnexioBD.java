package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexioBD {
    private final String url = "jdbc:mysql://localhost:3306/ClubTennisDB";
    private final String usuari = "root"; //root
    private final String contrasenya = "root"; //1234
    private Connection connexio;

    public Connection connectar() throws SQLException {
        if (connexio == null || connexio.isClosed()) {
            connexio = DriverManager.getConnection(url,usuari, contrasenya);
        }
        return connexio;
    }

    public void tancar() throws SQLException {
        if (connexio != null && !connexio.isClosed()) {
            connexio.close();
        }
    }
}
