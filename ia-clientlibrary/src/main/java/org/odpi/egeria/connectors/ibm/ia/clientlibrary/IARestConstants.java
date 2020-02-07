/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Set of constants for IA REST API usage.
 */
public class IARestConstants {

    public static final Pattern COOKIE_WHITELIST = Pattern.compile("^[{}.+/=:; a-zA-Z0-9_%\\-]+$");

    private static final Set<String> VALID_COOKIE_NAMES = createValidCookieNames();
    private static Set<String> createValidCookieNames() {
        Set<String> set = new HashSet<>();
        set.add("LtpaToken2");
        set.add("JSESSIONID");
        set.add("X-IBM-IISSessionId");
        set.add("X-IBM-IISSessionToken");
        return set;
    }

    /**
     * Retrieve the set of valid cookie names for the IGC REST API.
     *
     * @return {@code Set<String>}
     */
    public static Set<String> getValidCookieNames() { return VALID_COOKIE_NAMES; }

}
