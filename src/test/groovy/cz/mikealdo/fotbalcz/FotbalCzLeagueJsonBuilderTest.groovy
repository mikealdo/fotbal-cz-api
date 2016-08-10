package cz.mikealdo.fotbalcz

import cz.mikealdo.detailedleague.LeagueWithDetails
import cz.mikealdo.football.domain.*
import cz.mikealdo.fotbalcz.api.CompetitionSettings
import cz.mikealdo.fotbalcz.api.FotbalCzLeagueJsonBuilder
import cz.mikealdo.pages.CompetitionPageStub
import cz.mikealdo.results.Results
import org.joda.time.DateTime
import spock.lang.Specification

import static com.ofg.infrastructure.base.dsl.Matchers.equalsReferenceJson
import static org.junit.Assert.assertThat

class FotbalCzLeagueJsonBuilderTest extends Specification {

    def builder = new FotbalCzLeagueJsonBuilder()

    def "should produce valid json result with full details"() {
        given:
            League league = createLeague()
        when:
            String json = builder.buildLeagueJson("hash", league)
        then:
            assertThat(json, equalsReferenceJson(Results.FULL_RESULTS))

    }

    def "should produce only one round results"() {
        given:
            League league = createLeague()
            CompetitionSettings settings = new CompetitionSettings(1);
        when:
            String json = builder.buildLeagueJson("hash", league, settings)
        then:
            assertThat(json, equalsReferenceJson(Results.ROUND_RESULTS))
    }

    def "should produce valid json result with real html page"() {
        given:
            CompetitionPageStub competitionPageStub = new CompetitionPageStub();
            CompetitionDetails competitionDetails = competitionPageStub.createCompetitionDetailsFrom("hash");

            League league = new LeagueWithDetails(new League("Name")).enhanceByDetails(competitionDetails);
        when:
            String json = builder.buildLeagueJson("172c09d6-dd87-47df-a0b3-8efde6ac6842", league);
        then:
            assertThat(json, equalsReferenceJson(this.getClass().getResource("/json/complete-results.json").text))
    }

    private League createLeague() {
        League league = new League("I.B trida")
        league.setDescription("description")
        List<PairedTeam> teams = new LinkedList<>()
        def firstTeam = new PairedTeam(1, "first")
        teams.add(firstTeam)
        def secondTeam = new PairedTeam(2, "second")
        teams.add(secondTeam)
        league.setTeams(teams)
        List<Match> matches = new LinkedList<>()
        matches.add(new Match(DateTime.parse("2015-05-15"), firstTeam, secondTeam, new MatchResult("2:1", "(PK:5:4)"), 1))
        matches.add(new Match(DateTime.parse("2015-05-15"), secondTeam, firstTeam, new MatchResult("not parseable"), 2)) // TODO constructor for match without result
        league.setMatches(matches)
        return league
    }
}
