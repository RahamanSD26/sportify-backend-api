package sportify.backend.api.util.JavaApiClass.iplBallByBall;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class IplCricketMatch {
    private String apikey;
    private Data data;
}
