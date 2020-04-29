import sr.grpc.gen.Category;
import sr.grpc.gen.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private List <String> names = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List <String> cities = new ArrayList<>();
    private Random randomGenerator = new Random();

    public Generator(){
        names.add("Great event");
        names.add("Unforgettable event");
        names.add("What an event");
        categories.add(Category.BUSINESS);
        categories.add(Category.FASHION);
        categories.add(Category.IT);
        categories.add(Category.MUSIC);
        categories.add(Category.SPORT);
        cities.add("Cracow");
        cities.add("Warsaw");
        cities.add("Katowice");
        cities.add("Gdynia");
    }

    public Event generateEvent(){
        return Event.newBuilder().setName(names.get(randomGenerator.nextInt(3)))
                .setCategory(categories.get(randomGenerator.nextInt(5)))
                .setDate(Integer.toString(randomGenerator.nextInt(27)+1)
                + "-" + Integer.toString(randomGenerator.nextInt(12)+1)
                + "-" + Integer.toString(randomGenerator.nextInt(2)+2020))
                .addCities(cities.get(randomGenerator.nextInt(2)))
                .addCities(cities.get(randomGenerator.nextInt(2) + 2))
                .build();
    }
}
