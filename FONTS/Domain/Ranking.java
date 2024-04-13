package Domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Ranking {
    private static final Ranking rk = new Ranking();
    private static TreeMap<Integer,String> ranking;
    private static UserDB udb;

    Ranking() {
        ranking = new TreeMap<>(Comparator.reverseOrder());
        udb = UserDB.getInstance();
        loadRanking();
    }

    private void loadRanking() {
        HashMap<String,User> users = udb.getUsers();
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

    public void updateMaxPoint(String username, int maxPoint) {
        if(!ranking.containsValue(username) || !udb.isUserExist(username)) {
            System.err.println("El usuario no existe en ranking o tabla de usuarios");
        }

        int currentPoint = udb.getUser(username).getMaxPoint();

        ranking.remove(currentPoint,username);
        ranking.put(maxPoint,username);

        udb.getUser(username).setMaxPoint(maxPoint);
    }






}
