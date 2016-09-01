package cz.mikealdo.fotbalcz.api

import cz.mikealdo.football.domain.League
import groovy.text.SimpleTemplateEngine
import groovy.transform.TypeChecked
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.stereotype.Component

@TypeChecked
@Component
public class FotbalCzLeagueJsonBuilder {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    String buildLeagueJson(String competitionHash, League league, CompetitionSettings settings) {
        def templateToUse = JSON_LEAGUE_FULL_TEMPLATE
        if (settings.round.isPresent()) {
            templateToUse = "{" + JSON_LEAGUE_MATCHES_FOR_ROUND_TEMPLATE + "}"
        }
        return new SimpleTemplateEngine().createTemplate(templateToUse)
                .make([competitionHash  : competitionHash,
                       league           : league,
                       dateTimeFormatter: dateTimeFormatter,
                       settings         : settings,
                       matchesTemplate  : JSON_LEAGUE_MATCHES_FOR_ROUND_TEMPLATE])
                .toString()
    }

    String buildLeagueJson(String competitionHash, League league) {
        return buildLeagueJson(competitionHash, league, new CompetitionSettings())
    }

    private static final String JSON_LEAGUE_MATCHES_FOR_ROUND_TEMPLATE = '''
                            "matches": [
                                    <% league.matches.eachWithIndex { match, index -> %>
                                        {
                                            "homeTeam": {
                                                "pairingId": "${match.homeTeam.pairingId}",
                                                "pairingTeamName": "${match.homeTeam.pairingTeamName}"
                                            },
                                            "visitorTeam": {
                                                "pairingId": "${match.visitorTeam.pairingId}",
                                                "pairingTeamName": "${match.visitorTeam.pairingTeamName}"
                                            },
                                            <% if (match.result.isPresent() && match.result.get().isResultEntered()) { %> "result": "${match.result.get().prettyPrintSimpleResult()}", <% } %>
                                            "round": ${match.round},
                                            "date": "${match.date.toString(dateTimeFormatter)}"
                                        }
                                        <% if (index < league.matches.size-1) {%>,<%}%>
                                    <% } %>
                                ]
                    '''


    private static final String JSON_LEAGUE_FULL_TEMPLATE = '''
                {
                    "competitionHash" : "${competitionHash}",
                    "competitionName": "${league.name}",
                    "competitionDescription": "${league.description}"
                    <% if (league.teams != null) { %>
                        ,"teams": [
                            <% league.teams.eachWithIndex { team, index -> %>
                                {
                                    "pairingId": ${team.pairingId},
                                    "pairingTeamName": "${team.pairingTeamName}"
                                }
                                <% if (index < league.teams.size - 1) {%>,<%}%>
                            <% } %>
                        ]
                    <% } %>
                    <% if (league.matches != null) { %>
                        ,''' + JSON_LEAGUE_MATCHES_FOR_ROUND_TEMPLATE + '''
                    <% } %>
                }
                '''

}
