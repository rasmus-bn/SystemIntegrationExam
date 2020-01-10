import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main  {
    public static void main(String[] argv) throws IOException, TimeoutException {
        String toInt= "hey";
        try{

            Integer.parseInt(toInt);} catch(Exception e){

            Logger logger
                    = Logger.getLogger(
                    Main.class.getName());
            logger.setLevel(Level.WARNING);
            Message message = new Message(""+logger.getLevel(), "NaN: '" + toInt + "'", "rating on service");
            Sender sender = new Sender();
            sender.sendToServer(message);
    }}
}
