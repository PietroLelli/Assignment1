import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PersonMain {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Person.class, new PersonSerializer())
                .registerTypeAdapter(Person.class, new PersonDeserializer())
                .create();

        Person giovanni = new Person("giovanni", "giovanni.ciatto@unibo.it", 29);

        String serialized = gson.toJson(giovanni);
        System.out.println(serialized); // {"name":"giovanni","email":"giovanni.ciatto@unibo.it","age":29}

        Person deserialized = gson.fromJson(serialized, Person.class);
        System.out.println(giovanni.equals(deserialized)); // true
    }
}
