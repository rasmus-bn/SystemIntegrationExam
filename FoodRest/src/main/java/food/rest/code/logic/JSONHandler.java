package food.rest.code.logic;

import food.rest.code.Entities.Food;
import food.rest.code.interfaces.JSONHandlerInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONHandler implements JSONHandlerInterface {

    public JSONHandler() {}


    @Override
    public  ArrayList<Food> jsonToArrayList(ArrayList<Food> list) throws IOException, ParseException {
        ArrayList<Food> foods = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("./src/main/resources/foods.json"));
        JSONArray jsonArray = (JSONArray) obj;
        Iterator<JSONObject> iterator = jsonArray.iterator();

        while(iterator.hasNext()){
            JSONObject jsonObject = iterator.next();
            long id = (Long) jsonObject.get("id");
            String name = (String) jsonObject.get("name");
            String name_scientific = (String) jsonObject.get("name_scientific");
            String description = (String) jsonObject.get("description");
            String itis_id = (String) jsonObject.get("itid_id");
            String wikipedia_id = (String) jsonObject.get("wikipedia_id");
            String food_group = (String) jsonObject.get("food_group");
            String food_subgroup = (String) jsonObject.get("food_subgroup");
            String food_type = (String) jsonObject.get("food_type");
            String category = (String) jsonObject.get("category");
            Food food = new Food(id, name, name_scientific, description, itis_id, wikipedia_id, food_group, food_subgroup, food_type, category);
            foods.add(food);
        }
        return foods;
    }
    @Override
    public  ArrayList<Food> getSpecificFoods(ArrayList<Food> list, String category){
        ArrayList<Food> foods = new ArrayList<>();
        for (Food food : list){
            if(food.getFood_group().toLowerCase().contains(category.toLowerCase())) {
                foods.add(food);
            }
        }
        return foods;
    }
    @Override
    public  ArrayList<Food> getFoodsByName(ArrayList<Food> list, String name) {
        ArrayList<Food> foods = new ArrayList<>();
        for (Food food : list){
            if(food.getName().toLowerCase().contains(name.toLowerCase())) {
                foods.add(food);
            }
        }
        return foods;
    }
    @Override
    public  Food getFoodById(ArrayList<Food> list, long id) {
        Food foodById = null;
        for (Food food : list){
            if(food.getId() == id) {
           foodById = food;
           break;
            }
        }
        return foodById;
    }

    @Override
    public ArrayList<String> getAllCategories(ArrayList<Food> list) {
        ArrayList<String> categories = new ArrayList<String>();
        for (Food food : list){
            if(!categories.contains(food.getFood_group())){
                categories.add(food.getFood_group());
            }

        }
        return categories;
    }

}
