package nextstep.subway.line.dto;

import nextstep.subway.line.domain.Line;

public class LineRequest {
    private String name;
    private String color;
    private Long upStationId;
    private Long downStationId;
    private int distance;
    private int fare;

    private LineRequest() {
    }

    private LineRequest(String name, String color, Long upStationId, Long downStationId, int distance, int fare) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.fare = fare;
    }

    public static LineRequest of(String name, String color, Long upStationId, Long downStationId, int distance, int fare) {
        return new LineRequest(name, color, upStationId, downStationId, distance, fare);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() { return fare; }

    public Line toLine() {
        return Line.of(name, color, fare);
    }
}
