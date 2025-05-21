package team.lindo.backend.application.search.service;

public interface MatchScorer<T> {
    int calculateMatchScore(T target, String query);
}
