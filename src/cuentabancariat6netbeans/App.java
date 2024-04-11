/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cuentabancariat6netbeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Alex
 */
public class App {

    private static final String PATHNAME = "./archivos/";

    /**
     * @param args the command line arguments
     * @throws cuentabancariat6netbeans.DNIException
     */
    public static void main(String[] args) throws DNIException {
        Banco banco = null;
        Comparador comparador = null;

        try (Scanner scanner = new Scanner(System.in)) {
            boolean salir = false;
            String nombreBanco = null;
            String Documento = null;

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
                            boolean ibanValido = false;

                            while (!ibanValido) {
                                if (banco.existeCuenta(iban)) {
                                    System.out.println("No puedes usar este IBAN, ya está en uso, porfavor pon uno válido:");
                                    iban = scanner.nextLine();
                                } else {
                                    System.out.println("IBAN creado con exito");
                                    ibanValido = true;
                                }

                            }
                            System.out.print("Ingrese el titular de la cuenta: ");
                            String titular = scanner.nextLine();
                            boolean documentoValido = false;

                            while (!documentoValido) {
                                System.out.println("Ingrese tu DNI o tu NIE");
                                Documento = scanner.nextLine();

                                if (Documento.toUpperCase().startsWith("X") || Documento.toUpperCase().startsWith("Y") || Documento.toUpperCase().startsWith("Z")) {
                                    // Validar NIE
                                    try {
                                        if (Cuenta.validarNIE(Documento)) {
                                            System.out.println("El NIE " + Documento + " es válido.");
                                            documentoValido = true;
                                        }
                                    } catch (DNIException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                } else {
                                    // Validar NIF
                                    try {
                                        if (Cuenta.validarNIF(Documento)) {
                                            System.out.println("El DNI " + Documento + " es válido.");
                                            documentoValido = true;
                                        }
                                    } catch (DNIException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                            }

                            System.out.print("Ingrese el saldo inicial de la cuenta: ");
                            double saldoInicial = scanner.nextDouble();

                            Cuenta cuenta = new Cuenta(iban, titular, saldoInicial, Documento);
                            if (banco.agregarCuenta(cuenta)) {
                                System.out.println("Cuenta agregada al banco.");
                            } else {
                                System.out.println("No se pudo agregar la cuenta.");
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

                            double saldo = banco.informaSaldo(ibanIngreso);

                            if (saldo != -1) {
                                System.out.println("Saldo actual de la cuenta: " + saldo);

                                System.out.print("Ingrese la cantidad a ingresar: ");
                                double cantidadIngreso = scanner.nextDouble();
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
                                System.out.println("La cuenta con IBAN " + ibanIngreso + " no existe.");
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
                            double saldo = banco.informaSaldo(ibanRetiro);

                            if (saldo != -1) {
                                System.out.println("Saldo actual de la cuenta: " + saldo);

                                System.out.print("Ingrese la cantidad a retirar: ");
                                double cantidadRetiro = scanner.nextDouble();

                                banco.retirar(ibanRetiro, cantidadRetiro);
                                System.out.println("Retiro realizado.");

                            } else {
                                System.out.println("La cuenta con IBAN " + ibanRetiro + " no existe.");
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
                            System.out.println("Informe del banco " + nombreBanco + " :");
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
                        if (banco != null) {
                            System.out.println("Banco rellenado correctamente.");
                            banco.rellenarCuentas();
                        } else {
                            System.out.println("Debe crear un banco primero.");
                        }

                        break;

                    case 9:
                        if (banco != null) {
                            System.out.println("Ordenando cuentas por saldo ascendente...");
                            banco.ordenarCuentasPorSaldoAscendente();
                            System.out.println("Cuentas ordenadas correctamente.");
                        } else {
                            System.out.println("Debe crear un banco primero.");
                        }
                        break;

                    case 10:
                        if (banco != null) {
                            System.out.println("Ordenando cuentas por saldo descendente...");
                            banco.ordenarCuentasPorSaldoDescendente();
                            System.out.println("Cuentas ordenadas correctamente.");
                        } else {
                            System.out.println("Debe crear un banco primero.");
                        }
                        break;

                    case 11:
                        if (banco != null) {

                            System.out.println("Ordenando cuentas por IBAN descendente...");
                            banco.ordenarCuentasPorIBANDescendente();
                            System.out.println("Cuentas ordenadas correctamente.");

                        } else {
                            System.out.println("Debe crear un banco primero.");
                        }
                        break;

                    case 12:
                        if (banco != null) {

                            System.out.println("Ordenando cuentas por IBAN Ascendente...");
                            banco.ordenarCuentasPorIBANAscendente();
                            System.out.println("Cuentas ordenadas correctamente.");

                        } else {
                            System.out.println("Debe crear un banco primero.");
                        }
                        break;

                    case 13:
                        if (banco != null) {
                            System.out.println("Ordenando por Titular...");
                            banco.ordenarCuentasPorTitularYIBAN();
                            System.out.println("Cuentas ordenadas correctamente.");

                        } else {
                            System.out.println("Debe crear un banco primero.");
                        }
                        break;

                    case 14:
                        if (banco != null) {
                            System.out.print("Ingrese el IBAN de la cuenta que desea consultar: ");
                            String ibanConsulta = scanner.nextLine();
                            String datosCuenta = banco.consultarCuenta(ibanConsulta);
                            if (datosCuenta != null) {
                                System.out.println("Datos de la cuenta:");
                                System.out.println(datosCuenta);
                            } else {
                                System.out.println("La cuenta con IBAN " + ibanConsulta + " no existe.");
                            }
                        } else {
                            System.out.println("Debe crear un banco primero.");
                        }
                        break;

                    case 15:
                        if (banco == null) {
                            System.out.println("BANCO NO CREADO");
                            break;
                        }
                        guardarDatos(banco, banco.getNombre() + ".dat");
                        break;

                    case 16:
                        if (banco != null) {
                            System.out.println("Ya hay datos creados del banco " + banco.getNombre() + ". Si continúa se perderán. ¿Desea continuar?");
                            String respuesta;
                            do {
                                System.out.println("Introduzca S o N:");
                                respuesta = scanner.nextLine();
                            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));

                            if (respuesta.equalsIgnoreCase("s")) {
                                banco = recuperarDatos(banco.getNombre() + ".dat");
                            }
                        } else {
                            System.out.println("No hay banco creado para cargar datos.");
                        }
                        break;

                    case 17:

                        System.out.println("Saliendo del programa...");
                        salir = true;
                        break;

                    default:

                        System.out.println("Opción no válida. Por favor, seleccione una opción del menú.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Porfavor introduzca un número");

        }
    }

    public static void mostrarMenu() {
        System.out.println("*******************************");
        System.out.println("Menú:");
        System.out.println("1. Crear banco");
        System.out.println("2. Agregar cuenta");
        System.out.println("3. Ingresar en cuenta");
        System.out.println("4. Retirar de cuenta");
        System.out.println("5. Ver datos de cuenta");
        System.out.println("6. Ver informe del banco");
        System.out.println("7. Borrar cuenta");
        System.out.println("8. Rellenar banco con cuentas");
        System.out.println("9. Ordenar cuentas(Descendente por saldo)");
        System.out.println("10.Ordenar cuentas(Ascendente por saldo)");
        System.out.println("11.Ordenar cuentas(Descendente por IBAN)");
        System.out.println("12.Ordenar cuentas(Ascendente por IBAN)");
        System.out.println("13.Ordenar cuentas(Por titular)");
        System.out.println("14.Consultar cuenta(Por clave)");
        System.out.println("15.Guardar Datos");
        System.out.println("16.Cargar Datos.");
        System.out.println("17.Salir");
        System.out.println("*******************************");
        System.out.print("Seleccione una opción: ");
    }

    public static void guardarDatos(Banco banco, String archivo) {
        try {
            banco.guardarEstado(PATHNAME + archivo);
            System.out.println("Curso guardado en " + PATHNAME + archivo);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Banco recuperarDatos(String archivo) {
        try {
            Banco bancoRecuperado = Banco.cargarEstado(PATHNAME + archivo);
            System.out.println("Curso recuperado de " + PATHNAME + archivo);
            return bancoRecuperado;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

}
