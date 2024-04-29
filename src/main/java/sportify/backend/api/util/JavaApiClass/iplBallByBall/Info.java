package sportify.backend.api.util.JavaApiClass.iplBallByBall;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    @JsonProperty("hitsToday")
    private int hitsToday;

    @JsonProperty("hitsUsed")
    private int hitsUsed;

    @JsonProperty("hitsLimit")
    private int hitsLimit;

    private int credits;
    private int server;

    @JsonProperty("queryTime")
    private double queryTime;

    private int s;
    private int cache;
}