package util.JavaApiClass.iplscorecard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class IplScoreCard {
    @JsonProperty("apikey")
    private String apiKey;

    @JsonProperty("data")
    private Match match;

    @JsonProperty("status")
    private String status;

    @JsonProperty("info")
    private Info info;

}