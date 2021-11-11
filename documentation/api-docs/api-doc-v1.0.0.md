# ⛪️  Biblia Sacra Vulgata

---

## API Documentation - v1.0.0

All the routes below can be accompanied by the optional query param - `version` to query
a certain _supported_ version of the Scriptures.
If not given, the version will be defaulted to the **Vulgate**. 

Example:

```shell
curl "https://example.com/api/v1/testaments?version=Vulgate"
```
The open source version of this application supports two versions namely, Vulgate and CPDV (Catholic Public Domain Version).

> Be informed that the CPDV or the Vulgate cannot be utilised in the Sacred Liturgy
due to a slightly deviant numbering system in certain books.

## Testaments

> Version 1.0.0

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

> Version 1.0.0

Gives info on the books in the given testament. Only these names will be accepted
by the API.

```plaintext
GET /testament/:testament/books
```

Supported attributes:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `testament` | String   | Yes      | Code Name of the testament. | NT        |

Supported Query Params:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `version`   | String   | No       | Bible Version               | Vulgate   |

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

> Version 1.0.0

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

> Version 1.0.0

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

Supported Query Params:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `version`   | String   | No       | Bible Version               | CPDV      |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Sirach/chapterCount?version=CPDV"
```

Example response:

```json
52
```

---

## Get all the verses in a chapter

> Version 1.0.0

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

Supported Query Params:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `version`   | String   | No       | Bible Version               | CPDV      |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Psalms/chapter/116?version=CPDV"
```

Example response:

```json
[
  {
    "chapter": 116,
    "verse": 1,
    "text": "Alleluia. All nations, praise the Lord. All peoples, praise him."
  },
  {
    "chapter": 116,
    "verse": 2,
    "text": "For his mercy has been confirmed over us. And the truth of the Lord remains for all eternity."
  }
]
```
Note that the chapter 117 is 116 here (in the Latin Vulgate version). Read more about why [here](https://bible.usccb.org/bible/psalms/0).

---

## Verse Count in a Chapter

> Version 1.0.0

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

Supported Query Params:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `version`   | String   | No       | Bible Version               | Vulgate   |

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

> Version 1.0.0

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

Supported Query Params:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `version`   | String   | No       | Bible Version               | Vulgate   |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Psalms/chapter/116/verse/2"
```

Example response:

```json
{
  "chapter": 116,
  "verse": 2,
  "text": "Quoniam confirmata est super nos misericordia eius: et veritas Domini manet in æternum."
}
```

---

## Get Verses within a range

> Version 1.0.0

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

Supported Query Params:

| Attribute   | Type     | Required | Description                 | Example   |
|:------------|:---------|:---------|:----------------------------|:----------|
| `version`   | String   | No       | Bible Version               | CPDV      |

Example request:

```shell
curl "https://example.com/api/v1/testament/OT/book/Psalms/chapter/117/verses/from/2/to/8?version=CPDV"
```

Example response:

```json
[
  {
    "chapter": 117,
    "verse": 2,
    "text": "Let Israel now say: For he is good, for his mercy is forever."
  },
  {
    "chapter": 117,
    "verse": 3,
    "text": "Let the house of Aaron now say: For his mercy is forever."
  },
  {
    "chapter": 117,
    "verse": 4,
    "text": "Let those who fear the Lord now say: For his mercy is forever."
  },
  {
    "chapter": 117,
    "verse": 5,
    "text": "In my tribulation, I called upon the Lord. And the Lord heeded me with generosity."
  },
  {
    "chapter": 117,
    "verse": 6,
    "text": "The Lord is my helper. I will not fear what man can do to me."
  },
  {
    "chapter": 117,
    "verse": 7,
    "text": "The Lord is my helper. And I will look down upon my enemies."
  },
  {
    "chapter": 117,
    "verse": 8,
    "text": "It is good to trust in the Lord, rather than to trust in man."
  }
]
```

---

