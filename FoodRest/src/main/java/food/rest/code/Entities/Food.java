package food.rest.code.Entities;

public class Food {

    private long id;
    private String name;
    private String name_scientific;
    private String description;
    private String itis_id;
    private String wikipedia_id;
    private String food_group;
    private String food_subgroup;
    private String food_type;
    private String category;

    public Food(long id, String name, String name_scientific, String description, String itis_id, String wikipedia_id, String food_group, String food_subgroup, String food_type, String category) {
        this.id = id;
        this.name = name;
        this.name_scientific = name_scientific;
        this.description = description;
        this.itis_id = itis_id;
        this.wikipedia_id = wikipedia_id;
        this.food_group = food_group;
        this.food_subgroup = food_subgroup;
        this.food_type = food_type;
        this.category = category;
    }

    public Food() {

    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_scientific() {
        return name_scientific;
    }

    public void setName_scientific(String name_scientific) {
        this.name_scientific = name_scientific;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItis_id() {
        return itis_id;
    }

    public void setItis_id(String itis_id) {
        this.itis_id = itis_id;
    }

    public String getWikipedia_id() {
        return wikipedia_id;
    }

    public void setWikipedia_id(String wikipedia_id) {
        this.wikipedia_id = wikipedia_id;
    }

    public String getFood_group() {
        return food_group;
    }

    public void setFood_group(String food_group) {
        this.food_group = food_group;
    }

    public String getFood_subgroup() {
        return food_subgroup;
    }

    public void setFood_subgroup(String food_subgroup) {
        this.food_subgroup = food_subgroup;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", name_scientific='" + name_scientific + '\'' +
                ", description='" + description + '\'' +
                ", itis_id='" + itis_id + '\'' +
                ", wikipedia_id='" + wikipedia_id + '\'' +
                ", food_group='" + food_group + '\'' +
                ", food_subgroup='" + food_subgroup + '\'' +
                ", food_type='" + food_type + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
