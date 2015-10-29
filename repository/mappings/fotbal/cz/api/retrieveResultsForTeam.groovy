import io.codearte.accurest.dsl.GroovyDsl

GroovyDsl.make {
    request {
        method 'GET'
        url '/api/172c09d6-dd87-47df-a0b3-8efde6ac6842'
        headers {
            header 'Content-Type': 'application/vnd.cz.mikealdo.fotbal-cz-api.v1+json'
        }
        body '''\
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
    }
    response {
        status 200
    }
}