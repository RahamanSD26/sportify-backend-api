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
import sportify.backend.api.util.JavaApiClass.iplBallByBall.Score;
import sportify.backend.api.util.JavaApiClass.iplBallByBall.TeamInfo;

import java.time.*;
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
         iplBallByBallApiDto.setTossWinner(iplCricketMatch.getData().getTossWinner());
         iplBallByBallApiDto.setTossChoice(iplCricketMatch.getData().getTossChoice());
//         iplBallByBallApiDto.setSeries_id(iplCricketMatch.getData().getSeries_id());
         iplBallByBallApiDto.setCommentary(iplCricketMatch.getData().getBbb());


        String team1=iplCricketMatch.getData().getTeamInfo().get(0).getName();

        List<TeamInfo> teamInfoList=new ArrayList<>();

        if(team1.equals(iplCricketMatch.getData().getTossWinner())&&iplCricketMatch.getData().getTossChoice().equals("bat")){
            teamInfoList.add(iplCricketMatch.getData().getTeamInfo().get(0));
            teamInfoList.add(iplCricketMatch.getData().getTeamInfo().get(1));
        }else{
            teamInfoList.add(iplCricketMatch.getData().getTeamInfo().get(1));
            teamInfoList.add(iplCricketMatch.getData().getTeamInfo().get(0));
        }

        iplBallByBallApiDto.setTeamInfoList(teamInfoList);



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

    private int count = 0;
    private List<IplAllMatchesApiDto> iplAllMatchesApiDtoList;

    @Scheduled(fixedRate = 60 * 1000) // 1 min in milliseconds
    public void scheduledMethod() {
        try {
            LocalDate date=LocalDate.now();
            String today = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Initialize match data if not already initialized
            if (count == 0) {
                initializeMatchData(today);
                count++;
            }


            // Check if there are any active matches scheduled for today
            for (IplAllMatchesApiDto match : iplAllMatchesApiDtoList) {
                String matchDate = match.getDate();
                if (matchDate.equals(today) && match.getIsActive() && !match.getStatus().equals("Match not started")) {
                    // If match is active, check if it's live
                    if (match.getIsActive() && !match.getStatus().contains("won") && !match.getStatus().contains("lost") && !match.getStatus().contains("tied")) {
                        // Call ball-by-ball API for the live match
                        IplBallByBallApiDto ballByBallData = createEntityById(match.getMatchId());
                        if(ballByBallData.getStatus().contains("won") && !match.getStatus().contains("lost")){
                            iplAllMatchesApiService.createEntity();
                        }
                    } else {
                        // If match has ended, reset count and reinitialize match data
                        count = 0;
                        initializeMatchData(today);
                    }
                }else{
                    // Parse the target time string from the match object (assuming it has a time property)
                    LocalDateTime targetDateTimeGMT = LocalDateTime.parse(match.getTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                    // Convert target time to Indian Standard Time (IST)
                    ZoneId indianTimeZone = ZoneId.of("Asia/Kolkata");
                    LocalDateTime targetDateTimeIST = targetDateTimeGMT.atZone(ZoneOffset.UTC).withZoneSameInstant(indianTimeZone).toLocalDateTime();

                    // Get current time in IST
                    LocalDateTime nowWithIST = LocalDateTime.now(indianTimeZone);

                    // Check if within 3 minutes of target time in IST (inclusive)
                    boolean withinWindow = nowWithIST.isAfter(targetDateTimeIST.minusMinutes(1)) && nowWithIST.isBefore(targetDateTimeIST.plusMinutes(3));

                    if (withinWindow) {
                        // Call your method here
                        iplAllMatchesApiService.createEntity();
                    }

                }
            }
        } catch (Exception e) {
            // Handle exceptions gracefully
            e.printStackTrace();
        }
    }

    private void initializeMatchData(String date) throws Exception {
        iplAllMatchesApiService.createEntity();
        iplAllMatchesApiDtoList = iplAllMatchesApiService.getMatchListByDate(date);
    }


    public IplBallByBallApiDto changeEntityAsPerRequirement(IplBallByBallApiDto iplBallByBallApiDto){
        String team1=iplBallByBallApiDto.getTeamInfoList().get(0).getName();
        String team2=iplBallByBallApiDto.getTeamInfoList().get(1).getName();

        List<Score> scoreList=new ArrayList<>();

            scoreList.add(iplBallByBallApiDto.getScoreList().get(team1));
            scoreList.add(iplBallByBallApiDto.getScoreList().get(team2));


        iplBallByBallApiDto.setTempScoreList(scoreList);
        return iplBallByBallApiDto;
    }
}
