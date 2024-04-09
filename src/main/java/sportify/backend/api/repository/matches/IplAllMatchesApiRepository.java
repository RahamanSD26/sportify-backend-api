package sportify.backend.api.repository.matches;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sportify.backend.api.domain.matches.IplAllMatchesApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public interface IplAllMatchesApiRepository extends MongoRepository<IplAllMatchesApi,String> {
   Page<IplAllMatchesApi> findAll(Pageable pageable);
    List<IplAllMatchesApi> findByIsActiveOrderByIntMatchNumberAsc(Boolean isActive);
    List<IplAllMatchesApi> findByVenueOrderByIntMatchNumberAsc(String venue);
    Optional<IplAllMatchesApi> findByGuid(String guid);
    Optional<IplAllMatchesApi> findTopByOrderByCreatedOnDesc();
    List<IplAllMatchesApiDto> findAllByOrderByIntMatchNumberAsc();
    List<IplAllMatchesApiDto> findByDate(String date);
    Optional<IplAllMatchesApi> findByTime(String time);
    Optional<IplAllMatchesApi> findByMatchId(String id);
    List<IplAllMatchesApi> findByTeamInfoShortnameOrderByIntMatchNumberAsc(String shortName);
   List<IplAllMatchesApi> findByTeamInfoShortnameAndVenueOrderByIntMatchNumberAsc(String shortName, String venue);
}
