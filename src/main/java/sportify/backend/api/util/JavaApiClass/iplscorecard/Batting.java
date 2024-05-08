package sportify.backend.api.util.JavaApiClass.iplscorecard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Batting {
    private BatsMan batsman;
    private String dismissal;
    private Bowler bowler;
    private Fielder catcher;

    @JsonProperty("dismissal-text")
    private String dismissalText;

    private int r;
    private int b;

    @JsonProperty("4s")
    private int _4s;

    @JsonProperty("6s")
    private int _6s;

    private double sr;
}
