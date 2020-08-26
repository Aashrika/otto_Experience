import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@Slf4j
public class Migration {

    public JSONObject saveJSON(String pathToJsonFile) {
        JSONParser parser = new JSONParser();
        JSONObject response = new JSONObject();

        try {
            Object obj = parser.parse(new FileReader(pathToJsonFile));
            JSONObject testObject = (JSONObject) obj;
            log.info("Received a JSON Object: " + testObject);

            if (checkJSON(testObject)) {
                addNewEntry(testObject);
                log.info("Request successful");
                response.put("Status", "Successful");
                return response;
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        response.put("Status", "Not suitable Request");
        log.info("Request not successful");
        return response;
    }

    public boolean checkJSON(JSONObject testObject) {
        log.info("Checking the testObject: " + testObject);
        //        TODO check if JSONObject has necessary details
        return true;
    }

    public void addNewEntry(JSONObject newObject) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // TODO change into the suitable connection
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");

            //TODO change the name of table and fields
            PreparedStatement preparedStatement = con.prepareStatement("insert into  Atable values ( ?, ? )");
            JSONParser parser = new JSONParser();

            String id = (String) newObject.get("id"); // from JSON tag
            preparedStatement.setString(1, id); // to the Database table

            String name = (String) newObject.get("name");
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();


            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
