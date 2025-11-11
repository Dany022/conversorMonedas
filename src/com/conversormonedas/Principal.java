package com.conversormonedas;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsultaMoneda consulta = new ConsultaMoneda();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== 🪙 CONVERSOR DE MONEDAS MXN ===");
            System.out.println("1) MXN → USD (Dólar Americano)");
            System.out.println("2) MXN → EUR (Euro)");
            System.out.println("3) MXN → BRL (Real Brasileño)");
            System.out.println("4) MXN → GBP (Libra Esterlina)");
            System.out.println("5) MXN → JPY (Yen Japonés)");
            System.out.println("6) MXN → COP (Peso Colombiano)");
            System.out.println("7) Salir");
            System.out.print("Elija una opción: ");

            int opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingrese la cantidad en MXN: ");
                double cantidad = scanner.nextDouble();
                convertirDesdeMXN(consulta, opcion, cantidad);

                // Preguntar si quiere hacer otra conversión
                salir = preguntarSalida(scanner);

            } else if (opcion == 7) {
                salir = true;
            } else {
                System.out.println("❌ Opción inválida");
            }
        }

        mostrarMensajeDespedida();
        scanner.close();
    }

    private static void convertirDesdeMXN(ConsultaMoneda consulta, int opcion, double cantidad) {
        // Primero obtenemos las tasas desde MXN
        Moneda monedaMXN = consulta.buscaMoneda("MXN");

        if (monedaMXN != null && monedaMXN.getConversion_rates() != null) {
            String monedaDestino = "";

            switch (opcion) {
                case 1: monedaDestino = "USD"; break;
                case 2: monedaDestino = "EUR"; break;
                case 3: monedaDestino = "BRL"; break;
                case 4: monedaDestino = "GBP"; break;
                case 5: monedaDestino = "JPY"; break;
                case 6: monedaDestino = "COP"; break;
            }

            Double tasa = monedaMXN.getConversion_rates().get(monedaDestino);

            if (tasa != null && tasa > 0) {
                double resultado = cantidad * tasa;
                System.out.printf("\n💱 $%.2f MXN = $%.2f %s%n", cantidad, resultado, monedaDestino);
                System.out.printf("   Tasa de cambio: 1 MXN = %.4f %s%n", tasa, monedaDestino);

                // Mostramos también la tasa inversa para referencia
                double tasaInversa = 1 / tasa;
                System.out.printf("   Tasa inversa: 1 %s = %.4f MXN%n", monedaDestino, tasaInversa);
            } else {
                System.out.println("❌ No se pudo obtener la tasa de cambio para " + monedaDestino);
            }
        } else {
            System.out.println("❌ Error al obtener datos de la API");
        }
    }
    private static boolean preguntarSalida(Scanner scanner) {
        System.out.print("\n¿Desea hacer otra conversión? (S/N): ");
        String respuesta = scanner.next().toUpperCase();

        if (respuesta.equals("N") || respuesta.equals("NO")) {
            return true; // Sí quiere salir
        } else if (respuesta.equals("S") || respuesta.equals("SI")) {
            return false; // No quiere salir, continuar
        } else {
            System.out.println("❌ Respuesta no válida, continuando...");
            return false;
        }
    }

    private static void mostrarMensajeDespedida() {
        System.out.println("\n=================================");
        System.out.println("¡GRACIAS POR USAR EL CONVERSOR! 💰");
        System.out.println("¡Vuelve pronto! 👋");
        System.out.println("=================================");
    }
}