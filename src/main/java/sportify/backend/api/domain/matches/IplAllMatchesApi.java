package sportify.backend.api.domain.matches;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sportify.backend.api.domain.BaseEntity;
import util.JavaApiClass.iplAllMatches.TeamInfo;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class IplAllMatchesApi extends BaseEntity {
    private String venue;
    private String date;
    private String time;
    private String teamsName;
    private String matchNumber;
    private List<TeamInfo> teamInfo;
    private String status;
    private String matchId;
    private Integer intMatchNumber;
}
