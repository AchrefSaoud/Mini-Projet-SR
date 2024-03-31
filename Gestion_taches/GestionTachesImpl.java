import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class GestionTachesImpl extends UnicastRemoteObject implements GestionTachesInterface{

    private Set<String> taches;

    protected GestionTachesImpl() throws RemoteException {
        super();
        taches=new HashSet<>();
    }

    @Override
    public void ajouter_tache(String tache) throws RemoteException {
        if (!tache.equals("") && !taches.contains(tache)) {
            taches.add(tache);
        }
    }

    @Override
    public void supprimer_tache(String tache) throws RemoteException {
        if(taches.contains(tache)){
            taches.remove(tache);
        }
    }

    @Override
    public Set<String> recuperer_taches() throws RemoteException {
        return taches;
    }
    
}
