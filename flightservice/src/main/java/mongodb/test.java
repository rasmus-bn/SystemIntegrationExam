package mongodb;

public class test {



    public static void main(String[] args) {
        Connector con = new Connector();
        System.out.println(con.getCollection());

        System.out.println("finished");
    }
}
