package com.example.demo.services;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Cart;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        if (purchase.getCustomer() == null || purchase.getCustomer().getId() == null) {
            throw new RuntimeException("Customer cannot be null");
        }

        if (purchase.getCart() == null) {
            throw new RuntimeException("Cart cannot be null");
        }

        if (purchase.getCartItems() == null || purchase.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart must contain at least one item");
        }

        Customer customer = customerRepository.findById(purchase.getCustomer().getId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = purchase.getCart();
        cart.setId(null);
        cart.setCustomer(customer);
        cart.setCreate_date(new Date());
        cart.setLast_update(new Date());
        cart.setStatus(StatusType.ordered);

        // Tracking Number
        String trackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(trackingNumber);

        // Associates cart items with cart
        for (CartItem item : purchase.getCartItems()) {
            item.setCart(cart);
            item.setCreate_date(new Date());
            item.setLast_update(new Date());
        }

        // Add cart to customer
        cart.setCartItems(purchase.getCartItems());
        customer.getCarts().add(cart);
        customerRepository.save(customer);

        // Response
        return new PurchaseResponse(trackingNumber);
    }
}
