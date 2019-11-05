/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton defining the mapping to the OMRS "SpineObject" classification.
 */
public class SpineObjectMapper extends Spine_Mapper {

    private static final Logger log = LoggerFactory.getLogger(SpineObjectMapper.class);

    private static class Singleton {
        private static final SpineObjectMapper INSTANCE = new SpineObjectMapper();
    }
    public static SpineObjectMapper getInstance(IGCVersionEnum version) {
        return SpineObjectMapper.Singleton.INSTANCE;
    }

    private SpineObjectMapper() {
        super("SpineObject");
    }

}
