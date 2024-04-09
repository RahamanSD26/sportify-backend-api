package sportify.backend.api.domain.scoreCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sportify.backend.api.domain.BaseEntity;
import util.JavaApiClass.iplscorecard.*;
import java.util.List;
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
    private List<Score> scoreList;
    private String tossWinner;
    private String tossChoice;
    private String seriesId;
    private List<Batting> batting;
    private List<Bowling> bowling;
    private List<Catching> catching;
    private Extras extras;
//    private Totals totals;
    private String inning;

}
