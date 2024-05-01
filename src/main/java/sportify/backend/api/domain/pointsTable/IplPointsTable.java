package sportify.backend.api.domain.pointsTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sportify.backend.api.domain.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class IplPointsTable extends BaseEntity {
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
