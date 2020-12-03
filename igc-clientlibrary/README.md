<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![javadoc](https://javadoc.io/badge2/org.odpi.egeria/ibm-igc-rest-client-library/javadoc.svg)](https://javadoc.io/doc/org.odpi.egeria/ibm-igc-rest-client-library) [![Maven Central](https://img.shields.io/maven-central/v/org.odpi.egeria/ibm-igc-rest-client-library)](https://mvnrepository.com/artifact/org.odpi.egeria/ibm-igc-rest-client-library)

# IBM IGC REST Client Library

A Java client library for integrating with IBM Information Governance Catalog's (IGC's) REST API.

## Requirements

- An IBM Information Server (including IGC) environment
- User credentials to access the REST API of that environment

## Usage

### Connecting

The `IGCRestClient` class provides the entry point to creating a connection to IGC:

```java
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;

IGCRestClient igcrest = new IGCRestClient("myenv.myhost.com", "9446", "isadmin", "password");
```

Creating an IGCRestClient object will connect to the environment and retrieve basic information,
such as whether the workflow is enabled or not in the environment, as well as opening and retaining
the cookies for a session.

To cleanly disconnect, simply call the `disconnect()` method on the client:

### Disconnecting

```java
igcrest.disconnect();
```

This will close the active session and logout from the REST API.

### Retrieving assets

Assets can be retrieved using an Object-based (POJO-based) approach.

To do so, ensure you have imported the POJO models:

```java
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
```

IGC metadata objects all inherit from the `Reference` super-type, so they can all be retrieved using
functions that generally return `Reference` objects; and these `Reference` objects can be explicitly
cast to their more descriptive type (eg. `Term` or `Category`), as needed.

You can also dynamically retrieve properties (ie. using a variable as the name of the property) by
using the `getPropertyByName` method available in the client:

```java
Reference something = igcrest.getAssetById(rid);
String propertyNameFromSomewhere = "short_description";
if (something.isSimpleType(propertyNameFromSomewhere)) {
    Object value = igcrest.getPropertyByName(something, propertyNameFromSomewhere);
    System.out.println("Simple property '" + propertyNameFromSomewhere + "' = " + value);
} else if (something.isReference(propertyNameFromSomewhere)) {
    Reference value = (Reference) igcrest.getPropertyByName(something, propertyNameFromSomewhere);
    System.out.println("Property was a relationship to: " + value.getId());
} else if (something.isItemList(propertyNameFromSomewhere)) {
    ItemList<Reference> value = (ItemList<Reference>) igcrest.getPropertyByName(something, propertyNameFromSomewhere);
    System.out.println("Property was a list of " + value.getPaging().getNumTotal() + " total relationships.");
}
```

If the property does not exist, you'll simply receive back a `null` (and a stacktrace will be dumped
in the background). (So you'd want to add null handling to the above simplified example.)

### Searching for assets

Additional classes have been provided to help simplify searching against IGC as well.

```java
IGCSearchCondition cond1 = new IGCSearchCondition("name", "=", "Street Number");
IGCSearchCondition cond2 = new IGCSearchCondition("name", "=", "City");
IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(cond1);
igcSearchConditionSet.addCondition(cond2);
igcSearchConditionSet.setMatchAnyCondition(true);
IGCSearch igcSearch = new IGCSearch("term", igcSearchConditionSet);
ItemList<Term> searchResult = igcrest.search(igcSearch);
```

> In this example, a search retrieves any terms whose name is either `Street Number` or `City`

## Included asset types

The client includes POJOs for all asset types (with their properties as class members) that are
understood by a vanilla IGC environment. These are generated, and included in the package
`org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base`. To avoid the need for version-specific
beans, these contain a super-set of all available properties starting from v11.5.0.2 through to the
current release of IGC.

The following POJOs define common characteristics across objects for ease of re-use and generic
representation, but are not themselves asset types:

- `Reference` defines the most minimalistic representation of an IGC asset, and is used anywhere an
    asset is referenced (eg. for relationships). It is the superclass of all asset types.
- `ItemList<>` encapsulates a set of relationships (`Reference` objects) and their paging
    characteristics (`Paging` object). It is generic-type-restricted within `Reference` so you can
    always be sure that an `ItemList` will at least be an `ItemList<Reference>` though you may also
    have a more precise type like `ItemList<Term>` as in the search example above.
- `Paging` encapsulates the details of a page of results (eg. the total number, the URL to the next
    page of results, etc).
- `Identity` provides a semantically-meaningful characteristic that can be used for comparison between
    assets for equality, without relying on ID-level (RID) equivalency or having the asset hosted in
    the same instance of IGC.

## Using your own asset types

If your environment includes new objects (ie. via OpenIGC) or adds custom attributes to the native IGC
asset types, you will likely want to make use of your own asset types.

The recommended way to do this is to create your own POJOs:

- For OpenIGC assets, create a new class that extends from `InformationAsset` for each class in your
    OpenIGC bundle, and add each of your classes' properties as members to each of those POJOs.
    Also add a `@JsonTypeName("...")` annotation set to the precise type string that IGC uses to
    refer to assets of this type. (See the
    [OMRSStub](../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/model/OMRSStub.java)
    POJO for an example.)
- For native asset types against which you've defined custom attributes, you'll need to either
    extend or replace the existing (base) POJO by adding the custom attribute properties to it.
    For any custom attributes of IGC type `relationship` use a Java type of `ItemList<>`, for any
    multi-valued custom attributes use a Java type of `List<String>`, and for any singular values
    simply use the appropriate type (eg. `String`, `Number`, `Date`, or `Boolean`).

Alternatively, you could simply re-run the
[org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.IGCBeanGenerator]()
against your environment to re-generate the POJOs for that version, including any OpenIGC objects and
custom attributes. (Just remember to re-build the module after doing so to compile those re-generated
classes.)

**Important**: Note that you'll need to register your own POJO _before_ attempting any retrievals of
any assets through the client, or you will most likely see various `ClassCastExceptions`! (This is
assuming that you create your own classes rather than modifying or re-generating the (base) classes:
in that case the sub-typing needed for Jackson should already be handled in annotations and you
should not need to do this registration.)

Therefore, before doing any operations against IGC through the client, first register your POJO using:

```java
@JsonTypeName("$my_custom_object")
public class MyCustomObject extends InformationAsset {
    // ...
}
```

```java
igcrest.registerPOJO(MyCustomObject.class);
```

> In this example, we register an OpenIGC object named `$my_custom_object` to be handled by the `MyCustomObject.class` POJO (the IGC name is picked up automatically from the `@JsonTypeName` annotation)

This registration of POJOs **must** occur before any usage of the other methods to actually retrieve
and process metadata.

Once the type is registered, metadata assets of that type can then be retrieved using functions that
generally return `Reference` objects; and these `Reference` objects can be explicitly cast to their
more descriptive type (eg. `(MyCustomObject)` in our example), as needed.

For more information on the recommended approach to applying these extensions in the broader context of the Egeria
connector, see [Extending the connector](../docs/extending/README.md).

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
