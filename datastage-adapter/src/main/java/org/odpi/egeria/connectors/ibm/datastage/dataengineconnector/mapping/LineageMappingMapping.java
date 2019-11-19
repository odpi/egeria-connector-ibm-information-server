/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.openmetadata.accessservices.dataengine.model.LineageMapping;
import org.odpi.openmetadata.openconnectors.governancedaemonconnectors.dataengineproxy.model.DataEngineLineageMappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mappings for creating a set of LineageMappings.
 */
class LineageMappingMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(LineageMappingMapping.class);

    private DataEngineLineageMappings lineageMappings;

    /**
     * Creates LineageMappings for stages that have links as input and output.
     * - {@code STAGEB ( -> processing -> )}
     *   - {@code DSLink1_STAGEB to DSLink2_STAGEB (INPUT_PORT to OUTPUT_PORT)}
     *
     * @param job the job for which to create the LineageMappings
     * @param link the link for which to create the LineageMappings
     * @param stageNameSuffix the stage name for which to create the LineageMappings
     * @param bSource true if processing a source link, false if a target link
     */
    LineageMappingMapping(DataStageJob job, Link link, String stageNameSuffix, boolean bSource) {
        super(job.getIgcRestClient());
        Set<LineageMapping> lineageMappingsSet = new HashSet<>();
        String userId = link.getModifiedBy();
        ItemList<DataItem> stageColumns = link.getStageColumns();
        stageColumns.getAllPages(igcRestClient);
        // For each stage column defined on the link...
        for (DataItem stageColumnRef : stageColumns.getItems()) {
            String colId = stageColumnRef.getId();
            DsStageColumn stageColumnFull = job.getStageColumnByRid(colId);
            String thisColumnName = getFullyQualifiedName(stageColumnFull) + stageNameSuffix;
            if (!bSource) {
                // Create a LineageMapping from each previous stage column to this stage column
                ItemList<DataItem> previousColumns = stageColumnFull.getPreviousStageColumns();
                previousColumns.getAllPages(igcRestClient);
                for (DataItem previousColumnRef : previousColumns.getItems()) {
                    DsStageColumn previousColumnFull = job.getStageColumnByRid(previousColumnRef.getId());
                    LineageMapping lineageMapping = getLineageMapping(getFullyQualifiedName(previousColumnFull) + stageNameSuffix, thisColumnName);
                    lineageMappingsSet.add(lineageMapping);
                }
            } else {
                // Create a LineageMapping from this stage column to each next stage column
                ItemList<DataItem> nextColumns = stageColumnFull.getNextStageColumns();
                nextColumns.getAllPages(igcRestClient);
                for (DataItem nextColumnRef : nextColumns.getItems()) {
                    DsStageColumn nextColumnFull = job.getStageColumnByRid(nextColumnRef.getId());
                    LineageMapping lineageMapping = getLineageMapping(thisColumnName, getFullyQualifiedName(nextColumnFull) + stageNameSuffix);
                    lineageMappingsSet.add(lineageMapping);
                }
            }
        }
        lineageMappings = new DataEngineLineageMappings(lineageMappingsSet, userId);
    }

    /**
     * Creates LineageMappings between stages.
     * - {@code STAGEA (data source -> )}
     *   - {@code DSLink1_STAGEA to DSLink1_STAGEB (cross-process mapping)}
     * - {@code STAGEB ( -> processing -> )}
     *   - {@code DSLink2_STAGEB to DSLink2_STAGEC (cross-process mapping)}
     *
     * @param job the job for which to create the LineageMappings
     * @param link the link for which to create the LineageMappings
     */
    LineageMappingMapping(DataStageJob job, Link link) {
        super(job.getIgcRestClient());
        Set<LineageMapping> lineageMappingsSet = new HashSet<>();
        // Despite the plural name, a link can only have one input and one output stage so these are singular
        Stage inputStage = link.getInputStages();
        Stage outputStage = link.getOutputStages();
        ItemList<DataItem> stageColumns = link.getStageColumns();
        stageColumns.getAllPages(igcRestClient);
        String userId = link.getModifiedBy();
        // For each stage column defined on the link...
        for (DataItem stageColRef : stageColumns.getItems()) {
            DsStageColumn stageColFull = job.getStageColumnByRid(stageColRef.getId());
            String stageColName = getFullyQualifiedName(stageColFull);
            // Create a single mapping between the input stage and the output stage that use this link
            LineageMapping lineageMapping = getLineageMapping(stageColName + "_" + inputStage.getName(), stageColName + "_" + outputStage.getName());
            lineageMappingsSet.add(lineageMapping);
        }
        lineageMappings = new DataEngineLineageMappings(lineageMappingsSet, userId);
    }

    /**
     * Creates LineageMappings between data stores and stages.
     * - {@code STAGEA (data source -> )}
     *   - {@code StoreX to StoreX_STAGEA (reads_from_(design) to INPUT_PORT)}
     *   - {@code StoreX_STAGEA to DSLink1_STAGEA (INPUT_PORT to OUTPUT_PORT)}
     * - {@code STAGEC ( -> data store)}
     *   - {@code DSLink2_STAGEC to StoreY_STAGEC (INPUT_PORT to OUTPUT_PORT)}
     *   - {@code StoreY_STAGEC to StoreY (OUTPUT_PORT to written_by_(design))}
     *
     * @param job the job for which to create the LineageMappings
     * @param fields list of IGC field objects (data_file_field or database_column)
     * @param relationshipProperty one of 'read_by_(design)' or 'written_by_(design)'
     * @param bSource true if processing a source link, false if a target link
     * @param fullyQualifiedStageName the fully qualifiedName of the stage itself
     * @param stageNameSuffix the stage name for which to create the LineageMappings
     */
    LineageMappingMapping(DataStageJob job, List<Classificationenabledgroup> fields, String relationshipProperty, boolean bSource, String fullyQualifiedStageName, String stageNameSuffix) {
        super(job.getIgcRestClient());
        Set<LineageMapping> lineageMappingsSet = new HashSet<>();
        String userId = job.getJobObject().getModifiedBy();
        // For each field in the data store...
        for (Classificationenabledgroup fieldObj : fields) {
            String field1QN = getFullyQualifiedName(fieldObj);
            ItemList<InformationAsset> relatedStageCols = (ItemList<InformationAsset>) igcRestClient.getPropertyByName(fieldObj, relationshipProperty);
            relatedStageCols.getAllPages(igcRestClient);
            // For each object that reads / writes to that field...
            for (InformationAsset stageColRef : relatedStageCols.getItems()) {
                DsStageColumn stageColFull = job.getStageColumnByRid(stageColRef.getId());
                if (stageColFull != null) {
                    String field2QN = getFullyQualifiedName(stageColFull);
                    if (bSource) {
                        // StoreX to StoreX_STAGEA (reads_from_(design) to INPUT_PORT)
                        LineageMapping oneToOne = getLineageMapping(field1QN, field1QN + fullyQualifiedStageName);
                        lineageMappingsSet.add(oneToOne);
                        // StoreX_STAGEA to DSLink1_STAGEA (INPUT_PORT to OUTPUT_PORT)
                        LineageMapping portToPort = getLineageMapping(field1QN + fullyQualifiedStageName, field2QN + stageNameSuffix);
                        lineageMappingsSet.add(portToPort);
                    } else {
                        // DSLink2_STAGEC to StoreY_STAGEC (INPUT_PORT to OUTPUT_PORT)
                        LineageMapping portToPort = getLineageMapping(field1QN + fullyQualifiedStageName, field1QN);
                        lineageMappingsSet.add(portToPort);
                        // StoreY_STAGEC to StoreY (OUTPUT_PORT to written_by_(design))
                        LineageMapping oneToOne = getLineageMapping(field2QN + stageNameSuffix, field1QN + fullyQualifiedStageName);
                        lineageMappingsSet.add(oneToOne);
                    }
                } else {
                    log.error("Unable to find referenced stage column: {}", stageColRef);
                }
            }
        }
        lineageMappings = new DataEngineLineageMappings(lineageMappingsSet, userId);
    }

    /**
     * Retrieve the LineageMappings that were setup.
     *
     * @return DataEngineLineageMappings
     */
    DataEngineLineageMappings getLineageMappings() { return lineageMappings; }

    /**
     * Create a simple LineageMapping from source to target.
     *
     * @param source the qualified name of the source attribute
     * @param target the qualified name of the target attribute
     * @return LineageMapping
     */
    private LineageMapping getLineageMapping(String source, String target) {
        LineageMapping lineageMapping = new LineageMapping();
        lineageMapping.setSourceAttribute(source);
        lineageMapping.setTargetAttribute(target);
        return lineageMapping;
    }

}
