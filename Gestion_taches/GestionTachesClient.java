import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Set;

public class GestionTachesClient {
    public static void main(String[] args) {
        try {
            Registry registry=LocateRegistry.getRegistry(1097);
            GestionTachesInterface gestiontache=(GestionTachesInterface) registry.lookup("GestionTachesService");


            //Éxecution
            Scanner sc=new Scanner(System.in);
            int choix;
            String tache;
            boolean boo=true;
            Set<String> taches;
                while (boo) {
                    System.out.println("Gestion de Taches");
                    System.out.println("1 : ajouter une tache");
                    System.out.println("2 : supprimer une tache");
                    System.out.println("3 : afficher liste de taches");
                    System.out.println("4 : exit");
                    choix=sc.nextInt();
                    sc.nextLine();
                    switch (choix) {
                        case 1:
                            System.out.println("Entrez la tâche à ajouter : ");
                            tache=sc.nextLine();
                            gestiontache.ajouter_tache(tache);
                            break;

                        case 2:
                            System.out.println("Entrez la tâche à supprimer : ");
                            tache=sc.nextLine();
                            gestiontache.supprimer_tache(tache);
                            break;
                        case 3:
                            taches=gestiontache.recuperer_taches();
                            System.out.println(taches);
                            break;
                        case 4:
                            boo=false;
                        default:
                            break;
                    }
                }
                sc.close();

        } catch (Exception e) {
           System.out.println("Probléme de connexion au serveur");
           e.printStackTrace();
        }
    }
}
