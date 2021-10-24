# ⛪️  Biblia Sacra Vulgata - v1.0.0

![](src/resources/images/catholic.jpg)

Biblia Sacra Vulgata is an [Apache 2.0](LICENSE) licenced, open-sourced API project primarily serving the
_Original Latin Vulgate_ ( ✝️ Biblia Sacra Vulgata) and its English Translation -
Catholic Public Domain Version (CPDV) with study notes.
In addition to the Vulgate, it will also support the other versions and/or translations of the Scriptures approved by the Church in the future.

This API does not employ a backing database, in lieu, uses an in-house built in-memory data store,
which allows for constant time look-ups. In other words, the user get lightning fast replies 💨.

This API is designed to be lenient (or forgiving 🥺, if you will) with the requests, given the nature of the content served by the application.
For example, If the user requests verses 2 through 30 from a chapter with only 20 verses,
the application would respond with the available information (verses 2 through 20) rather than throwing an error. This is a
conscious design decision and contributors are solicited to be mindful of it.

> **Caution:** This application is not production-ready yet. Check out the releases tab for the first release.

### 👉🏻 Contents
1. [REST API Documentation](documentation/api-docs/api-doc-v1.0.0.md)
2. [Running the application locally](#running-the-app-locally)

---

### Running the app locally

The official version of the app is shipped as docker images 🐟. However, if you wish to contribute or
tweak the app for personal use, you may proceed with the following steps.

#### Prerequisites:

1. ☕️ **Java** v11.0 or above
2. **Gradle** (optional) - If you do not have Gradle installed, you could possibly get away with
the included Gradle Wrapper.
3. 💡 **IntelliJ Idea** (optional) - I personally prefer this IDE for its incredible support for building
JVM applications. But, hey, your preferred IDE would do just fine too! 😊

After cloning the project, here are some gradle scripts to trigger
some customary functionalities.

#### To launch your tests:
```
./gradlew clean test
```

#### To package your application:
```
./gradlew clean assemble
```

#### To run your application:
```
./gradlew clean run
```

---
**Disclaimer:** Some parts of the CPDV or the Latin Vulgate may look different
from the versions used in the Church today (verse number, and such. You can read about it [here](https://bible.usccb.org/bible/psalms/0)). Hence, it is adviced to use the Vulgate
for study and the newer translations (which, by the way will become available in this application soon!! 🙌🏼 🎉 🎊) in/for the Divine Liturgy.

To contribute, feel free to fork and create PRs! 😇
