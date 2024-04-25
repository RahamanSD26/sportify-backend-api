package sportify.backend.api.repository.pointsTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sportify.backend.api.domain.pointsTable.IplPointsTable;
import sportify.backend.api.domain.scoreCard.IplScoreCardApi;
import sportify.backend.api.dto.pointsTable.IplPointsTableDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointsTableRepository extends MongoRepository<IplPointsTable,String> {
    Page<IplPointsTable> findAll(Pageable pageable);
    @Aggregation(pipeline = {
            "{ $sort: { PTS: -1, NRR: -1 } }",
            "{ $group: { _id: '$PTS', teams: { $push: '$$ROOT' } } }",
            "{ $replaceRoot: { newRoot: { $arrayElemAt: ['$teams', 0] } } }",
            "{ $sort: { PTS: -1 } }"
    })
    List<IplPointsTableDto> findAllSortedByPTSAndNRR();
    Optional<IplPointsTableDto> findByTeam(String name);
}
