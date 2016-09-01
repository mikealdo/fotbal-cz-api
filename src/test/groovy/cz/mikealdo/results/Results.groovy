package cz.mikealdo.results

class Results {

    public static String FULL_RESULTS = '''
                {
                    "competitionHash" : "hash",
                    "competitionName": "I.B trida",
                    "competitionDescription": "description",
                    "teams": [
                            {
                                "pairingId": 1,
                                "pairingTeamName": "first"
                            },

                            {
                                "pairingId": 2,
                                "pairingTeamName": "second"
                            }
                        ],
                    "matches":[
                        {
                            "homeTeam": {
                                "pairingId": "1",
                                "pairingTeamName": "first"
                            },
                            "visitorTeam": {
                                "pairingId": "2",
                                "pairingTeamName": "second"
                            },
                            "result": "3:1p",
                            "round": 1,
                            "date": "2015-05-15 00:00"
                        }
                        ,
                        {
                            "homeTeam": {
                                "pairingId": "2",
                                "pairingTeamName": "second"
                            },
                            "visitorTeam": {
                                "pairingId": "1",
                                "pairingTeamName": "first"
                            },
                            "round": 2,
                            "date": "2015-05-15 00:00"
                        }
                    ]
                }
                '''

    public static String ROUND_RESULTS = '''
                {
                    "matches":[
                        {
                            "homeTeam": {
                                "pairingId": "1",
                                "pairingTeamName": "first"
                            },
                            "visitorTeam": {
                                "pairingId": "2",
                                "pairingTeamName": "second"
                            },
                            "result": "3:1p",
                            "round": 1,
                            "date": "2015-05-15 00:00"
                        }
                        ,
                        {
                            "homeTeam": {
                                "pairingId": "2",
                                "pairingTeamName": "second"
                            },
                            "visitorTeam": {
                                "pairingId": "1",
                                "pairingTeamName": "first"
                            },
                            "round": 2,
                            "date": "2015-05-15 00:00"
                        }
                    ]
                }
'''

}
