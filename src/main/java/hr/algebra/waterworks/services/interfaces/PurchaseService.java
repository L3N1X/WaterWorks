package hr.algebra.waterworks.services.interfaces;

import hr.algebra.waterworks.dao.enums.PurchaseType;
import hr.algebra.waterworks.shared.dtos.ReceiptDto;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.sessionmodels.Cart;

import java.util.List;

public interface PurchaseService {
    String initPurchaseFromCart(Cart cart, PurchaseType purchaseType, UserDto user);
    List<ReceiptDto> getReceipts(String username);
    List<ReceiptDto> getAllReceipts();
}
