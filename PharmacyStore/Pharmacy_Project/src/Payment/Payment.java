package Payment;

import java.util.*;

public class Payment {
    private LinkedList<PaymentMethod> paymentMethods;

    public Payment() {
        paymentMethods = new LinkedList<>();
        paymentMethods.add(new CardPaymentMethod());
        paymentMethods.add(new CashPaymentMethod());
        paymentMethods.add(new OnlinePaymentMethod());
    }

    public void listPaymentMethods() {
        System.out.println("Available payment methods:");
        for (PaymentMethod pm : paymentMethods) {
            System.out.println(pm.getName());
        }
    }

    public void makePayment() {
        listPaymentMethods();
        System.out.print("Enter your choice (1-" + paymentMethods.size() + "): ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        if (choice >= 1 && choice <= paymentMethods.size()) {
            PaymentMethod paymentMethod = paymentMethods.get(choice - 1);
            paymentMethod.makePayment();
        } else {
            System.out.println("Invalid payment method choice.");
        }
    }
}
