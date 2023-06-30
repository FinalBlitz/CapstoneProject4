package org.yearup.controllers;

<<<<<<< HEAD
<<<<<<< Updated upstream
import org.springframework.http.HttpStatus;
=======
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.http.HttpStatus;
>>>>>>> a9dc18d9cf90d17f388fee04b414abddd21a6a39
>>>>>>> Stashed changes
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
>>>>>>> main
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;
<<<<<<< HEAD
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import java.util.Map;
>>>>>>> Stashed changes
=======
import java.util.Map;
>>>>>>> main

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    // a shopping cart requires

    private ShoppingCartDao shoppingCartDao;

    private UserDao userDao;

    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }




    // each method in this controller requires a Principal object as a parameter
<<<<<<< HEAD
<<<<<<< Updated upstream
=======
    @GetMapping("{id}")
=======

// convert this class to a REST controller
// only logged in users should have access to these actions
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;



    // each method in this controller requires a Principal object as a parameter
>>>>>>> a9dc18d9cf90d17f388fee04b414abddd21a6a39
>>>>>>> Stashed changes
=======
    @GetMapping("{id}")
>>>>>>> main
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
<<<<<<< HEAD
<<<<<<< Updated upstream
            return null;
        }
        catch(Exception e)
        {
=======
<<<<<<< HEAD
            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            e.printStackTrace();
=======
            return null;
        }
        catch(Exception e)
        {
>>>>>>> a9dc18d9cf90d17f388fee04b414abddd21a6a39
>>>>>>> Stashed changes
=======
            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            e.printStackTrace();
>>>>>>> main
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

<<<<<<< HEAD
<<<<<<< Updated upstream
    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
=======
<<<<<<< HEAD
//     add a POST method to add a product to the cart - the url should be
=======
    //     add a POST method to add a product to the cart - the url should be
>>>>>>> main
//     https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{productId}")
    public ShoppingCart addToCart(Principal principal, @PathVariable int productId) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();
        shoppingCartDao.addToCart(userId, productId);
        return shoppingCartDao.getByUserId(userId);
    }
<<<<<<< HEAD
=======
    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
>>>>>>> a9dc18d9cf90d17f388fee04b414abddd21a6a39
>>>>>>> Stashed changes
=======
>>>>>>> main


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
<<<<<<< HEAD
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
=======
>>>>>>> main
    @PutMapping("/products/{productId}")
    public ShoppingCart updateCart(Principal principal, @PathVariable int productId, @RequestParam int quantity) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();
        shoppingCartDao.update(userId, productId, quantity);
        return shoppingCartDao.getByUserId(userId);
    }
<<<<<<< HEAD
=======
>>>>>>> a9dc18d9cf90d17f388fee04b414abddd21a6a39
>>>>>>> Stashed changes
=======
>>>>>>> main


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
<<<<<<< HEAD
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
=======
>>>>>>> main
    @DeleteMapping("{id}")
    public void clearCart(Principal principal) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();
        shoppingCartDao.getByUserId(userId);
    }
<<<<<<< HEAD
=======
>>>>>>> a9dc18d9cf90d17f388fee04b414abddd21a6a39
>>>>>>> Stashed changes
=======
>>>>>>> main

}