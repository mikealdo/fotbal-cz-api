package cz.mikealdo.place.extractor

import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import groovy.text.SimpleTemplateEngine
import groovy.transform.PackageScope
import groovy.transform.TypeChecked

@TypeChecked
@PackageScope
class FotbalCzLeagueJsonBuilder {

    String buildLeagueJson(String competitionHash, FotbalCzLeague league) {
        return """[
                       ${

                            return new SimpleTemplateEngine().createTemplate(JSON_LEAGUE_WITH_KEY_TEMPLATE)
                            .make([competitionHash: competitionHash,
                                   league : league])
                            .toString()
                        }.join(',')}
                   ]""".toString()
    }


    private static final String JSON_LEAGUE_WITH_KEY_TEMPLATE = '''
                {
                    "competition_hash" : $competitionHash,
                    "league" :
                        {
                            "name":"${league.name}",
                            "description": "${league.description}"
                            <% if (league.teams != null) %>
                                ,"teams": [
                                    <% for (FotbalCzTeam team : league.teams) { %>
                                        {

                                        }
                                    <% } %>
                                ]
                            <% } %>
                        }
                }
                '''

}
