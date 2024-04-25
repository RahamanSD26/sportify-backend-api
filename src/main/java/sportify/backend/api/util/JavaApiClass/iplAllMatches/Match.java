package sportify.backend.api.util.JavaApiClass.iplAllMatches;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private String id;
    private String name;
    private String matchType;
    private String status;
    private String venue;
    private String date;
    private String dateTimeGMT;
    private List<String> teams;
    private List<TeamInfo> teamInfo;
    private boolean fantasyEnabled;
    private boolean bbbEnabled;
    private boolean hasSquad;
    private boolean matchStarted;
    private boolean matchEnded;
}
