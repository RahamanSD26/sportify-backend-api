package sportify.backend.api.dto.pointsTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportify.backend.api.dto.BaseEntityDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IplPointsTableDto extends BaseEntityDTO {
    String pos;
    String team;
    String P;
    String W;
    String L;
    Integer NR;
    Float NRR;
    String FOR;
    String AGAINST;
    Integer PTS;

}
