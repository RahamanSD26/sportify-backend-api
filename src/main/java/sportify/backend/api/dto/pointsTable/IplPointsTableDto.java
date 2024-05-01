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
    private String pos;
    private String team;
    private String P;
    private String W;
    private String L;
    private Integer NR;
    private Float NRR;
    private String FOR;
    private String AGAINST;
    private Integer PTS;
    private String imgUrl;
}
