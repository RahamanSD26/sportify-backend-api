package sportify.backend.api.service.scoreCard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;

public interface IplScoreCardApiService {
  IplScoreCardApiDto createEntity(String dateTime)throws Exception;
  IplScoreCardApiDto getEntityById(String id) throws Exception;
  Page<IplScoreCardApiDto> getAllEntities(Pageable pageable)throws Exception;
}
