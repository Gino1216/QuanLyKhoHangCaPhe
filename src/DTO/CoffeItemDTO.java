/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author hjepr
 */
public class CoffeItemDTO {

    private int stt;
    private String coffeeCode;
    private String coffeeName;
    private String coffeeType;
    private double weight;
    private long price;
    private int quantity;

    public CoffeItemDTO(int stt, String coffeeCode, String coffeeName, String coffeeType,
            double weight, long price, int quantity) {
        this.stt = stt;
        this.coffeeCode = coffeeCode;
        this.coffeeName = coffeeName;
        this.coffeeType = coffeeType;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getCoffeeCode() {
        return coffeeCode;
    }

    public void setCoffeeCode(String coffeeCode) {
        this.coffeeCode = coffeeCode;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object[] toTableRow() {
        return new Object[]{stt, coffeeCode, coffeeName, coffeeType, weight, price, quantity};
    }
}
