package Processing.Data;

import java.sql.ResultSet;

/**
 * Created by s124392 on 1-4-2016.
 * An interface for the various data processors required by the {@code DataHandler} class.
 */
public interface DataProcessorInterface<T> {

     T process(ResultSet entry);
}
