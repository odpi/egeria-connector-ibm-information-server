<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![javadoc](https://javadoc.io/badge2/org.odpi.egeria/ibm-ia-rest-client-library/javadoc.svg)](https://javadoc.io/doc/org.odpi.egeria/ibm-ia-rest-client-library) [![Maven Central](https://img.shields.io/maven-central/v/org.odpi.egeria/ibm-ia-rest-client-library)](https://mvnrepository.com/artifact/org.odpi.egeria/ibm-ia-rest-client-library)

# IBM IA REST Client Library

A Java client library for integrating with IBM Information Analyzer's (IA's) REST API.
(**Note**: this library is still under development, so may not yet operate fully or as you would expect.)

## Requirements

- An IBM Information Server (including IA) environment
- User credentials to access the REST API of that environment

## Usage

### Connecting

The `IARestClient` class provides the entry point to creating a connection to IA:

```java
import org.odpi.egeria.connectors.ibm.ia.clientlibrary.IARestClient;

IARestClient iarest = new IARestClient("myenv.myhost.com", "9446", "isadmin", "password");
```

Creating an IARestClient object will connect to the environment, open and retain the cookies for a session.

To cleanly disconnect, simply call the `disconnect()` method on the client:

### Disconnecting

```java
iarest.disconnect();
```

This will close the active session and logout from the REST API.

## Using the library

This library is still under development -- further instructions to follow...

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
