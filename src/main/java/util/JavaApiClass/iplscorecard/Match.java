package util.JavaApiClass.iplscorecard;

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
        private List<Score> score;
        private String tossWinner;
        private String tossChoice;
        private String matchWinner;
        private String series_id;
        private List<ScoreCard> scorecard;
        private boolean matchStarted;
        private boolean matchEnded;
}
