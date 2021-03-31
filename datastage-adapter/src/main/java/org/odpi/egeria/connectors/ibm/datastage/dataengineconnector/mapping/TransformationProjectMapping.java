/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnector;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCIOException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.openmetadata.accessservices.dataengine.model.TransformationProject;

/**
 * Mappings for creating a TransformationObject.
 */
public class TransformationProjectMapping extends BaseMapping{

    private static final String TRANSFORMATION_PROJECT_KEY = "transformation_project";

    /**
     * Default constructor to pass in the cache for re-use.
     *
     * @param cache used by this mapping
     */
    TransformationProjectMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Retrieves the transformation project from an asset's context and returns it or null if it does not exists
     *
     * @param igcObj the asset for which to obtain the transformation project
     * */
    public TransformationProject getTransformationProject(InformationAsset igcObj) {
        String methodName = "getTransformationProject";
        for (Reference reference : igcObj.getContext()) {
            if (TRANSFORMATION_PROJECT_KEY.equals(reference.getType())) {
                TransformationProject transformationProject = new TransformationProject();
                String qualifiedName = getQualifiedName(methodName, reference);
                transformationProject.setQualifiedName(qualifiedName);
                transformationProject.setName(reference.getName());
                return transformationProject;
            }
        }
        return null;
    }

    private String getQualifiedName(String methodName, Reference reference) {
        try {
            return getFullyQualifiedName(reference);
        } catch (IGCConnectivityException | IGCParsingException | IGCIOException e) {
            DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                    this.getClass().getName(),
                    methodName,
                    e);
        }
        return null;
    }
}
