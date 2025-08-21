/*
 * ==========================================================================
 * ======================== PurchaseHistory Class ===========================
 * ==========================================================================
 */
package Admin;
import java.sql.Date;



public class PurchaseHistory {
    public String name;
    public int medicineId;
    public int quantity;
    public Date purchaseDate;

    public PurchaseHistory(String name, int medicineId, int quantity, Date purchaseDate) {
        this.name = name;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }


    public String getName() {
        return name;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }
}
