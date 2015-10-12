io.codearte.accurest.dsl.GroovyDsl.make {
    request {
        method 'GET'
        url '/api/results'
        headers {
            header 'Content-Type': 'application/vnd.cz.mikealdo.fotbal-cz-api.v1+json'
        }
        body '''\
    [{     
          "seasonType": "AUTUMN_SPRING", 
          "sportType" : "FOOTBALL",
          "competitionHash": "172c09d6-dd87-47df-a0b3-8efde6ac6842",
          "clubTeamName": "A mužstvo"
    }]
'''
    }
    response {
        status 200
    }
}