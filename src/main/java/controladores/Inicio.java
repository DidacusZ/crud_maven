package controladores;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import dtos.LibroDto;
import servicios.ConexionImplementacion;
import servicios.ConexionInterfaz;
import servicios.ConsultasImplementacion;
import servicios.ConsultasInterfaz;
import servicios.MenuInterfaz;
import servicios.MenuImplementacion;

public class Inicio {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ConsultasInterfaz consultas = new ConsultasImplementacion();
		ArrayList<LibroDto>listaLibros =new ArrayList<>();

		MenuInterfaz menu = new MenuImplementacion();
		Scanner sc = new Scanner(System.in);
		int n = 0;
		
		ArrayList<Integer> lista = new ArrayList<>();
		lista.add(12341);
		System.out.println(lista.get(0));
		
		try {

			do {
				menu.Menu();
				n = sc.nextInt();

				switch (n) {
				
				case 0:
					System.out.println("APP CERRADA");
					break;

				case 1://todos los libros
					listaLibros = consultas.seleccionaTodosLibros();
					System.out.println("---todos los libros---");
					for (int i = 0; i < listaLibros.size(); i++)
						System.out.println(listaLibros.get(i).toString());
					
					System.out.print("pulsa una tecla y dale a ENTER para volver al menu: ");
					sc.next();
					break;
					
				case 2://libro particular					
					consultas.seleccionLibros(sc);														
					break;

				case 3://modificar
					consultas.modificarLibro(sc);
					break;

				case 4://borrar
					consultas.borrarLibros(sc);
					break;
					
				case 5://crear libro
					consultas.crearLibro(sc);
					break;
					
					
				}

			} while (n != 0);
			
		} catch (Exception e) {
			System.out.println("[ERROR-Main] Se ha producido un error al ejecutar la aplicación: " + e);
		}
		
		
		
		
		
		
		
		
		
		
		
	}

}