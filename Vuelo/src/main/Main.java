package main;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        File f = new File("operaciones.bin");
        int id, num;
        String ciudad, pais, continente, fecha;
        char operacion;
        Operacion o;
        List<Operacion> lista = new ArrayList<>();
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(f));    
            try {
                while (true) {
                    id = dis.readInt();
                    num = dis.readInt();
                    ciudad = dis.readUTF();
                    fecha = dis.readUTF();
                    operacion = dis.readChar();
                    o = new Operacion(id, num, ciudad, fecha, operacion);
                    lista.add(o);
                }
            } catch (EOFException e) {
                System.err.println("end of file");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Id del vuelo, num del vuelo, ciudad de ciudades (clave - valor con los nombres) fecha del vuelo y operacion a realizar
        Connection con;
        con = DriverManager.getConnection("jdbc:mysql://localhost/vuelo", "root", "");
        PreparedStatement insert = con.prepareStatement("Insert into vuelos values (?,?,?,?)");
        PreparedStatement update = con.prepareStatement("Update vuelos set  Numero = ?, Destino = ?, Fecha = ? WHERE Id = ?");
        PreparedStatement delete = con.prepareStatement("Delete from vuelos WHERE Id = ?");
        PreparedStatement insertCiudad = con.prepareStatement("Insert into ciudades values (?,?,?,?)");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from ciudades");
        
        HashMap<String, Integer> ciudades = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        
        while(rs.next()){
            id = rs.getInt(1);
            ciudad = rs.getString(2);
            ciudades.put(ciudad, id);
        }
        for (Operacion op : lista) {
            if (!ciudades.containsKey(op.getCiudad())) {
                System.err.println("LA CIUDAD " + op.getCiudad() + " NO EXISTE EN LA TABLA DE CIUDADES");
                System.out.println("ID: ");
                do {
                    id = sc.nextInt();
                    if (ciudades.containsValue(id)) System.err.println("EL ID DE LA CIUDAD NO LE PUEDE PERTENECER A OTRA CIUDAD YA EXISTENTE");
                    if(id<=0) System.err.println("EL ID NO PUEDE SER MENOR O IGUAL A 0");
                } while (id<=0 || ciudades.containsValue(id));
                sc.nextLine();
                System.out.println("PAIS: ");
                do {
                    pais = sc.nextLine();
                    if(pais.isEmpty()) System.err.println("No se puede dejar el pais vacío");
                } while (pais.isEmpty());
                System.out.println("CONTINENTE: ");
                do {
                    continente = sc.nextLine();
                    if(continente.isEmpty()) System.err.println("No se puede dejar el continente vacío");
                } while (continente.isEmpty());
                insertCiudad.setInt(1, id);
                insertCiudad.setString(2, op.getCiudad());
                insertCiudad.setString(3, pais);
                insertCiudad.setString(4, continente);
                insertCiudad.execute();
                ciudades.put(op.getCiudad(), id);
            }
            switch(op.getOperacion()){
                case 'A':
                    insert.setInt(1, op.getId());
                    insert.setInt(2, op.getNum());
                    insert.setInt(3, ciudades.get(op.getCiudad()));
                    insert.setString(4, op.getFecha());
                    insert.execute();
                    break;
                case 'M':
                    update.setInt(1, op.getNum());
                    update.setInt(2, ciudades.get(op.getCiudad()));
                    update.setString(3, op.getFecha());
                    update.setInt(4, op.getId());
                    update.execute();
                    break;
                case 'D':
                    delete.setInt(1, op.getId());
                    delete.execute();
                    break;
            }
        }
        rs = st.executeQuery("select * from ciudades");
        System.out.println("\n\n------------------ CIUDADES ------------------");
        while(rs.next()){
            System.out.println("ID: " + rs.getInt(1)+" CIUDAD: "+ rs.getString(2)+ 
                    " PAIS: " + rs.getString(3) + " CONTINENTE: " + rs.getString(4));
        }
        rs = st.executeQuery("select * from vuelos");
        System.out.println("\n\n------------------ VUELOS ------------------");
        while(rs.next()){
            System.out.println("ID: " + rs.getInt(1)+" NUMERO: "+ rs.getInt(2)+ 
                    " DESTINO: " + rs.getInt(3) + " FECHA: " + rs.getString(4));
        }
    }
    
}
