package sportify.backend.api.config;

public class RestEndPoints {
    public static final String VERSION_1="/v1" ;
    public static final String IPL_PATH="/ipl" ;
    public static final String IPL_API_V1=IPL_PATH+VERSION_1;
    public static final String IPL_ALL_MATCHES=IPL_API_V1+"/all_matches";
    public static final String IPL_SCORE_CARD=IPL_API_V1+"/scorecard";
    public static final String IPL_POINTS_TABLE=IPL_API_V1+"/pointstable";
    public static final String IPL_TEAM_SQUAD=IPL_API_V1+"/squad";
}
