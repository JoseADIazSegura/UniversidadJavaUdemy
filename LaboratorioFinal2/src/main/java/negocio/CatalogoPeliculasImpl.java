package negocio;

import datos.AccesoDatos;
import datos.AccesoDatosImpl;
import domain.Pelicula;
import excepciones.AccesoDatosEx;
import excepciones.EscrituraDatosEx;
import excepciones.LecturaDatosEx;

import java.util.List;

public class CatalogoPeliculasImpl implements CatalogoPeliculas {
    private final AccesoDatos datos;

    public CatalogoPeliculasImpl() {
        this.datos = new AccesoDatosImpl();
    }

    @Override
    public void agregarPelicula(String nombrePelicula, String nombreArchivo) throws EscrituraDatosEx {
        Pelicula pelicula = new Pelicula(nombrePelicula);
        boolean anexar = false;
        try {
            anexar = datos.existe(nombreArchivo);
            datos.escribir(pelicula,nombreArchivo,anexar);
        } catch (AccesoDatosEx accesoDatosEx) {
            System.out.println("Error de acceso a datos");
            accesoDatosEx.printStackTrace();
        }

    }

    @Override
    public void listarPeliculas(String nombreArchivo) throws AccesoDatosEx {
        try {
            List<Pelicula> peliculas = datos.listar(nombreArchivo);
            for (Pelicula pelicula : peliculas){
                System.out.println("Pelicula: " +pelicula.getNombre());
            }
        }catch (AccesoDatosEx ex){
            System.out.println("Error de acceso a datos");
            ex.printStackTrace();
        }

    }

    @Override
    public void buscarPelicula(String nombreArchivo, String buscar) throws AccesoDatosEx {
        String resultado = null;
        try {
            resultado = datos.buscar(buscar, nombreArchivo);
        } catch (LecturaDatosEx ex) {
            System.out.println("Error al buscar la pelicula");
            ex.printStackTrace();
        }
        System.out.println("Resultado busqueda:" + resultado);
    }

    @Override
    public void iniciarArchivo(String nombreArchivo) throws EscrituraDatosEx {
        try {
            if (datos.existe(nombreArchivo)) {
                datos.borrar(nombreArchivo);
                datos.crear(nombreArchivo);
            } else {
                //creamos archivo
                datos.crear(nombreArchivo);
            }
        } catch (AccesoDatosEx ex) {
            System.out.println("Error de acceso a datos");
            ex.printStackTrace();
        }
    }
}
