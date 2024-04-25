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
    List<IplAllMatchesApiDto> findByIsActiveOrderByIntMatchNumberAsc(Boolean isActive);
    List<IplAllMatchesApiDto> findByVenueOrderByIntMatchNumberAsc(String venue);
    Optional<IplAllMatchesApi> findByGuid(String guid);
    Optional<IplAllMatchesApi> findTopByOrderByCreatedOnDesc();
    List<IplAllMatchesApiDto> findAllByOrderByIntMatchNumberAsc();
    List<IplAllMatchesApiDto> findByDate(String date);
    Optional<IplAllMatchesApi> findByTime(String time);
    Optional<IplAllMatchesApi> findByMatchId(String id);
    List<IplAllMatchesApiDto> findByTeamInfoShortnameOrderByIntMatchNumberAsc(String shortName);
    List<IplAllMatchesApiDto> findByTeamInfoShortnameAndIsActiveOrderByIntMatchNumberAsc(String shortName,Boolean isActive);
    List<IplAllMatchesApiDto> findByVenueAndIsActiveOrderByIntMatchNumberAsc(String venue,Boolean isActive);
   List<IplAllMatchesApiDto> findByTeamInfoShortnameAndVenueOrderByIntMatchNumberAsc(String shortName, String venue);
   List<IplAllMatchesApiDto> findByIsActiveAndTeamInfoShortnameAndVenueOrderByIntMatchNumberAsc(Boolean isActive,String shortName, String venue);
}
