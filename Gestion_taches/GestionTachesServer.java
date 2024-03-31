import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GestionTachesServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1097);
            GestionTachesInterface objetDistant=new GestionTachesImpl();
            registry.rebind("GestionTachesService", objetDistant);
            System.out.println("serveur démarré");
        } catch (Exception e) {
            System.out.println("Probléme au niveau de serveur");
            e.printStackTrace();
        }

    }
}
