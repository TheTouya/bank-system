import java.util.*;
import java.util.LinkedList;
import java.util.Queue;
public class BankAccount {
    class Transaction {
        private String senderName;
        private String receiverName;
        private double amount;
        public Transaction(String s, String r, double amount) {
            this.senderName = s;
            this.receiverName = r;
            this.amount = amount;
        }
        public String toString() {
            return String.format("FROM: %s, TO: %s, AMOUNT: $%.2f", senderName, receiverName, amount);
        }

    }
    class Node {
        private String cardnum;
        private Node left;
        private Node right;
        private String name;
        private String ID;
        private double balance = 0;
        private boolean freeze = false;
        private ArrayList<Transaction> arr;
        public Node(String cardnum, String name, String ID) {
            this.cardnum = cardnum;
            this.name = name;
            this.ID = ID;
            arr = new ArrayList<>();
        }
    }
    private Node root;
    private int users;
    private boolean isValidID(String ID) {
        ID = ID.replace("-","").replace(" ","");
        if(ID.length() < 10) {
            return false;
        } else {
            int total = 0;
            for (int i = 0; i < 10; i++) {
                char c = ID.charAt(i);
                int digit;

                if (c == 'X' && i == 9) {
                    digit = 10;
                } else if (Character.isDigit(c)) {
                    digit = Character.getNumericValue(c);
                } else {
                    return false;
                }

                total += (10 - i) * digit;
            }
            return total % 11 == 0;
        }
    }
    private boolean isValidCreditCard(String cnum) {
        int sum = 0;
        boolean isSecond = false;
        cnum = cnum.replace("-", "").replace(" ", "");
        for (int i = cnum.length() - 1; i >= 0; i--) {
            int digit = cnum.charAt(i) - '0';
            if (isSecond) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            isSecond = !isSecond;
        }
        return sum % 10 == 0;
    }
    public void insert(String num, String name, String ID){
        num = num.replace(" ","").replace("-","");
        ID = ID.replace("-","").replace(" ","");
        if(isValidCreditCard(num) && isValidID(ID)) {
            Node nd = new Node(num, name, ID);
            if(users == 0) {
                root = nd;
                users++;
            } else {
                int index = 0;
                Node temp = root;
                while(true) {
                    int newOne = num.compareTo(temp.cardnum);
                    if(newOne < 0) {
                        if(temp.left == null) {
                            temp.left = nd;
                            break;
                        } else {
                            temp = temp.left;
                        }
                    } else if(newOne > 0) {
                        if(temp.right == null) {
                            temp.right = nd;
                            break;
                        } else {
                            temp = temp.right;
                        }
                    } else {
                        throw new IllegalArgumentException("CARD ALREADY EXISTS");
                    }
                }
                users++;
            }

        } else {
            throw new IllegalArgumentException("One of these is wrong");
        }
    }
    public ArrayList<String> getAll() {
        Node temp = root;
        Queue<Node> que = new LinkedList<>();
        ArrayList<String> arr = new ArrayList<>();
        que.add(temp);
        while(!que.isEmpty()) {
            temp = que.remove();
            arr.add(temp.cardnum);
            if(temp.right != null) {
                que.add(temp.right);
            }
            if(temp.left != null) {
                que.add(temp.left);
            }
        }
        return arr;
    }
    private Node getCardInfo(String num) {
        num = num.replace("-","").replace(" ","");
        Node temp = root;
        while(temp != null) {
            int comparison = num.compareTo(temp.cardnum);
            if(comparison < 0) {
                temp = temp.left;
            } else if(comparison > 0) {
                temp = temp.right;
            } else {
                return temp;
            }
        }
        return null;
    }
    public void deposit(double amount, String num, String Id) {
        num = num.replace("-","").replace(" ","");
        Id = Id.replace("-","").replace(" ","");
        Node temp = getCardInfo(num);
        if(temp != null) {
            if(temp.ID.equals(Id)) {
                if(!temp.freeze){
                    if(amount > 0) {
                        temp.balance += amount;
                    } else {
                        throw new IllegalArgumentException("CAN'T HAPPEN WITH THIS AMOUNT");
                    }
                } else {
                    throw new IllegalArgumentException("YOUR ACCOUNT IS FREEZE");
                }
            } else {
                throw new IllegalArgumentException("INVALID ID");
            }
        } else {
            throw new IllegalArgumentException("INVALID CARD");
        }
    }
    public String getInfo(String num, String ID) {
        Node temp = getCardInfo(num);
        if(temp != null) {
            if(temp.ID.equals(ID)) {
                String name = temp.name;
                String balance = String.format("%.2f", temp.balance);
                return "Account of " + name + ", balance: " + balance + "$";
            } else {
                throw new IllegalArgumentException("INVALID ID");
            }
        } else {
            throw new IllegalArgumentException("INVALID CARD NUMBER");
        }
    }
    public void makeTransaction(String sender, String receiver, double amount) {
        Node s = getCardInfo(sender);
        Node r = getCardInfo(receiver);
        if(s != null && r != null) {
            if(!s.freeze && !r.freeze) {
                if(s.balance > amount && amount > 0) {
                    Transaction nr = new Transaction(sender,receiver,amount);
                    s.balance -= amount;
                    r.balance += amount;
                    s.arr.add(nr);
                    r.arr.add(nr);
                } else {
                    throw new IllegalArgumentException("INSUFFICIENT AMOUNT");
                }
            } else {
                throw new IllegalArgumentException("CARD FREEZED");
            }
        } else {
            throw new IllegalArgumentException("CARD NOT FOUND");
        }

    }
    public ArrayList<Transaction> getTransactionHistory(String num) {
        Node temp = getCardInfo(num);
        if(temp != null) {
            return temp.arr;
        } else {
            throw new IllegalArgumentException("INVALID CARD NUMBER");
        }
    }
    public void freezeAcc(String num, String ID) {
        ID = ID.replace("-","").replace(" ","");
        Node temp = getCardInfo(num);
        if(temp != null) {
            if(temp.ID.equals(ID)) {
                temp.freeze = true;
            } else {
                throw new IllegalArgumentException("INVALID ID");
            }
        } else {
            throw new IllegalArgumentException("INVALID CARD NUMBER");
        }
    }
    public String toString() {
        ArrayList<Node> arr = new ArrayList<>();
        class Traverse{
            public Traverse(Node temp) {
                if(temp.left != null) {
                    new Traverse(temp.left);
                }
                arr.add(temp);
                if(temp.right != null) {
                    new Traverse(temp.right);
                }
            }
        }
        new Traverse(root);
        String str = "";
        for(int i = 0; i < arr.size(); i++) {
            str += arr.get(i).cardnum + " NAME : " + arr.get(i).name + "  ID : " + arr.get(i).ID + "  BALANCE : " + arr.get(i).balance +  ". \n";
        }
        return str;
    }
}
