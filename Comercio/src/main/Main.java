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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) {
        File f = new File("ventas.dat");
        String nVendedor;
        int cod, uds;
        List<Venta> ventas = new ArrayList<>();
        Venta v;
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(f));
            try {
                while (true) {                    
                    nVendedor = dis.readUTF();
                    cod = dis.readInt();
                    uds = dis.readInt();
                    v = new Venta(nVendedor, cod, uds);
                    ventas.add(v);
                }
            } catch (EOFException e) {
                System.err.println("END OF FILE");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("------------- VENTAS DEL ARCHIVO BINARIO -------------");
        for (Venta venta : ventas) {
            System.out.println(venta.toString()); 
        } 
        
        Connection con;
        try {
            con=DriverManager.getConnection("jdbc:mysql://localhost/Comercio","root","");
            String cadena="select * from ventas";
            Statement st=con.createStatement();
            ResultSet rs;
            rs=st.executeQuery(cadena);
            System.out.println("CÓDIGO DE LA VENTA      CÓDIGO DEL PRODUCTO       UDS VENDIDAS        GANANCIAS");
            while(rs.next()){
                System.out.println("         "+rs.getString(1) + "                   " + rs.getInt(2) + "                        " + rs.getInt(3) + "               " + rs.getDouble(4));
            }
            PreparedStatement pst1 = con.prepareStatement("Select * from Ventas where CodVend = ? and CodProd = ?");
            PreparedStatement pst2 = con.prepareStatement("Insert into Ventas values (?,?,?,?)");
            PreparedStatement pst3 = con.prepareStatement("Update Ventas set Vendido = ?, Ganancia = ? where CodVend = ? and CodProd = ?");
            PreparedStatement pst4 = con.prepareStatement("select * from productos where Codprod = ?"); //Select para cuando veamos el precio
            
            for (Venta venta : ventas) {
                pst1.setString(1, venta.nVendedor);
                pst1.setInt(2, venta.cod);
                rs = pst1.executeQuery();
                int precio;
                if (rs.next()) {                  // VENTA EXISTE (PST1 DA RESULTADOS) --> PROCEDEMOS CON EL UPDATE (PST3)
                    pst4.setInt(1, venta.cod);
                    rs = pst4.executeQuery();
                    if(rs.next()){                // COMPROBACIÓN DE QUE EL PRODUCTO EXISTA (PST4)
                        precio = rs.getInt("Precio");
                        pst3.setInt(1, venta.uds);
                        pst3.setDouble(2, venta.uds * precio);
                        pst3.setString(3, venta.nVendedor);
                        pst3.setInt(4, venta.cod);
                        pst3.executeUpdate();    //UPDATE
                    }else System.err.println("EL PRODUCTO CON COD : " + venta.cod + " NO EXISTE."); //MENSAJE DE ERROR
                } else{                           // VENTA NO EXISTE --> PROCEDEMOS CON LA INSERCIÓN
                    pst4.setInt(1, venta.cod);
                    rs = pst4.executeQuery();
                    if(rs.next()){                // COMPROBACIÓN DE QUE EL PRODUCTO EXISTA (PST4)
                        precio = rs.getInt("Precio");
                        pst2.setString(1, venta.nVendedor);
                        pst2.setInt(2, venta.cod);
                        pst2.setInt(3, venta.uds);
                        pst2.setDouble(4, venta.uds * precio);
                        pst2.executeUpdate();    //INSERT
                    }else System.err.println("EL PRODUCTO CON COD : " + venta.cod + " NO EXISTE."); //MENSAJE DE ERROR
                }
            }
            
            System.out.println("\n------------ TABLA DE VENTAS ACTUALIZADA ------------");
            rs=st.executeQuery(cadena);
            System.out.println("CÓDIGO DE LA VENTA      CÓDIGO DEL PRODUCTO       UDS VENDIDAS        GANANCIAS");
            while(rs.next()){
                System.out.println("         "+rs.getString(1) + "                   " + rs.getInt(2) + "                        " + rs.getInt(3) + "               " + rs.getDouble(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
