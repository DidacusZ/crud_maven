package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import dtos.LibroDto;
import util.ADto;

/**
 * Implementacion de la interfaz de consultas 021023 dpm
 */
public class ConsultasImplementacion implements ConsultasInterfaz {

	ConexionInterfaz conexionImpl = new ConexionImplementacion();

	@Override
	public ArrayList<LibroDto> seleccionaTodosLibros() {
		// TODO Auto-generated method stub

		Connection conexion = conexionImpl.crearConexion();

		// Declaración SQL estática que devuelve los resultados que produce
		Statement declaracionSQL = null;// Solo se puede abrir un objeto ResultSet por cada objeto Statement
		// El resultado de la consulta a la BD
		ResultSet resultadoConsulta = null;
		ArrayList<LibroDto> listaLibros = new ArrayList<>();
		ADto aDto = new ADto();

		try {
			// Se abre una declaración
			declaracionSQL = conexion.createStatement();// Crea un objeto Statement para enviar declaraciones SQL a la
														// base de datos

			// Se define la consulta de la declaración y se ejecuta la query
			resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM gbp_estanteria.libros");

			listaLibros = aDto.ResultSetALibroDto(resultadoConsulta);

			// siempre cerrar la conexion y demas para no sobrecargar el server
			conexion.close();
			declaracionSQL.close();
			resultadoConsulta.close();
			System.out.println("[INFO] Conexion, declaracion, resultado cerrados [ConsultasImplementacion-seleccionaTodosLibros]");

		} catch (SQLException e) {
			System.out.println("[ERROR] No se puede lanzar la Query [ConsultasImplementacion-seleccionaTodosLibros]");
		}

		return listaLibros;
	}

	@Override
	public void seleccionLibros(Scanner sc) {
		// TODO Auto-generated method stub

		Connection conexion = conexionImpl.crearConexion();

		Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		ADto aDto = new ADto();

		// parametrizacion
		PreparedStatement parametro = null;

		ArrayList<LibroDto> listaLibros = new ArrayList<>();

		try {
			// Se abre una declaración
			declaracionSQL = conexion.createStatement();// Crea un objeto Statement para enviar declaraciones SQL a la

			parametro = conexion.prepareStatement("SELECT * FROM gbp_estanteria.libros WHERE isbn=?");
			do {
				System.out.print("Que libro quieres ver: ");
				parametro.setString(1, sc.next());// añade un valor al '?' nº1
				resultadoConsulta = parametro.executeQuery();// ejecuta la query
				listaLibros = aDto.ResultSetALibroDto(resultadoConsulta);
				System.out.println(listaLibros.get(0).toString());
			} while (preguntaSiNo("Quieres ver otro libro?[si/no]", sc));

			// siempre cerrar la conexion y demas para no sobrecargar el server
			conexion.close();
			declaracionSQL.close();
			resultadoConsulta.close();
			System.out.println("[INFO] Conexion, declaracion, resultado cerrados [ConsultasImplementacion-seleccionLibros]");

		} catch (SQLException e) {
			System.err.println("[ERROR] No se puede lanzar la Query [ConsultasImplementacion-seleccionLibros]");
		} catch (IndexOutOfBoundsException e) {
			System.err.println("[ERROR] El libro no existe [ConsultasImplementacion-seleccionLibros]");
		}

	}

	@Override
	public void modificarLibro(Scanner sc) {
		// TODO Auto-generated method stub
		Connection conexion = conexionImpl.crearConexion();

		Statement declaracionSQL = null;// Solo se puede abrir un objeto ResultSet por cada objeto Statement
		// El resultado de la consulta a la BD

		// parametrizacion
		PreparedStatement parametro = null;// sirve para poder parametrizar consultas

		try {
			// Se abre una declaración
			declaracionSQL = conexion.createStatement();

			parametro = conexion.prepareStatement("UPDATE gbp_almacen.gbp_alm_cat_libros SET ?=? WHERE isbn=?");

			do {
				preguntasModificar(parametro, sc);
				parametro.executeUpdate();
			}while(preguntaSiNo("Quieres modificar otro libro?[si/no]", sc));
			
			System.out.println("[INFO] Modificacion terminada [ConsultasImplementacion-modificarLibro]");

			// siempre cerrar la conexion y demas para no sobrecargar el server
			conexion.close();
			declaracionSQL.close();
			System.out.println("[INFO] Conexion, declaracion, resultado cerrados [ConsultasImplementacion-modificarLibro]");

		} catch (SQLException e) {
			System.err.println("[ERROR] No se puede lanzar la Query [ConsultasImplementacion-modificarLibro]");
			e.printStackTrace();
		}
	}

	@Override
	public void crearLibro(Scanner sc) {
		// TODO Auto-generated method stub
		Connection conexion = conexionImpl.crearConexion();

		Statement declaracionSQL = null;// Solo se puede abrir un objeto ResultSet por cada objeto Statement
		// El resultado de la consulta a la BD

		// parametrizacion
		PreparedStatement parametro = null;// sirve para poder parametrizar consultas

		try {
			// Se abre una declaración
			declaracionSQL = conexion.createStatement();

			// envia declaraciones sql parametrizadas
			parametro = conexion.prepareStatement("INSERT INTO gbp_estanteria.libros (titulo, autor, isbn,edicion) VALUES (?,?,?,?)");

			do {
				preguntascrear(parametro, sc);
				parametro.executeUpdate();// ejecuta la query
			} while (preguntaSiNo("Quieres registrar otro?[si/no]", sc));

			System.out.println("[INFO] Libro registrado correctamente [ConsultasImplementacion-crearLibro]");

			// siempre cerrar la conexion y demas para no sobrecargar el server
			conexion.close();
			declaracionSQL.close();
			System.out.println("[INFO] Conexion, declaracion, resultado cerrados [ConsultasImplementacion-crearLibro]");

		} catch (SQLException e) {
			System.err.println("[ERROR] No se puede lanzar la Query [ConsultasImplementacion-crearLibro]");
		}
	}

	@Override
	public void borrarLibros(Scanner sc) {
		// TODO Auto-generated method stub

		Connection conexion = conexionImpl.crearConexion();

		Statement declaracionSQL = null;// Solo se puede abrir un objeto ResultSet por cada objeto Statement
		// parametrizacion
		PreparedStatement parametro = null;

		try {
			// Se abre una declaración
			declaracionSQL = conexion.createStatement();// Crea un objeto Statement para enviar declaraciones SQL a la

			parametro = conexion.prepareStatement("DELETE FROM gbp_estanteria.libros WHERE isbn=?");

			do {
				System.out.print("Que libro quieres eliminar: ");
				parametro.setString(1, sc.next());
				parametro.executeUpdate();// ejecuta la query
			} while (preguntaSiNo("Quieres borrar otro?[si/no]", sc));

			System.out.println("[INFO] Borrado correcto [ConsultasImplementacion-borrarLibros]");

			// siempre cerrar la conexion y demas para no sobrecargar el server
			conexion.close();
			declaracionSQL.close();
			System.out.println("[INFO] Conexion, declaracion, resultado cerrados [ConsultasImplementacion-borrarLibros]");

		} catch (SQLException e) {
			System.err.println("[ERROR] No se puede lanzar la Query [ConsultasImplementacion-borrarLibros]");
		}

	}

	// METODOS PRIVADOS//

	/**
	 * devuelve true o false dependiendo de la respuesta
	 * 
	 * @param txt
	 * @param sc
	 * @return boolean
	 */
	private boolean preguntaSiNo(String txt, Scanner sc) {
		// String sioNo;
		boolean cerrarmenu = true;
		do {
			System.out.print(txt + ": ");
			// sioNo=sc.next().toLowerCase();
			switch (sc.next().toLowerCase()) {
			case "si":
				return true;
			case "no":
				return false;
			default:
				System.err.println("[ERROR] Entrada no válida");
				cerrarmenu = false;
			}

		} while (!cerrarmenu);
		return true;

	}

	/**
	 * preguntas necesarias para crear un nuevo libro
	 * @param parametro
	 * @throws SQLException
	 * @return PreparedStatement devuelve el parametro
	 */
	private void preguntascrear(PreparedStatement parametro, Scanner sc) throws SQLException {

		System.out.print("Introduce el titulo: ");
		parametro.setString(1, sc.next());

		System.out.print("Introduce el autor: ");
		parametro.setString(2, sc.next());

		System.out.print("Introduce el isbn: ");
		parametro.setString(3, sc.next());

		System.out.print("Introduce la edicion: ");
		parametro.setInt(4, sc.nextInt());
	}

	/**
	 * preguntas necesarias para modificar un libro
	 * @param parametro
	 * @throws SQLException
	 */
	private void preguntasModificar(PreparedStatement parametro, Scanner sc) throws SQLException {

		String campo = "";
		int n = 0;

		System.out.print("Que libro quieres modificar(isbn): ");
		parametro.setString(2, sc.next());

		
		
		switch(n) {
		System.out.println("");
		System.out.println("1-titulo");
		System.out.println("2-autor");
		System.out.println("3-isbn");
		System.out.println("4-edicion");
		System.out.print("\nElige una opcion: ");
		
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		do {
			System.out.print("Que campo/s quieres modificar[titulo,autor,isbn,edicion]: ");
			campo = sc.next();
		} while (!campo.equals("titulo") && !campo.equals("autor") && !campo.equals("isbn")
				&& !campo.equals("edicion"));

		System.out.print("Introduce el nuevo valor: ");
		if (campo.equals("titulo") || campo.equals("autor")|| campo.equals("isbn")) {
			parametro.setString(1, sc.next());
		} else {
			parametro.setInt(1, sc.nextInt());
		}

	}

}
