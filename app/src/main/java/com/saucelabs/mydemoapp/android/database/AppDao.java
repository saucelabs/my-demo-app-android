package com.saucelabs.mydemoapp.android.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.model.User;

import java.util.List;

@Dao
public interface AppDao {
    @Query("SELECT * FROM person ORDER BY ID")
    List<User> loadAllPersons();

    @Insert
    void insertPerson(User person);

    @Update
    void updatePerson(User person);

    @Delete
    void delete(User person);

    @Query("SELECT * FROM person WHERE id = :id")
    User loadPersonById(int id);

    @Query("SELECT * FROM Product ORDER BY ID")
    List<ProductModel> getAllProducts();

    @Query("SELECT * FROM Product ORDER BY price ASC")
    List<ProductModel> getPersonsSortByAscPrice();

    @Query("SELECT * FROM Product ORDER BY price DESC")
    List<ProductModel> getPersonsSortByDescPrice();

    @Query("SELECT * FROM Product ORDER BY title ASC")
    List<ProductModel> getPersonsSortByAscName();

    @Query("SELECT * FROM Product ORDER BY title DESC")
    List<ProductModel> getPersonsSortByDescName();


    @Insert
    void insertProduct(ProductModel person);

    @Insert
    void insertProduct(List<ProductModel> person);

    @Query("SELECT * FROM Product WHERE id = :id")
    ProductModel getProduct(int id);

}
