package food.rest.code.endpoints;

import com.google.gson.Gson;
import food.rest.code.Entities.Food;
import food.rest.code.logic.JSONHandler;
import logging.Message;
import logging.Sender;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "${v1API}/food")
public class FoodController {
    ArrayList<Food> foods = new ArrayList<>();
    Gson gson;
    JSONHandler jsonHandler = new JSONHandler();
    Sender sender = new Sender();


    public FoodController() throws IOException, ParseException, TimeoutException {

        this.foods = jsonHandler.jsonToArrayList(foods);
        this.gson = new Gson();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/test")
    public String doIt(){
        String toString = gson.toJson(foods.get(0));
        return toString;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/categories")
    public String getCategories(){
        ArrayList<String> categories = null;
        try {
            categories = jsonHandler.getAllCategories(foods);
        } catch(Exception e){
            sender.makeLog("FoodController", Level.SEVERE, e.getMessage(), "error at getting categories");
        }
        if(categories == null){
            sender.makeLog("FoodController", Level.WARNING, "NullPointerException", "list of all categories is null");
        } else {
            sender.makeLog("FoodController", Level.INFO, "select all categories", "(all foods) list size is: " + categories.size());
        }
        return gson.toJson(categories);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{id}")
    public String getFoodById(@PathVariable long id){
        Food foodById = null;
        try {
            foodById = jsonHandler.getFoodById(foods, id);
        } catch(Exception e){
            sender.makeLog("FoodController", Level.WARNING, e.getMessage(), "error at getting food by id");
        }
        if(foodById != null){
            sender.makeLog("FoodController", Level.INFO, "select food by id", "food name is: " + foodById.getName());
        } else {
            sender.makeLog("FoodController", Level.WARNING, "NullPointerException", "food with id: " + id + " does not exist");
        }

        return gson.toJson(foodById);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/category")
    public String getCategory(@RequestParam String name){
        ArrayList<Food> categorizedFoods = null;
        try {
            categorizedFoods = jsonHandler.getSpecificFoods(foods, name);
        } catch (Exception e) {
            sender.makeLog("FoodController", Level.WARNING, e.getMessage(), "error at category by name");
        }
        if(categorizedFoods == null){
            sender.makeLog("FoodController", Level.WARNING, "NullPointerException", "list of all categories by name: " + name + " is null");
        } else {
            sender.makeLog("FoodController", Level.INFO, "select categories", "list of all categories by name: " + name + " is size: " + categorizedFoods.size());
        }
        return gson.toJson(categorizedFoods);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/names")
    public String getFoodByName(@RequestParam String name){
        ArrayList<Food> foodsByName = null;
        try {
            foodsByName = jsonHandler.getFoodsByName(foods, name);
        } catch(Exception e){
            sender.makeLog("FoodController", Level.WARNING, e.getMessage(), "error at foods by name");
        }
        if(foodsByName == null){
            sender.makeLog("FoodController", Level.WARNING, "NullPointerException", "list of foods by name: " + name + " is null");
        } else {
            sender.makeLog("FoodController", Level.WARNING, "select foods by name", "list of foods by name: " + name + " is size: " + foodsByName.size());
        }
        return gson.toJson(foodsByName);
    }


}
