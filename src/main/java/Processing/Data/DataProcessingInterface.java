package Processing.Data;

import java.sql.ResultSet;

/**
 * Created by s124392 on 1-4-2016.
 */
public interface DataProcessingInterface<T> {

     T process(ResultSet entry);
}
