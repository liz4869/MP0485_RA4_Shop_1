package model;

public class Product {
    private int id;
    private String name;
    private double publicPrice;
    private double wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts;

    static double EXPIRATION_RATE = 0.60;

    public Product(String name, double wholesalerPrice, boolean available, int stock) {
        this.id = totalProducts + 1;
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.available = available;
        this.stock = stock;
        this.publicPrice = wholesalerPrice * 2;
        totalProducts++;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPublicPrice() { return publicPrice; }
    public double getWholesalerPrice() { return wholesalerPrice; }
    public boolean isAvailable() { return available; }
    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }
    public void setAvailable(boolean available) { this.available = available; }

    public void expire() {
        this.publicPrice = this.publicPrice * EXPIRATION_RATE;
    }

    @Override
    public String toString() {
        return "Id: " + id +
               ", Nombre: " + name +
               ", Precio público: " + publicPrice +
               ", Precio mayorista: " + wholesalerPrice +
               ", Disponible: " + available +
               ", Stock: " + stock;
    }
}
