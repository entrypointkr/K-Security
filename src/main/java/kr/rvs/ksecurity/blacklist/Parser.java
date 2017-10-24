package kr.rvs.ksecurity.blacklist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public class Parser {
    public static Set<Checker> parseBlackList(Reader reader) {
        Set<Checker> checkers = new HashSet<>();
        try (BufferedReader bufReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufReader.readLine()) != null) {
                String[] slice = line.split("\\|");
                if (slice.length != 2)
                    continue;

                String type = slice[0].toLowerCase();
                String content = slice[1];
                switch (type) {
                    case "ip":
                        checkers.add(new IPChecker(content));
                        break;
                    case "name":
                        checkers.add(new NameChecker(content));
                        break;
                    case "uuid":
                        checkers.add(new UUIDChecker(UUID.fromString(content)));
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return checkers;
    }
}
