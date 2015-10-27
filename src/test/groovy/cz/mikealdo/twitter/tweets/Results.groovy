package cz.mikealdo.twitter.tweets

class Results {

    public static String FULL_RESULT = '''
                {
                    "competition_hash" : "hash",
                    "competition" :
                        {
                            "name": "I.B trida",
                            "description": "description",
                            "teams": [
                                    {
                                        "pairId": 1,
                                        "name": "first"                                                    
                                    },

                                    {
                                        "pairId": 2,
                                        "name": "second"
                                    }
                                ],
                            "matches":[
                                {
                                    "homeTeam": "first",
                                    "visitorTeam": "second",
                                    "result": "2:1p",
                                    "round": 1,
                                    "date": "2015-05-15"
                                }
                            ]
                        }
                }
                '''

    public static String TWEET_WITHOUT_A_PLACE = '''
    {
        "created_at": "Sat Jul 26 09:38:57 +0000 2014",
        "id": 492967299297845248,
        "id_str": "492967299297845248",
        "text": "Gonna see you at Warsaw",
        "place": null
    }
'''

    public static String TWEET_WITH_COORDINATES = '''
    {
        "created_at": "Sat Jul 26 09:15:10 +0000 2014",
        "id": 492961315070439424,
        "id_str": "492961315070439424",
        "geo": null,
        "coordinates":
        {
            "coordinates":
                [
                    -75.14310264,
                    40.05701649
                ],
            "type":"Point"
        }
    }
'''

    public static String TWEET_WITHOUT_COORDINATES = '''
    {
        "created_at": "Sat Jul 26 09:15:10 +0000 2014",
        "id": 492961315070439424,
        "id_str": "492961315070439424",
        "geo": null
    }
'''

}
