package util.JavaApiClass.iplscorecard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Bowling {
    private Bowler bowler;
    private int o;
    private int m;
    private int r;
    private int w;
    private int nb;
    private int wd;
    private double eco;

}
