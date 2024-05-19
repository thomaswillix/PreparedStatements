package main;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    
    public static void main(String[] args) throws SQLException {
        File f = new File("jugadas.txt");
        int codP1, codP2, codArma, codEscudo;
        Jugada j;
        List<Jugada> lista = new ArrayList<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String datos[];
            System.out.println("JUGADAS DEL ARCHIVO .TXT");
            try {
                while ((line = br.readLine())!= null) {                    
                    datos = line.split(",");
                    codP1 = Integer.valueOf(datos[0]);
                    codArma = Integer.valueOf(datos[1]);
                    codP2 = Integer.valueOf(datos[2]);
                    codEscudo = Integer.valueOf(datos[3]);
                    j = new Jugada(codP1, codArma, codP2, codEscudo);
                    System.out.println(j.toString());
                    lista.add(j);
                }
            } catch (EOFException e) {
                System.err.println("End of file");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/videojuego", "root", "");
        
        PreparedStatement pst = con.prepareStatement("select *  from personaje where id = ?");
        PreparedStatement pst2 = con.prepareStatement("select *  from arma where codAr = ?");
        PreparedStatement pst3 = con.prepareStatement("select *  from escudo where codEs = ?");
        PreparedStatement delete = con.prepareStatement("delete from personaje where id = ?");
        PreparedStatement update = con.prepareStatement("update personaje set vida = ? where id = ?");
        Statement st = con.createStatement();
        
        ResultSet rs;
        
        int vidaIniP1, vidaIniP2;
        Personaje p1, p2;
        Arma a;
        Escudo e;
        
        System.out.println("\n\nTABLA DE PERSONAJES ANTES DE SER ACTUALIZADA");
        rs = st.executeQuery("select * from personaje");
        while (rs.next()) {            
            p1 = new Personaje(rs.getInt(1), rs.getString(2), rs.getInt(3));
            System.out.println(p1.toString());
        }
        
        for (Jugada jugada : lista) {
            pst.setInt(1, jugada.getCodP1());
            rs = pst.executeQuery();
            if (rs.next()) {
                p1 = new Personaje(rs.getInt(1), rs.getString(2), rs.getInt(3));
                pst.setInt(1, jugada.getCodP2());
                rs = pst.executeQuery();
                if (rs.next()) {
                    vidaIniP1 = p1.getVida();
                    p2 = new Personaje(rs.getInt(1), rs.getString(2), rs.getInt(3));
                    pst2.setInt(1, jugada.getCodArma());
                    rs = pst2.executeQuery();
                    if (rs.next()) {
                        vidaIniP2 = p2.getVida();
                        a = new Arma(rs.getInt(1), rs.getString(2), rs.getInt(3));
                        pst3.setInt(1, jugada.getCodEscudo());
                        rs = pst3.executeQuery();
                        if (rs.next()) {
                            e = new Escudo(rs.getInt(1), rs.getString(2), rs.getInt(3));
                            if (a.getDano()> e.getDefensa()){
                                p2.setVida(p2.getVida() -(a.getDano() - e.getDefensa()));
                                update.setInt(1, p2.getVida());
                                update.setInt(2, p2.getId());
                                update.execute();
                            } else if(a.getDano()< e.getDefensa()){
                                p1.setVida(p1.getVida() - (e.getDefensa() - a.getDano()));
                                update.setInt(1, p1.getVida());
                                update.setInt(2, p1.getId());
                                update.execute();
                            }
                            
                            if (vidaIniP1 / 2 > p1.getVida()) {
                                delete.setInt(1, p1.getId());
                                delete.execute();
                            }
                            
                            if (vidaIniP2 / 2 > p2.getVida()) {
                                delete.setInt(1, p2.getId());
                                delete.execute();
                            }
                        }else System.err.println("NO EXISTE EL CÓDIGO DEL ESCUDO EN LA BASE DE DATOS");
                    }else System.err.println("NO EXISTE EL CÓDIGO DEL ARMA EN LA BASE DE DATOS");
                } else System.err.println("NO EXISTE EL CÓDIGO DEL PERSONAJE 2 EN LA BASE DE DATOS");
            }else System.err.println("NO EXISTE EL CÓDIGO DEL PERSONAJE 1 EN LA BASE DE DATOS");
        }
        rs = st.executeQuery("select * from personaje");
        System.out.println("\n\nTABLA DE PERSONAJES DESPUÉS DE SER ACTUALIZADA");
        while (rs.next()) {
            p1 = new Personaje(rs.getInt(1), rs.getString(2), rs.getInt(3));
            System.out.println(p1.toString());
        }
    }
    
}
