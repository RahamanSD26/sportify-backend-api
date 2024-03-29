package sportify.backend.api.dto.matches;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sportify.backend.api.dto.BaseEntityDTO;
import util.JavaApiClass.iplAllMatches.TeamInfo;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IplAllMatchesApiDto extends BaseEntityDTO {
    private String venue;
    private String date;
    private String time;
    private List<String> teamsName;
    private String matchNumber;
    private List<TeamInfo> teamInfo;
    private String status;
    private String matchId;
}
