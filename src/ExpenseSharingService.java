import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseSharingService {

    private final Map<String, Map<String, Double>> balances = new HashMap<>();

    private enum SplitType {
        EQUAL, EXACT, PERCENT
    }

    public void solve(String input) {
        if (input.contains("SHOW")) {
            show(input);
        } else {
            addExpense(input);
        }
    }

    private void show(String input) {
        String[] inputArgs = input.split(" ");
        if (inputArgs.length == 1) {
            showAll();
        }else {
            showUser(inputArgs[1]);
        }

    }

    private void showAll() {
        if(balances.isEmpty()){
            System.out.println("No balances");
            return;
        }
        for (String payer : balances.keySet()) {
            Map<String, Double> userBalance = balances.get(payer);
            for (String user : userBalance.keySet()) {
                System.out.println(user + " owes " + payer + ": " + userBalance.get(user));
            }
        }
    }

    private void showUser(String u) {
        if(balances.isEmpty()){
            System.out.println("No balances");
            return;
        }
        boolean flagOwes = false;
        for (String payer : balances.keySet()) {
            Map<String, Double> userBalance = balances.get(payer);
            if (userBalance.containsKey(u)) {
                flagOwes = true;
                System.out.println(u + " owes " + payer + ": " + userBalance.get(u));
            }
        }
        if(!flagOwes) {
            Map<String, Double> uBalance = balances.get(u);
            for (String user : uBalance.keySet()) {
                System.out.println(user + " owes " + u + ": " + uBalance.get(user));
            }
        }
    }

    private void addExpense(String input) {
        String[] inputArgs = input.split(" ");
        try {
            int i = 1;
            String payer = inputArgs[i++];
            int amountPaid = Integer.parseInt(inputArgs[i++]);
            int noOfUsers = Integer.parseInt(inputArgs[i++]);
            List<String> users = new ArrayList<>();
            int lastuser = i + noOfUsers;
            for (; i < lastuser; ) {
                users.add(inputArgs[i++]);
            }
            switch (SplitType.valueOf(inputArgs[i])) {
                case SplitType.EQUAL:
                    addEqualShare(amountPaid, payer, users);
                    break;
                case EXACT:
                    addExactShares(amountPaid, payer, users, getShares(inputArgs, ++i, i + noOfUsers));
                    break;
                case PERCENT:
                    addPercentShares(amountPaid, payer, users, getShares(inputArgs, ++i, i + noOfUsers));
                    break;
                default:
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            //Illegal argument
        }
    }

    private List<Integer> getShares(String[] input, int i, int j) {
        List<Integer> shares = new ArrayList<>();
        for (int k = i; k < j; k++) {
            shares.add(Integer.parseInt(input[k]));
        }
        return shares;
    }

    private void addExactShares(int amount, String payer, List<String> users, List<Integer> shares) {
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).equals(payer)) {
                double toBePaid = shares.get(i);
                addBalance(toBePaid, payer, users.get(i));
            }
        }
    }

    private void addPercentShares(int amount, String payer, List<String> users, List<Integer> shares) {
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).equals(payer)) {
                double toBePaid = (double) (amount * shares.get(i)) / 100;
                addBalance(toBePaid, payer, users.get(i));
            }
        }
    }

    private void addEqualShare(int amount, String payer, List<String> users) {
        double toBePaid = (double) amount / users.size();
        for (String user : users) {
            if (!user.equals(payer)) {
                addBalance(toBePaid, payer, user);
            }
        }
    }

    private void addBalance(double toBePaid, String payer, String user) {

        //check if payer has to pay any amount to user
        if(balances.containsKey(user)){
            Map<String, Double> b = balances.get(user);
            if(b.containsKey(payer)){
                double balanceToBePaidByPayer = b.get(payer);
                if (toBePaid == balanceToBePaidByPayer) {
                    b.remove(payer);
                } else if (toBePaid > balanceToBePaidByPayer) {
                    b.remove(payer);
                    balances.putIfAbsent(payer, new HashMap<>());
                    balances.get(payer).put(user, toBePaid - balanceToBePaidByPayer);
                } else {
                    b.put(payer, balanceToBePaidByPayer - toBePaid);
                }
            }
        } else {
            balances.putIfAbsent(payer, new HashMap<>());
            if(balances.get(payer).containsKey(user)){
                balances.get(payer).put(user, balances.get(payer).get(user)+toBePaid);
            }else{
                balances.get(payer).put(user, toBePaid);
            }
        }
    }
}
