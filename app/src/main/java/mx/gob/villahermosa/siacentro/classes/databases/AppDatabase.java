package mx.gob.villahermosa.siacentro.classes.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import mx.gob.villahermosa.siacentro.classes.Usuario;

@Database(entities = {UserEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();


}
