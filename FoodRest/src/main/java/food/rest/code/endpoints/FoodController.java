package food.rest.code.endpoints;

import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import food.rest.code.Entities.Food;
import food.rest.code.logic.FlightServiceGrpc;
import food.rest.code.logic.GetBookingReply;
import food.rest.code.logic.GetBookingRequest;
import food.rest.code.logic.JSONHandler;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import logging.SILevel;
import logging.Sender;
import org.bson.Document;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.EnvHelper;
import services.Service;
import services.ServiceInfo;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "${v1API}/food")
public class FoodController {
    Gson gson = new Gson();
    JSONHandler jsonHandler = new JSONHandler();
    ArrayList<Food> foods = loadFood();

    private ArrayList<Food> loadFood() {
        try {
            return jsonHandler.jsonToArrayList(foods);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/test")
    public String doIt(){
        ArrayList<Food> foods = loadFood();
        String toString = gson.toJson(foods.get(0));
        return toString;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/categories")
    public String getCategories(){
        Sender sender = new Sender();
        ArrayList<String> categories = null;
        ArrayList<Food> foods = loadFood();

        try {
            categories = jsonHandler.getAllCategories(foods);
        } catch(Exception e){
            sender.makeLog("FoodController", SILevel.SEVERE, e.getMessage(), "error at getting categories");
        }
        if(categories == null){
            sender.makeLog("FoodController", SILevel.WARNING, "NullPointerException", "list of all categories is null");
        } else {
            sender.makeLog("FoodController", SILevel.INFO, "select all categories", "(all foods) list size is: " + categories.size());
        }
        return gson.toJson(categories);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{id}")
    public String getFoodById(@PathVariable long id){
        Sender sender = new Sender();
        Food foodById = null;
        ArrayList<Food> foods = loadFood();
        try {
            foodById = jsonHandler.getFoodById(foods, id);
        } catch(Exception e){
            sender.makeLog("FoodController", SILevel.WARNING, e.getMessage(), "error at getting food by id");
        }
        if(foodById != null){
            sender.makeLog("FoodController", SILevel.INFO, "select food by id", "food name is: " + foodById.getName());
        } else {
            sender.makeLog("FoodController", SILevel.WARNING, "NullPointerException", "food with id: " + id + " does not exist");
        }

        return gson.toJson(foodById);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/category")
    public String getCategory(@RequestParam String name){
        Sender sender = new Sender();
        ArrayList<Food> categorizedFoods = null;
        ArrayList<Food> foods = loadFood();
        try {
            categorizedFoods = jsonHandler.getSpecificFoods(foods, name);
        } catch (Exception e) {
            sender.makeLog("FoodController", SILevel.WARNING, e.getMessage(), "error at category by name");
        }
        if(categorizedFoods == null){
            sender.makeLog("FoodController", SILevel.WARNING, "NullPointerException", "list of all categories by name: " + name + " is null");
        } else {
            sender.makeLog("FoodController", SILevel.INFO, "select categories", "list of all categories by name: " + name + " is size: " + categorizedFoods.size());
        }
        return gson.toJson(categorizedFoods);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/names")
    public String getFoodByName(@RequestParam String name){
        Sender sender = new Sender();
        ArrayList<Food> foodsByName = null;
        ArrayList<Food> foods = loadFood();
        try {
            foodsByName = jsonHandler.getFoodsByName(foods, name);
        } catch(Exception e){
            sender.makeLog("FoodController", SILevel.WARNING, e.getMessage(), "error at foods by name");
        }
        if(foodsByName == null){
            sender.makeLog("FoodController", SILevel.WARNING, "NullPointerException", "list of foods by name: " + name + " is null");
        } else {
            sender.makeLog("FoodController", SILevel.WARNING, "select foods by name", "list of foods by name: " + name + " is size: " + foodsByName.size());
        }
        return gson.toJson(foodsByName);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/bookings")
    public String getBookingById(@RequestParam Long id){
        Sender sender = new Sender();
        ServiceInfo flightService = new EnvHelper().getService(Service.FLIGHT);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(flightService.getHost(), flightService.getPort())
                .usePlaintext()
                .build();

        FlightServiceGrpc.FlightServiceBlockingStub stub
                = FlightServiceGrpc.newBlockingStub(channel);

        GetBookingReply getBooking = stub.getBooking(GetBookingRequest.newBuilder()
                .setBookingId(id)
                .build());

        channel.shutdown();

        try {
            String json = JsonFormat.printer().print(getBooking);
            sender.makeLog(FoodController.class.getName(), SILevel.INFO, "Food service retrieved booking with id " + id, "");
            return json;
        } catch (InvalidProtocolBufferException e) {
            sender.makeLog(FoodController.class.getName(),
                    SILevel.ERROR,
                    InvalidProtocolBufferException.class.getName(),
                    "Protocol buffer invalid for booking reply with id " + id);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
        }
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping("/saveBookings")
    public void saveBooking(@RequestBody SaveBookingRequest reqObj){
        System.out.println("hello");
        System.out.println(reqObj);

        ServiceInfo mongoInfo = new EnvHelper().getService(Service.MONGO);
        MongoClient mongoClient = new MongoClient(mongoInfo.getHost(), mongoInfo.getPort());
        MongoDatabase database = mongoClient.getDatabase("food");
        MongoCollection<Document> coll = database.getCollection("booking");
        Document document = new Document("bookingId",reqObj.getBookingId())
                .append("tickets",new Document("ticketId",reqObj.getTicketId()))
                .append("foods",new Document("name",reqObj.getFoodName())
                        .append("description",reqObj.getDescription()));
        coll.insertOne(document);

        System.out.println("The food order to the ticket with id:" + reqObj.getTicketId() +
                " have ben successfully saved to the mongo database");


    }

}
