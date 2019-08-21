/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorList {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Error")
    private List<Error> errorList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "NameConflict")
    private List<NameConflict> nameConflictList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ResourceNotFound")
    private List<ResourceNotFound> resourceNotFoundList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InvalidIADBParmas")
    private List<Error> invalidIADBParams;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InvalidBinding")
    private List<InvalidBinding> invalidBindingList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InvalidExpression")
    private List<InvalidExpression> invalidExpressionList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ColumnNotRegistered")
    private List<ColumnNotRegistered> columnNotRegisteredList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "VirtualColumnCannotBeCreated")
    private List<VirtualColumnCannotBeCreated> virtualColumnCannotBeCreatedList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "VirtualTableCannotBeCreated")
    private List<VirtualTableCannotBeCreated> virtualTableCannotBeCreatedList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotExecuteTaskSequence")
    private List<CannotExecuteTaskSequence> cannotExecuteTaskSequenceList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotExecuteRule")
    private List<CannotExecuteRule> cannotExecuteRuleList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotExecuteMetric")
    private List<CannotExecuteMetric> cannotExecuteMetricList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotExecuteReferentialIntegrityAnalysis")
    private List<CannotExecuteReferentialIntegrityAnalysis> cannotExecuteReferentialIntegrityAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InvalidResource")
    private List<InvalidResource> invalidResourceList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotRunColumnAnalysis")
    private List<CannotRunColumnAnalysis> cannotRunColumnAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotRunCrossDomainAnalysis")
    private List<CannotRunCrossDomainAnalysis> cannotRunCrossDomainAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InvalidName")
    private List<InvalidName> invalidNameList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "UnauthorizedUser")
    private List<UnauthorizedUser> unauthorizedUserList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotParseExpression")
    private List<CannotParseExpression> cannotParseExpressionList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InvalidOutputColumn")
    private List<InvalidOutputColumn> invalidOutputColumnList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DataSourceNotConfigured")
    private List<DataSourceNotConfigured> dataSourceNotConfiguredList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "InvalidJoinCondition")
    private List<InvalidJoinCondition> invalidJoinConditionList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "NoExecutionFound")
    private List<NoExecutionFound> noExecutionFoundList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "FutureDateUsed")
    private List<FutureDateUsed> futureDateUsedList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "UnSupportedDateFormat")
    private List<Error> unSupportedDateFormatList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ColumnNotAnalyzed")
    private List<ColumnNotAnalyzed> columnNotAnalyzedList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TableNotAnalyzed")
    private List<TableNotAnalyzed> tableNotAnalyzedList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AmbiguousDataSourceDetails")
    private List<AmbiguousDataSourceDetails> ambiguousDataSourceDetailsList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotRunDataQualityAnalysis")
    private List<CannotRunDataQualityAnalysis> cannotRunDataQualityAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CannotRunAutoDiscovery")
    private List<CannotRunAutoDiscovery> cannotRunAutoDiscoveryList;

    public List<Error> getErrorList() { return errorList; }
    public void setErrorList(List<Error> errorList) { this.errorList = errorList; }

    public List<NameConflict> getNameConflictList() { return nameConflictList; }
    public void setNameConflictList(List<NameConflict> nameConflictList) { this.nameConflictList = nameConflictList; }

    public List<ResourceNotFound> getResourceNotFoundList() { return resourceNotFoundList; }
    public void setResourceNotFoundList(List<ResourceNotFound> resourceNotFoundList) { this.resourceNotFoundList = resourceNotFoundList; }

    public List<Error> getInvalidIADBParams() { return invalidIADBParams; }
    public void setInvalidIADBParams(List<Error> invalidIADBParams) { this.invalidIADBParams = invalidIADBParams; }

    public List<InvalidBinding> getInvalidBindingList() { return invalidBindingList; }
    public void setInvalidBindingList(List<InvalidBinding> invalidBindingList) { this.invalidBindingList = invalidBindingList; }

    public List<InvalidExpression> getInvalidExpressionList() { return invalidExpressionList; }
    public void setInvalidExpressionList(List<InvalidExpression> invalidExpressionList) { this.invalidExpressionList = invalidExpressionList; }

    public List<ColumnNotRegistered> getColumnNotRegisteredList() { return columnNotRegisteredList; }
    public void setColumnNotRegisteredList(List<ColumnNotRegistered> columnNotRegisteredList) { this.columnNotRegisteredList = columnNotRegisteredList; }

    public List<VirtualColumnCannotBeCreated> getVirtualColumnCannotBeCreatedList() { return virtualColumnCannotBeCreatedList; }
    public void setVirtualColumnCannotBeCreatedList(List<VirtualColumnCannotBeCreated> virtualColumnCannotBeCreatedList) { this.virtualColumnCannotBeCreatedList = virtualColumnCannotBeCreatedList; }

    public List<VirtualTableCannotBeCreated> getVirtualTableCannotBeCreatedList() { return virtualTableCannotBeCreatedList; }
    public void setVirtualTableCannotBeCreatedList(List<VirtualTableCannotBeCreated> virtualTableCannotBeCreatedList) { this.virtualTableCannotBeCreatedList = virtualTableCannotBeCreatedList; }

    public List<CannotExecuteTaskSequence> getCannotExecuteTaskSequenceList() { return cannotExecuteTaskSequenceList; }
    public void setCannotExecuteTaskSequenceList(List<CannotExecuteTaskSequence> cannotExecuteTaskSequenceList) { this.cannotExecuteTaskSequenceList = cannotExecuteTaskSequenceList; }

    public List<CannotExecuteRule> getCannotExecuteRuleList() { return cannotExecuteRuleList; }
    public void setCannotExecuteRuleList(List<CannotExecuteRule> cannotExecuteRuleList) { this.cannotExecuteRuleList = cannotExecuteRuleList; }

    public List<CannotExecuteMetric> getCannotExecuteMetricList() { return cannotExecuteMetricList; }
    public void setCannotExecuteMetricList(List<CannotExecuteMetric> cannotExecuteMetricList) { this.cannotExecuteMetricList = cannotExecuteMetricList; }

    public List<CannotExecuteReferentialIntegrityAnalysis> getCannotExecuteReferentialIntegrityAnalysisList() { return cannotExecuteReferentialIntegrityAnalysisList; }
    public void setCannotExecuteReferentialIntegrityAnalysisList(List<CannotExecuteReferentialIntegrityAnalysis> cannotExecuteReferentialIntegrityAnalysisList) { this.cannotExecuteReferentialIntegrityAnalysisList = cannotExecuteReferentialIntegrityAnalysisList; }

    public List<InvalidResource> getInvalidResourceList() { return invalidResourceList; }
    public void setInvalidResourceList(List<InvalidResource> invalidResourceList) { this.invalidResourceList = invalidResourceList; }

    public List<CannotRunColumnAnalysis> getCannotRunColumnAnalysisList() { return cannotRunColumnAnalysisList; }
    public void setCannotRunColumnAnalysisList(List<CannotRunColumnAnalysis> cannotRunColumnAnalysisList) { this.cannotRunColumnAnalysisList = cannotRunColumnAnalysisList; }

    public List<CannotRunCrossDomainAnalysis> getCannotRunCrossDomainAnalysisList() { return cannotRunCrossDomainAnalysisList; }
    public void setCannotRunCrossDomainAnalysisList(List<CannotRunCrossDomainAnalysis> cannotRunCrossDomainAnalysisList) { this.cannotRunCrossDomainAnalysisList = cannotRunCrossDomainAnalysisList; }

    public List<InvalidName> getInvalidNameList() { return invalidNameList; }
    public void setInvalidNameList(List<InvalidName> invalidNameList) { this.invalidNameList = invalidNameList; }

    public List<UnauthorizedUser> getUnauthorizedUserList() { return unauthorizedUserList; }
    public void setUnauthorizedUserList(List<UnauthorizedUser> unauthorizedUserList) { this.unauthorizedUserList = unauthorizedUserList; }

    public List<CannotParseExpression> getCannotParseExpressionList() { return cannotParseExpressionList; }
    public void setCannotParseExpressionList(List<CannotParseExpression> cannotParseExpressionList) { this.cannotParseExpressionList = cannotParseExpressionList; }

    public List<InvalidOutputColumn> getInvalidOutputColumnList() { return invalidOutputColumnList; }
    public void setInvalidOutputColumnList(List<InvalidOutputColumn> invalidOutputColumnList) { this.invalidOutputColumnList = invalidOutputColumnList; }

    public List<DataSourceNotConfigured> getDataSourceNotConfiguredList() { return dataSourceNotConfiguredList; }
    public void setDataSourceNotConfiguredList(List<DataSourceNotConfigured> dataSourceNotConfiguredList) { this.dataSourceNotConfiguredList = dataSourceNotConfiguredList; }

    public List<InvalidJoinCondition> getInvalidJoinConditionList() { return invalidJoinConditionList; }
    public void setInvalidJoinConditionList(List<InvalidJoinCondition> invalidJoinConditionList) { this.invalidJoinConditionList = invalidJoinConditionList; }

    public List<NoExecutionFound> getNoExecutionFoundList() { return noExecutionFoundList; }
    public void setNoExecutionFoundList(List<NoExecutionFound> noExecutionFoundList) { this.noExecutionFoundList = noExecutionFoundList; }

    public List<FutureDateUsed> getFutureDateUsedList() { return futureDateUsedList; }
    public void setFutureDateUsedList(List<FutureDateUsed> futureDateUsedList) { this.futureDateUsedList = futureDateUsedList; }

    public List<Error> getUnSupportedDateFormatList() { return unSupportedDateFormatList; }
    public void setUnSupportedDateFormatList(List<Error> unSupportedDateFormatList) { this.unSupportedDateFormatList = unSupportedDateFormatList; }

    public List<ColumnNotAnalyzed> getColumnNotAnalyzedList() { return columnNotAnalyzedList; }
    public void setColumnNotAnalyzedList(List<ColumnNotAnalyzed> columnNotAnalyzedList) { this.columnNotAnalyzedList = columnNotAnalyzedList; }

    public List<TableNotAnalyzed> getTableNotAnalyzedList() { return tableNotAnalyzedList; }
    public void setTableNotAnalyzedList(List<TableNotAnalyzed> tableNotAnalyzedList) { this.tableNotAnalyzedList = tableNotAnalyzedList; }

    public List<AmbiguousDataSourceDetails> getAmbiguousDataSourceDetailsList() { return ambiguousDataSourceDetailsList; }
    public void setAmbiguousDataSourceDetailsList(List<AmbiguousDataSourceDetails> ambiguousDataSourceDetailsList) { this.ambiguousDataSourceDetailsList = ambiguousDataSourceDetailsList; }

    public List<CannotRunDataQualityAnalysis> getCannotRunDataQualityAnalysisList() { return cannotRunDataQualityAnalysisList; }
    public void setCannotRunDataQualityAnalysisList(List<CannotRunDataQualityAnalysis> cannotRunDataQualityAnalysisList) { this.cannotRunDataQualityAnalysisList = cannotRunDataQualityAnalysisList; }

    public List<CannotRunAutoDiscovery> getCannotRunAutoDiscoveryList() { return cannotRunAutoDiscoveryList; }
    public void setCannotRunAutoDiscoveryList(List<CannotRunAutoDiscovery> cannotRunAutoDiscoveryList) { this.cannotRunAutoDiscoveryList = cannotRunAutoDiscoveryList; }

    /**
     * Utility method to retrieve all of the errors from the list, irrespective of the type of error they are
     * (also useful to check if there were any errors rather than massive conditional logic blocks that try to
     * encompass all of the different potential error types)
     *
     * @return {@code List<Error>}
     */
    @JsonIgnore
    public List<Error> getAllErrors() {
        List<Error> allErrors = new ArrayList<>();
        Optional.ofNullable(errorList).ifPresent(allErrors::addAll);
        Optional.ofNullable(nameConflictList).ifPresent(allErrors::addAll);
        Optional.ofNullable(resourceNotFoundList).ifPresent(allErrors::addAll);
        Optional.ofNullable(invalidIADBParams).ifPresent(allErrors::addAll);
        Optional.ofNullable(invalidBindingList).ifPresent(allErrors::addAll);
        Optional.ofNullable(invalidExpressionList).ifPresent(allErrors::addAll);
        Optional.ofNullable(columnNotRegisteredList).ifPresent(allErrors::addAll);
        Optional.ofNullable(virtualColumnCannotBeCreatedList).ifPresent(allErrors::addAll);
        Optional.ofNullable(virtualTableCannotBeCreatedList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotExecuteTaskSequenceList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotExecuteRuleList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotExecuteMetricList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotExecuteReferentialIntegrityAnalysisList).ifPresent(allErrors::addAll);
        Optional.ofNullable(invalidResourceList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotRunColumnAnalysisList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotRunCrossDomainAnalysisList).ifPresent(allErrors::addAll);
        Optional.ofNullable(invalidNameList).ifPresent(allErrors::addAll);
        Optional.ofNullable(unauthorizedUserList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotParseExpressionList).ifPresent(allErrors::addAll);
        Optional.ofNullable(invalidOutputColumnList).ifPresent(allErrors::addAll);
        Optional.ofNullable(dataSourceNotConfiguredList).ifPresent(allErrors::addAll);
        Optional.ofNullable(invalidJoinConditionList).ifPresent(allErrors::addAll);
        Optional.ofNullable(noExecutionFoundList).ifPresent(allErrors::addAll);
        Optional.ofNullable(futureDateUsedList).ifPresent(allErrors::addAll);
        Optional.ofNullable(unSupportedDateFormatList).ifPresent(allErrors::addAll);
        Optional.ofNullable(columnNotAnalyzedList).ifPresent(allErrors::addAll);
        Optional.ofNullable(tableNotAnalyzedList).ifPresent(allErrors::addAll);
        Optional.ofNullable(ambiguousDataSourceDetailsList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotRunDataQualityAnalysisList).ifPresent(allErrors::addAll);
        Optional.ofNullable(cannotRunAutoDiscoveryList).ifPresent(allErrors::addAll);
        return allErrors;
    }

    @Override
    public String toString() {
        return "{ \"Errors\": " + (errorList == null ? "[]" : errorList.toString())
                + ", \"NameConflicts\": " + (nameConflictList == null ? "[]" : nameConflictList.toString())
                + ", \"ResourcesNotFound\": " + (resourceNotFoundList == null ? "[]" : resourceNotFoundList.toString())
                + ", \"InvalidIADBParams\": " + (invalidIADBParams == null ? "[]" : invalidIADBParams.toString())
                + ", \"InvalidBindings\": " + (invalidBindingList == null ? "[]" : invalidBindingList.toString())
                + ", \"InvalidExpressions\": " + (invalidExpressionList == null ? "[]" : invalidExpressionList.toString())
                + ", \"ColumnsNotRegistered\": " + (columnNotRegisteredList == null ? "[]" : columnNotRegisteredList.toString())
                + ", \"VirtualColumnsCannotBeCreated\": " + (virtualColumnCannotBeCreatedList == null ? "[]" : virtualColumnCannotBeCreatedList.toString())
                + ", \"VirtualTablesCannotBeCreated\": " + (virtualTableCannotBeCreatedList == null ? "[]" : virtualTableCannotBeCreatedList.toString())
                + ", \"CannotExecuteTaskSequences\": " + (cannotExecuteTaskSequenceList == null ? "[]" : cannotExecuteTaskSequenceList.toString())
                + ", \"CannotExecuteRules\": " + (cannotExecuteRuleList == null ? "[]" : cannotExecuteRuleList.toString())
                + ", \"CannotExecuteMetrics\": " + (cannotExecuteMetricList == null ? "[]" : cannotExecuteMetricList.toString())
                + ", \"CannotExecuteReferentialIntegrityAnalyses\": " + (cannotExecuteReferentialIntegrityAnalysisList == null ? "[]" : cannotExecuteReferentialIntegrityAnalysisList.toString())
                + ", \"InvalidResources\": " + (invalidResourceList == null ? "[]" : invalidResourceList.toString())
                + ", \"CannotRunColumnAnalyses\": " + (cannotRunColumnAnalysisList == null ? "[]" : cannotRunColumnAnalysisList.toString())
                + ", \"CannotRunCrossDomainAnalyses\": " + (cannotRunCrossDomainAnalysisList == null ? "[]" : cannotRunCrossDomainAnalysisList.toString())
                + ", \"InvalidNames\": " + (invalidNameList == null ? "[]" : invalidNameList.toString())
                + ", \"UnauthorizedUsers\": " + (unauthorizedUserList == null ? "[]" : unauthorizedUserList.toString())
                + ", \"CannotParseExpressions\": " + (cannotParseExpressionList == null ? "[]" : cannotParseExpressionList.toString())
                + ", \"InvalidOutputColumns\": " + (invalidOutputColumnList == null ? "[]" : invalidOutputColumnList.toString())
                + ", \"DataSourceNotConfigured\": " + (dataSourceNotConfiguredList == null ? "[]" : dataSourceNotConfiguredList.toString())
                + ", \"InvalidJoinConditions\": " + (invalidJoinConditionList == null ? "[]" : invalidJoinConditionList.toString())
                + ", \"NoExecutionsFound\": " + (noExecutionFoundList == null ? "[]" : noExecutionFoundList.toString())
                + ", \"FutureDatesUsed\": " + (futureDateUsedList == null ? "[]" : futureDateUsedList.toString())
                + ", \"UnSupportedDateFormats\": " + (unSupportedDateFormatList == null ? "[]" : unSupportedDateFormatList.toString())
                + ", \"ColumnsNotAnalyzed\": " + (columnNotAnalyzedList == null ? "[]" : columnNotAnalyzedList.toString())
                + ", \"TableNotAnalyzed\": " + (tableNotAnalyzedList == null ? "[]" : tableNotAnalyzedList.toString())
                + ", \"AmbiguousDataSourceDetails\": " + (ambiguousDataSourceDetailsList == null ? "[]" : ambiguousDataSourceDetailsList.toString())
                + ", \"CannotRunDataQualityAnalyses\": " + (cannotRunDataQualityAnalysisList == null ? "[]" : cannotRunDataQualityAnalysisList.toString())
                + ", \"CannotRunAutoDiscoveries\": " + (cannotRunAutoDiscoveryList == null ? "[]" : cannotRunAutoDiscoveryList.toString())
                + " }";
    }

}
