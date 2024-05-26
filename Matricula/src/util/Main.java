package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/matricula", "root", "");
        
        //Dar de alta un alumno: se deberán proporcionar los datos del alumno
        PreparedStatement insertarAlumno = con.prepareStatement("insert into alumnos values (?,?,?,?,?)");
        //Eliminar un alumno: se deberá proporcionar el id del alumno
        PreparedStatement borrarAlumno = con.prepareStatement("delete from alumnos where id_alumno = ?");
        
        /*Matricular a un alumno en asignaturas: se identificará al alumno y a
        las asignaturas*/
        
        //Matricular ese alumno
        PreparedStatement matricularAlumno = con.prepareStatement("insert into alumnos_asignaturas values (?,?,?)");
        //Buscar la asignat
        PreparedStatement buscarAsignatura = con.prepareStatement("select * from asignaturas where id_asignatura = ?");
        
        /*Ver las asignaturas asociadas a un alumno, mostrando las que ha
        cursado y las que no. Se mostrarán todos los datos del alumno y de
        las asignaturas.*/
        PreparedStatement buscarAsignaturasPorAlumno = con.prepareStatement("select * from alumnos_asignaturas where id_alumno=?");
        //Buscar los datos de un alumno
        PreparedStatement buscarAlumno = con.prepareStatement("select * from alumnos where id_alumno=?");

        //Variables necesarias
        Scanner sc = new Scanner(System.in);
        int id = 0, curso=0, titulacion=0, nroAsignaturas=0, operacion;
        String apellidos="", nombre="";
        //Lista con todos los ids de los Alumnos
        List<Integer> idsAlumnos = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from alumnos");
        while (rs.next()) {            
            idsAlumnos.add(rs.getInt(1));
        }
        //Número total de asignarutas
        rs = st.executeQuery("select * from asignaturas");
        while (rs.next()) {
            nroAsignaturas++;
        }
        System.out.println("Número total de asignaturas: " + nroAsignaturas);
        do {
            System.out.println("1- Crear un nuevo alumno y almacenarlo en BD\n2- Busca al alumno nuevo y muestra sus datos por pantalla"
                    + "\n3- Borrar un alumno existente\n4- Matricular al alumno nuevo en varias asignaturas\n5- Consultar las asignaturas "
                    + "para las que está matriculado el nuevo alumno y mostrarlas por pantalla");
                    
            operacion = sc.nextInt();
            sc.nextLine();
            if (operacion<1 || operacion>6) {
                System.err.println("Número de operación incorrecto");
            }else{
                switch(operacion){
                    case 1:
                        id = insertarAlumno(idsAlumnos, insertarAlumno ,sc, id, nombre, apellidos, nroAsignaturas, titulacion, curso);
                        break;
                    case 2:
                        buscarAlumno(sc, id, buscarAlumno, rs);
                        break;
                    case 3:
                        borrarAlumno(sc, id, borrarAlumno,idsAlumnos, rs);
                        break;
                    case 4:
                        matricularAlumno( buscarAsignatura, matricularAlumno, buscarAlumno, id, nombre, apellidos, sc, rs);
                        break;
                    case 5:
                        consultarAsignaturasMatriculadas(sc, id, buscarAsignaturasPorAlumno, buscarAsignatura, buscarAlumno, rs);
                        break;
                }
            }
        } while (operacion!=6);
        insertarAlumno.close();
        borrarAlumno.close();
        buscarAlumno.close();
        buscarAsignatura.close();
        buscarAsignaturasPorAlumno.close();
        matricularAlumno.close();
    }

    private static int insertarAlumno(List<Integer> idsAlumnos,PreparedStatement insertarAlumno, Scanner sc, int id, String nombre, String apellidos, int nroAsignaturas, int titulacion, int curso) throws SQLException {
        System.out.println("----------- INSERTAR ALUMNO -----------");
        do {
            System.out.println("id: ");
            try {
                id = sc.nextInt();
                if (id<=0) {
                System.err.println("El id no puede ser inferior o igual a 0");
            }
            } catch (Exception e) {
                System.err.println("Solo se aceptan valores enteros");
                sc.nextLine();
            }
            if (idsAlumnos.contains(id)) {
                System.err.println("Ese id ya está siendo utilizado por otro alumno, prueba con otro distinto");
            }
        } while (id<=0 || idsAlumnos.contains(id));
        sc.nextLine();
        idsAlumnos.add(id);
        do {
            System.out.println("Apellidos: ");
            apellidos =sc.nextLine().trim().toUpperCase();
            if (apellidos.isEmpty()) {
                System.err.println("El campo está vacío");
            }
        } while (apellidos.isEmpty());
        do {
            System.out.println("Nombre: ");
            nombre =sc.nextLine().trim().toUpperCase();
            if (nombre.isEmpty()) {
                System.err.println("El campo está vacío");
            }
        } while (nombre.isEmpty());
        do {
            System.out.println("Asignaturas en curso: ");
            try {
                curso = sc.nextInt();
                if (curso<0) {
                System.err.println("El número de asignaturas cursadas no puede ser inferior a 0");
            }
            } catch (Exception e) {
                System.err.println("Solo se aceptan valores enteros");
                sc.nextLine();
            }
            if (curso>nroAsignaturas) {
                System.err.println("El número de asignaturas en curso no puede ser mayor al número de asignaturas totales");
            }
        } while (curso<0 || curso>nroAsignaturas);
        do {
            System.out.println("Asignaturas tituladas: ");
            try {
                titulacion = sc.nextInt();
                if (titulacion<0) {
                System.err.println("El número de asignaturas tituladas no puede ser inferior a 0");
            }
            } catch (Exception e) {
                System.err.println("Solo se aceptan valores enteros");
                sc.nextLine();
            }
            if (titulacion>nroAsignaturas-curso){
                System.err.println("El número de asignaturas tituladas no puede ser mayor al número de asignaturas totales menos las que están en curso");
            }
        } while (titulacion<0 || titulacion>nroAsignaturas-curso);
        insertarAlumno.setInt(1, id);
        insertarAlumno.setString(2, apellidos);
        insertarAlumno.setString(3, nombre);
        insertarAlumno.setInt(4, curso);
        insertarAlumno.setInt(5, titulacion);
        insertarAlumno.execute();
        return id;
    }

    private static void buscarAlumno(Scanner sc, int id, PreparedStatement buscarAlumno, ResultSet rs) throws SQLException {
        buscarAlumno.setInt(1, id);
        rs = buscarAlumno.executeQuery();
        if (rs.next()) {
            System.out.println("id: " +rs.getInt(1)+", apellidos: " + rs.getString(2) + ", nombre: " + rs.getString(3) + ", curso: " 
                    + rs.getInt(4) + ", titulacion: " + rs.getInt(5));
        }else System.err.println("El id: " + id + ", no pertenece a ningún alumno de la base de datos");
    }

    private static void borrarAlumno(Scanner sc, int id, PreparedStatement borrarAlumno, List<Integer> idsAlumnos, ResultSet rs) throws SQLException {
        System.out.println("Dime el id del alumno al que quieras borrar: ");
        id = sc.nextInt();
        borrarAlumno.setInt(1, id);
        if(idsAlumnos.contains(id)){
            borrarAlumno.execute();
            idsAlumnos.remove(id);
            System.err.println("El alumno con id: " + id + ", ha sido borrado de la base de datos");
        }else System.out.println("No se ha podido encontrar el id que se ha introducido");
    }

    private static void matricularAlumno(PreparedStatement buscarAsignatura, PreparedStatement matricularAlumno, PreparedStatement buscarAlumno, int id, String nombre, String apellidos, Scanner sc, ResultSet rs) throws SQLException {
        char respuesta, c;
        boolean cursada;
        int idAsig;
        buscarAlumno.setInt(1, id);
        rs = buscarAlumno.executeQuery();
        if (rs.next()) {            
            apellidos = rs.getString(2);
            nombre = rs.getString(3);
        }
        do {
            System.out.println("A qué asignatura quieres matricular al alumno con id: " + id);
            idAsig = sc.nextInt();
            sc.nextLine();
            buscarAsignatura.setInt(1, idAsig);
            rs = buscarAsignatura.executeQuery();
            if (rs.next()) {
                System.out.println("¿Está cursada? (s-n)");
                c = sc.nextLine().trim().toLowerCase().charAt(0);
                if (c == 's'){
                    cursada = true;
                }else cursada = false;
                System.out.println("Vamos a matricular al alumno: " + nombre + " " + apellidos + ", a la asignatura " + rs.getString(3));
                matricularAlumno.setInt(1, id);
                matricularAlumno.setInt(2, idAsig);
                matricularAlumno.setBoolean(3, cursada);
                matricularAlumno.execute();
            } else System.err.println("Ese id de asignatura no figura en la base de datos");
            System.out.println("¿Quieres matricular al alumno en otra asignatura? (s-n)");
            respuesta = sc.nextLine().trim().toLowerCase().charAt(0);
        } while (respuesta == 's');
    }

    private static void consultarAsignaturasMatriculadas(Scanner sc, int id, PreparedStatement buscarAsignaturasPorAlumno, PreparedStatement buscarAsignatura, PreparedStatement buscarAlumno, ResultSet rs) throws SQLException {
        System.out.println("Dime el id de un alumno y te enseñaré las asignaturas en las que está matriculado: ");
        id = sc.nextInt();
        buscarAlumno.setInt(1, id);
        rs = buscarAlumno.executeQuery();
        if (rs.next()) {
        List<Integer> idsAsignaturas = new ArrayList<>();
        buscarAsignaturasPorAlumno.setInt(1, id);
        rs = buscarAsignaturasPorAlumno.executeQuery();
        while (rs.next()) {
            idsAsignaturas.add(rs.getInt(2));
        }
        System.out.println("Asignaturas en las que está matriculado:");
        for (Integer idAsignatura : idsAsignaturas) {
            buscarAsignatura.setInt(1, idAsignatura);
            rs = buscarAsignatura.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(3));
            }
        }
        }else System.err.println("El id: " + id + ", no pertenece a ningún alumno de la base de datos");
        
    }
    
}
