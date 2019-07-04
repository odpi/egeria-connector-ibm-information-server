/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DSJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.openmetadata.accessservices.dataengine.model.LineageMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mappings for creating a set of LineageMappings.
 */
public class LineageMappingMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(LineageMappingMapping.class);

    private Set<LineageMapping> lineageMappings;

    /**
     * Creates LineageMappings for stages that have links as input and output.
     * - STAGEB ( -> processing -> )
     *   - DSLink1_STAGEB to DSLink2_STAGEB (INPUT_PORT to OUTPUT_PORT)
     *
     * @param job the job for which to create the LineageMappings
     * @param link the link for which to create the LineageMappings
     * @param stageNameSuffix the stage name for which to create the LineageMappings
     * @param bSource true if processing a source link, false if a target link
     */
    public LineageMappingMapping(DSJob job, Reference link, String stageNameSuffix, boolean bSource) {
        super(job.getIgcRestClient());
        lineageMappings = new HashSet<>();
        ReferenceList stageColumns = (ReferenceList) igcRestClient.getPropertyByName(link, "stage_columns");
        // For each stage column defined on the link...
        for (Reference stageColumnRef : stageColumns.getItems()) {
            String colId = stageColumnRef.getId();
            Reference stageColumnFull = job.getStageColumnByRid(colId);
            String thisColumnName = getFullyQualifiedName(stageColumnFull) + stageNameSuffix;
            if (!bSource) {
                // Create a LineageMapping from each previous stage column to this stage column
                ReferenceList previousColumns = (ReferenceList) igcRestClient.getPropertyByName(stageColumnFull, "previous_stage_columns");
                for (Reference previousColumnRef : previousColumns.getItems()) {
                    Reference previousColumnFull = job.getStageColumnByRid(previousColumnRef.getId());
                    LineageMapping lineageMapping = getLineageMapping(getFullyQualifiedName(previousColumnFull) + stageNameSuffix, thisColumnName);
                    lineageMappings.add(lineageMapping);
                }
            } else {
                // Create a LineageMapping from this stage column to each next stage column
                ReferenceList nextColumns = (ReferenceList) igcRestClient.getPropertyByName(stageColumnFull, "next_stage_columns");
                for (Reference nextColumnRef : nextColumns.getItems()) {
                    Reference nextColumnFull = job.getStageColumnByRid(nextColumnRef.getId());
                    LineageMapping lineageMapping = getLineageMapping(thisColumnName, getFullyQualifiedName(nextColumnFull) + stageNameSuffix);
                    lineageMappings.add(lineageMapping);
                }
            }
        }
    }

    /**
     * Creates LineageMappings between stages.
     * - STAGEA (data source -> )
     *   - DSLink1_STAGEA to DSLink1_STAGEB (cross-process mapping)
     * - STAGEB ( -> processing -> )
     *   - DSLink2_STAGEB to DSLink2_STAGEC (cross-process mapping)
     *
     * @param job the job for which to create the LineageMappings
     * @param link the link for which to create the LineageMappings
     */
    public LineageMappingMapping(DSJob job, Reference link) {
        super(job.getIgcRestClient());
        lineageMappings = new HashSet<>();
        // Despite the plural name, a link can only have one input and one output stage so these are singular
        Reference inputStage = (Reference) igcRestClient.getPropertyByName(link, "input_stages");
        Reference outputStage = (Reference) igcRestClient.getPropertyByName(link, "output_stages");
        ReferenceList stageColumns = (ReferenceList) igcRestClient.getPropertyByName(link, "stage_columns");
        // For each stage column defined on the link...
        for (Reference stageColRef : stageColumns.getItems()) {
            Reference stageColFull = job.getStageColumnByRid(stageColRef.getId());
            String stageColName = getFullyQualifiedName(stageColFull);
            // Create a single mapping between the input stage and the output stage that use this link
            LineageMapping lineageMapping = getLineageMapping(stageColName + "_" + inputStage.getName(), stageColName + "_" + outputStage.getName());
            lineageMappings.add(lineageMapping);
        }
    }

    /**
     * Creates LineageMappings between data stores and stages.
     * - STAGEA (data source -> )
     *   - StoreX to StoreX_STAGEA (reads_from_(design) to INPUT_PORT)
     *   - StoreX_STAGEA to DSLink1_STAGEA (INPUT_PORT to OUTPUT_PORT)
     * - STAGEC ( -> data store)
     *   - DSLink2_STAGEC to StoreY_STAGEC (INPUT_PORT to OUTPUT_PORT)
     *   - StoreY_STAGEC to StoreY (OUTPUT_PORT to written_by_(design))
     *
     * @param job the job for which to create the LineageMappings
     * @param fields list of IGC field objects (data_file_field or database_column)
     * @param relationshipProperty one of 'read_by_(design)' or 'written_by_(design)'
     * @param bSource true if processing a source link, false if a target link
     * @param fullyQualifiedStageName the fully qualifiedName of the stage itself
     * @param stageNameSuffix the stage name for which to create the LineageMappings
     */
    public LineageMappingMapping(DSJob job, List<Reference> fields, String relationshipProperty, boolean bSource, String fullyQualifiedStageName, String stageNameSuffix) {
        super(job.getIgcRestClient());
        lineageMappings = new HashSet<>();
        // For each field in the data store...
        for (Reference fieldObj : fields) {
            String field1QN = getFullyQualifiedName(fieldObj);
            ReferenceList relatedStageCols = (ReferenceList) igcRestClient.getPropertyByName(fieldObj, relationshipProperty);
            // For each object that reads / writes to that field...
            for (Reference stageColRef : relatedStageCols.getItems()) {
                Reference stageColFull = job.getStageColumnByRid(stageColRef.getId());
                if (stageColFull != null) {
                    String field2QN = getFullyQualifiedName(stageColFull);
                    if (bSource) {
                        // StoreX to StoreX_STAGEA (reads_from_(design) to INPUT_PORT)
                        LineageMapping oneToOne = getLineageMapping(field1QN, field1QN + fullyQualifiedStageName);
                        lineageMappings.add(oneToOne);
                        // StoreX_STAGEA to DSLink1_STAGEA (INPUT_PORT to OUTPUT_PORT)
                        LineageMapping portToPort = getLineageMapping(field1QN + fullyQualifiedStageName, field2QN + stageNameSuffix);
                        lineageMappings.add(portToPort);
                    } else {
                        // DSLink2_STAGEC to StoreY_STAGEC (INPUT_PORT to OUTPUT_PORT)
                        LineageMapping portToPort = getLineageMapping(field1QN + fullyQualifiedStageName, field1QN);
                        lineageMappings.add(portToPort);
                        // StoreY_STAGEC to StoreY (OUTPUT_PORT to written_by_(design))
                        LineageMapping oneToOne = getLineageMapping(field2QN + stageNameSuffix, field1QN + fullyQualifiedStageName);
                        lineageMappings.add(oneToOne);
                    }
                } else {
                    log.error("Unable to find referenced stage column: {}", stageColRef);
                }
            }
        }
    }

    /**
     * Retrieve the LineageMappings that were setup.
     *
     * @return {@Set<LineageMapping>}
     */
    public Set<LineageMapping> getLineageMappings() { return lineageMappings; }

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
