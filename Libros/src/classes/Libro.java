package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Thomas Freitas
 */
public class Libro {

    private static ResultSet rs;

    public static void anadirLibro(Connection con, PreparedStatement comprobacion, int isbn, String titulo, String autor, String editorial, int paginas, int copias) throws SQLException {
        comprobacion.setInt(1, isbn);
        rs = comprobacion.executeQuery();
        if (rs.next()) {
            System.err.println("Ese isbn ya le pertenece a otro libro");
        }else{
            PreparedStatement pst = con.prepareStatement("insert into libros values (?,?,?,?,?,?)");
            pst.setInt(1, isbn);
            pst.setString(2, titulo);
            pst.setString(3, autor);
            pst.setString(4, editorial);
            pst.setInt(5, paginas);
            pst.setInt(6, copias);
            pst.executeQuery();
            System.out.println("Libro insertado en la base de datos");
        }
    }
    public static void borrarLibro(Connection con, PreparedStatement comprobacion, int isbn) throws SQLException{
        comprobacion.setInt(1, isbn);
        rs = comprobacion.executeQuery();
        if (rs.next()) {
            PreparedStatement pst = con.prepareStatement("delete from libros where isbn = ?");
            pst.execute();
            System.out.println("Libro borrado con éxito");
        }else{
            System.err.println("Ese isbn no figura en la base de datos");
        }
    }
    public static void verCatalogo(Connection con) throws SQLException{
        Statement st = con.createStatement();
        rs = st.executeQuery("select * from libros");
        while (rs.next()) {            
            System.out.println("isbn: " + rs.getInt(1) + ", titulo: " + rs.getString(2) + ", autor: " + rs.getString(3)
            + ", editorial: " + rs.getString(4)+ ", paginas: " + rs.getInt(5)+ ", copias: " + rs.getInt(6) + ", precio: "
            + rs.getDouble(7));
        }
        st.close();
    }
    public static void actualizarCopias(Connection con, PreparedStatement comprobacion, int isbn, int copias) throws SQLException{
        comprobacion.setInt(1, isbn);
        rs = comprobacion.executeQuery();
        if (rs.next()) {
            PreparedStatement pst = con.prepareStatement("update libros set copias = ?");
            pst.setInt(1, copias);
            pst.execute();
            System.out.println("Copias actualizada");
        }else{
            System.err.println("Ese isbn no figura en la base de datos");
        }
    }
   
    public static void setPrecio(Connection con, double precio) throws SQLException {
        PreparedStatement setPrecio = con.prepareStatement("update libros set precio = ? where isbn = ?");
        Statement st = con.createStatement();
        rs = st.executeQuery("select * from libros");
        while (rs.next()) {            
            setPrecio.setDouble(1, precio* rs.getInt(5));
            setPrecio.setInt(2, rs.getInt(1));
            setPrecio.execute();
        }
        st.close();
        setPrecio.close();
    }
    
    public static void setPrecioPorIsbn(Connection con, PreparedStatement comprobacion, int isbn1, int isbn2, double precio) throws SQLException {
        PreparedStatement setPrecioPst = con.prepareStatement("update libros set precio = ? where isbn = ?");
        comprobacion.setInt(1,isbn1);
        rs = comprobacion.executeQuery();
        boolean existeISBN1;
        double precio1 = 0, precio2 = 0;
        if (rs.next()) {
            existeISBN1 = true;
            precio2 = precio * rs.getInt(5);
        } else {
            existeISBN1 = false;
            System.err.println("El isbn 1 no pertenece a ningún libro");
        }
        comprobacion.setInt(1,isbn2);
        rs = comprobacion.executeQuery();
        if (rs.next()) {
            int paginas = rs.getInt(5);
            precio1=precio*paginas;
            //Actualización libro 2
            setPrecioPst.setDouble(1, Math.max(precio1, precio2));
            setPrecioPst.setInt(2, isbn2);
            setPrecioPst.execute();
        }else System.err.println("El isbn 2 no pertenece a ningún libro");
        if (existeISBN1) {
        //Actualización libro 1;
            setPrecioPst.setDouble(1, Math.max(precio1, precio2));
            setPrecioPst.setInt(2, isbn1);
            setPrecioPst.execute();
        }
        setPrecioPst.close();
    }
    
    public static void actualizarPaginasYPrecio(Connection con, PreparedStatement comprobacion, int isbn, int paginas, float precioF) throws SQLException{
        PreparedStatement update = con.prepareStatement("update libros set paginas=?, precio = ? where isbn = ?");
        comprobacion.setInt(1, isbn);
        rs = comprobacion.executeQuery();
        double precio = (double) precioF;
        if (rs.next()) {
            paginas += rs.getInt(5);
            update.setInt(1, paginas);
            update.setDouble(2, precio * paginas);
            update.setInt(3, isbn);
            update.executeUpdate();
        }else System.err.println("No existe el libro por lo que no se pueden realizar la operacion");
        update.close();
    }
    public static void InsertarCopiaLibro(Connection con, PreparedStatement comprobacion, int isbn1, int isbn2) throws SQLException {
        PreparedStatement insert = con.prepareStatement("insert into libros values (?,?,?,?,?,?,?)");
        comprobacion.setInt(1, isbn1);
        rs = comprobacion.executeQuery();
        
        String titulo, autor, editorial;
        int paginas, copias;
        double precio;
        
        if (rs.next()) {
            titulo = rs.getString(2);
            autor = rs.getString(3);
            editorial = rs.getString(4);
            paginas = rs.getInt(5);
            copias = rs.getInt(6);
            precio = rs.getDouble(7);
            
            comprobacion.setInt(1, isbn2);
            rs = comprobacion.executeQuery();
            if(rs.next()){
                System.err.println("El ISBN ya pertenece a otro libro de la base de datos");
            }else {
                insert.setInt(1, isbn2);
                insert.setString(2, titulo);
                insert.setString(3, autor);
                insert.setString(4, editorial);
                insert.setInt(5, paginas);
                insert.setInt(6, copias);
                insert.setDouble(7, precio);
                insert.execute();
                System.out.println("Libro insertado con éxito\n");
            }
        }else System.err.println("No existe el primer ISBN en la base de datos");
        insert.close();
    }
    
}
