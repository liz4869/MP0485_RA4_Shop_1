package model;

import java.util.ArrayList;

public class Sale {

    private Client client;
    private ArrayList<Product> products;

    public Sale(Client client) {
        this.client = client;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public double getTotal() {
        double total = 0;
        for (Product p : products) {
            total += p.getPublicPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Venta a cliente: " + client.getName() +
               " | Productos: " + products.size() +
               " | Total: " + getTotal() + "?";
    }
}
