package food.rest.code.interfaces;

import food.rest.code.Entities.Food;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface JSONHandlerInterface {
    ArrayList<Food> jsonToArrayList(ArrayList<Food> list) throws IOException, ParseException;
    ArrayList<Food> getSpecificFoods(ArrayList<Food> list, String category);
    ArrayList<Food> getFoodsByName(ArrayList<Food> list, String name);
    Food getFoodById(ArrayList<Food> list, long id);
    ArrayList<String> getAllCategories(ArrayList<Food> list);
}
