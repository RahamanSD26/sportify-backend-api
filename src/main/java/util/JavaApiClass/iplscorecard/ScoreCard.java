package util.JavaApiClass.iplscorecard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCard {
    private List<Batting> batting;
    private List<Bowling> bowling;
    private List<Catching> catching;
    private Extras extras;
//    private Totals totals;
    private String inning;
}
