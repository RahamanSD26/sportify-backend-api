package sportify.backend.api.repository.iplBallbyBall;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sportify.backend.api.domain.iplBallByBall.IplBallByBallApi;
import sportify.backend.api.dto.iplBallByBall.IplBallByBallApiDto;

import java.util.Optional;

@Repository
public interface IplBallByBallApiRepositoty extends MongoRepository<IplBallByBallApi,String> {
    Optional<IplBallByBallApiDto> findByMatchId(String matchId);
}
