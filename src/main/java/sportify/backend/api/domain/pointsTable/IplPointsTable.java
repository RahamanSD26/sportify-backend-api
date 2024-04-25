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
