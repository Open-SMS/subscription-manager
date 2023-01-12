# Coding Standards

Code style standards are enforced by [Checkstyle](https://checkstyle.sourceforge.io/). The standards for this project
are defined in `sms-checkstyle.xml` and are based on `sun_checks.xml` configuration shipped with Checkstyle.

The `checkstyle:check` goal is bound to the `verify` phase. The build will fail if any checkstyle violations occur. To
obtain details about Checkstyle violations execute the `checkstyle:checkstyle` goal. Note, I would like this to
generate a report, but the `site` phase seems to currently fail because of the `checkstyle:check` goal bound to the
`verify` phase.
