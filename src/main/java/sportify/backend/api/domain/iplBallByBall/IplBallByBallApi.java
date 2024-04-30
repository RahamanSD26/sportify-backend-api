package sportify.backend.api.domain.iplBallByBall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sportify.backend.api.domain.BaseEntity;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.Commentary;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.Score;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.TeamInfo;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class IplBallByBallApi extends BaseEntity {
    private String matchId;
    private String name;
    private String matchType;
    private String status;
    private String venue;
    private String date;
    private String dateTimeGmt;
    private List<TeamInfo> teamInfoList;
    private Map<String, Score> scoreList;
    private String tossWinner;
    private String tossChoice;
//    private String series_id;
    private List<Commentary> commentary;

}
