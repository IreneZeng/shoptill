import java.util.*;
import java.util.Scanner;
import java.util.*;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShopTill {
    public static int[] coins_types = {1, 2, 5, 10, 20, 50, 100, 200};
    public static HashMap<Integer, String> coins_types_string = new HashMap<Integer, String>() {{
                    put(1, "one penny: ");
                    put(2, "two pence: ");
                    put(5, "five pence: ");
                    put(10, "ten pence: ");
                    put(20, "twenty pence: ");
                    put(50, "fifty pence: ");
                    put(100, "one pound: ");
                    put(200, "two pounds: ");
    }};
        
    public static int one_penny_num;
    public static int two_pence_num;
    public static int five_pence_num;
    public static int ten_pence_num;
    public static int twenty_pence_num;
    public static int fifty_pence_num;
    public static int one_pound_num;
    public static int two_pounds_num;

    public static double sale_amount;
    public static int paid_one_penny_num;
    public static int paid_two_pence_num;
    public static int paid_five_pence_num;
    public static int paid_ten_pence_num;
    public static int paid_twenty_pence_num;
    public static int paid_fifty_pence_num;
    public static int paid_one_pound_num;
    public static int paid_two_pounds_num;

    public static int total_coins = 0;
    public static boolean change_status = true;

    public static void main(String[] args) {
        HashMap<Integer, Integer> shop_till = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> return_coins = new HashMap<Integer, Integer>();

        // Initialise the till
        shop_till = initialise_till();

        // Count coins number in the till
        total_coins = one_penny_num + two_pence_num + five_pence_num + ten_pence_num +
                        twenty_pence_num + fifty_pence_num + one_pound_num + two_pounds_num;
                        
        while(true){
            try {
                if(total_coins <= 0){
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Sorry, no coins in the till now.");
                break;
            }
            
            // Initialise the payment
            initialise_payment();

            return_coins = get_changes(shop_till);
            
            if(change_status){
                System.out.println("\nChanges are: ");

                for (HashMap.Entry<Integer, Integer> entry : return_coins.entrySet()) {
                    int key = entry.getKey();
                    int value = entry.getValue();
                    if(value != 0){
                        System.out.println(coins_types_string.get(key) + value);
                    }
                }

                System.out.println("\nCoins in till: ");

                for (HashMap.Entry<Integer, Integer> entry : shop_till.entrySet()) {
                    int key = entry.getKey();
                    int value = entry.getValue();
                    System.out.println(coins_types_string.get(key) + value);
                }
                
            }
        }
    }

    public static HashMap<Integer, Integer> initialise_till(){
        
        // Request user input for initialise
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter integers to initialize the shop till.\n");

        System.out.print("The number of one penny: ");
        one_penny_num = input.nextInt();
        
        System.out.print("The number of two pence: ");
        two_pence_num = input.nextInt();

        System.out.print("The number of five pence: ");
        five_pence_num = input.nextInt();

        System.out.print("The number of ten pence: ");
        ten_pence_num = input.nextInt();

        System.out.print("The number of twenty pence: ");
        twenty_pence_num = input.nextInt();

        System.out.print("The number of fifty pence: ");
        fifty_pence_num = input.nextInt();

        System.out.print("The number of one pound: ");
        one_pound_num = input.nextInt();

        System.out.print("The number of two pounds: ");
        two_pounds_num = input.nextInt();

         // initialise the number of different amount of coins
        int[] coins_num = {one_penny_num, two_pence_num, five_pence_num, 
                            ten_pence_num, twenty_pence_num, fifty_pence_num, 
                            one_pound_num, two_pounds_num};
                  
        HashMap<Integer, Integer> shop_till = new HashMap<Integer, Integer>();
        
        for(int i = 0; i < coins_types.length; i++){
            shop_till.put(coins_types[i], coins_num[i]);
        }
      
        return shop_till;
    }

    public static void initialise_payment(){
        // Request user input to initialise the payment
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease enter the sale amount as a double number (e.g. 15.23):\n");
        sale_amount = input.nextDouble();

        BigDecimal bg = new BigDecimal(sale_amount).setScale(2, RoundingMode.HALF_UP);

        sale_amount = bg.doubleValue();

        System.out.println("\nPlease enter integers to indicate the details of the payment.\n");

        System.out.print("The number of one penny paid: ");
        paid_one_penny_num = input.nextInt();
        
        System.out.print("The number of two pence paid: ");
        paid_two_pence_num = input.nextInt();

        System.out.print("The number of five pence paid: ");
        paid_five_pence_num = input.nextInt();

        System.out.print("The number of ten pence paid: ");
        paid_ten_pence_num = input.nextInt();

        System.out.print("The number of twenty pence paid: ");
        paid_twenty_pence_num = input.nextInt();

        System.out.print("The number of fifty pence paid: ");
        paid_fifty_pence_num = input.nextInt();

        System.out.print("The number of one pound paid: ");
        paid_one_pound_num = input.nextInt();

        System.out.print("The number of two pounds paid: ");
        paid_two_pounds_num = input.nextInt();
    }

    public static HashMap<Integer, Integer> get_changes(HashMap<Integer, Integer> shop_till){
        // Initialise variables
        HashMap<Integer, Integer> return_coins = new HashMap<Integer, Integer>();
        int[] paid_coins = {paid_one_penny_num, paid_two_pence_num, paid_five_pence_num,
                            paid_ten_pence_num, paid_twenty_pence_num, paid_fifty_pence_num,
                            paid_one_pound_num, paid_two_pounds_num};

        // Update the number of coins in the till                 
        int update_num;
        for(int i = 0; i < paid_coins.length; i++){
            update_num = shop_till.get(coins_types[i]) + paid_coins[i];
            shop_till.put(coins_types[i], update_num);
        }
        
        // Calculate the total amounts in the till
        int total_amounts_in_till = 0;
        for(int i = 0; i < coins_types.length; i++){
            total_amounts_in_till += coins_types[i] * shop_till.get(coins_types[i]);
        }

        // Convert the sale amounts into pence
        int sale_amount_pence = (int)Math.round(sale_amount * 100);

        // Calculate the paid amount
        int paid_amounts = paid_one_penny_num + paid_two_pence_num * coins_types[1] + paid_five_pence_num * coins_types[2] +
                                        paid_ten_pence_num * coins_types[3] + paid_twenty_pence_num * coins_types[4] +
                                        paid_fifty_pence_num * coins_types[5] + paid_one_pound_num * coins_types[6] +
                                        paid_two_pounds_num * coins_types[7];
        
        // Caculate the changes
        int changes = paid_amounts - sale_amount_pence;
        try {
            if(changes < 0) {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            change_status = false;
            System.out.println("Not enough payment!");
        }

        try {
            if(total_amounts_in_till < changes){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            change_status = false;
            System.out.println("Not enough changes in till!");
        }

        // When the changes equal to the total amount in the till
        if(total_amounts_in_till == changes){
            return_coins = shop_till;
        }

        // Find corresponding number of coins to make the changes
        if(total_amounts_in_till > changes){
            int hundred = changes / 100;
            int decimal = changes % 100 / 10;
            int digit = changes % 100 % 10;

            int two_pounds_num_in_till = shop_till.get(200);
            int one_pound_num_in_till = shop_till.get(100);
            int fifty_pence_num_in_till = shop_till.get(50);
            int twenty_pence_num_in_till = shop_till.get(20);
            int ten_pence_num_in_till = shop_till.get(10);
            int five_pence_num_in_till = shop_till.get(5);
            int two_pence_num_in_till = shop_till.get(2);
            int one_penny_num_in_till = shop_till.get(1);

            int two_pounds_need = 0;
            int two_pounds_change = 0;

            int one_pound_need = 0;
            int one_pound_change = 0;

            int fifty_pence_need = 0;
            int fifty_pence_change = 0;

            int twenty_pence_need = 0;
            int twenty_pence_change = 0;

            int ten_pence_need = 0;
            int ten_pence_change = 0;

            int five_pence_need = 0;
            int five_pence_change = 0;

            int two_pence_need = 0;
            int two_pence_change = 0;

            int one_penny_change = 0;
            int one_penny_need = 0;

            if(hundred > 0){
                // Calculate the number of two/one pound coins we need
                two_pounds_need = hundred / 2;
                one_pound_need = hundred - two_pounds_need * 2;

                if(two_pounds_need > 0){
                    if(two_pounds_need <= two_pounds_num_in_till){
                        two_pounds_change = two_pounds_need;
                    }else{
                        two_pounds_change = two_pounds_num_in_till;

                        // Put the rest into one pound
                        one_pound_need += (two_pounds_need - two_pounds_num_in_till) * 2;
                    }
                    return_coins.put(coins_types[7], two_pounds_change);
                }
                
                if(one_pound_need > 0){
                    if(one_pound_need <= one_pound_num_in_till){
                        one_pound_change = one_pound_need;
                    }else{
                        // Put the rest into decimal
                        one_pound_change = one_pound_num_in_till;
                        decimal += (one_pound_need - one_pound_num_in_till) * 100;
                    }
                    return_coins.put(coins_types[6], one_pound_change);
                }
            }

            if(decimal > 0){
                fifty_pence_need = decimal / 5;
                twenty_pence_need = (decimal - fifty_pence_need * 5) / 2;
                ten_pence_need = decimal - fifty_pence_need * 5 - twenty_pence_need * 2;

                if(fifty_pence_need > 0){
                    if(fifty_pence_need <= fifty_pence_num_in_till){
                        fifty_pence_change = fifty_pence_need;
                    }else{
                        // Put the rest into 20 and 10 pence
                        fifty_pence_change = fifty_pence_num_in_till;
                        
                        int rest_decimal = (fifty_pence_need - fifty_pence_change) * 5;
                        twenty_pence_need += rest_decimal / 2;
                        ten_pence_need += rest_decimal - (rest_decimal / 2) * 2;
                    }
                    return_coins.put(coins_types[5], fifty_pence_change);
                }

                if(twenty_pence_need > 0){
                    
                    if(twenty_pence_need <= twenty_pence_num_in_till){
                        twenty_pence_change = twenty_pence_need;
                    }else{
                        // Put the rest into 10 pence
                        twenty_pence_change = twenty_pence_num_in_till;
                        ten_pence_need += (twenty_pence_need - twenty_pence_change) * 2;
                    }
                    return_coins.put(coins_types[4], twenty_pence_change);
                }

                if(ten_pence_need > 0){
                    if(ten_pence_need <= ten_pence_num_in_till){
                        ten_pence_change = ten_pence_need;
                    }else{
                        // Put the rest into digits
                        ten_pence_change = ten_pence_num_in_till;
                        digit += (ten_pence_need - ten_pence_change) * 10;
                    }
                    return_coins.put(coins_types[3], ten_pence_change); 
                }
            }

            if(digit > 0){
                // 1, 2, 5 in the till
                five_pence_need = digit / 5;
                two_pence_need = (digit - five_pence_need * 5) / 2;
                one_penny_need = digit - five_pence_need * 5 - two_pence_need * 2;

                if(five_pence_need > 0){
                    
                    if(five_pence_need <= five_pence_num_in_till){
                        five_pence_change = five_pence_need;
                    }else{
                        // Put the rest into 2 pence and 1 penny
                        five_pence_change = five_pence_num_in_till;
                        int rest_digit = (five_pence_need - five_pence_change) * 5;
                        two_pence_need += rest_digit / 2;
                        one_penny_need += rest_digit - (rest_digit / 2) * 2;
                    }
                    return_coins.put(coins_types[2], five_pence_change);
                }

                if(two_pence_need > 0){
                    if(two_pence_need <= two_pence_num_in_till){
                        two_pence_change = two_pence_need;
                    }else{
                        two_pence_change = two_pence_num_in_till;
                        //Put the rest into one penny
                        one_penny_need += (two_pence_need - two_pence_change) * 2;
                    }
                    return_coins.put(coins_types[1], two_pence_change);
                }

                if(one_penny_need > 0){
                    try {
                        if(one_penny_need > one_penny_num_in_till){
                            change_status = false;
                            throw new IllegalArgumentException();
                        }else{
                            return_coins.put(coins_types[0], one_penny_change);   
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not enough coins to change!");
                    }
                }
            }
            // Update the till
            if(change_status){
                shop_till.put(coins_types[0], one_penny_num_in_till - one_penny_change);
                shop_till.put(coins_types[1], two_pence_num_in_till - two_pence_change);
                shop_till.put(coins_types[2], five_pence_num_in_till - five_pence_change);
                shop_till.put(coins_types[3], ten_pence_num_in_till - ten_pence_change);
                shop_till.put(coins_types[4], twenty_pence_num_in_till - twenty_pence_change);
                shop_till.put(coins_types[5], fifty_pence_num_in_till - fifty_pence_change);
                shop_till.put(coins_types[6], one_pound_num_in_till - one_pound_change);
                shop_till.put(coins_types[7], two_pounds_num_in_till - two_pounds_change);
            }
        }
        

        // Count the coins
        total_coins = 0;
        for (int i : shop_till.values()) {
            total_coins += i;
        }
        
        return return_coins;
    }
}