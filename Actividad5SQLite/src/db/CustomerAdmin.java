package db;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerAdmin {
	static final Logger logger = LogManager.getLogger();
	// version de la aplicación
	private static final String APP_VERSION = "v1.0";
	// 1. Base de datos a conectarse en la variable llamada DB
	// Escribe tu codigo aqui {
	static final String DB = "Chinook.db";

	// }
	// String con 100 guiones
	public static final String GUIONES = new String(new char[100]).replace("\0", "-");

	public static void main(String[] args) throws SQLException {
		
		int seleccion;
		Chinook chinook = null;
		try (Scanner scanner = new Scanner(System.in)){
			/* 
			 * 2. Validar si el objeto chinook es null 
			 * si lo es crear un nuevo objeto Chinook con parámetro 
			 * la variable DB creada en el punto 1.
			 */
			// Escribe tu codigo aqui {
			if(chinook == null) {
				chinook = new Chinook(DB);
			}

			// }
			while (true){
				System.out.format("Administrador de Clientes %s\n\n", APP_VERSION);
				// Inicio Menu
				System.out.println("Opciones: ");
				System.out.println("(1) Listar Clientes");
				System.out.println("(2) Crear Cliente");
				System.out.println("(3) Actualizar Cliente");
				System.out.println("(4) Borrar Cliente");
				System.out.println("(0) Salir");
				System.out.println("\nPor favor ingrese una opción: ");
				// Fin de Menu
				// Try Anidado
				try{
					// Asigna token Integer parseado
					seleccion = scanner.nextInt();
					switch(seleccion){
					case 0:
						System.out.println("Saliendo..");
						logger.info("Saliendo...");
						return;
					case 1:
						// Lista Registros
						ArrayList<Customer> customers = chinook.getCustomers();
						logger.info("Se encontraron {} clientes", customers.size());
						System.out.println(String.format("%-5s %-10s %-20s %-25s %-15s %-5s",
								"Id", "Nombre", "Apellido", "Compañia", "Email", "Empleado de Soporte"));
						System.out.println(GUIONES);
						customers.forEach(System.out::println);

						break;
					case 2:
						// Crea Registros
						// Obteniendo los datos para crear el cliente
						Customer newCustomer = new Customer();
						System.out.println("Nombre:");
						newCustomer.setFirstName(scanner.next());
						System.out.println("Apellido:");
						newCustomer.setLastName(scanner.next());
						System.out.println("Compañía:");
						newCustomer.setCompany(scanner.next());
						System.out.println("Email:");
						newCustomer.setEmail(scanner.next());
						System.out.println("Empleado de Soporte:");
						newCustomer.setSupportRepId(scanner.nextInt());
						
						logger.info("Creando registro..");
						if (chinook.addCustomer(newCustomer))
							logger.info("Registro creado.");
						
						break;
					case 3: 
						// Actualiza registros
						// Creando objeto para actualizar
						Customer updCustomer = new Customer();
						System.out.println("Id del Cliente a actualizar:");
						updCustomer.setCustomerId(scanner.nextInt());
						System.out.println("Nombre:");
						updCustomer.setFirstName(scanner.next());
						System.out.println("Apellido:");
						updCustomer.setLastName(scanner.next());
						System.out.println("Compañía:");
						updCustomer.setCompany(scanner.next());
						System.out.println("Email:");
						updCustomer.setEmail(scanner.next());
						System.out.println("Empleado de Soporte:");
						updCustomer.setSupportRepId(scanner.nextInt());

						// Ejecuta el métod updateCustomer para actualizar
						int registrosActualizados = chinook.updateCustomer(updCustomer);
						logger.info("{} registro actualizado.", registrosActualizados);
						break;
					case 4:
						// Borra Registros
						Customer delCustomer = new Customer();
						System.out.println("Id del Cliente a borrar:");
						delCustomer.setCustomerId(scanner.nextInt());

						// Ejecuta el método deleteCustomer
						int registroActualizado = chinook.deleteCustomer(delCustomer);
						logger.info("{} registro borrado.", registroActualizado);
						break;
					default:
						System.err.println("Opción inválida.");
						logger.error("Opción inválida: {}", seleccion);
						break;
					}

				} catch (InputMismatchException|SQLException ex){
					logger.error("{}: {}", ex.getClass(), ex.getMessage());
					System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
					scanner.next();
				}
			}
		} catch (Exception ex){
			logger.error("{}: {}", ex.getClass(), ex.getMessage());
			System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
		} finally{
			/* 3. Validar si la variable chinook es null, si lo es obtener el estado de la conexión 
			 * del objeto mediante getConnection().isClosed() y 
			 * cerrarla con el método chinook.getConnection().close() si no está cerrada
			 */
			// Escribe tu codigo aqui {
			if(chinook != null) {
				if(!chinook.getConnection().isClosed()) {
					chinook.getConnection().close();
				}
			}

			// }
		}
	}
}
