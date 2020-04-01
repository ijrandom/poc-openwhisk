package poc.openwhisk;

import com.google.gson.JsonObject;

import java.util.Random;

public class Main {
    private static final Random random = new Random();

    public static JsonObject main(JsonObject args) throws InterruptedException {
        //if (args.has("name"))
        //    name = args.getAsJsonPrimitive("name").getAsString();
        JsonObject response = new JsonObject();
        long delay = Math.abs(random.nextLong() % 5000);
        Thread.sleep(delay);
        response.addProperty("delay", delay);
        return response;
    }
}
