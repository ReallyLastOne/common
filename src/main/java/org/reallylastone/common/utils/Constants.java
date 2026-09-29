package org.reallylastone.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String API_PREFIX = "/api";

    public static final String AUTH_SCHEMA = "auth";
    public static final String AUTH_API_PREFIX = API_PREFIX + "/auth";

    public static final String MARKET_SCHEMA = "market";
    public static final String MARKET_API_PREFIX = API_PREFIX + "/market";

    public static final String SYSTEM_USER = "SYSTEM";

}
