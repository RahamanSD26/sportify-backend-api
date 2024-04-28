package sportify.backend.api.service.iplBallbyBall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sportify.backend.api.config.Constants;
import sportify.backend.api.dto.iplBallByBall.IplBallByBallApiDto;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
import sportify.backend.api.mapper.iplBallByBall.IplBallByBallApiMapper;
import sportify.backend.api.repository.iplBallbyBall.IplBallByBallApiRepositoty;
import sportify.backend.api.service.CricketDataService;
import sportify.backend.api.service.matches.IplAllMatchesApiService;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.IplCricketMatch;

import java.util.List;
import java.util.Optional;

@Service
public class IplBallByBallApiServiceImpl implements IplBallByBallApiService{
    @Autowired
    CricketDataService cricketDataService;
    @Autowired
    IplBallByBallApiRepositoty iplBallByBallApiRepositoty;

    @Autowired
    IplAllMatchesApiService iplAllMatchesApiService;

    @Override
    public IplBallByBallApiDto createEntityById(String matchId) throws Exception {
        IplCricketMatch iplCricketMatch=cricketDataService.fetchDataFromApi(IplCricketMatch.class,matchId, Constants.IPL_SCORE_CARD_BALL_BY_BALL);
        if(iplCricketMatch.getData()==null){
            throw new Exception("ball By Ball Data not found From CrickData");
        }
        Optional<IplBallByBallApiDto> iplBallByBallApiDtoOptional=iplBallByBallApiRepositoty.findByMatchId(matchId);

        IplBallByBallApiDto iplBallByBallApiDto;
        if(iplBallByBallApiDtoOptional.isPresent()){
          iplBallByBallApiDto=iplBallByBallApiDtoOptional.get();
        }else{
            iplBallByBallApiDto=new IplBallByBallApiDto();
        }

         iplBallByBallApiDto.setMatchId(iplCricketMatch.getData().getId());
         iplBallByBallApiDto.setName(iplCricketMatch.getData().getName().split(",")[0].trim());
         iplBallByBallApiDto.setMatchType(iplCricketMatch.getData().getMatchType());
         iplBallByBallApiDto.setStatus(iplCricketMatch.getData().getStatus());
         iplBallByBallApiDto.setVenue(iplCricketMatch.getData().getVenue());
         iplBallByBallApiDto.setDate(iplCricketMatch.getData().getDate());
         iplBallByBallApiDto.setDateTimeGmt(iplCricketMatch.getData().getDateTimeGMT());
         iplBallByBallApiDto.setTeamInfoList(iplCricketMatch.getData().getTeamInfo());
         iplBallByBallApiDto.setScoreList(iplCricketMatch.getData().getScore());
         iplBallByBallApiDto.setTossWinner(iplCricketMatch.getData().getTossWinner());
         iplBallByBallApiDto.setTossChoice(iplCricketMatch.getData().getTossChoice());
//         iplBallByBallApiDto.setSeries_id(iplCricketMatch.getData().getSeries_id());
         iplBallByBallApiDto.setCommentary(iplCricketMatch.getData().getBbb());

         return IplBallByBallApiMapper.toDTO(iplBallByBallApiRepositoty.save(IplBallByBallApiMapper.toEntty(iplBallByBallApiDto)));
    }

    @Override
    public IplBallByBallApiDto getEntityById(String matchId) {
        Optional<IplBallByBallApiDto> iplBallByBallApiDtoOptional=iplBallByBallApiRepositoty.findByMatchId(matchId);
        if(iplBallByBallApiDtoOptional.isEmpty()){
            return null;
        }
        return  iplBallByBallApiDtoOptional.get();
    }

    @Scheduled(fixedRate = 60 * 1000) // 1 min in milliseconds
    public void scheduledMethod() throws Exception {
        // Call your parameterized method with the stored arguments
        List<IplAllMatchesApiDto> iplAllMatchesApiDtoList=iplAllMatchesApiService.getEntitiesByStatus(true);

        if(iplAllMatchesApiDtoList.size()==1) {
            if (iplAllMatchesApiDtoList.get(0).getIsActive() && !iplAllMatchesApiDtoList.get(0).getStatus().equals("Match not started")) {
                createEntityById(iplAllMatchesApiDtoList.get(0).getMatchId());
            }
        }else if(iplAllMatchesApiDtoList.size()==2){
            if (iplAllMatchesApiDtoList.get(0).getIsActive() && !iplAllMatchesApiDtoList.get(0).getStatus().equals("Match not started")) {
                createEntityById(iplAllMatchesApiDtoList.get(0).getMatchId());
            } else if(iplAllMatchesApiDtoList.get(1).getIsActive() && !iplAllMatchesApiDtoList.get(1).getStatus().equals("Match not started")) {
                createEntityById(iplAllMatchesApiDtoList.get(1).getMatchId());
            }
        }
    }
}
