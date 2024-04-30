package sportify.backend.api.dto.iplBallByBall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportify.backend.api.dto.BaseEntityDTO;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.Commentary;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.Score;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.TeamInfo;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IplBallByBallApiDto extends BaseEntityDTO {
    private String matchId;
    private String name;
    private String matchType;
    private String status;
    private String venue;
    private String date;
    private String dateTimeGmt;
    private List<TeamInfo> teamInfoList;
    private Map<String, Score> scoreList;
    private List<Score> tempScoreList;
    private String tossWinner;
    private String tossChoice;
//    private String series_id;
    private List<Commentary> commentary;
}
