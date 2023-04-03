import model.Person;
import utils.Logger;

public class Main {
    public static void main(String[] args){
        Logger l = new Logger();
        Person p = new Person("Bob", 33);
        Logger.logPrintString("Hello " + p);
    }
}
