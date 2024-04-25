package sportify.backend.api.domain.scoreCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sportify.backend.api.domain.BaseEntity;
import sportify.backend.api.util.JavaApiClass.iplscorecard.TeamInfo;
import sportify.backend.api.util.JavaApiClass.iplscorecard.Score;
import sportify.backend.api.util.JavaApiClass.iplscorecard.ScoreCard;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class IplScoreCardApi extends BaseEntity {
    private String name;
    private String matchId;
    private String venue;
    private String status;
    private String dateTimeGMT;
    private List<TeamInfo> teamInfo;
    private Map<String, Score> scoreList;
    private String tossWinner;
    private String tossChoice;
    private String seriesId;
    private Map<String, ScoreCard> scoreCardList;
    private Boolean isMatchStarted;
    private Boolean isMatchEnded;
}
