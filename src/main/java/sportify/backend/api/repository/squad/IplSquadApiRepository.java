package sportify.backend.api.repository.squad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sportify.backend.api.domain.squad.IplSquadApi;
import sportify.backend.api.dto.squad.IplSquadApiDto;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface IplSquadApiRepository extends MongoRepository<IplSquadApi,String> {
    Page<IplSquadApi> findAll(Pageable pageable);
    Optional<IplSquadApiDto> findByMatchId(String matchId);
}
