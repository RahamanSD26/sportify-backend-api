package sportify.backend.api.dto.scoreCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportify.backend.api.dto.BaseEntityDTO;
import sportify.backend.api.util.JavaApiClass.iplscorecard.Score;
import sportify.backend.api.util.JavaApiClass.iplscorecard.ScoreCard;
import sportify.backend.api.util.JavaApiClass.iplscorecard.TeamInfo;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IplScoreCardApiDto extends BaseEntityDTO {
    private String name;
    private String matchId;
    private String venue;
    private String status;
    private String dateTimeGMT;
    private List<TeamInfo> teamInfo;
    private Map<String, Score> scoreList;
    private List<Score> tempScoreList;
    private String tossWinner;
    private String tossChoice;
    private String seriesId;
    private Map<String, ScoreCard> scoreCardList;
    private List<ScoreCard> tempScoreCardList;
    private Boolean isMatchStarted;
    private Boolean isMatchEnded;
}
