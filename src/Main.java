import java.util.Scanner;



public class Main {
    // Declaramos el Scanner como una variable estática de la clase
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        Banco banco = new Banco("Mi Banco");

        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del Scanner

            switch (opcion) {
                case 1:
                    agregarCuenta(banco);
                    break;
                case 2:
                    consultarCuenta(banco);
                    break;
                case 3:
                    borrarCuenta(banco);
                    break;
                case 4:
                    ingresarDinero(banco);
                    break;
                case 5:
                    retirarDinero(banco);
                    break;
                case 6:
                    listarCuentas(banco);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción del menú.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Agregar cuenta");
        System.out.println("2. Consultar cuenta");
        System.out.println("3. Borrar cuenta");
        System.out.println("4. Ingresar dinero");
        System.out.println("5. Retirar dinero");
        System.out.println("6. Listar cuentas");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void agregarCuenta(Banco banco) {
        System.out.print("Ingrese IBAN de la cuenta: ");
        String iban = scanner.next();
        System.out.print("Ingrese titular de la cuenta: ");
        String titular = scanner.next();
        System.out.print("Ingrese saldo inicial de la cuenta: ");
        double saldo = scanner.nextDouble();

        CuentaBancaria cuenta = new CuentaBancaria(iban, titular, saldo);
        if (banco.agregarCuenta(cuenta)) {
            System.out.println("Cuenta agregada exitosamente.");
        } else {
            System.out.println("No se pudo agregar la cuenta. El banco está lleno.");
        }
    }

    public static void consultarCuenta(Banco banco) {
        System.out.print("Ingrese IBAN de la cuenta a consultar: ");
        String iban = scanner.next();
        String informe = banco.consultarCuenta(iban);
        if (informe != null) {
            System.out.println(informe);
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban);
        }
    }

    public static void borrarCuenta(Banco banco) {
        System.out.print("Ingrese IBAN de la cuenta a borrar: ");
        String iban = scanner.next();
        if (banco.borrarCuenta(iban)) {
            System.out.println("Cuenta con IBAN " + iban + " borrada exitosamente.");
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban);
        }
    }

    public static void ingresarDinero(Banco banco) {
        System.out.print("Ingrese IBAN de la cuenta en la que desea ingresar dinero: ");
        String iban = scanner.next();
        System.out.print("Ingrese la cantidad a ingresar: ");
        double cantidad = scanner.nextDouble();
        if (banco.ingresar(iban, cantidad)) {
            System.out.println("Ingreso realizado exitosamente.");
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban);
        }
    }

    public static void retirarDinero(Banco banco) {
        System.out.print("Ingrese IBAN de la cuenta de la que desea retirar dinero: ");
        String iban = scanner.next();
        System.out.print("Ingrese la cantidad a retirar: ");
        double cantidad = scanner.nextDouble();
        if (banco.retirar(iban, cantidad)) {
            System.out.println("Retiro realizado exitosamente.");
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban + " o saldo insuficiente.");
        }
    }

    public static void listarCuentas(Banco banco) {
        String informe = banco.listadoCuentas();
        if (!informe.isEmpty()) {
            System.out.println("Listado de cuentas:");
            System.out.println(informe);
        } else {
            System.out.println("No hay cuentas registradas en el banco.");
        }
    }
}

class CuentaBancaria {
    private String iban;
    private String titular;
    private double saldo;

    public CuentaBancaria(String iban, String titular, double saldo) {
        this.iban = iban;
        this.titular = titular;
        this.saldo = saldo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void ingresar(double cantidad) {
        saldo += cantidad;
    }

    public boolean retirar(double cantidad) {
        if (saldo >= cantidad) {
            saldo -= cantidad;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cuenta Bancaria{" +
                "IBAN='" + iban + '\'' +
                ", Titular='" + titular + '\'' +
                ", Saldo=" + saldo +
                '}';
    }
}

class Banco {
    private String nombre;
    private CuentaBancaria[] cuentas;
    private int numeroCuentas;

    public Banco(String nombre) {
        this.nombre = nombre;
        this.cuentas = new CuentaBancaria[100];
        this.numeroCuentas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean agregarCuenta(CuentaBancaria cuenta) {
        if (numeroCuentas < 100) {
            cuentas[numeroCuentas++] = cuenta;
            return true;
        }
        return false;
    }

    public String consultarCuenta(String iban) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(iban)) {
                return cuentas[i].toString();
            }
        }
        return null;
    }

    public boolean borrarCuenta(String iban) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(iban)) {
                cuentas[i] = cuentas[numeroCuentas - 1];
                cuentas[numeroCuentas - 1] = null;
                numeroCuentas--;
                return true;
            }
        }
        return false;
    }

    public boolean ingresar(String iban, double cantidad) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(iban)) {
                cuentas[i].ingresar(cantidad);
                return true;
            }
        }
        return false;
    }

    public boolean retirar(String iban, double cantidad) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(iban)) {
                return cuentas[i].retirar(cantidad);
            }
        }
        return false;
    }

    public String listadoCuentas() {
        StringBuilder informe = new StringBuilder();
        for (int i = 0; i < numeroCuentas; i++) {
            informe.append(cuentas[i].toString()).append("\n");
        }
        return informe.toString();
    }
}
