/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.services;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.ReportRow;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportService {

    public static final String JOB = "Job";
    public static final String DOWNSTREAM = "Downstream:\n";
    public static final String UPSTREAM = "Upstream:\n";
    public static final String ROW_SPLITTER = ",";
    public static final String LINE_SPLITTER = "\n";
    public static final String URL_SPLITTER = "/";
    public static final int ROW_ITEMS_NUMBER = 8;
    public static final String REPORT_ROW_SHOULD_HAVE_A_NUMBER_OF_ITEMS = "Report row should have {} items. This one has {}. Row is: {}.";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
    private IGCRestClient igcRestClient;

    public ReportService(IGCRestClient igcRestClient) {
        this.igcRestClient = igcRestClient;
    }

    public List<String> getJobsFromReports(List<String> reportRids) {
        Set<String> uniqueJobRids = new HashSet<>();
        if(CollectionUtils.isEmpty(reportRids)) {
            return Collections.emptyList();
        }

        for(String reportRid : reportRids) {
            String report = igcRestClient.getReport(reportRid);
            if(StringUtils.isNotEmpty(report)) {
                uniqueJobRids.addAll(getUniqueJobRids(report));
            }
        }
        return new ArrayList<>(uniqueJobRids);
    }

    private Set<String> getUniqueJobRids(String report) {

        String[] splitByDownstream = StringUtils.splitByWholeSeparator(report, DOWNSTREAM);
        String downstreamAndUpstream = splitByDownstream[1];
        String[] downstreamAndUpstreamSplit = StringUtils.splitByWholeSeparator(downstreamAndUpstream, UPSTREAM);

        String downstream = downstreamAndUpstreamSplit[0];
        String upstream = downstreamAndUpstreamSplit[1];

        List<ReportRow> downstreamLineageRows = getLineageFlows(downstream);
        List<ReportRow> upstreamLineageRows = getLineageFlows(upstream);

        Set<String> uniqueJobRids =  getJobRidsFromRows(downstreamLineageRows);
        uniqueJobRids.addAll(getJobRidsFromRows(upstreamLineageRows));
        return uniqueJobRids;
    }

    private Set<String> getJobRidsFromRows(List<ReportRow> reportRows) {
        Set<String> uniqueJobRids = new HashSet<>();
        for(ReportRow row : reportRows) {
            String sourceType = row.getSourceType();
            if(StringUtils.isNotEmpty(sourceType) && sourceType.contains(JOB)) {
                uniqueJobRids.add(row.getSourceId());
            }
            String targetType = row.getTargetType();
            if(StringUtils.isNotEmpty(targetType) && targetType.contains(JOB)) {
                uniqueJobRids.add(row.getTargetId());
            }
        }
        return uniqueJobRids;
    }

    private List<ReportRow> getLineageFlows(String stream) {
        String[] streamRows = stream.split(LINE_SPLITTER);

        List<ReportRow> streamRowsResult = new ArrayList<>();

        for (int i = 1; i < streamRows.length; i++) {
            String[] fields = streamRows[i].split(ROW_SPLITTER);
            if (fields.length != ROW_ITEMS_NUMBER) {
                LOGGER.error(REPORT_ROW_SHOULD_HAVE_A_NUMBER_OF_ITEMS, ROW_ITEMS_NUMBER, fields.length, streamRows[i]);
            }
            ReportRow reportRow = new ReportRow();
            reportRow.setSourceType(removeQuotes(fields[0]));
            reportRow.setSourceAsset(removeQuotes(fields[1]));
            reportRow.setSourceContext(removeQuotes(fields[2]));
            reportRow.setSourceURL(removeQuotes(fields[3]));
            String[] urlPartsSource = reportRow.getSourceURL().split(URL_SPLITTER);
            reportRow.setSourceId(urlPartsSource[urlPartsSource.length - 1]);
            reportRow.setTargetType(removeQuotes(fields[4]));
            reportRow.setTargetAsset(removeQuotes(fields[5]));
            reportRow.setTargetContext(removeQuotes(fields[6]));
            reportRow.setTargetURL(removeQuotes(fields[7]));
            String[] urlPartsTarget = reportRow.getTargetURL().split(URL_SPLITTER);
            reportRow.setTargetId(urlPartsTarget[urlPartsTarget.length - 1]);
            streamRowsResult.add(reportRow);
        }
        return streamRowsResult;
    }

    private String removeQuotes(String string) {
        return string.substring(1, string.length() - 1);
    }

}
