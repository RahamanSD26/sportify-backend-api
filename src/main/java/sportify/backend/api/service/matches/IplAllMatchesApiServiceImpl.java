package sportify.backend.api.service.matches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sportify.backend.api.config.Constants;
import sportify.backend.api.domain.matches.IplAllMatchesApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
import sportify.backend.api.mapper.matches.IplAllMatchesApiMapper;
import sportify.backend.api.repository.matches.IplAllMatchesApiRepository;
import sportify.backend.api.service.CricketDataService;
import util.JavaApiClass.CommonUtil;
import util.JavaApiClass.iplAllMatches.IplAllMatches;
import util.JavaApiClass.iplAllMatches.Match;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class IplAllMatchesApiServiceImpl implements IplAllMatchesApiService{
    @Autowired
    CricketDataService cricketDataService;
    @Autowired
    IplAllMatchesApiRepository iplAllMatchesApiRepository;
    @Override
    public List<IplAllMatchesApi> createEntity() throws Exception {
        IplAllMatches iplAllMatches = cricketDataService.fetchDataFromApi(IplAllMatches.class,Constants.IPL_ALL_MATCHES_ID, Constants.IPL_SERIES_INFO);
        List<Match> matchList = iplAllMatches.getData().getMatchList();

                    for (Match match : matchList) {
                        Optional<IplAllMatchesApi> existingMatchOpt = iplAllMatchesApiRepository.findByMatchId(match.getId());
                        if (!existingMatchOpt.isPresent()) {
                            // Match does not exist, create a new entity
                            IplAllMatchesApiDto iplAllMatchesApiDto = new IplAllMatchesApiDto();
                            iplAllMatchesApiDto.setVenue(match.getVenue());
                            iplAllMatchesApiDto.setDate(match.getDate());
                            iplAllMatchesApiDto.setTime(match.getDateTimeGMT());
                            iplAllMatchesApiDto.setTeamsName(match.getName().split(",")[0].trim());
                            iplAllMatchesApiDto.setTeamInfo(match.getTeamInfo());
                            iplAllMatchesApiDto.setMatchId(match.getId());
                            iplAllMatchesApiDto.setStatus(match.getStatus());
                            iplAllMatchesApiDto.setMatchNumber(match.getName().split(",")[1].trim());
                            iplAllMatchesApiDto.setIsActive(iplAllMatchesApiDto.getStatus().equals("Match not started") ||
                                    iplAllMatchesApiDto.getStatus().contains("bowl") ||
                                    iplAllMatchesApiDto.getStatus().contains("bat") ||
                                    iplAllMatchesApiDto.getStatus().contains("need")||
                                    iplAllMatchesApiDto.getStatus().contains("in progress"));

                            String matchNumber=iplAllMatchesApiDto.getMatchNumber();
                            if(matchNumber.equals("Final") ||matchNumber.equals("Qualifier 1") || matchNumber.equals("Eliminator")|| matchNumber.equals("Qualifier 2")){
                                iplAllMatchesApiDto.setIntMatchNumber(74);
                            }else {
                                iplAllMatchesApiDto.setGuid(matchNumber.replaceAll("[^0-9]", ""));
                                iplAllMatchesApiDto.setIntMatchNumber(Integer.valueOf(iplAllMatchesApiDto.getGuid()));
                            }
                             iplAllMatchesApiRepository.save(IplAllMatchesApiMapper.toEntity(iplAllMatchesApiDto));
                        } else {
                            if(!match.getStatus().equals(existingMatchOpt.get().getStatus())){
                                IplAllMatchesApiDto iplAllMatchesApiDto=IplAllMatchesApiMapper.toDTO(existingMatchOpt.get());
                                iplAllMatchesApiDto.setStatus(match.getStatus());
                                iplAllMatchesApiRepository.save(IplAllMatchesApiMapper.toEntity(iplAllMatchesApiDto));
                            }
                            continue;
                        }
                    }
                    return iplAllMatchesApiRepository.findAll();
            }


    @Override
    public List<IplAllMatchesApiDto> getAllEntities() throws Exception {
           List<IplAllMatchesApiDto> iplAllMatchesApiListDto=iplAllMatchesApiRepository.findAllByOrderByIntMatchNumberAsc();
           if (iplAllMatchesApiListDto.isEmpty()){
               return Collections.emptyList();
           }
           return iplAllMatchesApiListDto;
    }

    @Override
    public List<IplAllMatchesApiDto> getMatchListByDate(String date)throws Exception {
        List<IplAllMatchesApiDto> list  = iplAllMatchesApiRepository.findByDate(date);
        if(list!=null){
            return list;
        }
        throw new Exception("Ipl List by date Not Found");
    }

    @Override
    public IplAllMatchesApiDto getMatchByTime(String dateTime) throws Exception {
        Optional<IplAllMatchesApi> optionalMatch=iplAllMatchesApiRepository.findByTime(dateTime);
        if(optionalMatch.isEmpty()){
            throw new Exception("Ipl List by dateTime Not Found");
        }
        return IplAllMatchesApiMapper.toDTO(optionalMatch.get());
    }

    @Override
    public IplAllMatchesApiDto getMatchById(String id) throws Exception {
        Optional<IplAllMatchesApi> iplAllMatchesApiDtoOptional=iplAllMatchesApiRepository.findByMatchId(id);
        if(iplAllMatchesApiDtoOptional.isEmpty()){
            throw new Exception("Ipl Match not found by Id");
        }
        return IplAllMatchesApiMapper.toDTO(iplAllMatchesApiDtoOptional.get());
    }

    @Override
    public List<IplAllMatchesApi> getEntitiesByStatus(Boolean isActive) throws Exception {
              List<IplAllMatchesApi> iplAllMatchesApiList=iplAllMatchesApiRepository.findByIsActiveOrderByIntMatchNumberAsc(isActive);
              if(iplAllMatchesApiList.isEmpty()){
                  return Collections.emptyList();
              }
              return iplAllMatchesApiList;
    }

    @Override
    public List<IplAllMatchesApi> getEntitiesByVenue(String venue) throws Exception {
        List<IplAllMatchesApi> iplAllMatchesApiList=iplAllMatchesApiRepository.findByVenueOrderByIntMatchNumberAsc(venue);
        if(iplAllMatchesApiList.isEmpty()){
            return Collections.emptyList();
        }
        return iplAllMatchesApiList;
    }

    @Override
    public List<IplAllMatchesApi> getEntitiesByTeamName(String shortName) {
        List<IplAllMatchesApi> iplAllMatchesApiList=iplAllMatchesApiRepository.findByTeamInfoShortnameOrderByIntMatchNumberAsc(shortName);
        if(iplAllMatchesApiList.isEmpty()){
            return Collections.emptyList();
        }
        return iplAllMatchesApiList;
    }

    @Override
    public List<IplAllMatchesApi> getEntitiesByTeamNameAndVenue(String shortName, String venue) {
          List<IplAllMatchesApi> iplAllMatchesApiList=iplAllMatchesApiRepository.findByTeamInfoShortnameAndVenueOrderByIntMatchNumberAsc(shortName,venue);
          if(iplAllMatchesApiList.isEmpty()){
              return Collections.emptyList();
          }
          return iplAllMatchesApiList;
    }

    @Override
    public List<IplAllMatchesApi> getEntitiesByTeamNameAndVenueAndStatus(Boolean status,String shortName, String venue) {
        List<IplAllMatchesApi>  iplAllMatchesApiList  =iplAllMatchesApiRepository.findByIsActiveAndTeamInfoShortnameAndVenueOrderByIntMatchNumberAsc(status,shortName,venue);
        if(iplAllMatchesApiList.isEmpty()){
            return Collections.emptyList();
        }
        return iplAllMatchesApiList;
    }

    private String getNewGenratedDepartmentId() throws Exception{
        Optional<IplAllMatchesApi> lastMatch = iplAllMatchesApiRepository.findTopByOrderByCreatedOnDesc();
        Long serialNumber = Constants.NUMBER_CONSTANT_LONG_ONE;
        if (lastMatch.isPresent()) {
            Long lastSerialNumber = Long.valueOf(lastMatch.get().getGuid().replace(Constants.ID_PREFIX_IPL_ALLMATCHES, Constants.BLANK_CONSTANT));
            serialNumber = lastSerialNumber + 1;
            if (serialNumber > 999) {
                throw new Exception("ERR_ADMIN_0074");
            }
        }
        return CommonUtil.getCustomGeneratedId(Constants.ID_PREFIX_IPL_ALLMATCHES, Constants.ID_PART_NUMBER_COUNT_IPL_ALLMATCHES, serialNumber);
    }
}
