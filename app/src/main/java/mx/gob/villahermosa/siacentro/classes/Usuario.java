package mx.gob.villahermosa.siacentro.classes;

import mx.gob.villahermosa.siacentro.classes.responses.UserResponse;
import mx.gob.villahermosa.siacentro.classes.responses.UsuarioResponse;

public class Usuario  {


    private static UsuarioResponse user_response = new UsuarioResponse();
    private static UserResponse user = new UserResponse();

    // Singleton Usuario
    private static Usuario ourInstance = new Usuario();
    static Usuario getInstance() {
        return ourInstance;
    }
    public Usuario() { }

    public static UsuarioResponse getUser_response() {
        return user_response;
    }

    public static void setUser_response(UsuarioResponse user_response) {
        Usuario.user_response = user_response;
    }

    public static UserResponse getUser() {
        return user;
    }

    public static void setUser(UserResponse user) {
        Usuario.user = user;
    }

}
