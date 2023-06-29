package org.yearup.data;

import org.springframework.stereotype.Repository;
import org.yearup.models.ShoppingCart;

@Repository
public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    ShoppingCart addToCart(int userId, int productId);

    ShoppingCart update(int userId, int productId, int quantity);

    ShoppingCart clearCart(int userId);
}
