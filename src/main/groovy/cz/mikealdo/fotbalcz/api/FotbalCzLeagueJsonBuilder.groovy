package cz.mikealdo.fotbalcz.api

import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import groovy.text.SimpleTemplateEngine
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.stereotype.Component

@TypeChecked
@Component
public class FotbalCzLeagueJsonBuilder {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    String buildLeagueJson(String competitionHash, FotbalCzLeague league, CompetitionSettings settings) {
        def templateToUse = JSON_LEAGUE_FULL_TEMPLATE
        if (settings.round.isPresent()) {
            templateToUse = "{"+JSON_LEAGUE_MATCHES_FOR_ROUND_TEMPLATE+"}"
        }
        return new SimpleTemplateEngine().createTemplate(templateToUse)
                .make([competitionHash: competitionHash,
                       league : league,
                       dateTimeFormatter : dateTimeFormatter,
                       settings: settings])
                .toString()
    }

    String buildLeagueJson(String competitionHash, FotbalCzLeague league) {
       return buildLeagueJson(competitionHash, league, new CompetitionSettings())
    }
    private static final String JSON_LEAGUE_MATCHES_FOR_ROUND_TEMPLATE = '''
                            "matches": [
                                    <% league.matches.eachWithIndex { match, index -> %>
                                        {
                                            "homeTeam": "${match.homeTeam.teamNameToDisplay}",
                                            "visitorTeam": "${match.visitorTeam.teamNameToDisplay}",
                                            "result": "${match.result.prettyPrintSimpleResult()}",
                                            "round": ${match.round},
                                            "date": "${match.date.toString(dateTimeFormatter)}"
                                        }
                                        <% if (index < league.matches.size-1) {%>,<%}%>
                                    <% } %>
                                ]
                    '''


    private static final String JSON_LEAGUE_FULL_TEMPLATE = '''
                {
                    "competition_hash" : "${competitionHash}",
                    "competition" :
                        {
                            "name": "${league.name}",
                            "description": "${league.description}"
                            <% if (league.teams != null) { %>
                                ,"teams": [
                                    <% league.teams.eachWithIndex { team, index -> %>
                                        {
                                            "pairId": ${team.pairingId},
                                            "name": "${team.teamNameToDisplay}"
                                        }
                                        <% if (index < league.teams.size-1) {%>,<%}%>
                                    <% } %>
                                ]
                            <% } %>
                            <% if (league.matches != null) { %>
                                ,'''+JSON_LEAGUE_MATCHES_FOR_ROUND_TEMPLATE+'''
                            <% } %>
                        }
                }
                '''

}
