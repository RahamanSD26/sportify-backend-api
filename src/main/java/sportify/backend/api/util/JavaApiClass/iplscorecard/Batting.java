package sportify.backend.api.util.JavaApiClass.iplscorecard;

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
    private String dismissalText;
    private int r;
    private int b;
    private int _4s;
    private int _6s;
    private double sr;
}
