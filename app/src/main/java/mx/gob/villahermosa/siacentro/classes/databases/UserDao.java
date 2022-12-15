package mx.gob.villahermosa.siacentro.classes.databases;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
//    @Query("SELECT * FROM user")
//    List<UserEntity> getAll();
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<UserEntity> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserEntity findByName(String first, String last);

    @Query("SELECT * FROM users WHERE id = :Id")
    UserEntity getUserFromId(Integer Id);

    @Query("SELECT * FROM users WHERE user_id = :User_Id")
    UserEntity getUserFromUserId(Integer User_Id);

    @Insert
    void addUser(UserEntity user);

    @Delete
    void removeUser(UserEntity UserE);

    @Update
    void updateUser(UserEntity UserE);

}
