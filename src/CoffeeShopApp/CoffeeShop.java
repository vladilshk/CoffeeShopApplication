package CoffeeShopApp;

import CoffeeShopApp.Coffee;

import java.util.*;

public class CoffeeShop {

    private ArrayList<Coffee> cart;

    public CoffeeShop() {
        this.cart = new ArrayList<Coffee>();
    }

    public static void main(String[] args) {
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.runCoffeeShopApp();
    }

    public String toString() {

        if (this.cart.size() == 0) {
            return "empty";
        }

        Iterator<Coffee> iterator = this.cart.iterator();
        StringBuffer stringbuffer = new StringBuffer(iterator.next().toString());

        for (; iterator.hasNext(); stringbuffer.append(iterator.next().toString())) {

            stringbuffer.append("\n");
        }
        return stringbuffer.toString();
    }

    public double getTotalCost() {
        double d = 0.0D;

        if (this.cart.size() == 0) {
            System.err.println("There is no coffee in the cart!");
            return 0.0D;
        }

        for (Iterator iterator = this.cart.iterator(); iterator.hasNext(); ) {

            d += ((Coffee) iterator.next()).getCost();
        }
        return d;
    }

    public void addCoffee(Coffee coffee) {
        this.cart.add(coffee);
    }

    public Coffee readCoffee() {
        Scanner scanner = new Scanner(System.in);
        String separator = "_";
        Coffee coffee = null;
        while (true) {
            System.out.println("Type \"q\" if you don't want to add coffee.");
            System.out.println("Input coffee (format \"id_name_taste_cost\") : ");
            String coffeeInfo = scanner.nextLine();
            if (coffeeInfo.equals("q")) {
                break;
            }

            String[] subStr;
            subStr = coffeeInfo.split(separator);
            if (subStr.length != 4) {
                System.err.println("Wrong input format!");
                continue;
            }
            if (!checkInput(subStr))
                continue;

            coffee = new Coffee(Integer.parseInt(subStr[0]), subStr[1], subStr[2], Double.parseDouble(subStr[3]));
            break;
        }
        System.out.println("Coffee added.");
        return coffee;
    }





    public boolean checkInput(String[] fields) {
        //check id
        if (!checkId(fields[0]))
            return false;

        //check name and taste
        if (fields[1].length() < 1 || fields[2].length() < 1) {
            System.err.println("Name or taste is empty!");
            return false;
        }

        //check cost
        if (!checkCost(fields[3]))
            return false;

        return true;
    }

    public boolean checkId(String inputId) {

        try {
            int id = Integer.parseInt(inputId);
            if (id < 0 || id > 50) {
                System.err.println("Id should be more then 0 and less then 50!");
                return false;
            }
            int itr = existSameId(id);
            if (itr != -1) {
                System.err.println("Coffee with this id is already exist. If you want to rewrite it type \"y\" else type any button.");
                Scanner scanner = new Scanner(System.in);
                if (!scanner.nextLine().equals("y")) {
                    return false;
                }
                cart.remove(itr);
            }
        } catch (NumberFormatException e) {
            System.err.println("Id should be integer (Example: 4)!");
            return false;
        }
        return true;
    }

    public boolean checkCost(String inputCost) {
        try {
            double cost = Double.parseDouble(inputCost);
            if (cost < 2 || cost > 50) {
                System.err.println("Cost should be more then 2 and less then 50!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.err.println("Cost should be double (Example: 30.0)!");
            return false;
        }
        return true;
    }

    //returns -1 if there is no any coffee with this id or place in list if exists
    private int existSameId(int id) {
        if (cart.size() == 0) {
            return -1;
        }

        int counter = 0;
        for (Iterator iterator = this.cart.iterator(); iterator.hasNext(); ) {
            if (id == ((Coffee) iterator.next()).getId())
                return counter;
            counter++;
        }

        return -1;
    }

    public void runCoffeeShopApp() {
        int action;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            printMenu();
            try {
                action = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Wrong input");
                continue;
            }
            if (action == 0) {
                System.out.println("See you soon!");
                break;
            }
            if (action == 1) {
                System.out.println(toString());
                continue;
            }
            if (action == 2) {
                System.out.println("Total coffee cost is: " + getTotalCost());
                continue;
            }
            if (action == 3) {
                Coffee coffee = readCoffee();
                if (coffee != null) {
                    addCoffee(coffee);
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("+----------------------+");
        System.out.println("|     Coffee Shop      |");
        System.out.println("+----------------------+");
        System.out.println("1. Type 0 to quit");
        System.out.println("2. Type 1 to get info about all coffee in Coffee Shop");
        System.out.println("3. Type 2 to to get total cost of coffee");
        System.out.println("4. Type 3 to add coffee");
    }
}
