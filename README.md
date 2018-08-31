### Sample application to reproduce issue gini/dropwizard-gelf#27


This contains two applications:

* com.github.madmuffin1.WorkingApplication which is a fully working application

* com.github.madmuffin1.RequestLoggingApplication which inherits from the former and only adds dropwizard-gelf

Both applications provide two enpoints

`GET /test/blocking`

and

`GET /test/async`.

The latter fails in the application which has dropwizard-gelf enabled.
