/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic utility class to provide a standard mechanism through which to print out all of an asset's
 * details in a standard way that can be easily JSON-esque formatted.
 */
public abstract class ObjectPrinter {

    private static final Logger log = LoggerFactory.getLogger(ObjectPrinter.class);

    /**
     * Provides a string representation of the object that can be easily read and formatted in JSON-esque fashion.
     *
     * @return String
     */
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.info("Unable to translate to JSON representation.", e);
            return super.toString();
        }
    }

}
