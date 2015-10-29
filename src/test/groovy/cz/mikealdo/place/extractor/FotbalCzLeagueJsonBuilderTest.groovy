package cz.mikealdo.place.extractor

import cz.mikealdo.football.domain.MatchResult
import cz.mikealdo.fotbalcz.api.CompetitionSettings
import cz.mikealdo.fotbalcz.api.FotbalCzLeagueJsonBuilder
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import cz.mikealdo.fotbalcz.domain.FotbalCzMatch
import cz.mikealdo.fotbalcz.domain.FotbalCzTeam
import cz.mikealdo.twitter.tweets.Results
import org.joda.time.DateTime
import spock.lang.Specification
import static com.ofg.infrastructure.base.dsl.Matchers.equalsReferenceJson

import static org.junit.Assert.assertThat

class FotbalCzLeagueJsonBuilderTest extends Specification {

    def builder = new FotbalCzLeagueJsonBuilder()

    def "should produce valid json result with full details"() {
        given:
            FotbalCzLeague league = createLeague()
        when:
            String json = builder.buildLeagueJson("hash", league)
        then:
            assertThat(json, equalsReferenceJson(Results.FULL_RESULTS))

    }

    def "should produce only one round results"() {
        given:
            FotbalCzLeague league = createLeague()
            CompetitionSettings settings = new CompetitionSettings(1);
        when:
            String json = builder.buildLeagueJson("hash", league, settings)
        then:
            assertThat(json, equalsReferenceJson(Results.ROUND_RESULTS))
    }

    private FotbalCzLeague createLeague() {
        FotbalCzLeague league = new FotbalCzLeague("I.B trida")
        league.setDescription("description")
        List<FotbalCzTeam> teams = new LinkedList<>()
        def firstTeam = new FotbalCzTeam(1, "first")
        teams.add(firstTeam)
        def secondTeam = new FotbalCzTeam(2, "second")
        teams.add(secondTeam)
        league.setTeams(teams)
        List<FotbalCzMatch> matches = new LinkedList<>()
        matches.add(new FotbalCzMatch(DateTime.parse("2015-05-15"), firstTeam, secondTeam, new MatchResult("2:1", "(PK:5:4)"), 1))
        league.setMatches(matches)
        return league
    }
}
