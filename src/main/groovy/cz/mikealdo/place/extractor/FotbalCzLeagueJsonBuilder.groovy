package cz.mikealdo.place.extractor

import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import groovy.text.SimpleTemplateEngine
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.stereotype.Component

@TypeChecked
@PackageScope
@Component
class FotbalCzLeagueJsonBuilder {

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    String buildLeagueJson(String competitionHash, FotbalCzLeague league) {
       return new SimpleTemplateEngine().createTemplate(JSON_LEAGUE_WITH_KEY_TEMPLATE)
                            .make([competitionHash: competitionHash,
                                   league : league,
                                    formatter : formatter])
                            .toString()
    }


    private static final String JSON_LEAGUE_WITH_KEY_TEMPLATE = '''
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
                                ,"matches": [
                                    <% league.matches.eachWithIndex { match, index -> %>
                                        {
                                            "homeTeam": "${match.homeTeam.teamNameToDisplay}",
                                            "visitorTeam": "${match.visitorTeam.teamNameToDisplay}",
                                            "result": "${match.result.prettyPrintSimpleResult()}",
                                            "round": ${match.round},
                                            "date": "${match.date.toString(formatter)}"
                                        }
                                        <% if (index < league.matches.size-1) {%>,<%}%>
                                    <% } %>
                                ]
                            <% } %>
                        }
                }
                '''

}
