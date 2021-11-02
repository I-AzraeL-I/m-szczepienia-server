package com.mycompany.mszczepienia.util;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("UnstableApiUsage")
public class RangeParser {

    private final RangeSet<LocalTime> rangeSet = TreeRangeSet.create();
    private final LocalTime startTime;
    private final LocalTime endTime;

    public RangeParser(LocalTime startTime, LocalTime endTime) {
        this.rangeSet.add(Range.closed(startTime, endTime));
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void subtractRange(LocalTime from, LocalTime to) {
        rangeSet.remove(Range.closed(from, to));
    }

    public List<LocalTime> toLowerEndpointList(long duration, ChronoUnit chronoUnit) {
        Stream.iterate(startTime, step -> step.compareTo(endTime) <= 0, step -> step.plus(duration, chronoUnit))
                .forEach(step -> rangeSet.remove(Range.closed(step, step)));
        return rangeSet.asRanges().stream()
                .filter(range -> chronoUnit.between(range.lowerEndpoint(), range.upperEndpoint()) == duration)
                .map(Range::lowerEndpoint)
                .collect(Collectors.toUnmodifiableList());
    }
}
