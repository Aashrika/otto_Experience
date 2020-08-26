import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


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
            // TODO check the suitable connection
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
            Statement stmt = con.createStatement();

            // TODO rename the table name and all fields
//            String query = "INSERT INTO someTable" +
//                    "SELECT * FROM "+ OPENJSON(newObject) +
//                    "WITH (id int, nameOfField )";

//            stmt.executeQuery(query);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
