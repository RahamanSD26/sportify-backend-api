package sportify.backend.api.domain.squad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sportify.backend.api.domain.BaseEntity;
import sportify.backend.api.util.JavaApiClass.iplSquad.Player;
import sportify.backend.api.util.JavaApiClass.iplSquad.Team;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class IplSquadApi extends BaseEntity {
//    private String image;
//    private String teamName;
//    private String shortName;
//    List<Player> playersList;
    private String matchId;
    private List<Team> teamList;
}
