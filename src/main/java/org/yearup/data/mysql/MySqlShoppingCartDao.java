package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {

        String query = "SELECT * FROM shopping_cart\n" +
                "       JOIN products on shopping_cart.product_id = products.product_id\n" +
                "       WHERE user_id = ?;";
        ShoppingCart shoppingCart = new ShoppingCart();


        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, userId);

            try(ResultSet set = preparedStatement.executeQuery()) {
                while(set.next()) {
                    Product product = new Product(
                            set.getInt("product_id"),
                            set.getString("name"),
                            set.getBigDecimal("price"),
                            set.getInt("category_id"),
                            set.getString("description"),
                            set.getString("color"),
                            set.getInt("stock"),
                            set.getBoolean("featured"),
                            set.getString("image_url")
                    );
                    ShoppingCartItem item = new ShoppingCartItem();
                    item.setProduct(product);
                    item.setQuantity(set.getInt("quantity"));
                    shoppingCart.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart addToCart(int userId, int productId) {
        String query = "INSERT INTO shopping_cart(user_id, product_id)" + "VALUES(?,?)";

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);



            preparedStatement.executeUpdate();

            return getByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShoppingCart update(int userId, int productId, int quantity) {
        String query = "INSERT INTO shopping_cart(user_id, product_id, quantity)" + "VALUES(?,?,?)" + "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);



            preparedStatement.executeUpdate();

            return getByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShoppingCart clearCart(int userId) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ? " ;

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);




            preparedStatement.executeUpdate();

            return getByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
