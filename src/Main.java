import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Banco banco = new Banco("Mi Banco");

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de nextInt()

            switch (opcion) {
                case 1:
                    banco.agregarCuenta();
                    break;
                case 2:
                    banco.consultarCuenta();
                    break;
                case 3:
                    banco.borrarCuenta();
                    break;
                case 4:
                    banco.ingresarDinero();
                    break;
                case 5:
                    banco.retirarDinero();
                    break;
                case 6:
                    banco.listarCuentas();
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
    public static final int MAX_CUENTAS = 100;
    private static Scanner scanner = new Scanner(System.in);

    public Banco(String nombre) {
        this.nombre = nombre;
        cuentas = new CuentaBancaria[MAX_CUENTAS];
        this.numeroCuentas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean agregarCuenta() {
        if (numeroCuentas < MAX_CUENTAS) { // Verificamos que no se exceda el límite
            System.out.print("Ingrese IBAN de la cuenta: ");
            String iban = scanner.next();
            scanner.nextLine(); // Consumir la nueva línea después de next()
            System.out.print("Ingrese titular de la cuenta: ");
            String titular = scanner.nextLine();
            System.out.print("Ingrese saldo inicial de la cuenta: ");
            double saldo = scanner.nextDouble();

            CuentaBancaria cuenta = new CuentaBancaria(iban, titular, saldo);
            if (agregarCuenta(cuenta)) {
                System.out.println("Cuenta agregada exitosamente.");
                return true;
            } else {
                System.out.println("No se pudo agregar la cuenta. El banco está lleno.");
                return false;
            }
        } else {
            System.out.println("No se pudo agregar la cuenta. El banco está lleno.");
            return false;
        }
    }

    public boolean consultarCuenta() {
        System.out.print("Ingrese IBAN de la cuenta a consultar: ");
        String iban = scanner.next();
        String informe = consultarCuenta(iban);
        if (informe != null) {
            System.out.println(informe);
            return true;
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban);
            return false;
        }
    }

    public boolean borrarCuenta() {
        System.out.print("Ingrese IBAN de la cuenta a borrar: ");
        String iban = scanner.next();
        if (borrarCuenta(iban)) {
            System.out.println("Cuenta con IBAN " + iban + " borrada exitosamente.");
            return true;
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban);
            return false;
        }
    }

    public boolean ingresarDinero() {
        System.out.print("Ingrese IBAN de la cuenta en la que desea ingresar dinero: ");
        String iban = scanner.next();
        System.out.print("Ingrese la cantidad a ingresar: ");
        double cantidad = scanner.nextDouble();
        if (ingresar(iban, cantidad)) {
            System.out.println("Ingreso realizado exitosamente.");
            return true;
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban);
            return false;
        }
    }

    public boolean retirarDinero() {
        System.out.print("Ingrese IBAN de la cuenta de la que desea retirar dinero: ");
        String iban = scanner.next();
        System.out.print("Ingrese la cantidad a retirar: ");
        double cantidad = scanner.nextDouble();
        if (retirar(iban, cantidad)) {
            System.out.println("Retiro realizado exitosamente.");
            return true;
        } else {
            System.out.println("No se encontró la cuenta con IBAN: " + iban + " o saldo insuficiente.");
            return false;
        }
    }

    public void listarCuentas() {
        String informe = listadoCuentas();
        if (!informe.isEmpty()) {
            System.out.println("Listado de cuentas:");
            System.out.println(informe);
        } else {
            System.out.println("No hay cuentas registradas en el banco.");
        }
    }

    private boolean agregarCuenta(CuentaBancaria cuenta) {
        if (numeroCuentas < MAX_CUENTAS) {
            cuentas[numeroCuentas++] = cuenta;
            return true;
        }
        return false;
    }

    private String consultarCuenta(String iban) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(iban)) {
                return cuentas[i].toString();
            }
        }
        return null;
    }

    private boolean borrarCuenta(String iban) {
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

    private boolean ingresar(String iban, double cantidad) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(iban)) {
                cuentas[i].ingresar(cantidad);
                return true;
            }
        }
        return false;
    }

    private boolean retirar(String iban, double cantidad) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(iban)) {
                return cuentas[i].retirar(cantidad);
            }
        }
        return false;
    }

    private String listadoCuentas() {
        StringBuilder informe = new StringBuilder();
        for (int i = 0; i < numeroCuentas; i++) {
            informe.append(cuentas[i].toString()).append("\n");
        }
        return informe.toString();
    }
}
