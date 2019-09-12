package matyliano.task_pg.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ExchangeController {


    @GetMapping("/data")
    public ModelAndView showExchangeRate() {

        RestTemplate restTemplate = new RestTemplate();

        String exchangeUrl =  "http://api.nbp.pl/api/exchangerates/rates/c/usd/today/";

        ResponseEntity<String> exchangeResponse = restTemplate.getForEntity(exchangeUrl,String.class);

        String  mid = exchangeResponse.getBody();


        StringBuffer sentenceBuilder = new StringBuffer();
        StringBuffer sentenceBuilder2 = new StringBuffer();
        StringBuffer sentenceBuilder3 = new StringBuffer();


        JsonParser jsonParser = new JsonParser();

        JsonElement baseJsonElement = jsonParser.parse(mid);

        JsonObject baseJsonObject = baseJsonElement.getAsJsonObject();

        JsonArray ratesJsonArray = baseJsonObject.get("rates").getAsJsonArray();

        JsonObject ratesJsonObject = ratesJsonArray.get(0).getAsJsonObject();

        String bid = ratesJsonObject.get("bid").getAsString();
        String ask = ratesJsonObject.get("ask").getAsString();
        String effectiveDate = ratesJsonObject.get("effectiveDate").getAsString();

        sentenceBuilder.append(bid);
        sentenceBuilder2.append(ask);
        sentenceBuilder3.append(effectiveDate);

        ModelAndView modelAndView = new ModelAndView("data");
        modelAndView.addObject("bid", sentenceBuilder);
        modelAndView.addObject("ask", sentenceBuilder2);
        modelAndView.addObject("effectiveDate", sentenceBuilder3);

        return modelAndView;
    }

}
