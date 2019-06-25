/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DSJob {

    private static final List<String> SEARCH_PROPERTIES = createSearchProperties();

    private static List<String> createSearchProperties() {
        ArrayList<String> searchProperties = new ArrayList<>();
        searchProperties.add("short_description");
        searchProperties.add("long_description");
        searchProperties.add("references_local_or_shared_containers");
        searchProperties.add("type");
        searchProperties.add("reads_from_(design)");
        searchProperties.add("writes_to_(design)");
        searchProperties.add("created_by");
        searchProperties.add("created_on");
        searchProperties.add("modified_by");
        searchProperties.add("modified_on");
        return Collections.unmodifiableList(searchProperties);
    }

    /**
     * Retrieve a list of the search properties to use when querying via the IGC REST API.
     *
     * @return {@code List<String>}
     */
    public static final List<String> getSearchProperties() { return SEARCH_PROPERTIES; }

    private IGCRestClient igcRestClient;
    private Reference job;
    private JobType type;
    private Map<String, Reference> stageMap;
    private Map<String, Reference> linkMap;
    private Map<String, Reference> columnMap;
    private Map<String, Reference> fieldMap;
    private Map<String, List<Reference>> storeToFieldsMap;
    private Map<String, String> storeToQualifiedNameMap;
    private List<String> inputStageRIDs;
    private List<String> outputStageRIDs;

    public enum JobType {
        JOB, SEQUENCE
    }

    /**
     * Create a new detailed DataStage job object.
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param job the dsjob object from IGC
     * @param stages a listing of stage objects from IGC
     * @param links a listing of link objects from IGC
     * @param columns a listing of stage columns from IGC
     * @param fields a listing of data store fields (including database columns) from IGC
     */
    public DSJob(IGCRestClient igcRestClient, Reference job, ReferenceList stages, ReferenceList links, ReferenceList columns, List<Reference> fields) {
        this.igcRestClient = igcRestClient;
        this.job = job;
        this.type = job.getType().equals("sequence_job") ? JobType.SEQUENCE : JobType.JOB;
        this.inputStageRIDs = new ArrayList<>();
        this.outputStageRIDs = new ArrayList<>();
        classifyStages(stages);
        buildMap(linkMap, links);
        buildMap(columnMap, columns);
        classifyFields(fields);
    }

    /**
     * Retrieve the type of job represented by this instance.
     *
     * @return JobType
     */
    public JobType getType() { return type; }

    /**
     * Retrieve a list of the input stages for this job.
     *
     * @return {@code List<Reference>}
     */
    public List<Reference> getInputStages() {
        List<Reference> list = new ArrayList<>();
        for (String inputRid : inputStageRIDs) {
            list.add(stageMap.get(inputRid));
        }
        return list;
    }

    /**
     * Retrieve a list of the output stages for this job.
     *
     * @return {@code List<Reference>}
     */
    public List<Reference> getOutputStages() {
        List<Reference> list = new ArrayList<>();
        for (String outputRid : outputStageRIDs) {
            list.add(stageMap.get(outputRid));
        }
        return list;
    }

    private void buildMap(Map<String, Reference> map, ReferenceList objects) {
        List<Reference> listOfObjects = objects.getItems();
        for (Reference candidateObject : listOfObjects) {
            String rid = candidateObject.getId();
            map.put(rid, candidateObject);
        }
    }

    private void classifyStages(ReferenceList stages) {
        List<Reference> listOfStages = stages.getItems();
        for (Reference candidateStage : listOfStages) {
            String rid = candidateStage.getId();
            stageMap.put(rid, candidateStage);
            if (DSStage.isInputStage(igcRestClient, candidateStage)) {
                inputStageRIDs.add(rid);
            }
            if (DSStage.isOutputStage(igcRestClient, candidateStage)) {
                outputStageRIDs.add(rid);
            }
        }
    }

    private void classifyFields(List<Reference> listOfFields) {
        for (Reference candidateField : listOfFields) {
            String rid = candidateField.getId();
            fieldMap.put(rid, candidateField);
            Identity storeIdentity = candidateField.getIdentity(igcRestClient).getParentIdentity();
            String storeId = storeIdentity.getRid();
            storeToQualifiedNameMap.put(storeId, storeIdentity.toString());
            if (!storeToFieldsMap.containsKey(storeId)) {
                storeToFieldsMap.put(storeId, new ArrayList<>());
            }
            storeToFieldsMap.get(storeId).add(candidateField);
        }
    }

}
