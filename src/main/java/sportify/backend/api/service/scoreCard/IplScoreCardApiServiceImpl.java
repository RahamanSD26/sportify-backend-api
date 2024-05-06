package sportify.backend.api.service.scoreCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sportify.backend.api.config.Constants;
import sportify.backend.api.domain.scoreCard.IplScoreCardApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;
import sportify.backend.api.mapper.scoreCard.IplScoreCardApiMapper;
import sportify.backend.api.repository.scoreCard.IplScoreCardApiRepository;
import sportify.backend.api.service.CricketDataService;
import sportify.backend.api.service.matches.IplAllMatchesApiService;
import sportify.backend.api.service.pointsTable.IplPointsTableService;
import sportify.backend.api.util.JavaApiClass.iplscorecard.IplScoreCard;
import sportify.backend.api.util.JavaApiClass.iplscorecard.Score;
import sportify.backend.api.util.JavaApiClass.iplscorecard.ScoreCard;
import sportify.backend.api.util.JavaApiClass.iplscorecard.TeamInfo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IplScoreCardApiServiceImpl implements IplScoreCardApiService {
    @Autowired
    CricketDataService cricketDataService;
    @Autowired
    IplAllMatchesApiService iplAllMatchesApiService;
    @Autowired
    IplScoreCardApiRepository iplScoreCardApiRepository;
    @Lazy
    @Autowired
    IplPointsTableService pointsTableService;
    @Override
    public IplScoreCardApiDto createEntity(String time) throws Exception {
        IplAllMatchesApiDto match = iplAllMatchesApiService.getMatchByTime(time);
        if (match == null) {
            throw new Exception("Match not found");
        }

        IplScoreCard iplScoreCard = cricketDataService.fetchDataFromApi(IplScoreCard.class, match.getMatchId(), Constants.IPL_SCORE_CARD);
        cricketDataService.setCount(iplScoreCard.getInfo().getHitsUsed());
        if(iplScoreCard.getMatch()==null){
            throw new Exception("ScoreCard not Found from CrickData");
        }

        Optional<IplScoreCardApiDto> existingScoreCardOptional = iplScoreCardApiRepository.findByMatchId(iplScoreCard.getMatch().getId());

        // Fetch the existing entity from the database
        IplScoreCardApiDto iplScoreCardApiDto;
        if (existingScoreCardOptional.isPresent()) {
            iplScoreCardApiDto = existingScoreCardOptional.get();
        } else {
            // If entity does not exist, create a new one
            iplScoreCardApiDto = new IplScoreCardApiDto();
        }

        // Update other fields of the entity if needed
        iplScoreCardApiDto.setName(iplScoreCard.getMatch().getName());
        iplScoreCardApiDto.setVenue(iplScoreCard.getMatch().getVenue());
        iplScoreCardApiDto.setStatus(iplScoreCard.getMatch().getStatus());
        iplScoreCardApiDto.setDateTimeGMT(iplScoreCard.getMatch().getDateTimeGMT());
        iplScoreCardApiDto.setTossWinner(iplScoreCard.getMatch().getTossWinner());
        iplScoreCardApiDto.setTossChoice(iplScoreCard.getMatch().getTossChoice());
        iplScoreCardApiDto.setSeriesId(iplScoreCard.getMatch().getSeries_id());
        iplScoreCardApiDto.setMatchId(iplScoreCard.getMatch().getId());
        iplScoreCardApiDto.setIsMatchStarted(iplScoreCard.getMatch().isMatchStarted());
        iplScoreCardApiDto.setIsMatchEnded(iplScoreCard.getMatch().isMatchEnded());

        String team=iplScoreCard.getMatch().getTeamInfo().get(0).getName();

        List<TeamInfo> teamInfoList=new ArrayList<>();

        if(team.equals(iplScoreCard.getMatch().getTossWinner())&&iplScoreCard.getMatch().getTossChoice().equals("bat")){
            teamInfoList.add(iplScoreCard.getMatch().getTeamInfo().get(0));
            teamInfoList.add(iplScoreCard.getMatch().getTeamInfo().get(1));
        }else{
            teamInfoList.add(iplScoreCard.getMatch().getTeamInfo().get(1));
            teamInfoList.add(iplScoreCard.getMatch().getTeamInfo().get(0));
        }
        iplScoreCardApiDto.setTeamInfo(teamInfoList);


        // Update scoreMap and scoreCardMap with the new data
        Map<String, Score> scoreMap = new HashMap<>();
        List<Score> scoreList = iplScoreCard.getMatch().getScore();
        if (scoreList != null) {
            for (Score score : scoreList) {
                String inning = score.getInning().replace("Inning 1", "").trim();
                scoreMap.put(inning, score);
            }
        }
        iplScoreCardApiDto.setScoreList(scoreMap);

        Map<String, ScoreCard> scoreCardMap = new HashMap<>();
        List<ScoreCard> scoreCardList = iplScoreCard.getMatch().getScorecard();
        if (scoreCardList != null) {
            for (ScoreCard scoreCard : scoreCardList) {
                String inning = scoreCard.getInning().replace("Inning 1", "").trim();
                scoreCardMap.put(inning, scoreCard);
            }
        }
        iplScoreCardApiDto.setScoreCardList(scoreCardMap);

        // Save the updated entity back to the database
        IplScoreCardApi savedEntity = iplScoreCardApiRepository.save(IplScoreCardApiMapper.toEntity(iplScoreCardApiDto));
        if(savedEntity.getIsMatchEnded()){
            String team1=savedEntity.getTeamInfo().get(0).getShortname();
            String team2=savedEntity.getTeamInfo().get(1).getShortname();
            pointsTableService.createEntity(team1);
            pointsTableService.createEntity(team2);
        }
        return IplScoreCardApiMapper.toDTO(savedEntity);
    }

    @Override
    public IplScoreCardApiDto getEntityById(String id) throws Exception {
        Optional<IplScoreCardApiDto> iplScoreCardApiDtoOptional=iplScoreCardApiRepository.findByMatchId(id);
        if(iplScoreCardApiDtoOptional.isEmpty()){
            throw new Exception("ScoreCard not found with the given id");
        }
       return changeEntityAsPerRequirement(iplScoreCardApiDtoOptional.get());
    }

    @Override
    public Page<IplScoreCardApiDto> getAllEntities(Pageable pageable) throws Exception {
        Page<IplScoreCardApi> entityPage= iplScoreCardApiRepository.findAll(pageable);
        if (entityPage.getContent().size() > 0) {
            return new PageImpl<>(entityPage.getContent().stream().map(IplScoreCardApiMapper::toDTO).collect(Collectors.toList()), pageable, entityPage.getTotalElements());
        } else {
            return new PageImpl<>(new ArrayList<>(), pageable, entityPage.getTotalElements());
        }
    }

    @Override
    public long getEntitiesCountByTeamName(String id) {
        return iplScoreCardApiRepository.countByTeamInfoShortname(id);
    }
    int count=0;
    List<IplAllMatchesApiDto> iplAllMatchesApiDtoList=null;
    IplAllMatchesApiDto currentMatch=null;
    @Scheduled(fixedRate = 6*60 * 1000) // 6 min in milliseconds
    public void scheduledMethod() throws Exception {
        try {
            LocalDate date=LocalDate.now();
            String today = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Initialize match data if not already initialized
            if (count == 0) {
                initializeMatchData(today);
                count++;
            }
            if(!iplAllMatchesApiDtoList.get(0).getDate().equals(today)){
                count=0;
            }

            // Check if there are any active matches scheduled for today
            for (IplAllMatchesApiDto match : iplAllMatchesApiDtoList) {
                String matchDate = match.getDate();
                if (matchDate.equals(today) && match.getIsActive() && !match.getStatus().equals("Match not started")) {
                    // If match is active, check if it's live
                    if (match.getIsActive() && !match.getStatus().contains("won") && !match.getStatus().contains("lost") && !match.getStatus().contains("tied")) {
                        // Call IplScoreCard API for the live match
                        IplScoreCardApiDto iplScoreCardApiDto = createEntity(match.getTime());
                    }
                }
//                else {
//                    // Parse the target time string in GMT format (assuming ISO 8601)
//                    LocalDateTime targetDateTimeGMT = LocalDateTime.parse(match.getTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//
//                    // Convert target time to Indian Standard Time (IST)
//                    ZoneId indianTimeZone = ZoneId.of("Asia/Kolkata");
//                    ZonedDateTime targetDateTimeIST = targetDateTimeGMT.atZone(ZoneOffset.UTC).withZoneSameInstant(indianTimeZone);
//
//                    // Get current time in IST
//                    LocalDateTime nowWithIST = LocalDateTime.now(indianTimeZone);
//
//                    // Define start and end times for the range (7:30 PM to 7:33 PM IST)
//                    LocalTime startTime = LocalTime.of(23, 20);
//                    LocalTime endTime = LocalTime.of(23, 45);
//                    LocalTime startTimeAfternoon = LocalTime.of(19, 0);
//                    LocalTime endTimeAfternoon = LocalTime.of(19, 30);
//
//                    // Check if the current time is within the specified range
//                    if ((nowWithIST.toLocalTime().isAfter(startTime) && nowWithIST.toLocalTime().isBefore(endTime))||(nowWithIST.toLocalTime().isAfter(startTimeAfternoon) && nowWithIST.toLocalTime().isBefore(endTimeAfternoon))) {
//                        // Call your method here
//                        createEntity(match.getTime());
//                    }
//                }
                if(!match.getIsActive()){
                    createEntity(match.getTime());
                    initializeMatchData(today);
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

    public IplScoreCardApiDto changeEntityAsPerRequirement(IplScoreCardApiDto iplScoreCardApiDto){
        String team1=iplScoreCardApiDto.getTeamInfo().get(0).getName();
        String team2=iplScoreCardApiDto.getTeamInfo().get(1).getName();

        List<Score> scoreList=new ArrayList<>();
        List<ScoreCard> scoreCardList=new ArrayList<>();


            scoreList.add(iplScoreCardApiDto.getScoreList().get(team1));
            scoreList.add(iplScoreCardApiDto.getScoreList().get(team2));

            scoreCardList.add(iplScoreCardApiDto.getScoreCardList().get(team1));
            scoreCardList.add(iplScoreCardApiDto.getScoreCardList().get(team2));


        iplScoreCardApiDto.setTempScoreList(scoreList);
        iplScoreCardApiDto.setTempScoreCardList(scoreCardList);
        return iplScoreCardApiDto;
    }

}
