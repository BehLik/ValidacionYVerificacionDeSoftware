/*
 * Click nbfs://nbfs/NetBeansProjects/PatronSingleton/Templates/license-default.txt
 */
package com.mycompany.patronsingleton;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author alex
 */
public class PatronSingleton {

    // Códigos ANSI para colores
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(AZUL + "╔══════════════════════════════════════╗");
        System.out.println("║     🏪 Bienvenido al sistema de Papelería     ║");
        System.out.println("╚══════════════════════════════════════╝" + RESET);

        System.out.print("Ingrese su nombre de empleado: ");
        String nombreEmpleado = sc.nextLine();

        // Crear empleado identificado
        Empleado empleado = new Empleado(nombreEmpleado);

        // Cargar algunos productos iniciales (solo una vez)
        InventarioPapeleria inventario = InventarioPapeleria.getInstancia();
        if (inventario.estaVacio()) {
            inventario.agregarProducto("Lápiz", 50);
            inventario.agregarProducto("Cuaderno", 30);
            inventario.agregarProducto("Borrador", 25);
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println(AZUL + "\n--- Menú Principal ---" + RESET);
            System.out.println("1. Mostrar inventario");
            System.out.println("2. Vender producto");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero(sc, AMARILLO + "⚠️ Debe ingresar un número válido (1-3): " + RESET);

            switch (opcion) {
                case 1:
                    empleado.mostrarInventario();
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del producto: ");
                    String producto = sc.nextLine().trim();

                    // Validar nombre del producto (solo letras y espacios)
                    if (!producto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        System.out.println(ROJO + "❌ Nombre de producto no válido. Solo se permiten letras." + RESET);
                        break;
                    }

                    System.out.print("Ingrese la cantidad a vender: ");
                    int cantidad = leerEnteroPositivo(sc, AMARILLO + "⚠️ Ingrese una cantidad numérica válida mayor que 0: " + RESET);

                    empleado.venderProducto(producto, cantidad);
                    break;
                case 3:
                    continuar = false;
                    System.out.println(CYAN + "👋 Cerrando sesión de " + nombreEmpleado + "..." + RESET);
                    break;
                default:
                    System.out.println(AMARILLO + "⚠️ Opción no válida. Intente nuevamente." + RESET);
            }
        }
        sc.close();
    }

    // Método para leer enteros con manejo de error
    private static int leerEntero(Scanner sc, String mensajeError) {
        while (true) {
            try {
                int valor = Integer.parseInt(sc.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print(mensajeError);
            }
        }
    }

    // Método para leer solo números positivos
    private static int leerEnteroPositivo(Scanner sc, String mensajeError) {
        while (true) {
            try {
                int valor = Integer.parseInt(sc.nextLine());
                if (valor > 0) return valor;
                System.out.print(mensajeError);
            } catch (NumberFormatException e) {
                System.out.print(mensajeError);
            }
        }
    }

    // Método para normalizar texto (elimina acentos y pasa a minúscula)
    public static String normalizarTexto(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("\\p{M}", ""); // quita tildes
        return texto.toLowerCase().trim();
    }
}

// Clase Singleton: Inventario compartido
class InventarioPapeleria {

    private static InventarioPapeleria instancia;
    private Map<String, Integer> productos;

    private InventarioPapeleria() {
        productos = new HashMap<>();
        System.out.println("📦 Inventario de papelería creado.");
    }

    public static InventarioPapeleria getInstancia() {
        if (instancia == null) {
            instancia = new InventarioPapeleria();
        }
        return instancia;
    }

    public void agregarProducto(String nombre, int cantidad) {
        String clave = PatronSingleton.normalizarTexto(nombre);
        productos.put(clave, productos.getOrDefault(clave, 0) + cantidad);
        System.out.println("➕ Se agregaron " + cantidad + " unidades de " + nombre);
    }

    public boolean vender(String nombre, int cantidad) {
        String clave = PatronSingleton.normalizarTexto(nombre);
        if (!productos.containsKey(clave)) {
            System.out.println(PatronSingleton.ROJO + "❌ El producto '" + nombre + "' no existe en el inventario." + PatronSingleton.RESET);
            return false;
        }
        int stockActual = productos.get(clave);
        if (cantidad > stockActual) {
            System.out.println(PatronSingleton.AMARILLO + "⚠️ No hay suficiente stock. Solo quedan " + stockActual + " unidades." + PatronSingleton.RESET);
            return false;
        }
        productos.put(clave, stockActual - cantidad);
        System.out.println(PatronSingleton.VERDE + "💰 Venta realizada: " + cantidad + " unidades de " + nombre + PatronSingleton.RESET);
        return true;
    }

    public void mostrarProductos() {
        System.out.println("\n🧾 Inventario actual:");
        System.out.printf("%-15s | %s\n", "Producto", "Stock");
        System.out.println("-----------------------------");
        for (Map.Entry<String, Integer> p : productos.entrySet()) {
            String nombreProducto = capitalizar(p.getKey());
            System.out.printf("%-15s | %d\n", nombreProducto, p.getValue());
        }
    }

    private String capitalizar(String texto) {
        if (texto.isEmpty()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }
}

// Clase Empleado (usa el Singleton)
class Empleado {

    private String nombre;
    private InventarioPapeleria inventario;

    public Empleado(String nombre) {
        this.nombre = nombre;
        this.inventario = InventarioPapeleria.getInstancia();
        System.out.println("👤 Empleado " + nombre + " ha iniciado sesión.");
    }

    public void mostrarInventario() {
        System.out.println("\n👤 Inventario visto por " + nombre + ":");
        inventario.mostrarProductos();
    }

    public void venderProducto(String producto, int cantidad) {
        System.out.println("\n🛒 " + nombre + " intenta vender " + cantidad + " unidades de " + producto + "...");
        inventario.vender(producto, cantidad);
    }

    public InventarioPapeleria getInventario() {
        return inventario;
    }
}
