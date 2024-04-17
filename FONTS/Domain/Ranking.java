package Domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Ranking {
    private static final Ranking rk = new Ranking();
    private static TreeMap<Integer,String> ranking;
    private static CtrlDomainUser ctrlDomainUser;

    Ranking() {
        ranking = new TreeMap<>(Comparator.reverseOrder());
        ctrlDomainUser = CtrlDomainUser.getInstance();
        loadRanking();
    }

    private void loadRanking() {
        HashMap<String,User> users = ctrlDomainUser.getAllUsers();
        for(Map.Entry<String,User> u: users.entrySet()) {
            ranking.put(u.getValue().getMaxPoint(),u.getKey());
        }
    }

    public static Ranking getInstance() {
        return rk;
    }


    public void showRanking() {
        if(ranking.isEmpty()) {
            System.out.println("No hay usuario, el ranking está vacío");
            return;
        }
        int count = 1;
        System.out.println("Count  Username  MaxPoint");
        for(Map.Entry<Integer,String> set : ranking.entrySet()) {
            System.out.println(count+". "+set.getValue()+": "+set.getKey());
            count++;
        }
    }


    public TreeMap<Integer, String> getRanking() {
        return ranking;
    }

    public void updateMaxPoint(String username, int maxPoint) {
        if(!ranking.containsValue(username) || !ctrlDomainUser.isUserExist(username)) {
            System.err.println("El usuario no existe en ranking o tabla de usuarios");
        }

        int currentPoint = ctrlDomainUser.getUser(username).getMaxPoint();

        ranking.remove(currentPoint,username);
        ranking.put(maxPoint,username);

        ctrlDomainUser.getUser(username).setMaxPoint(maxPoint);
    }






}
