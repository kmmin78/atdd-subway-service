package nextstep.subway.path.domain;

import nextstep.subway.exception.BadRequestException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.station.domain.Station;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class PathSelector {
    private static WeightedMultigraph<Long, DefaultWeightedEdge> graph
            = new WeightedMultigraph<>(DefaultWeightedEdge.class);
    private static DijkstraShortestPath<Long, DefaultWeightedEdge> path
            = new DijkstraShortestPath<>(graph);

    public static void init(List<Line> lines) {
        for (Line line : lines) {
            addSections(line.getSections());
        }
    }

    public static void add(Section section) {
        Station upStation = section.getUpStation();
        Station downStation = section.getDownStation();

        graph.addVertex(upStation.getId());
        graph.addVertex(downStation.getId());
        graph.setEdgeWeight(graph.addEdge(upStation.getId(),downStation.getId()), section.getDistance());
    }

    public static void remove(Section section) {
        graph.removeEdge(section.getUpStation().getId(), section.getDownStation().getId());
    }

    public static PathResult select(Station source, Station target) {
        try {
            return new PathResult(path.getPath(source.getId(), target.getId()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("연결되지 않은 역은 조회 할 수 없습니다.");
        }
    }

    public static void clear() {
        graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        path = new DijkstraShortestPath<>(graph);
    }

    private static void addSections(List<Section> sections) {
        for (Section section : sections) {
            add(section);
        }
    }
}