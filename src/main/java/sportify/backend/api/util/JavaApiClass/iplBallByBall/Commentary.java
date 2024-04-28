package sportify.backend.api.util.JavaApiClass.iplBallByBall;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentary {
    private int n;
    private int inning;
    private int over;
    private int ball;
    private Batsman batsman;
    private Bowler bowler;
    private int runs;
    private int extras;
    private String dismissal;
    private String catcher;
}
