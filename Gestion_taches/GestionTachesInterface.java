import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface GestionTachesInterface extends Remote{
    void ajouter_tache(String tache) throws RemoteException;
    void supprimer_tache(String tache) throws RemoteException;
    Set<String> recuperer_taches()throws RemoteException;
}
