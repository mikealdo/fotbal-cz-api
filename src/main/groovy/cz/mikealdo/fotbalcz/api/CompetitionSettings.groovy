package cz.mikealdo.fotbalcz.api

class CompetitionSettings {
    boolean includeMatchDetails
    boolean includeTeams
    Optional<String> teamName
    Optional<Integer> round

    CompetitionSettings() {
        this.includeMatchDetails = true
        this.includeTeams = true
        this.teamName = Optional.empty()
        this.round = Optional.empty()
    }

    CompetitionSettings(Integer round) {
        this.round = round != null ? Optional.of(round) : Optional.empty();
    }

}
