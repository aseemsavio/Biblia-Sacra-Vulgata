# ⛪️  Biblia Sacra Vulgata

Open API for the Latin Vulgate, its English Translation - Catholic Public Domain Version [CPDV] and study notes.

### 👉🏻 Contents
1. [API Documentation](#api-documentation-v10)
2. [Local Setup](#building)

---

# API Documentation v1.0

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

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `testament` | String   | Yes      | Code Name of the testament. | NT        |


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
## Get all the Books

> Version 1.0

Gives info on all the books in the Bible. Only these names will be accepted
by the API.

```plaintext
GET /books
```

Example request:

```shell
curl "https://example.com/api/v1/books"
```

Example response:

```json
[
  {
    "testament": "OT",
    "bookNames": [
      "Genesis",
      "Exodus",
      "Leviticus",
      "Numbers",
      "Deuteronomy",
      "Joshua",
      "Judges",
      "Ruth",
      "1-Samuel",
      "2-Samuel",
      "1-Kings",
      "2-Kings",
      "1-Chronicles",
      "2-Chronicles",
      "Ezra",
      "Nehemiah",
      "Tobit",
      "Judith",
      "Esther",
      "Job",
      "Psalms",
      "Proverbs",
      "Ecclesiastes",
      "Song",
      "Wisdom",
      "Sirach",
      "Isaiah",
      "Jeremiah",
      "Lamentations",
      "Baruch",
      "Ezekiel",
      "Daniel",
      "Hosea",
      "Joel",
      "Amos",
      "Obadiah",
      "Jonah",
      "Micah",
      "Nahum",
      "Habakkuk",
      "Zephaniah",
      "Haggai",
      "Zechariah",
      "Malachi",
      "1-Maccabees",
      "2-Maccabees"
    ]
  },
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
## Chapter Count in a Book

> Version 1.0

Gives the chapter count in a book. This route will be deprecated in the future
and will be replaced by an `info` route that provides metadata on the book.

```plaintext
GET /testament/:testament/book/:book/chapterCount
```

Supported attributes:

| Attribute   | Type     | Required | Description                  |Example    |
|:------------|:---------|:---------|:-----------------------------|:----------|
| `testament` | String   | Yes      | Code Name of the Testament.  | OT        |
| `book`      | String   | Yes      | Code Name of the Book.       | Sirach    |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Sirach/chapterCount"
```

Example response:

```json
52
```

---

## Get all the verses in a chapter

> Version 1.0

Gives all the verses in a chapter.

```plaintext
GET /testament/:testament/book/:book/chapter/:chapter
```

Supported attributes:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `testament` | String   | Yes      | Code Name of the Testament. | OT        |
| `book`      | String   | Yes      | Code Name of the Book.      | Psalms    |
| `chapter`   | Int      | Yes      | Chapter Number.             | 116       |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Psalms/chapter/116"
```

Example response:

```json
[
  {
    "chapter": 116,
    "verse": 1,
    "textEn": "Alleluia. All nations, praise the Lord. All peoples, praise him.",
    "textLa": "Alleluia. Laudate Dominum omnes Gentes: laudate eum omnes populi:"
  },
  {
    "chapter": 116,
    "verse": 2,
    "textEn": "For his mercy has been confirmed over us. And the truth of the Lord remains for all eternity.",
    "textLa": "Quoniam confirmata est super nos misericordia eius: et veritas Domini manet in æternum."
  }
]
```
Note that the chapter 117 is 116 here (in the Latin Vulgate version). Read more about why [here](https://bible.usccb.org/bible/psalms/0).

---

## Verse Count in a Chapter

> Version 1.0

Gives the verse count in a chapter.

```plaintext
GET /testament/:testament/book/:book/chapter/:chapter/verseCount
```

Supported attributes:

| Attribute   | Type     | Required | Description                  |Example    |
|:------------|:---------|:---------|:-----------------------------|:----------|
| `testament` | String   | Yes      | Code Name of the Testament.  | OT        |
| `book`      | String   | Yes      | Code Name of the Book.       | Sirach    |
| `chapter`   | Int      | Yes      | Chapter Number.              | Sirach    |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Sirach/chapter/12/verseCount"
```

Example response:

```json
19
```

---

## Get a specific Verse

> Version 1.0

Gives a specific verse.

```plaintext
GET /testament/:testament/book/:book/chapter/:chapter/verse/:verse
```

Supported attributes:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `testament` | String   | Yes      | Code Name of the Testament. | OT        |
| `book`      | String   | Yes      | Code Name of the Book.      | Psalms    |
| `chapter`   | Int      | Yes      | Chapter Number.             | 116       |
| `verse`     | Int      | Yes      | Verse Number.               | 2         |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Psalms/chapter/116/verse/2"
```

Example response:

```json
{
  "chapter": 116,
  "verse": 2,
  "textEn": "For his mercy has been confirmed over us. And the truth of the Lord remains for all eternity.",
  "textLa": "Quoniam confirmata est super nos misericordia eius: et veritas Domini manet in æternum."
}
```

---

## Get Verses within a range

> Version 1.0

Gives the verses between two verse numbers.

```plaintext
GET /testament/:testament/book/:book/chapter/:chapter/verses/from/:from/to/:to
```

Supported attributes:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `testament` | String   | Yes      | Code Name of the Testament. | OT        |
| `book`      | String   | Yes      | Code Name of the Book.      | Psalms    |
| `chapter`   | Int      | Yes      | Chapter Number.             | 117       |
| `from`      | Int      | Yes      | Verse Number.               | 2         |
| `to`        | Int      | Yes      | Verse Number.               | 8         |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Psalms/chapter/117/verses/from/2/to/8"
```

Example response:

```json
[
  {
    "chapter": 117,
    "verse": 2,
    "textEn": "Let Israel now say: For he is good, for his mercy is forever.",
    "textLa": "Dicat nunc Israel quoniam bonus: quoniam in sæculum misericordia eius."
  },
  {
    "chapter": 117,
    "verse": 3,
    "textEn": "Let the house of Aaron now say: For his mercy is forever.",
    "textLa": "Dicat nunc domus Aaron: quoniam in sæculum misericordia eius."
  },
  {
    "chapter": 117,
    "verse": 4,
    "textEn": "Let those who fear the Lord now say: For his mercy is forever.",
    "textLa": "Dicant nunc qui timent Dominum: quoniam in sæculum misericordia eius."
  },
  {
    "chapter": 117,
    "verse": 5,
    "textEn": "In my tribulation, I called upon the Lord. And the Lord heeded me with generosity.",
    "textLa": "De tribulatione invocavi Dominum: et exaudivit me in latitudine Dominus."
  },
  {
    "chapter": 117,
    "verse": 6,
    "textEn": "The Lord is my helper. I will not fear what man can do to me.",
    "textLa": "Dominus mihi adiutor: non timebo quid faciat mihi homo."
  },
  {
    "chapter": 117,
    "verse": 7,
    "textEn": "The Lord is my helper. And I will look down upon my enemies.",
    "textLa": "Dominus mihi adiutor: et ego despiciam inimicos meos."
  },
  {
    "chapter": 117,
    "verse": 8,
    "textEn": "It is good to trust in the Lord, rather than to trust in man.",
    "textLa": "Bonum est confidere in Domino, quam confidere in homine:"
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
