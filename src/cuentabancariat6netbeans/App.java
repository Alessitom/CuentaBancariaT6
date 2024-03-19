/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cuentabancariat6netbeans;

import java.util.Scanner;

/**
 *
 * @author Alex
 */
public class App {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        Banco banco = null;
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        String nombreBanco =null;

        while (!salir) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    if (banco == null) {
                        System.out.print("Ingrese el nombre del banco: ");
                        nombreBanco = scanner.nextLine();
                        banco = new Banco(nombreBanco);
                        System.out.println("Banco creado: " + banco.getNombre());
                    } else {
                        System.out.print("Ya existe un banco. ¿Desea sobrescribirlo? (S/N): ");
                        String confirmacion = scanner.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            System.out.print("Ingrese el nombre del banco: ");
                            nombreBanco = scanner.nextLine();
                            banco = new Banco(nombreBanco);
                            System.out.println("Banco creado: " + banco.getNombre());
                        }
                    }
                    break;

                case 2:
                    if (banco != null) {
                        System.out.print("Ingrese el IBAN de la cuenta: ");
                        String iban = scanner.nextLine();
                        System.out.print("Ingrese el titular de la cuenta: ");
                        String titular = scanner.nextLine();
                        System.out.print("Ingrese el saldo inicial de la cuenta: ");
                        double saldoInicial = scanner.nextDouble();
                      
                        Cuenta cuenta = new Cuenta(iban, titular, saldoInicial);
                        if (banco.agregarCuenta(cuenta)) {
                            System.out.println("Cuenta agregada al banco.");
                        } else {
                            System.out.println("No se pudo agregar la cuenta. El banco está lleno.");
                        }
                    } else {
                        System.out.println("Debe crear un banco primero.");
                    }
                    break;

                case 3:
                    if (banco != null) {
                        System.out.println("Cuentas actuales:");
                        System.out.println(banco.listadoCuentas());
                        System.out.print("Ingrese el IBAN de la cuenta: ");
                        String ibanIngreso = scanner.nextLine();
                        System.out.print("Ingrese la cantidad a ingresar: ");
                        double cantidadIngreso = scanner.nextDouble();
                        scanner.nextLine(); // Limpiar el buffer de entrada
                        if (cantidadIngreso > 0) {
                            if (banco.ingresar(ibanIngreso, cantidadIngreso)) {
                                System.out.println("Ingreso realizado con éxito.");
                            } else {
                                System.out.println("No se pudo realizar el ingreso. La cuenta no existe.");
                            }
                        } else {
                            System.out.println("La cantidad a ingresar debe ser positiva.");
                        }
                    } else {
                        System.out.println("Debe crear un banco primero.");
                    }
                    break;

                case 4:
                    if (banco != null) {
                        System.out.println("Cuentas actuales:");
                        System.out.println(banco.listadoCuentas());
                        System.out.print("Ingrese el IBAN de la cuenta: ");
                        String ibanRetiro = scanner.nextLine();
                        System.out.print("Ingrese la cantidad a retirar: ");
                        double cantidadRetiro = scanner.nextDouble();
                        scanner.nextLine(); // Limpiar el buffer de entrada
                        if (cantidadRetiro > 0) {
                            if (banco.retirar(ibanRetiro, cantidadRetiro)) {
                                System.out.println("Retiro realizado con éxito.");
                            } else {
                                System.out.println("No se pudo realizar el retiro. La cuenta no existe o saldo insuficiente.");
                            }
                        } else {
                            System.out.println("La cantidad a retirar debe ser positiva.");
                        }
                    } else {
                        System.out.println("Debe crear un banco primero.");
                    }
                    break;

                case 5:
                    if (banco != null) {
                        System.out.print("Ingrese el IBAN de la cuenta: ");
                        String ibanConsulta = scanner.nextLine();
                        String datosCuenta = banco.consultarCuenta(ibanConsulta);
                        if (datosCuenta != null) {
                            System.out.println("Datos de la cuenta:");
                            System.out.println(datosCuenta);
                        } else {
                            System.out.println("La cuenta no existe.");
                        }
                    } else {
                        System.out.println("Debe crear un banco primero.");
                    }
                    break;

                case 6:
                    if (banco != null) {
                        System.out.println("Informe del banco " +nombreBanco+" :");
                        System.out.println(banco.listadoCuentas());
                    } else {
                        System.out.println("Debe crear un banco primero.");
                    }
                    break;

                case 7:
                    if (banco != null) {
                        System.out.print("Ingrese el IBAN de la cuenta a borrar: ");
                        String ibanBorrar = scanner.nextLine();
                        if (banco.borrarCuenta(ibanBorrar)) {
                            System.out.println("Cuenta borrada con éxito.");
                        } else {
                            System.out.println("No se pudo borrar la cuenta. La cuenta no existe.");
                        }
                    } else {
                        System.out.println("Debe crear un banco primero.");
                    }
                    break;

                case 8:
                    salir = true;
                    break;

                default:
                    
                    System.out.println("Opción no válida. Por favor, seleccione una opción del menú.");
            }
        }
        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\nMenú:");
            System.out.println("1. Crear banco");
            System.out.println("2. Agregar cuenta");
            System.out.println("3. Ingresar en cuenta");
            System.out.println("4. Retirar de cuenta");
            System.out.println("5. Ver datos de cuenta");
            System.out.println("6. Ver informe del banco");
            System.out.println("7. Borrar cuenta");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");
    }

}
