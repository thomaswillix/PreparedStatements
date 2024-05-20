package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("“jdbc:mysql://localhost/colegio", "root", "");
        PreparedStatement pst = con.prepareStatement("update alumnos set Nota = ? where id = ?");
        
        Statement st  = con.createStatement();
        ResultSet rs = st.executeQuery("select *  from cambios");
        String operacion;
        int id, incremento;
        
        while (rs.next()) {            
            operacion = rs.getString(2);
            id = rs.getInt(3);
            incremento = rs.getInt(4);
            switch(operacion){
                
            }
        }
    }
    
}
