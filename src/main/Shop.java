package main;

import model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {

    private double cash = 100.00;

    private ArrayList<Product> inventory;
    private ArrayList<Sale> sales;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.initSession(); // LOGIN EMPLEADO
        shop.loadInventory();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver total de las ventas");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> shop.showCash();
                case 2 -> shop.addProduct();
                case 3 -> shop.addStock();
                case 4 -> shop.setExpired();
                case 5 -> shop.showInventory();
                case 6 -> shop.sale();
                case 7 -> shop.showSales();
                case 8 -> shop.showTotalSales();
                case 9 -> shop.deleteProduct();
                case 10 -> exit = true;
            }

        } while (!exit);
    }

    
    public void initSession() {
        Scanner sc = new Scanner(System.in);
        Employee emp = new Employee("test", Employee.EMPLOYEE_ID, Employee.PASSWORD);

        boolean ok = false;

        while (!ok) {
            System.out.print("Número de empleado: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            if (emp.login(id, pass)) {
                System.out.println("Acceso concedido.");
                ok = true;
            } else {
                System.out.println("Datos incorrectos. Intente de nuevo.");
            }
        }
    }

   
    public void loadInventory() {
        inventory.add(new Product("Manzana", 10.00, true, 10));
        inventory.add(new Product("Pera", 20.00, true, 20));
        inventory.add(new Product("Hamburguesa", 30.00, true, 30));
        inventory.add(new Product("Fresa", 5.00, true, 20));
    }

    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        if (findProduct(name) != null) {
            System.out.println("Este producto ya existe.");
            return;
        }

        inventory.add(new Product(name, wholesalerPrice, true, stock));
        System.out.println("Producto añadido.");
    }

    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Cantidad a añadir: ");
            int stock = scanner.nextInt();
            product.setStock(product.getStock() + stock);
            System.out.println("Stock actualizado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product p = findProduct(name);
        if (p != null) {
            p.expire();
            System.out.println("Producto marcado como caducado.");
        }
    }

    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product p : inventory) {
            System.out.println(p);
        }
    }

    public void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del producto a eliminar: ");
        String name = sc.nextLine();

        Product p = findProduct(name);

        if (p != null) {
            inventory.remove(p);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    
    public void sale() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Realizar venta, escribir nombre cliente:");
    String clientName = sc.nextLine();

    // Crear cliente con saldo inicial 50?
    Client client = new Client(clientName);

    // Crear venta
    Sale sale = new Sale(client);

    String name = "";
    while (!name.equals("0")) {
        System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
        name = sc.nextLine();

        if (name.equals("0")) break;

        Product product = findProduct(name);

        if (product != null && product.isAvailable()) {
            sale.addProduct(product);

            // Reducir stock
            product.setStock(product.getStock() - 1);

            if (product.getStock() == 0) {
                product.setAvailable(false);
            }

            System.out.println("Producto añadido con éxito");
        } else {
            System.out.println("Producto no encontrado o sin stock");
        }
    }

    // Calcular total con IVA
    double total = sale.getTotal() * TAX_RATE;

    // Intentar cobrar al cliente
    boolean paid = client.pay(total);

    if (paid) {
        System.out.printf("Venta realizada con éxito, total: %.2f?\n", total,"?");
    } else {
        double deuda = -client.getBalance();
        System.out.printf("Venta realizada con éxito, total: %.2f?\n", total,"?");
System.out.printf("Cliente debe: %.2f?\n", deuda,"?");

    }

    // Registrar venta
    sales.add(sale);

    // Sumar a caja
    cash += total;
}


    public void showSales() {
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        for (Sale s : sales) {
            System.out.println(s);
        }
    }

    public void showTotalSales() {
        double total = 0;
        for (Sale s : sales) total += s.getTotal();
        System.out.println("Total de ventas: " + total);
    }

    public Product findProduct(String name) {
        for (Product p : inventory) {
            if (p.getName().equals(name)) return p;
        }
        return null;
    }
}
