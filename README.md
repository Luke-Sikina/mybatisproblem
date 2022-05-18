# MyBatis Problem

This is a simple example repo I'm using to demonstrate a problem I'm having
with MyBatis. If you run the integration test in the repo, it will break due
to the following error:

```
java.lang.reflect.InaccessibleObjectException: Unable to make public final java.util.stream.Stream java.util.stream.ReferencePipeline.distinct() accessible
```

I think this is due to the finalization of the effort to encapsulate JRE internals.
