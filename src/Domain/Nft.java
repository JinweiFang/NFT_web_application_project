package Domain;

public class Nft {
    private int id;
    private int ownerId;
    private double price;
    private String picture;

    public Nft() {}
    public Nft(int id, int ownerId, double price, String picture) {
        this.id = id;
        this.ownerId = ownerId;
        this.price = price;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
