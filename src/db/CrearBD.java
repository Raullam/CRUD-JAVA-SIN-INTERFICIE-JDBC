package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearBD {
    private static final ConnexioBD connexioBD = new ConnexioBD();


    public static void crearDB() {
        String url = "jdbc:mysql://localhost:3306";
        String usuari = "root";
        String contrasenya = "root";
        String greenText = "\u001B[32m";
        String resetText = "\u001B[0m";

        try (//Connection connexio = DriverManager.getConnection(url, usuari, contrasenya);
             Connection connexio = connexioBD.connectar();
             Statement stmt = connexio.createStatement()) {

            // Eliminar y crear la base de datos
            stmt.executeUpdate("DROP DATABASE IF EXISTS ClubTennisDB");
            stmt.executeUpdate("CREATE DATABASE ClubTennisDB");
            System.out.println(greenText + "Base de datos ClubTennisDB creada o ya existe." + resetText);

            // Seleccionar la base de datos para crear tablas
            stmt.executeUpdate("USE ClubTennisDB");

            // Crear tabla Membre
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Membre (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nom VARCHAR(50) NOT NULL,
                    adreca VARCHAR(100),
                    telefon VARCHAR(15),
                    email VARCHAR(50) NOT NULL UNIQUE,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                )""");

            // Crear tabla Pista
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Pista (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nom VARCHAR(20) NOT NULL,
                    tipus VARCHAR(20) NOT NULL,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                )""");

            // Crear tabla Reserva sin columna pista_id
//            stmt.executeUpdate("""
//                CREATE TABLE IF NOT EXISTS Reserva (
//                    id INT AUTO_INCREMENT PRIMARY KEY,
//                    membre_id INT NOT NULL,
//                    data DATE NOT NULL,
//                    hora TIME NOT NULL,
//                    durada INT NOT NULL,
//                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
//                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//                    FOREIGN KEY (membre_id) REFERENCES Membre(id) ON DELETE CASCADE
//                )""");

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Reserva (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     membre_id INT NOT NULL,
                     pista_id INT NOT NULL,
                     data DATE NOT NULL,
                     hora TIME NOT NULL,
                     durada INT NOT NULL,
                     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                     updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                     FOREIGN KEY (membre_id) REFERENCES Membre(id) ON DELETE CASCADE,
                     FOREIGN KEY (pista_id) REFERENCES Pista(id) ON DELETE CASCADE
                 )""");


            // Crear tabla de unión ReservaPista para la relación muchos a muchos
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS ReservaPista (
                    reserva_id INT NOT NULL,
                    pista_id INT NOT NULL,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    PRIMARY KEY (reserva_id, pista_id),
                    FOREIGN KEY (reserva_id) REFERENCES Reserva(id) ON DELETE CASCADE,
                    FOREIGN KEY (pista_id) REFERENCES Pista(id) ON DELETE CASCADE
                )""");

            System.out.println(greenText + "Tablas creadas exitosamente." + resetText);

            String[] insertsMembre = {
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Alan Adrian', 'Carrer de les Flors, 12', '654321987', 'alan.adrian@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Magí Aristondo Alzina', 'Passeig de Gràcia, 45', '687123456', 'magi.aristondo@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Catalina Blasco Quetglas', 'Avinguda de Catalunya, 8', '698741236', 'catalina.blasco@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Gabriel Capó Bestard', 'Carrer Major, 15', '677849321', 'gabriel.capo@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Cristòfol Comas Llompart', 'Plaça de l’Ajuntament, 20', '655321984', 'cristofol.comas@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Pau Debionne Ferrer', 'Carrer Nou, 4', '665987432', 'pau.debionne@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Fernando Dorrego Sanz', 'Carrer del Mar, 22', '678943217', 'fernando.dorrego@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Jose David Guevara Rodriguez', 'Passeig de Sant Joan, 11', '688321765', 'jose.guevara@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Fortune Chukwuebuka Ihedioha', 'Carrer Gran, 18', '676543219', 'fortune.ihedioha@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Raul Lama Martorell', 'Avinguda de la Pau, 34', '689432156', 'raul.lama@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Ivan Martin Abbassi', 'Carrer de l’Estació, 10', '665321478', 'ivan.martin@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Alejandro Javier Mendoza Seguí', 'Plaça Catalunya, 5', '677891234', 'alejandro.mendoza@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('José Ramón Muñoz Colomar', 'Carrer del Parc, 13', '666987543', 'jose.munoz@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Facundo Nastasi Cantero', 'Rambla del Centre, 7', '688765432', 'facundo.nastasi@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Sergio Puertollano Racionero', 'Carrer de l’Esperança, 9', '654789123', 'sergio.puertollano@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Alberto Redondo Vicente', 'Passeig de l’Amistat, 23', '678541236', 'alberto.redondo@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('José Antonio Reyes Solera', 'Carrer dels Pins, 2', '687654312', 'jose.reyes@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Maria Elena Rivilla Calvo', 'Carrer de la Llibertat, 16', '666321879', 'maria.rivilla@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Ángel Rubio Oliver', 'Avinguda del Nord, 12', '687321456', 'angel.rubio@example.com')",
                    "INSERT INTO Membre (nom, adreca, telefon, email) VALUES ('Miquel Sansó Torres', 'Plaça Major, 6', '677543218', 'miquel.sanso@example.com')"
            };
            for (String insert : insertsMembre) {
                stmt.executeUpdate(insert);
            }

            String[] insertsPista = {
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 1', 'Terra batuda')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 2', 'Terra batuda')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 3', 'Sintètica')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 4', 'Sintètica')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 5', 'Gespa')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 6', 'Gespa')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 7', 'Terra batuda')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 8', 'Sintètica')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 9', 'Gespa')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 10', 'Terra batuda')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 11', 'Sintètica')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 12', 'Gespa')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 13', 'Terra batuda')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 14', 'Sintètica')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 15', 'Gespa')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 16', 'Terra batuda')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 17', 'Sintètica')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 18', 'Gespa')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 19', 'Terra batuda')",
                    "INSERT INTO Pista (nom, tipus) VALUES ('Pista 20', 'Sintètica')"
            };
            for (String insert : insertsPista) {
                stmt.executeUpdate(insert);
            }

            String[] insertsReserva = {
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (1, 1, '2024-11-01', '10:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (2, 2, '2024-11-01', '11:00:00', 90)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (3, 3, '2024-11-02', '12:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (4, 4, '2024-11-02', '14:00:00', 120)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (5, 5, '2024-11-03', '10:30:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (6, 6, '2024-11-03', '16:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (7, 7, '2024-11-04', '18:00:00', 90)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (8, 8, '2024-11-04', '09:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (9, 9, '2024-11-05', '08:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (10, 10, '2024-11-05', '11:30:00', 90)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (11, 11, '2024-11-06', '13:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (12, 12, '2024-11-06', '15:00:00', 120)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (13, 13, '2024-11-07', '17:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (14, 14, '2024-11-07', '10:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (15, 15, '2024-11-08', '14:00:00', 120)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (16, 16, '2024-11-08', '16:00:00', 90)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (17, 17, '2024-11-09', '18:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (18, 18, '2024-11-09', '10:00:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (19, 19, '2024-11-10', '11:30:00', 60)",
                    "INSERT INTO Reserva (membre_id, pista_id, data, hora, durada) VALUES (20, 20, '2024-11-10', '13:00:00', 90)"
            };
            for (String insert : insertsReserva) {
                stmt.executeUpdate(insert);
            }

            System.out.println(greenText + "Datos insertados exitosamente."+ resetText);


        } catch (SQLException e) {
            System.out.println("\u001B[31m" + "Error al crear la base de datos o las tablas: " + e.getMessage() + "\u001B[0m");
        }
    }


}

