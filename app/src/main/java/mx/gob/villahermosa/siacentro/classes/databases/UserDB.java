package mx.gob.villahermosa.siacentro.classes.databases;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import mx.gob.villahermosa.siacentro.classes.Usuario;

public class UserDB {
    @SuppressLint("StaticFieldLeak")
    private static UserDB userDB;
    private static AppDatabase db;
    private static UserDao UserDAO;
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    private UserDB(Context context) {
        context = context.getApplicationContext();

        db = Room.databaseBuilder(context, AppDatabase.class, "dbSIAC").
                allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        UserDAO = db.userDao();

    }

    public static UserDB get(Context context) {
        if (userDB == null) {
            userDB = new UserDB(context);
        }
        return userDB;
    }

    public static UserEntity getUserFromId(Integer id) {
        return UserDAO.getUserFromId(id);
    }

    public static UserEntity getUserFromUserId(Integer User_Id){
        return UserDAO.getUserFromUserId(User_Id);
    }

    public static void addUser(UserEntity userentity) {
        UserDAO.addUser(userentity);
    }

    public static void updateUser(UserEntity userentity) {
        UserDAO.updateUser(userentity);
    }

    public static void removeUser(UserEntity userentity) {
        UserDAO.removeUser(userentity);
    }

    public static void InsertUserEntity() {
        addUser(FillUserEntity());
    }

    public static void UpdateUserEntity() {
        updateUser(FillUserEntity());
    }

    private static UserEntity FillUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUser_id(Usuario.getUser().getId());
        userEntity.setUsername(Usuario.getUser().getUsername());
        userEntity.setEmail(Usuario.getUser().getEmail());
        userEntity.setCurp(Usuario.getUser().getCurp());
        userEntity.setAp_paterno(Usuario.getUser().getAp_paterno());
        userEntity.setAp_materno(Usuario.getUser().getAp_materno());
        userEntity.setNombre(Usuario.getUser().getNombre());
        userEntity.setGenero(Usuario.getUser().getGenero());
        userEntity.setRoot(Usuario.getUser().getRoot());
        userEntity.setFilename(Usuario.getUser().getFilename());
        userEntity.setFilename_png(Usuario.getUser().getFilename_png());
        userEntity.setFilename_thumb(Usuario.getUser().getFilename_thumb());
        userEntity.setEmail_verified_at(Usuario.getUser().getEmail_verified_at());
        userEntity.setUbicacion_id(Usuario.getUser().getUbicacion_id());
        userEntity.setToken(Usuario.getUser_response().getAccess_token());
        return userEntity;
    }

}
