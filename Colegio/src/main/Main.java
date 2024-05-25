package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/colegio", "root", "");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from alumnos");

        System.out.println("----------- TABLA DE ALUMNOS ANTES DE ACTUALIZARSE -----------");
        HashMap<Integer, Integer> notas = new HashMap<Integer, Integer>();
        int id, nota = 0, incremento;
        String nombre = "", apellido = "";

        while (rs.next()) {
            id = rs.getInt(1);
            nombre = rs.getString(2);
            apellido = rs.getString(3);
            nota = rs.getInt(4);
            System.out.println("Id: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Nota: " + nota);
            notas.put(id, nota);
        }

        PreparedStatement update = con.prepareStatement("update alumnos set Nota = ? where id = ?");
        PreparedStatement delete = con.prepareStatement("delete from alumnos where id=?");
        PreparedStatement insert = con.prepareStatement("insert into alumnos values (?,?,?,?) ");

        System.out.println("\n\n-------- Cambios --------");
        rs = st.executeQuery("select * from cambios");
        String operacion;
        char respuesta;
        boolean respuestaCorrecta;
        Scanner sc = new Scanner(System.in);
        while (rs.next()) {
            operacion = rs.getString(2);
            id = rs.getInt(3);
            incremento = rs.getInt(4);
            switch (operacion) {
                case "BJ":
                    if (notas.containsKey(id)) {
                        delete.setInt(1, id);
                        delete.execute();
                        System.err.println("Se ha borrado correctamente el alumno con id: " + id);
                    } else {
                        insertarAlumno(id, nombre, apellido, nota, sc, insert, notas);
                    }
                    break;
                case "MD":
                    if (notas.containsKey(id)) {
                        update.setInt(1, notas.get(id) + incremento);
                        update.setInt(2, id);
                        update.execute();
                        System.out.println("Se ha modificado correctamente al alumno con id: " + id);
                    } else {
                        insertarAlumno(id, nombre, apellido, nota, sc, insert, notas);
                    }
                    break;
            }
        }
        rs = st.executeQuery("select * from alumnos");

        System.out.println("\n\n----------- TABLA DE ALUMNOS DESPUÉS DE ACTUALIZARSE -----------");
        while (rs.next()) {
            id = rs.getInt(1);
            nombre = rs.getString(2);
            apellido = rs.getString(3);
            nota = rs.getInt(4);
            System.out.println("Id: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Nota: " + nota);
            notas.put(id, nota);
        }
    }

    private static void insertarAlumno(int id, String nombre, String apellido, int nota, Scanner sc, PreparedStatement insert, HashMap<Integer, Integer> notas) throws SQLException {
        char respuesta;
        boolean respuestaCorrecta;
        System.err.println("\nEl id introducido (" + id+ ") no existe en la base de datos");
        System.out.println("¿Quiere introducir un alumno con ese id? (s-n) ");
        do {
            respuesta = sc.nextLine().trim().toLowerCase().charAt(0);
            if (respuesta != 's' && respuesta != 'n') {
                System.err.println("Respuesta incorrecta (escriba s o n)");
                respuestaCorrecta = false;
            } else {
                respuestaCorrecta = true;
            }
        } while (!respuestaCorrecta);
        if (respuesta == 's') {

            do {
                System.out.println("Nombre: ");
                nombre = sc.nextLine();
                if (nombre.isEmpty()) {
                    System.err.println("No se puede dejar vacío el nombre");
                }
            } while (nombre.isEmpty());
            do {
                System.out.println("Apellido: ");
                apellido = sc.nextLine();
                if (apellido.isEmpty()) {
                    System.err.println("No se puede dejar vacío el apellido");
                }
            } while (apellido.isEmpty());
            do {
                System.out.println("Nota: ");
                nota = sc.nextInt();
                if (nota < 0 || nota > 10) {
                    System.err.println("La nota no puede ser menor a 0 o mayor a 10");
                }
            } while (nota < 0 || nota > 10);
            insert.setInt(1, id);
            insert.setString(2, nombre);
            insert.setString(3, apellido);
            insert.setInt(4, nota);
            insert.execute();
            System.out.println("La operación de inserción del alumno con id: " + id + " ha sido realizada con éxito");
            notas.put(id, nota);
        } else {
            System.err.println("No se ha querido insertar el registro");
        }
    }
}
