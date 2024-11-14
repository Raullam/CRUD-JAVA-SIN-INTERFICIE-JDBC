package dao;

import db.ConnexioBD;
import dto.Membre;
import dto.Pista;
import dto.Reserva;
import validacióErrors.GestioErrors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservaDAO {
    private static final ConnexioBD connexioBD = new ConnexioBD();
    static String greenText = "\u001B[32m";
    static String redText = "\u001B[31m";
    static String yellowText = "\u001B[33m";
    static String resetText = "\u001B[0m";
    static String orangeText = "\u001B[38;5;214m";  // Naranja aproximado usando 256 colores ANSI



    public void afegirReserva(int membreId, int pistaId, Date data, Time hora, int durada) {
        String sql = "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membreId);
            stmt.setInt(2, pistaId);
            stmt.setDate(3, data);
            stmt.setTime(4, hora);
            stmt.setInt(5, durada);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == 1452) {
                System.out.println(redText + "Error: No existeix una persona o pista amb aquest id." + resetText);
            } else {
                GestioErrors.mostrarError(e);
            }
        }
    }

    public void afegirUsuari(String nom, String adreca, String telefon, String email) {
        String sql = "INSERT INTO membre (nom, adreca, telefon, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = connexioBD.connectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, adreca != null ? adreca : null);
            pstmt.setString(3, telefon);
            pstmt.setString(4, email);

            pstmt.executeUpdate();
            System.out.println(greenText + "Membre afegit correctament!" + resetText);

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println(redText + "Error: El correu electrònic ja està registrat. Si us plau, utilitzeu un altre correu." + resetText);
            } else {
                GestioErrors.mostrarError(e);
            }
        }
    }

    public void afegirPista(String nom, String tipus) {
        String sql = "INSERT INTO Pista (nom, tipus) VALUES (?, ?)";

        try (Connection conn = connexioBD.connectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, tipus);

            pstmt.executeUpdate();
            System.out.println("Pista afegida correctament!");

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }

    public List<Reserva> obtenirReserves() {
        String sql = "SELECT * FROM Reserva";
        List<Reserva> reserves = new ArrayList<>();

        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setMembreId(rs.getInt("membre_id"));
                reserva.setPistaId(rs.getInt("pista_id"));
                reserva.setData(rs.getDate("data"));
                reserva.setHora(rs.getTime("hora"));
                reserva.setDurada(rs.getInt("durada"));
                reserva.setHora(rs.getTime("updated_at"));

                // Obtenemos las fechas como java.sql.Date desde la base de datos
                java.sql.Date createdSqlDate = rs.getDate("created_at");
                java.sql.Date updatedSqlDate = rs.getDate("updated_at");

                // Convertimos java.sql.Date a java.util.Date para el objeto Reserva
                Date created_ad = new Date(createdSqlDate.getTime());
                reserva.setCreated_at(created_ad);

                Date updated_ad = new Date(updatedSqlDate.getTime());
                reserva.setUpdated_at(updated_ad);

                reserves.add(reserva);
            }
        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
        return reserves;
    }

    public List<Membre> obtenirMembres() {
        String sql = "SELECT * FROM Membre";
        List<Membre> membres = new ArrayList<>();

        try (Connection connection = connexioBD.connectar();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Membre membre = new Membre();
                membre.setId(rs.getInt("id"));
                membre.setNom(rs.getString("nom"));
                membre.setAdreca(rs.getString("adreca"));
                membre.setTelefono(rs.getInt("telefon"));
                membre.setEmail(rs.getString("email"));
                membre.setCreated_at(rs.getDate("created_at"));
                membre.setUpdated_at(rs.getDate("updated_at"));
                membres.add(membre);
            }

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
        return membres;
    }

    public List<Pista> obtenirPistas() {
        String sql = "SELECT * FROM Pista";
        List<Pista> pistas = new ArrayList<>();

        try {
            Connection conn = connexioBD.connectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pista pista = new Pista();
                pista.setId(rs.getInt("id"));
                pista.setNom(rs.getString("nom"));
                pista.setTipus(rs.getString("tipus"));
                pista.setCreated_at(rs.getDate("created_at"));
                pista.setUpdated_at(rs.getDate("updated_at"));
                pistas.add(pista);
            }
        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
        return pistas;
    }


    // Actualitzar una reserva existent
    public void actualitzarReserva(Reserva reserva) {
        String sql = "UPDATE Reserva SET data = ?, hora = ?, durada = ? WHERE id = ?";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, reserva.getData());
            stmt.setTime(2, reserva.getHora());
            stmt.setInt(3, reserva.getDurada());
            stmt.setInt(4, reserva.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }

    // Actualitzar una reserva existent
    public void actualitzarMembre(Membre membre) {
        String sql = "UPDATE membre SET nom = ?, adreca = ?, telefon = ?, email = ? WHERE id = ?";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getAdreca());
            stmt.setInt(3, membre.getTelefono());
            stmt.setString(4, membre.getEmail());
            stmt.setInt(5, membre.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }

    // Actualitzar una reserva existent
    public void actualitzarPista(Pista pista) {
        String sql = "UPDATE pista SET nom = ?, tipus = ? WHERE id = ?";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pista.getNom());
            stmt.setString(2, pista.getTipus());
            stmt.setInt(3, pista.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }


    // Eliminar una reserva
    public void eliminarMembre(int id) {
        String sql = "DELETE FROM Membre WHERE id = ?";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(greenText + "Membre eliminat correctament." + resetText);
            } else {
                System.out.println(redText + "No s'ha trobat cap membre amb aquest ID." + resetText);
            }

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }

    // Eliminar una reserva
    public void eliminarReserva(int id) {
        String sql = "DELETE FROM Reserva WHERE id = ?";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(greenText + "Reserva eliminada correctament." + resetText);
            } else {
                System.out.println(redText + "No s'ha trobat cap membre amb aquest ID." + resetText);
            }

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }

    public void eliminarReservasPerIDdePista(int id) {
        String sql = "DELETE FROM Reserva WHERE pista_id = ?";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(greenText + "Reserva eliminada correctament." +resetText);
            } else {
                System.out.println(redText + "No s'ha trobat cap membre amb aquest ID." + resetText);
            }

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }

    public void eliminarReservaPerIdUsuari(int id) {
        String sql = "DELETE FROM Reserva WHERE membre_id = ?";
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(greenText + "Reserva eliminada correctament." + resetText);
            } else {
                System.out.println(greenText + "No s'ha trobat cap membre amb aquest ID." + resetText);
            }

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
    }


    public int eliminarPista(int id) {
        String sql = "DELETE FROM Pista WHERE id = ?";
        int numFilesEliminades = 0;
        try (Connection conn = connexioBD.connectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            numFilesEliminades = stmt.executeUpdate();

        } catch (SQLException e) {
            GestioErrors.mostrarError(e);
        }
        return numFilesEliminades;
    }

    public static Reserva obtenirReserva(int idReserva) {
        // Inicializa la reserva como null en caso de no encontrarla
        Reserva reserva = null;

        // Usamos 'try-with-resources' para manejar automáticamente los recursos
        String query = "SELECT * FROM reserva WHERE id = ?";

        try (Connection conn = connexioBD.connectar();  // Conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query);  // Preparar la consulta
        ) {
            stmt.setInt(1, idReserva);  // Establecer el parámetro de la consulta
            try (ResultSet rs = stmt.executeQuery()) {  // Ejecutar la consulta y obtener los resultados
                if (rs.next()) {
                    // Extraer los datos de la reserva desde el ResultSet
                    int id = rs.getInt("id");  // Obtén el ID de la reserva
                    int membreId = rs.getInt("membre_id");
                    int pistaId = rs.getInt("pista_id");
                    Date data = rs.getDate("data");
                    Time hora = rs.getTime("hora");
                    int durada = rs.getInt("durada");
                    // Obtenemos las fechas como java.sql.Date desde la base de datos
                    java.sql.Date createdSqlDate = rs.getDate("created_at");
                    java.sql.Date updatedSqlDate = rs.getDate("updated_at");

                    // Convertimos java.sql.Date a java.util.Date para el objeto Reserva
                    Date created_ad = new Date(createdSqlDate.getTime());
                    Date updated_ad = new Date(updatedSqlDate.getTime());


                    // Crear la reserva con los datos obtenidos
                    reserva = new Reserva(id, membreId, pistaId, data, hora, durada, created_ad, updated_ad);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar la reserva encontrada o null si no se encontró
        return reserva;
    }

    public static Membre obtenirUsuari(int idMembre) {
        // Inicializa la reserva como null en caso de no encontrarla
        Membre membre = null;

        // Usamos 'try-with-resources' para manejar automáticamente los recursos
        String query = "SELECT * FROM Membre WHERE id = ?";

        try (Connection conn = connexioBD.connectar();  // Conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query);  // Preparar la consulta
        ) {
            stmt.setInt(1, idMembre);  // Establecer el parámetro de la consulta
            try (ResultSet rs = stmt.executeQuery()) {  // Ejecutar la consulta y obtener los resultados
                if (rs.next()) {
                    // Extraer los datos de la reserva desde el ResultSet
                    int id = rs.getInt("id");  // Obtén el ID de la reserva
                    String nom = rs.getString("nom");
                    String adreca = rs.getString("adreca");
                    int telefon = rs.getInt("telefon");
                    String email = rs.getString("email");

                    // Obtenemos las fechas como java.sql.Date desde la base de datos
                    java.sql.Date createdSqlDate = rs.getDate("created_at");
                    java.sql.Date updatedSqlDate = rs.getDate("updated_at");

                    // Convertimos java.sql.Date a java.util.Date para el objeto Reserva
                    Date created_ad = new Date(createdSqlDate.getTime());
                    Date updated_ad = new Date(updatedSqlDate.getTime());


                    // Crear la reserva con los datos obtenidos
                    membre = new Membre(id, nom, adreca, telefon, email, created_ad, updated_ad);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar la reserva encontrada o null si no se encontró
        return membre;
    }

    public static Pista obtenirPista(int idPista) {
        // Inicializa la reserva como null en caso de no encontrarla
        Pista pista = null;

        // Usamos 'try-with-resources' para manejar automáticamente los recursos
        String query = "SELECT * FROM Pista WHERE id = ?";

        try (Connection conn = connexioBD.connectar();  // Conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query);  // Preparar la consulta
        ) {
            stmt.setInt(1, idPista);  // Establecer el parámetro de la consulta
            try (ResultSet rs = stmt.executeQuery()) {  // Ejecutar la consulta y obtener los resultados
                if (rs.next()) {
                    // Extraer los datos de la reserva desde el ResultSet
                    int id = rs.getInt("id");  // Obtén el ID de la reserva
                    String nom = rs.getString("nom");
                    String tipus = rs.getString("tipus");

                    // Obtenemos las fechas como java.sql.Date desde la base de datos
                    java.sql.Date createdSqlDate = rs.getDate("created_at");
                    java.sql.Date updatedSqlDate = rs.getDate("updated_at");

                    // Convertimos java.sql.Date a java.util.Date para el objeto Reserva
                    Date created_ad = new Date(createdSqlDate.getTime());
                    Date updated_ad = new Date(updatedSqlDate.getTime());


                    // Crear la reserva con los datos obtenidos
                    pista = new Pista(id, nom, tipus, created_ad, updated_ad);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar la reserva encontrada o null si no se encontró
        return pista;
    }

    public static void insertarMiembrosDesdeArchivo(String filePath) {
        String insertSQL = "INSERT INTO Membre (nom, adreca, telefon, email) VALUES (?, ?, ?, ?)";
        String countSQL = "SELECT COUNT(*) AS total FROM Membre";
        Set<String> emails = new HashSet<>(); // Conjunto para verificar duplicidad de emails

        try (Connection conn = connexioBD.connectar();
             PreparedStatement pstInsert = conn.prepareStatement(insertSQL);
             PreparedStatement pstCount = conn.prepareStatement(countSQL)) {

            // Iniciar transacción
            conn.setAutoCommit(false);
            System.out.println(orangeText + "Transacció iniciada..." + resetText);

            // Leer el número actual de registros en la tabla "Membre"
            contarRegistros(pstCount);

            // Leer y procesar el archivo
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                Savepoint savepoint = conn.setSavepoint();  // Guardar punto de guardado

                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(","); // Suponiendo que cada línea es "nom,adreca,telefon,email"
                    if (fields.length < 4) continue;

                    String nom = fields[0].trim();
                    String adreca = fields[1].trim();
                    String telefon = fields[2].trim();
                    String email = fields[3].trim();

                    // Verificar si el email ya existe en el conjunto (para evitar duplicados)
                    if (emails.contains(email)) {
                        System.out.println( redText + "Email duplicat: " + email + ". Desfent transacció." + resetText);
                        conn.rollback(savepoint);
                        return;
                    }
                    emails.add(email);  // Agregar el email al conjunto

                    // Preparar la inserción
                    pstInsert.setString(1, nom);
                    pstInsert.setString(2, adreca);
                    pstInsert.setString(3, telefon);
                    pstInsert.setString(4, email);

                    // Ejecutar la inserción
                    pstInsert.executeUpdate();
                }

                // Confirmar la transacción si todos los registros fueron insertados
                conn.commit();
                System.out.println( orangeText + "Transacció confirmada. Tots els membres han estat insertats." + resetText);

            } catch (IOException e) {
                System.out.println(redText + "Error al llegir l'arxiu: " + e.getMessage() + resetText);
                conn.rollback();
            }

            // Contar nuevamente para verificar el número de registros
            contarRegistros(pstCount);

        } catch (SQLException e) {
            System.out.println(redText + "Error en la transacció: " + e.getMessage() + resetText);
        }
    }

    private static void contarRegistros(PreparedStatement pstCount) {
        try (ResultSet rs = pstCount.executeQuery()) {
            if (rs.next()) {
                System.out.println( greenText + "Número total de membres: " + rs.getInt("total")+ resetText);
            }
        } catch (SQLException e) {
            System.out.println( redText + "Error al contar registres: " + e.getMessage() + resetText);
        }
    }


}
