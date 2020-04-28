package com.example.indecisiveeater;

public class Constants {
    public static final String YELP_TOKEN = "Bearer UHyMGb7ABKFSn84xd3eS7a3wsLOS-z1jpElki2Pt0-6oLtXD-rqyyKqBijE0YweioMR_aezwoNVyv3ZmSiAIb6a6LSM0HkPZlZUE40j_hRwVwrMaKAy1_b5ocQaVXnYx";
    public static final String YELP_BASE_URL = "https://api.yelp.com/v3/businesses/search?term=restaurants";

    //required
      public static final String YELP_LOCATION_QUERY_PARAMETER = "location";
//    public static final String YELP_LAT_QUERY_PARAMETER = "latitude";
//    public static final String YELP_LONG_QUERY_PARAMETER = "longitude";
      public static final String YELP_LIMIT_QUERY_PARAMETER = "limit";
      public static final String YELP_OFFSET_QUERY_PARAMETER = "offset";

    //optional
    public static final String YELP_RADIUS_QUERY_PARAMETER = "radius";
    public static final String YELP_PRICE_QUERY_PARAMETER = "price";
    public static final String YELP_OPEN_QUERY_PARAMETER = "open_now";
    public static final String YELP_CATEGORIES_QUERY_PARAMETER = "categories";

}
