package sportify.backend.api.service.iplBallbyBall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sportify.backend.api.config.Constants;
import sportify.backend.api.dto.iplBallByBall.IplBallByBallApiDto;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;
import sportify.backend.api.mapper.iplBallByBall.IplBallByBallApiMapper;
import sportify.backend.api.repository.iplBallbyBall.IplBallByBallApiRepositoty;
import sportify.backend.api.service.CricketDataService;
import sportify.backend.api.service.matches.IplAllMatchesApiService;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.IplCricketMatch;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.Score;
import sportify.backend.api.util.JavaApiClass.iplscorecard.ScoreCard;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        cricketDataService.setCount(iplCricketMatch.getInfo().getHitsToday());
        Optional<IplBallByBallApiDto> iplBallByBallApiDtoOptional=iplBallByBallApiRepositoty.findByMatchId(matchId);

        IplBallByBallApiDto iplBallByBallApiDto;
        if(iplBallByBallApiDtoOptional.isPresent()){
          iplBallByBallApiDto=iplBallByBallApiDtoOptional.get();
        }else{
            iplBallByBallApiDto=new IplBallByBallApiDto();
        }

         iplBallByBallApiDto.setMatchId(iplCricketMatch.getData().getId());
         iplBallByBallApiDto.setName(iplCricketMatch.getData().getName());
         iplBallByBallApiDto.setMatchType(iplCricketMatch.getData().getMatchType());
         iplBallByBallApiDto.setStatus(iplCricketMatch.getData().getStatus());
         iplBallByBallApiDto.setVenue(iplCricketMatch.getData().getVenue());
         iplBallByBallApiDto.setDate(iplCricketMatch.getData().getDate());
         iplBallByBallApiDto.setDateTimeGmt(iplCricketMatch.getData().getDateTimeGMT());
         iplBallByBallApiDto.setTeamInfoList(iplCricketMatch.getData().getTeamInfo());
         iplBallByBallApiDto.setTossWinner(iplCricketMatch.getData().getTossWinner());
         iplBallByBallApiDto.setTossChoice(iplCricketMatch.getData().getTossChoice());
//         iplBallByBallApiDto.setSeries_id(iplCricketMatch.getData().getSeries_id());
         iplBallByBallApiDto.setCommentary(iplCricketMatch.getData().getBbb());

        Map<String, Score> scoreMap=new HashMap<>();
        List<Score> scoreList=iplCricketMatch.getData().getScore();

        if(!scoreList.isEmpty()){
            for(Score score:scoreList){
                String inning=score.getInning().replace("Inning 1", "").trim();
                scoreMap.put(inning,score);
            }
        }
        iplBallByBallApiDto.setScoreList(scoreMap);

         return IplBallByBallApiMapper.toDTO(iplBallByBallApiRepositoty.save(IplBallByBallApiMapper.toEntty(iplBallByBallApiDto)));
    }

    @Override
    public IplBallByBallApiDto getEntityById(String matchId) {
        Optional<IplBallByBallApiDto> iplBallByBallApiDtoOptional=iplBallByBallApiRepositoty.findByMatchId(matchId);
        if(iplBallByBallApiDtoOptional.isEmpty()){
            return null;
        }
        return  changeEntityAsPerRequirement(iplBallByBallApiDtoOptional.get());
    }
    int count=0;
    List<IplAllMatchesApiDto> iplAllMatchesApiDtoList=null;
    IplAllMatchesApiDto iplAllMatchesApiDto=null;
    @Scheduled(fixedRate = 60 * 1000) // 1 min in milliseconds
    public void scheduledMethod() throws Exception {
        // Call your parameterized method with the stored arguments
        if(count==0) {
            iplAllMatchesApiService.createEntity();
            iplAllMatchesApiDtoList= iplAllMatchesApiService.getEntitiesByStatus(true);
            iplAllMatchesApiDto = iplAllMatchesApiDtoList.get(0);
            count++;
        }
            if (iplAllMatchesApiDto.getIsActive() && !iplAllMatchesApiDto.getStatus().equals("Match not started")) {
                createEntityById(iplAllMatchesApiDtoList.get(0).getMatchId());
        }
            if(!iplAllMatchesApiDto.getIsActive()){
                count=0;
                iplAllMatchesApiService.createEntity();
            }
//        if(iplAllMatchesApiDtoList.size()==2){
//            if (iplAllMatchesApiDtoList.get(0).getIsActive() && !iplAllMatchesApiDtoList.get(0).getStatus().equals("Match not started")) {
//                createEntityById(iplAllMatchesApiDtoList.get(0).getMatchId());
//            } else if(iplAllMatchesApiDtoList.get(1).getIsActive() && !iplAllMatchesApiDtoList.get(1).getStatus().equals("Match not started")) {
//                createEntityById(iplAllMatchesApiDtoList.get(1).getMatchId());
//            }
//        }
    }

    public IplBallByBallApiDto changeEntityAsPerRequirement(IplBallByBallApiDto iplBallByBallApiDto){
        String team1=iplBallByBallApiDto.getTeamInfoList().get(0).getName();
        String team2=iplBallByBallApiDto.getTeamInfoList().get(1).getName();

        List<Score> scoreList=new ArrayList<>();

        if(team1.equals(iplBallByBallApiDto.getTossWinner())&&iplBallByBallApiDto.getTossChoice().equals("bat")){
            scoreList.add(iplBallByBallApiDto.getScoreList().get(team1));
            scoreList.add(iplBallByBallApiDto.getScoreList().get(team2));
        }else{
            scoreList.add(iplBallByBallApiDto.getScoreList().get(team2));
            scoreList.add(iplBallByBallApiDto.getScoreList().get(team1));
        }

        iplBallByBallApiDto.setTempScoreList(scoreList);
        return iplBallByBallApiDto;
    }
}
