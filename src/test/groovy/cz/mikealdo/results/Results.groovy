package cz.mikealdo.results

class Results {

    public static String FULL_RESULTS = '''
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
                                },
                                {
                                    "homeTeam": "second",
                                    "visitorTeam": "first",
                                    "round": 2,
                                    "date": "2015-05-15"
                                }
                            ]
                        }
                }
                '''

    public static String ROUND_RESULTS = '''
                {
                    "matches":[
                        {
                            "homeTeam": "first",
                            "visitorTeam": "second",
                            "result": "2:1p",
                            "round": 1,
                            "date": "2015-05-15"
                        },
                        {
                            "homeTeam": "second",
                            "visitorTeam": "first",
                            "round": 2,
                            "date": "2015-05-15"
                        }
                    ]
                }
'''

}
