package servicios;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import dtos.LibroDto;

/**
 * Interfaz con los metodos necesarios para hacer las consultas al la BD
 * 021023 dpm
 */
public interface ConsultasInterfaz {

	/**
	 * hace un select de toda la tabla
	 * @param conexionGenerada
	 * @return
	 */
	ArrayList<LibroDto> seleccionaTodosLibros();
	
	void seleccionLibros(Scanner sc);	
	
	void modificarLibro(Scanner sc);
	
	void crearLibro(Scanner sc);
	
	void borrarLibros(Scanner sc);
}
