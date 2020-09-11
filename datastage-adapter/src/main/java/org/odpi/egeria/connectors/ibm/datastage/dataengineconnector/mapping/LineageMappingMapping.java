/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.interfaces.ColumnLevelLineage;
import org.odpi.openmetadata.accessservices.dataengine.model.LineageMapping;
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

    private Set<LineageMapping> lineageMappings;

    /**
     * Creates LineageMappings for stages that have links as input and output.
     * - {@code STAGEB ( -> processing -> )}
     *   - {@code DSLink1_STAGEB to DSLink2_STAGEB (INPUT_PORT to OUTPUT_PORT)}
     *
     * @param cache used by this mapping
     * @param job the job for which to create the LineageMappings
     * @param stageRid the RID of the stage for which we are building mappings
     * @param knownLinks set of known link RIDs
     * @param link the link for which to create the LineageMappings
     * @param fullyQualifiedStageName the stage name for which to create the LineageMappings
     * @param bSource true if processing a source link, false if a target link
     */
    LineageMappingMapping(DataStageCache cache, DataStageJob job, String stageRid, Set<String> knownLinks, Link link, String fullyQualifiedStageName, boolean bSource) {
        super(cache);
        lineageMappings = new HashSet<>();
        ItemList<DataItem> stageColumns = link.getStageColumns();
        List<DataItem> allStageColumns = igcRestClient.getAllPages("stage_columns", stageColumns);
        log.debug("Constructing LineageMappings for stage columns: {}", allStageColumns);
        // For each stage column defined on the link...
        for (DataItem stageColumnRef : allStageColumns) {
            String colId = stageColumnRef.getId();
            ColumnLevelLineage stageColumnFull = job.getColumnLevelLineageByRid(colId);
            log.debug(" ... introspecting stage column: {}", stageColumnFull);
            String stageColumnFullQN = getFullyQualifiedName(stageColumnFull, fullyQualifiedStageName);
            if (stageColumnFullQN != null) {
                // TODO: something sketchy happening somewhere in lineage mappings: the previous / next columns COULD
                //  refer to stage columns that are not actually part of the job's input or output links. In such cases
                //  the specific stage column that uses such a non-job-related link should be ignored (it will be
                //  covered elsewhere by the job that actually DOES related to that link).
                if (!bSource) {
                    // Create a LineageMapping from each previous stage column to this stage column
                    ItemList<DataItem> previousColumns = stageColumnFull.getPreviousStageColumns();
                    List<DataItem> allPreviousColumns = igcRestClient.getAllPages("previous_stage_columns", previousColumns);
                    log.debug(" ...... iterating through previous columns: {}", allPreviousColumns);
                    for (DataItem previousColumnRef : allPreviousColumns) {
                        ColumnLevelLineage previousColumnFull = job.getColumnLevelLineageByRid(previousColumnRef.getId());
                        if (stageColumnForKnownLink(previousColumnFull, stageRid, knownLinks)) {
                            String previousColumnFullQN = getFullyQualifiedName(previousColumnFull, fullyQualifiedStageName);
                            if (previousColumnFullQN != null) {
                                LineageMapping lineageMapping = getLineageMapping(previousColumnFullQN, stageColumnFullQN);
                                lineageMappings.add(lineageMapping);
                            } else {
                                log.error("Unable to determine identity for previous column -- not including (full was {}): {}",
                                        previousColumnFull == null ? "null" : "non-null",
                                        previousColumnRef);
                            }
                        } else {
                            log.warn("Found a stage column for a link not listed as an input link for this stage -- ignoring: {}", previousColumnFull);
                        }
                    }
                } else {
                    // Create a LineageMapping from this stage column to each next stage column
                    ItemList<DataItem> nextColumns = stageColumnFull.getNextStageColumns();
                    List<DataItem> allNextColumns = igcRestClient.getAllPages("next_stage_columns", nextColumns);
                    log.debug(" ...... iterating through next columns: {}", allNextColumns);
                    for (DataItem nextColumnRef : allNextColumns) {
                        ColumnLevelLineage nextColumnFull = job.getColumnLevelLineageByRid(nextColumnRef.getId());
                        if (stageColumnForKnownLink(nextColumnFull, stageRid, knownLinks)) {
                            String nextColumnFullQN = getFullyQualifiedName(nextColumnFull, fullyQualifiedStageName);
                            if (nextColumnFullQN != null) {
                                LineageMapping lineageMapping = getLineageMapping(stageColumnFullQN, nextColumnFullQN);
                                lineageMappings.add(lineageMapping);
                            } else {
                                log.error("Unable to determine identity for next column -- not including (full was {}): {}",
                                        nextColumnFull == null ? "null" : "non-null",
                                        nextColumnRef);
                            }
                        } else {
                            log.warn("Found a stage column for a link not listed as an output link for this stage -- ignoring: {}", nextColumnFull);
                        }
                    }
                }
            } else {
                log.error("Unable to determine identity for stage column -- not including (full was {}): {}",
                        stageColumnFull == null ? "null" : "non-null",
                        stageColumnRef);
            }
        }
    }

    /**
     * Creates LineageMappings between stages.
     * - {@code STAGEA (data source -> )}
     *   - {@code DSLink1_STAGEA to DSLink1_STAGEB (cross-process mapping)}
     * - {@code STAGEB ( -> processing -> )}
     *   - {@code DSLink2_STAGEB to DSLink2_STAGEC (cross-process mapping)}
     *
     * @param cache used by this mapping
     * @param job the job for which to create the LineageMappings
     * @param link the link for which to create the LineageMappings
     */
    LineageMappingMapping(DataStageCache cache, DataStageJob job, Link link) {
        super(cache);
        lineageMappings = new HashSet<>();
        // Despite the plural name, a link can only have one input and one output stage so these are singular
        Stage inputStage = link.getInputStages();
        Stage outputStage = link.getOutputStages();
        ItemList<DataItem> stageColumns = link.getStageColumns();
        List<DataItem> allStageColumns = igcRestClient.getAllPages("stage_columns", stageColumns);
        log.debug("Constructing LineageMappings between stages: {}", link);
        // For each stage column defined on the link...
        for (DataItem stageColRef : allStageColumns) {
            ColumnLevelLineage stageColFull = job.getColumnLevelLineageByRid(stageColRef.getId());
            String inputQN  = getFullyQualifiedName(inputStage);
            String outputQN = getFullyQualifiedName(outputStage);
            String stageColNameIn  = getFullyQualifiedName(stageColFull, inputQN);
            String stageColNameOut = getFullyQualifiedName(stageColFull, outputQN);
            if (stageColNameIn != null && stageColNameOut != null) {
                // Create a single mapping between the input stage and the output stage that use this link
                LineageMapping lineageMapping = getLineageMapping(stageColNameIn, stageColNameOut);
                lineageMappings.add(lineageMapping);
            } else {
                if (stageColNameIn == null) {
                    log.error("Unable to determine identity for input stage column -- not including (full was {}): {}",
                            stageColFull == null ? "null" : "non-null",
                            inputStage);
                }
                if (stageColNameOut == null) {
                    log.error("Unable to determine identity for output stage column -- not including (full was {}): {}",
                            stageColFull == null ? "null" : "non-null",
                            outputStage);
                }
            }
        }
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
     * @param cache used by this mapping
     * @param job the job for which to create the LineageMappings
     * @param fields list of IGC field objects (data_file_field or database_column)
     * @param bSource true if processing a source link, false if a target link
     * @param fullyQualifiedStageName the fully qualifiedName of the stage itself
     */
    LineageMappingMapping(DataStageCache cache, DataStageJob job, List<Classificationenabledgroup> fields, boolean bSource, String fullyQualifiedStageName) {
        super(cache);
        lineageMappings = new HashSet<>();
        // For each field in the data store...
        if (fields != null) {
            for (Classificationenabledgroup fieldObj : fields) {
                String field1QN = getFullyQualifiedName(fieldObj);
                if (field1QN != null) {
                    ItemList<InformationAsset> relatedStageCols;
                    String propertyName;
                    if (bSource) {
                        relatedStageCols = fieldObj.getReadByDesign();
                        propertyName = "read_by_(design)";
                    } else {
                        relatedStageCols = fieldObj.getWrittenByDesign();
                        propertyName = "written_by_(design)";
                    }
                    log.debug("Constructing LineageMappings between store field and stages' {}: {}", bSource ? "source" : "target", fieldObj);
                    if (relatedStageCols != null) {
                        List<InformationAsset> allRelatedStageCols = igcRestClient.getAllPages(propertyName, relatedStageCols);
                        // For each object that reads / writes to that field...
                        for (InformationAsset stageColRef : allRelatedStageCols) {
                            ColumnLevelLineage stageColFull = job.getColumnLevelLineageByRid(stageColRef.getId());
                            if (stageColFull != null) {
                                String field1EmbeddedQN = getFullyQualifiedName(fieldObj, fullyQualifiedStageName);
                                String field2EmbeddedQN = getFullyQualifiedName(stageColFull, fullyQualifiedStageName);
                                if (bSource) {
                                    // StoreX to StoreX_STAGEA (reads_from_(design) to INPUT_PORT)
                                    LineageMapping oneToOne = getLineageMapping(field1QN, field1EmbeddedQN);
                                    lineageMappings.add(oneToOne);
                                    // StoreX_STAGEA to DSLink1_STAGEA (INPUT_PORT to OUTPUT_PORT)
                                    LineageMapping portToPort = getLineageMapping(field1EmbeddedQN, field2EmbeddedQN);
                                    lineageMappings.add(portToPort);
                                } else {
                                    // DSLink2_STAGEC to StoreY_STAGEC (INPUT_PORT to OUTPUT_PORT)
                                    LineageMapping portToPort = getLineageMapping(field2EmbeddedQN, field1EmbeddedQN);
                                    lineageMappings.add(portToPort);
                                    // StoreY_STAGEC to StoreY (OUTPUT_PORT to written_by_(design))
                                    LineageMapping oneToOne = getLineageMapping(field1EmbeddedQN, field1QN);
                                    lineageMappings.add(oneToOne);
                                }
                            } else {
                                log.error("Unable to find referenced stage column: {}", stageColRef);
                            }
                        }
                    } else {
                        log.info("No fields were found for lineage mapping of: {}", fieldObj);
                    }
                } else {
                    log.error("Unable to determine identity for field -- not including: {}", fieldObj);
                }
            }
        } else {
            log.warn("No fields were found for a data store for stage: {}", fullyQualifiedStageName);
        }
    }

    /**
     * Retrieve the LineageMappings that were setup.
     *
     * @return {@code Set<LineageMapping>}
     */
    Set<LineageMapping> getLineageMappings() { return lineageMappings; }

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

    /**
     * Determine whether the provided stage column is part of a known input / output link for the particular
     * stage for which we are generating a lineage mapping.
     *
     * @param column the stage column to check whether it is part of a known link
     * @param stageRid the RID of the stage in which to check for known-ness
     * @param knownLinks set of RIDs of links known as inputs / outputs from the stage
     * @return boolean
     */
    private boolean stageColumnForKnownLink(ColumnLevelLineage column, String stageRid, Set<String> knownLinks) {
        if (stageRid != null) {
            List<Reference> context = column.getContext();
            Reference link = context.get(context.size() - 1);
            if (link.getType().equals("link")) {
                return knownLinks.contains(link.getId());
            } else if (link.getType().equals("stage")) {
                return stageRid.equals(link.getId());
            } else {
                log.warn("Unknown parent type '{}' for column-level lineage -- skipping: {}", link.getType(), column);
            }
        } else {
            log.error("Unable to verify stage column as stage is null -- skipping: {}", column);
        }
        return false;
    }

}
