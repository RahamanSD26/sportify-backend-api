package sportify.backend.api.util.JavaApiClass.iplSquad;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class SquadInfo {
    private int hitsToday;
    private int hitsUsed;
    private int hitsLimit;
    private int credits;
    private int server;
    private double queryTime;
    private int s;
    private int cache;
}
