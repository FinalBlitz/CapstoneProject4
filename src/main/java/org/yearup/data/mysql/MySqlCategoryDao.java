package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        List<Category>categories = new ArrayList<>();
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM categories");

            while (resultSet.next()) {
                categories.add(mapRow(resultSet));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // get all categories
        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        Category category = null;
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = getConnection();
            String query = "SELECT * FROM categories WHERE category_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                category = mapRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // get category by id
        return category;
    }

    @Override
    public Category create(Category category)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            String query = "INSERT INTO categories (name, description) VALUES(?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            int rows = preparedStatement.executeUpdate();

            if(rows == 0) {
                throw new SQLException("Failure");
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setCategoryId(generatedKeys.getInt(1));
            } else {
                throw  new SQLException("Screwed Up");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // create a new category
        return category;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        String query = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, categoryId);

            int rows = statement.executeUpdate();
            if(rows == 0) {
                throw new SQLException("Did not work");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
        String query = "DELETE FROM categories WHERE category_id = ?";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Did not work");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } ;
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
