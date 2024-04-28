package sportify.backend.api.repository.scoreCard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sportify.backend.api.domain.scoreCard.IplScoreCardApi;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface IplScoreCardApiRepository extends MongoRepository<IplScoreCardApi,String> {
    Page<IplScoreCardApi> findAll(Pageable pageable);
    Page<IplScoreCardApi> findByIsActive(Boolean isActive, Pageable pageable);
    Optional<IplScoreCardApi> findByGuid(String guid);
    Optional<IplScoreCardApi> findTopByOrderByCreatedOnDesc();
    Optional<IplScoreCardApiDto> findByMatchId(String id);
    long countByTeamInfoShortname(String name);
}
