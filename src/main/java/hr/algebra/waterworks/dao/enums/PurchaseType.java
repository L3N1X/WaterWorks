package hr.algebra.waterworks.dao.enums;

public enum PurchaseType {
    CASH(1),
    PAYPAL(2);

    private final int intValue;

    PurchaseType(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
