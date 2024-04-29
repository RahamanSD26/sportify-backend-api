package sportify.backend.api.service.iplBallbyBall;

import sportify.backend.api.dto.iplBallByBall.IplBallByBallApiDto;

public interface IplBallByBallApiService {
    IplBallByBallApiDto createEntityById(String matchId) throws Exception;
    IplBallByBallApiDto getEntityById(String matchId) throws Exception;
    void scheduledMethod() throws Exception;
}
