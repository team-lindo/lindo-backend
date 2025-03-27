package team.lindo.backend.application.matcher;

public interface MatchScorer<T> {
    int calculateMatchScore(T target, String query);
}
