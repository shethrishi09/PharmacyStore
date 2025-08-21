package Pharmacy_System;

public class MedicinePurchase {
    public int medicineId;
    public int quantity;

    public MedicinePurchase(int medicineId, int quantity) {
        this.medicineId = medicineId;
        this.quantity = quantity;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }
}