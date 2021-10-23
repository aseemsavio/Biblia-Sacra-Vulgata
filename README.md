# ⛪️  Biblia Sacra Vulgata

Open API for the Latin Vulgate, its English Translation - Catholic Public Domain Version [CPDV] and study notes.

### 👉🏻 Contents
1. [API Documentation](#api-documentation)
2. [Local Setup](#building)

---

# API Documentation

## Testaments

> Version 1.0

Gives the name-codes of the testaments in the Bible. Only these will be accepted by the API
through the rest of the end points.

```plaintext
GET /testaments
```

Example request:

```shell
curl "https://example.com/api/v1/testaments"
```

Example response:

```json
[
  "OT",
  "NT"
]
```

---
## Book Names in a Testament

> Version 1.0

Gives info on the books in the given testament. Only these names will be accepted
by the API.

```plaintext
GET /testament/:testament/books
```

Supported attributes:

| Attribute   | Type     | Required | Description           |
|:------------|:---------|:---------|:----------------------|
| `testament` | String | Yes | Code Name of the testament. |


Example request:

```shell
curl "https://example.com/api/v1/testament/NT/books"
```

Example response:

```json
[
  {
    "testament": "NT",
    "bookNames": [
      "Matthew",
      "Mark",
      "Luke",
      "John",
      "Acts",
      "Romans",
      "1-Corinthians",
      "2-Corinthians",
      "Galatians",
      "Ephesians",
      "Philippians",
      "Colossians",
      "1-Thessalonians",
      "2-Thessalonians",
      "1-Timothy",
      "2-Timothy",
      "Titus",
      "Philemon",
      "Hebrews",
      "James",
      "1-Peter",
      "2-Peter",
      "1-John",
      "2-John",
      "3-John",
      "Jude",
      "Revelation"
    ]
  }
]
```

---

### Building

To launch your tests:
```
./gradlew clean test
```

To package your application:
```
./gradlew clean assemble
```

To run your application:
```
./gradlew clean run
```
