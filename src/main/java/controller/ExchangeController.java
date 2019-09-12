package controller;

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

         String exchangeUrl =  "http://api.nbp.pl/api/exchangerates/rates/a/usd/?format=json";

         ResponseEntity<String> exchangeResponse = restTemplate.getForEntity(exchangeUrl,String.class);

         String  mid = exchangeResponse.getBody();

        StringBuffer sentenceBuilder = new StringBuffer();
        JsonParser jsonParser = new JsonParser();
        JsonElement baseJsonElement = jsonParser.parse(mid);
        JsonObject baseJsonObject = baseJsonElement.getAsJsonObject();
        JsonArray ratesJsonArray = baseJsonObject.get("rates").getAsJsonArray();
        JsonObject ratesJsonObject = ratesJsonArray.get(0).getAsJsonObject();
        String rates = ratesJsonObject.get("mid").getAsString();
        sentenceBuilder.append(rates);


        ModelAndView modelAndView = new ModelAndView("data");
        modelAndView.addObject("usd", sentenceBuilder);
        return modelAndView;
    }

}
