<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Developer notes

Following are important notes for developers wishing to extend this connector.

## JavaDoc generation

The JavaDoc comments in the codebase make use of UTF-8 characters to represent different languages, and therefore when
generating the JavaDocs it is important to make use of these additional command-line parameters:

```
-encoding utf8 -docencoding utf8 -charset utf8
```

In IntelliJ, for example, these can be entered into the "Other command line arguments:" box in the pop-up dialogue when
generating the JavaDocs.


----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
