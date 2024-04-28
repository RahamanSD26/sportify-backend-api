package sportify.backend.api.service;

import sportify.backend.api.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sportify.backend.api.util.JavaApiClass.iplAllMatches.IplAllMatches;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.IplCricketMatch;
import sportify.backend.api.util.JavaApiClass.iplSquad.IplSquad;
import sportify.backend.api.util.JavaApiClass.iplscorecard.IplScoreCard;

import java.util.HashMap;
import java.util.Map;


@Service
public class CricketDataService {

    @Autowired
    private WebClient webClient;
    long count=0;
    int apiSuffixNumber=1;

        public <T> T fetchDataFromApi(Class<T> responseType,String id,String path) throws Exception {
            if(responseType.equals(IplCricketMatch.class)){
                  count++;
            }
           else if(responseType.equals(IplAllMatches.class)){
                count++;
            } else if (responseType.equals(IplScoreCard.class)) {
                count+=10;
            }else {
               count+=10;
            }

            Map<Integer,String> apiKeyValue=new HashMap<>();

            apiKeyValue.put(1,Constants.CRICKET_API_KEY_1);
            apiKeyValue.put(2,Constants.CRICKET_API_KEY_2);
            apiKeyValue.put(3,Constants.CRICKET_API_KEY_3);
            apiKeyValue.put(4,Constants.CRICKET_API_KEY_4);
            apiKeyValue.put(5,Constants.CRICKET_API_KEY_5);
            apiKeyValue.put(6,Constants.CRICKET_API_KEY_6);
            apiKeyValue.put(7,Constants.CRICKET_API_KEY_7);
            apiKeyValue.put(8,Constants.CRICKET_API_KEY_8);
            apiKeyValue.put(9,Constants.CRICKET_API_KEY_9);
            apiKeyValue.put(10,Constants.CRICKET_API_KEY_10);

            if(count>90&&responseType.equals(IplScoreCard.class)||responseType.equals(IplSquad.class)){
               apiSuffixNumber++;
               count=0;
               if(apiSuffixNumber>10) {
                   apiSuffixNumber=1;
                   throw new Exception("All Crickdata Accounts Used");
               }
            }
            T response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(path)
//                            .queryParam("apikey", Constants.CRICK_API_KEY)
                            .queryParam("apikey", apiKeyValue.get(apiSuffixNumber))
                            .queryParam("offset", "0")
                            .queryParam("id", id)
                            .build())
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();  // Consider asynchronous approach for non-blocking

            System.out.println(response);
            return response;
        }
    }

