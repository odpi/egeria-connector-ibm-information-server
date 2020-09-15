#!/usr/bin/env bash

# SPDX-License-Identifier: Apache-2.0
# Copyright Contributors to the Egeria project.

release_version=$1
override_file="overrides.yaml"

echo "Kicking off the chart..."
if [ -f $override_file ]; then
  helm install -f $override_file "$release_version" ./ec-cts-ibmis
else
  helm install "$release_version" ./ec-cts-ibmis
fi

echo "Capturing the names of the pods of interest..."
pod_report=$(kubectl get pods | grep "${release_version}"-init-and-report | awk '{ print $1 }')
echo " ... report pod: $pod_report"
pod_actual=$(kubectl get pods | grep "${release_version}"-ibm-igc | awk '{ print $1 }')
echo " ... actual pod: $pod_actual"

echo "Monitoring the reporting pod until we see completion..."
report_status="None"
while [ "$report_status" != "Complete -- CTS results available to download from /tmp/$release_version.tar.gz" ]; do
  echo " ... found status $report_status -- waiting ..."
  sleep 120
  report_status=$(kubectl logs --tail=1 "${pod_report}")
done

echo "Detailing runtime and reports into /tmp/$release_version..."
kubectl cp "$pod_report":/tmp/"$release_version".tar.gz /tmp/"$release_version".tar.gz
kubectl describe pod "$pod_actual" > /tmp/"$release_version".deployment
kubectl get configmap "$release_version"-configmap -o yaml > /tmp/"$release_version".configmap

echo "Deleting completed helm chart..."
helm delete "$release_version"
exit
