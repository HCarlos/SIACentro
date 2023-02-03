package mx.gob.villahermosa.siacentro.classes.connections;

import mx.gob.villahermosa.siacentro.classes.responses.RegistryResponse;

public class Registry {

    private static RegistryResponse registry_response = new RegistryResponse();

    // Singleton Usuario
    private static final Registry ourInstance = new Registry();
    static Registry getInstance() {
        return ourInstance;
    }
    public Registry() { }

    public static RegistryResponse getRegistry_response() {
        return registry_response;
    }

    public static void setRegistry_response(RegistryResponse registry_response) {
        Registry.registry_response = registry_response;
    }

}
