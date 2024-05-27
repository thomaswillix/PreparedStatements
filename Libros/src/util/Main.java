package util;

import classes.Libro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/libreria", "root", "");
        Statement st = con.createStatement();
        PreparedStatement comprobacion = con.prepareStatement("select * from libros where isbn = ?");

        //st.execute("ALTER TABLE Libros ADD precio double");
        
        System.out.println("------------SET PRECIO ESTÁNDAR------------");
        Scanner sc = new Scanner(System.in);
        double precio = 0;
        float precioF = 0;
        int isbn = 0, isbn2= 0, paginas = 0;
        do {            
            System.out.println("Precio: ");
            precio = sc.nextDouble();
            if (precio<=0) {
                System.err.println("El precio no puede ser menor o igual a 0");
            }
        } while (precio<=0);
        
        Libro.setPrecio(con, precio);
        Libro.verCatalogo(con);
        
        System.out.println("\n--------- Set precio máximo entre dos libros ---------");
        System.out.print("Primer ");
        isbn = comprobacionISBN(isbn, sc);
        System.out.print("Segundo ");
        isbn2 = comprobacionISBN(isbn2, sc);
        precio = comprobacionPrecio(precio, sc);
        
        Libro.setPrecioPorIsbn(con, comprobacion, isbn, isbn2, precio);
        Libro.verCatalogo(con);
        
        System.out.println("\n--------- Actualizar paginas y precio por isbn ---------");
        isbn = comprobacionISBN(isbn, sc);
        System.out.println("El número de páginas que pongas a continuación se añadirá a las que ya había previamente");
        paginas = comprobacionPaginas(paginas, sc);
        precioF = (float)comprobacionPrecio(precio, sc);
        Libro.actualizarPaginasYPrecio(con, comprobacion, isbn, paginas, precioF);
        Libro.verCatalogo(con);
        System.out.println("\n------------------ Clonar un libro ------------------");
        System.out.print("El primer ISBN será el libro que copies y el segundo la copia (tendrás que poner un nuevo ISBN)\n\nPrimer ");
        isbn = comprobacionISBN(isbn, sc);
        System.out.print("Segundo ");
        isbn2 = comprobacionISBN(isbn2, sc);
        
        Libro.InsertarCopiaLibro(con, comprobacion, isbn, isbn2);
        Libro.verCatalogo(con);
        
        st.close();
        comprobacion.close();
        con.close();
    }
    private static int comprobacionISBN(int isbn, Scanner sc){
        do {            
            System.out.println("ISBN: ");
            isbn = sc.nextInt();
            if (isbn<=0) {
                System.err.println("El isbn no puede ser menor o igual a 0");
            }
        } while (isbn<=0);
        return isbn;
    }
    private static int comprobacionPaginas(int paginas, Scanner sc){
        do {            
            System.out.println("Número de paginas: ");
            paginas = sc.nextInt();
            if (paginas<=0) {
                System.err.println("El número de páginas no puede ser menor o igual a 0");
            }
        } while (paginas<=0);
        return paginas;
    }
    private static double comprobacionPrecio(double precio, Scanner sc){
        do {            
            System.out.println("Precio: ");
            precio = sc.nextDouble();
            if (precio<=0) {
                System.err.println("El precio no puede ser menor o igual a 0");
            }
        } while (precio<=0);
        return precio;
    }
}
