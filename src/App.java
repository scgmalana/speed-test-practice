import java.util.*;
public class App{

    private Scanner sc = new Scanner(System.in);

    private static String[] trigFunctions = {"cos(", "sin(", "tan(", "cotan(", "sec(", "cosec("};
    private static final int TRIG_FUNCTION_SIZE = 6;

    private static String[] radianValues = 
    {"0", "pi/2", "pi", "3pi/2", 
    "pi/4", "3pi/4", "5pi/4", "7pi/4",
    "pi/6", "5pi/6", "7pi/6", "11pi/6",
    "pi/3", "2pi/3", "4pi/3", "5pi/3"};
    private static final int RADIAN_VALUE_SIZE = 15;

    private static String[][] answerKey = {
        //row numbers is radianValues 
        //column numbers is trigFunctions
        //could maybe be optimized further with constants 

        {"1", "0", "0", "und", "1", "und"}, 
        {"0", "1", "und", "0", "und", "1"},
        {"-1", "0", "0", "und", "-1", "und"},
        {"0", "-1", "und", "0", "und", "-1"},
        {"sqrt2/2", "sqrt2/2", "1", "1", "sqrt2", "sqrt2"},
        {"-sqrt2/2", "sqrt2/2", "-1", "-1", "-sqrt2", "sqrt2"},
        {"-sqrt2/2", "-sqrt2/2", "1", "1", "-sqrt2", "-sqrt2"},
        {"sqrt2/2", "-sqrt2/2", "-1", "-1", "sqrt2", "-sqrt2"},
        {"sqrt3/2", "1/2", "sqrt3/3", "sqrt3", "2sqrt3/3", "2"},
        {"-sqrt3/2", "1/2", "-sqrt3/3", "-sqrt3", "-2sqrt3/3", "2"},
        {"-sqrt3/2", "-1/2", "sqrt3/3", "sqrt3", "-2sqrt3/3", "-2"},
        {"sqrt3/2", "-1/2", "-sqrt3/3", "-sqrt3", "2sqrt3/3", "-2"},
        {"1/2", "sqrt3/2", "sqrt3", "sqrt3/3", "2", "2sqrt3/3"},
        {"-1/2", "sqrt3/2", "-sqrt3", "-sqrt3/3", "-2", "2sqrt3/3"},
        {"-1/2", "-sqrt3/2", "sqrt3", "sqrt3/3", "-2", "-2sqrt3/3"},
        {"1/2", "-sqrt3/2", "-sqrt3", "-sqrt3/3", "2", "-2sqrt3/3"},
    };
    
    private boolean thread1Running = false; 
    private boolean thread2Running = false;
    int runtime = 0;
    boolean answerCorrect = true;

    private Thread thread1 = new Thread() {
        public void run() {
            long lastTime = System.nanoTime();
            long timer = System.currentTimeMillis();
            double delta = 0.0;
            double ns = 1000000000.0 / 60.0;
            runtime = 0;
    
            while(thread1Running){
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while(delta >= 1.0){            
                    delta --;
                }
                if(System.currentTimeMillis() - timer > 1000){
                    runtime++;
                    timer += 1000;
                }
                if(runtime == 15){
                    System.out.println("You ran out of time L");
                    thread1Running = false;
                    thread2Running = false;
                }
                if(answerCorrect){
                    runtime = 0;
                }
            }  
        }
    };

    private Thread thread2 = new Thread() {
        public void run(){
            while(thread2Running){
                if(answerCorrect){
                    answerCorrect = false;
                    int trigFunctionID = getRandomNumber(0, TRIG_FUNCTION_SIZE);
                    int radianValueID = getRandomNumber(0, RADIAN_VALUE_SIZE);
                    String expectedAnswer = answerKey[radianValueID][trigFunctionID];
        
                    System.out.println(trigFunctions[trigFunctionID] + radianValues[radianValueID] + ")");
                    String response5 = sc.nextLine();
                    if(!response5.equals(expectedAnswer)){
                        System.out.println("You're dumb");
                    }
                    if(response5.equals(expectedAnswer)){
                        System.out.println("What a genius");
                        answerCorrect = true;
                    }
                }
            }
        }
    };

    

    private static int getRandomNumber(int min, int max) {
        return (int) Math.floor(Math.random()*max) + min;
    }

    public void start(){
        thread1Running = true;
        thread2Running = true;
        thread1.start();
        thread2.start();

        if(!thread2Running){
            thread2.interrupt();
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("This is a program to practice memorizing trig stuff for the Math speedtest");
        System.out.println("Answers should be formatted like this: 2sqrt3/3, sqrt2, sqrt3, etc.");
        System.out.println("Would you like to begin? y/n");
        String response = sc1.nextLine();
        if(response.equalsIgnoreCase("y")){
            new App().start();
        }
    }
}
