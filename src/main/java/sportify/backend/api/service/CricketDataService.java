package sportify.backend.api.service;

import sportify.backend.api.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class CricketDataService {

    @Autowired
    private WebClient webClient;

        public <T> T fetchDataFromApi(Class<T> responseType,String id,String path) {
            T response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(path)
//                            .queryParam("apikey", Constants.CRICK_API_KEY)
                            .queryParam("apikey", "6ada99af-25b1-44cf-80c3-3d850428bcd0")
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

