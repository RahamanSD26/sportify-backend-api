package sportify.backend.api.dto.scoreCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportify.backend.api.dto.BaseEntityDTO;
import util.JavaApiClass.iplscorecard.*;

import java.util.List;
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
