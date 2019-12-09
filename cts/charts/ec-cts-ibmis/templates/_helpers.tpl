{{/* <!-- SPDX-License-Identifier: Apache-2.0 --> */}}
{{/* Copyright Contributors to the Egeria project. */}}{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "myapp.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "myapp.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create release name without any dots.
*/}}
{{- define "myapp.release" -}}
{{- printf "%s" .Release.Name | replace "." "" | trunc 63 | trimSuffix "-" -}}
{{- end -}}
