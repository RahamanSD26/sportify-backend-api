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
        String team2=iplCricketMatch.getData().getTeamInfo().get(1).getName();
        List<TeamInfo> teamInfoList=new ArrayList<>();

        if((team1.equals(iplCricketMatch.getData().getTossWinner())&&iplCricketMatch.getData().getTossChoice().equals("bat"))||(team2.equals(iplCricketMatch.getData().getTossWinner())&&!iplCricketMatch.getData().getTossChoice().equals("bat"))){
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

//    @Scheduled(fixedRate = 60 * 1000) // 1 min in milliseconds
    public void scheduledMethod() {
        try {
            LocalDate date=LocalDate.now();
            String today = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Initialize match data if not already initialized
            if (count == 0||(!iplAllMatchesApiDtoList.isEmpty()&&!iplAllMatchesApiDtoList.get(0).getDate().equals(today))) {
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
                        if (ballByBallData.getStatus().contains("won") && !match.getStatus().contains("lost")) {
                            iplAllMatchesApiService.createEntity();
                            count = 0;
                            initializeMatchData(today);
                        }
                    }
                } else {
                    // Get the current time in the local time zone (Asia/Kolkata)
                    ZoneId localTimeZone = ZoneId.of("Asia/Kolkata");
                    LocalDateTime now = LocalDateTime.now(localTimeZone);

                    // Define the start and end times for the time window for each match
                    LocalDateTime match1StartTime = LocalDateTime.of(now.toLocalDate(), LocalTime.of(15, 30)); // First match starts at 3:30 PM
                    LocalDateTime match1EndTime = LocalDateTime.of(now.toLocalDate(), LocalTime.of(19, 0)); // First match ends at 3:33 PM
                    LocalDateTime match2StartTime = LocalDateTime.of(now.toLocalDate(), LocalTime.of(19, 30)); // Second match starts at 7:30 PM
                    LocalDateTime match2EndTime = LocalDateTime.of(now.toLocalDate(), LocalTime.of(23, 30)); // Second match ends at 7:33 PM

                    // Check if the current time is within the time window for the first match
                    if (now.isAfter(match1StartTime) && now.isBefore(match1EndTime)) {
                        // Call the method to create the match entity for the first match
                        initializeMatchData(today);
                    }

                    // Check if the current time is within the time window for the second match
                    if (now.isAfter(match2StartTime) && now.isBefore(match2EndTime)) {
                        // Call the method to create the match entity for the second match
                        initializeMatchData(today);
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
