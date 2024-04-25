package sportify.backend.api.util.JavaApiClass.iplscorecard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Catching {
    private Fielder catcher;
    private int stumped;
    private int runout;
    private int _catch;
    private int cb;
    private int lbw;
    private int bowled;
}
