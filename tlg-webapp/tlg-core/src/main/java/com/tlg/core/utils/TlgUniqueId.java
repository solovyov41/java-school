package com.tlg.core.utils;

import com.tlg.core.entity.Cargo;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.Driver;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class TlgUniqueId implements UniqueIdGenerator {

    private static final int ID_LENGTH = 8;

    @Override
    public String generateUniqueId(Object object) {
        StringJoiner stringJoiner = new StringJoiner("-");

        if (object instanceof Driver) {
            stringJoiner.add(Prefix.DRIVER.abbr);
        }

        if (object instanceof Carriage) {
            stringJoiner.add(Prefix.CARRIAGE.abbr);
        }

        if (object instanceof Cargo) {
            stringJoiner.add(Prefix.CARGO.abbr);
        }

        stringJoiner.add(RandomStringUtils.randomNumeric(ID_LENGTH, ID_LENGTH));
        return stringJoiner.toString();
    }

    public enum Prefix {
        DRIVER("DVR"), CARRIAGE("ORD"), CARGO("CRG");

        Prefix(String abbr) {
            this.abbr = abbr;
        }

        private String abbr;

        public String getAbbr() {
            return abbr;
        }
    }

}
