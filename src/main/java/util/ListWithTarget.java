package util;

import java.util.List;

public record ListWithTarget<T>(T target, List<T> list) {
}
