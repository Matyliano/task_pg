package matyliano.task_pg.util;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import matyliano.task_pg.model.Dollar;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class CurrencyReader {

    private final static Type CURRENCIES_TYPE = new TypeToken<List<Dollar>>() {
    }.getType();

    public static List<Dollar> loadDataFrom(String fileName) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:" + fileName);
        JsonReader reader = new JsonReader(new FileReader(file));

        return new Gson().fromJson(reader, CURRENCIES_TYPE);
    }
}
