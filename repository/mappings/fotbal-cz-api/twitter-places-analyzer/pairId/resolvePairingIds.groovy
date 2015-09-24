io.codearte.accurest.dsl.GroovyDsl.make {
    request {
        method 'PUT'
        url '/api/172c09d6-dd87-47df-a0b3-8efde6ac6842'
        headers {
            header 'Content-Type': 'application/vnd.cz.mikealdo.fotbal-cz-api.v1+json'
        }
        body '''\
    [{
        {
          "seasonType": "AUTUMN_SPRING", 
          "sportType" : "FOOTBALL",
          "pairingItems": [
             {
               "competitionId": 1,
               "competitionHash": "172c09d6-dd87-47df-a0b3-8efde6ac6842",
               "competitionName": "Fair Credit I.B třída skupina E",
               "competitionDescription": "A mužstvo bylo letos přeřazeno do \\"příbramské\\" skupiny E, kde chce hrát důstojnou roli minimálně ve středu tabulky. Pokud bude mužstvo kompletní, mělo by zaútočit na přední příčky celé soutěže.",
               "clubTeamName": "A mužstvo",
               "nameToDisplay": "Sedlec-Prčice A",
               "pairingTeamName": "SOKOL Sedlec-Prčice",
               "firstId": 100,
               "arrivals": [
                 {"round": 1,"time": "15:45"},
                 {"round": 3,"time": "15:00"},
                 {"round": 5,"time": "14:15"},
                 {"round": 8,"time": "13:30"},
                 {"round": 10,"time": "12:00"},
                 {"round": 12,"time": "07:45"}
               ]
            },
            {
              "competitionId": 2,
              "competitionHash": "c6405552-6dbc-4ec3-8029-d945290f740b",
              "competitionName": "III. třída skupina A",
              "competitionDescription": "",
              "clubTeamName": "B mužstvo",
              "nameToDisplay": "Sedlec-Prčice B",
              "pairingTeamName": "Sedlec-Prčice B",
              "firstId": 200,
              "arrivals": [
                {"round": 2,"time": "15:15"},
                {"round": 4,"time": "15:00"},
                {"round": 7,"time": "14:00"},
                {"round": 9,"time": "12:00"},
                {"round": 11,"time": "12:00"},
                {"round": 13,"time": "11:30"}
              ]
            },
            {
              "competitionId": 3,
              "competitionHash": "58930d9d-133c-4400-9f9d-d5591c3b61d6",
              "competitionName": "Okresní přebor starších žáků",
              "competitionDescription": "",
              "clubTeamName": "Starší žáci",
              "nameToDisplay": "Sedlec-Prčice",
              "pairingTeamName": "Tělovýchovná jednota SOKOL Sedlec-Prčice",
              "firstId": 300,
              "arrivals": [
                {"round": 2,"time": "12:45"},
                {"round": 6,"time": "12:30"},
                {"round": 8,"time": "12:30"},
                {"round": 10,"time": "10:15"},
                {"round": 12,"time": "11:00"}
              ]
            },
            {
              "competitionId": 4,
              "competitionHash": "2488319e-b122-4548-8a6a-c6d90195f284",
              "competitionName": "Okresní přebor mladší žáci 7+1",
              "competitionDescription": "",
              "clubTeamName": "Mladší žáci",
              "nameToDisplay": "Sedlec-Prčice",
              "pairingTeamName": "Sedlec-Prčice",
              "firstId": 400,
              "arrivals": [
                {"round": 2,"time": "08:15"},
                {"round": 4,"time": "08:15"},
                {"round": 7,"time": "08:15"},
                {"round": 13,"time": "14:00"},
                {"round": 9,"time": "08:00"},
                {"round": 11,"time": "08:15"}
              ]
            },
            {
              "competitionId": 5,
              "competitionHash": "7c99976d-eb92-437a-a117-aafb441e1adf",
              "competitionName": "Okresní přebor starší přípravka 5+1 Ferdináda",
              "competitionDescription": "",
              "clubTeamName": "Starší přípravka",
              "nameToDisplay": "Sedlec-Prčice A",
              "pairingTeamName": "Sedlec-Prčice A",
              "firstId": 500,
              "arrivals": [
                {"round": 1,"time": "15:45"},
                {"round": 3,"time": "08:00"},
                {"round": 5,"time": "08:15"},
                {"round": 7,"time": "08:00"},
                {"round": 10,"time": "14:45"}
              ]
            },
            {
              "competitionId": 6,
              "competitionHash": "f90ce456-b9fa-401c-a5b2-c8e811e97a7c",
              "competitionName": "Okresní soutěž starší přípravka 5+1 skupina A",
              "competitionDescription": "",
              "clubTeamName": "Starší přípravka B",
              "nameToDisplay": "Sedlec-Prčice B",
              "pairingTeamName": "Sedlec-Prčice B",
              "firstId": 600,
              "arrivals": [
                {"round": 1,"time": "15:15"},
                {"round": 3,"time": "07:45"},
                {"round": 5,"time": "08:30"},
                {"round": 7,"time": "09:00"},
                {"round": 10,"time": "08:15"}
              ]
            },
            {
              "competitionId": 7,
              "competitionHash": "0b4089bc-4186-4981-8780-b486ebfaa468",
              "competitionName": "I.A třída dorostu skupina D",
              "competitionDescription": "",
              "clubTeamName": "Dorost",
              "nameToDisplay": "Sedlec-Prčice",
              "pairingTeamName": "SOKOL Sedlec-Prčice",
              "firstId": 700,
              "arrivals": [
                {"round": 2,"time": "15:00"},
                {"round": 4,"time": "08:15"},
                {"round": 8,"time": "09:00"},
                {"round": 11,"time": "08:00"},
                {"round": 15,"time": "08:15"}
              ]
            }
          ]
        }
    }]
'''
    }
    response {
        status 200
    }
}