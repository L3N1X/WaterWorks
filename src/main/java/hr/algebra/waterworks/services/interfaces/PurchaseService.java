package hr.algebra.waterworks.services.interfaces;

import hr.algebra.waterworks.shared.sessionmodels.Cart;

public interface PurchaseService {
    void initPurchaseFromCart(Cart cart);
}
