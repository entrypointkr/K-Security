package kr.rvs.ksecurity.util;

import kr.rvs.ksecurity.collection.NullableArrayList;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public class VarargsParser {
    private final Object[] args;
    private int count = 2;

    public VarargsParser(Object[] args) {
        this.args = args;
    }

    public VarargsParser count(int count) {
        this.count = count;
        return this;
    }

    public void parse(Consumer<Section> sectionCallback) {
        Queue queue = new ArrayDeque<>(Arrays.asList(args));
        while (queue.size() >= count) {
            NullableArrayList<Object> values = new NullableArrayList<>(count);
            for (int i = 0; i < count; i++) {
                values.add(queue.poll());
            }
            sectionCallback.accept(new Section(values));
        }
    }

    public class Section {
        private final NullableArrayList values;

        public Section(NullableArrayList values) {
            this.values = values;
        }

        @SuppressWarnings("unchecked")
        public <T> T get(Integer index) {
            Object value = values.get(index);
            return value != null ? (T) value : null;
        }

        public String getString(Integer index) {
            Object value = values.get(index);
            return value != null ? String.valueOf(value) : null;
        }

        public <T> Optional<T> getOptional(Integer index) {
            return Optional.ofNullable(get(index));
        }
    }
}
