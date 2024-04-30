package sportify.backend.api.util.JavaApiClass.iplBallByBall;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private int r;
    private int w;
    private float o;
    private String inning;
}
