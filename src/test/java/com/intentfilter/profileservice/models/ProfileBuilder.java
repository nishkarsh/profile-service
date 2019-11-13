package com.intentfilter.profileservice.models;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ProfileBuilder {
    private final Profile profile;

    private ProfileBuilder() {
        final var id = new ObjectId().toHexString();
        final var randomString = RandomStringUtils.random(244, "abcd");
        final var now = new Date();

        this.profile = new Profile(id, now, now, randomString, randomString, randomString,
                LocalDate.now().minusYears(18), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 1.25,
                UUID.randomUUID(), UUID.randomUUID(), randomString, randomString, UUID.randomUUID());
    }

    public static ProfileBuilder withDefaults() {
        return new ProfileBuilder();
    }

    public Profile build() {
        return this.profile;
    }
}
